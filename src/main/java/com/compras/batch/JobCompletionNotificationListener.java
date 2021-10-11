package com.compras.batch;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.compras.model.entities.Productos;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public void afterJob(JobExecution jobExecution) {
    if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
      log.info("!!! TRABAJO TERMINADO! Es hora de verificar los resultados");

      jdbcTemplate.query("SELECT nombre, marca, precio, cantidad_stock, estado, porcentaje_descuento FROM productos",
        (rs, row) -> new Productos(
          rs.getString(1),
          rs.getString(2),
          rs.getDouble(3),
          rs.getInt(4),
          rs.getString(5),
          rs.getDouble(6))
      ).forEach(producto -> log.info("Encontrado <" + producto + "> en la base de datos."));
    }
  }
}
