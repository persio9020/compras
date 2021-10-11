package com.compras.config;
import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import com.compras.batch.JobCompletionNotificationListener;
import com.compras.model.entities.Cliente;
import com.compras.model.entities.Productos;
import com.compras.repository.ProductosRepository;
import com.compras.validations.ProductosValidator;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
  @Autowired
  public JobBuilderFactory jobBuilderFactory;

  @Autowired
  public StepBuilderFactory stepBuilderFactory;
  
  @Autowired
  private ProductosRepository productosRepository;
  
  @Value("${config.archivo.importacion.productos.csv}")
  public String recursoProductosEntrada;
  
  @Value("${config.archivo.importacion.clientes.csv}")
  public String recursoClientesEntrada;
  
  @Bean
  public FlatFileItemReader<Productos> readerProductos(){
    return new FlatFileItemReaderBuilder<Productos>()
        .name("productosItemReader")
        .linesToSkip(1)
        .resource(new ClassPathResource(recursoProductosEntrada))
        .delimited()
        .names(new String[]{"nombre", "marca", "precio", "cantidadStock", "estado", "porcentajeDescuento"})
        .fieldSetMapper(new BeanWrapperFieldSetMapper<Productos>() {{
          setTargetType(Productos.class);
        }})
        .build();
  }
  @Bean
  public FlatFileItemReader<Cliente> readerClientes(){
    return new FlatFileItemReaderBuilder<Cliente>()
        .name("clientesItemReader")
        .linesToSkip(1)
        .resource(new ClassPathResource(recursoClientesEntrada))
        .delimited()
        .names(new String[]{"id", "nombre"})
        .fieldSetMapper(new BeanWrapperFieldSetMapper<Cliente>() {{
          setTargetType(Cliente.class);
        }})
        .build();
  }
  
  @Bean
  public ItemProcessor<Productos, Productos> validatingProductoProcessor() {
    ValidatingItemProcessor<Productos> validatingItemProcessor = new ValidatingItemProcessor<>(new ProductosValidator(productosRepository));
    validatingItemProcessor.setFilter(true);
    return validatingItemProcessor;
  }

  
  @Bean
  public JdbcBatchItemWriter<Productos> writerProductos(DataSource dataSource) {
    return new JdbcBatchItemWriterBuilder<Productos>()
      .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
      .sql("INSERT INTO productos (nombre, marca, precio, cantidad_stock, estado, porcentaje_descuento) VALUES (:nombre, :marca, :precio, :cantidadStock, :estado, :porcentajeDescuento)")
      .dataSource(dataSource)
      .build();
  }
  
  @Bean
  public JdbcBatchItemWriter<Cliente> writerClientes(DataSource dataSource) {
    return new JdbcBatchItemWriterBuilder<Cliente>()
      .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
      .sql("INSERT INTO cliente (nombre) VALUES (:nombre)")
      .dataSource(dataSource)
      .build();
  }
  
  @Bean
  public Job importProductosJob(JobCompletionNotificationListener listener, Step step1) {
    return jobBuilderFactory.get("import")
      .incrementer(new RunIdIncrementer())
      .listener(listener)
      .flow(step1)
      .end()
      .build();
  }
  
  @Bean
  public Job importClientesJob(JobCompletionNotificationListener listener, Step step2) {
    return jobBuilderFactory.get("import")
      .incrementer(new RunIdIncrementer())
      .flow(step2)
      .end()
      .build();
  }

  @Bean
  public Step step1(JdbcBatchItemWriter<Productos> writerProductos) {
    return stepBuilderFactory.get("step1")
      .<Productos, Productos> chunk(10)
      .reader(readerProductos())
      .processor(validatingProductoProcessor())
      .writer(writerProductos)
      .build();
  }
  
  @Bean
  public Step step2(JdbcBatchItemWriter<Cliente> writerClientes) {
    return stepBuilderFactory.get("step2")
      .<Cliente, Cliente> chunk(10)
      .reader(readerClientes())
      .writer(writerClientes)
      .build();
  }
  
  
}
