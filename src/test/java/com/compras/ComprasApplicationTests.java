package com.compras;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.assertj.core.api.Assertions;

import com.compras.model.entities.Productos;
import com.compras.repository.ProductosRepository;

@AutoConfigureWebTestClient
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class ComprasApplicationTests {
  
  @Autowired
  private ProductosRepository repository;
  /**
   * Prueba unitaria para varificar que haya datos 
   */
	@Test
	void testData() {
	  Productos productos = repository.findById(1L).get();
	  Assertions.assertThat(productos.getNombre()).isEqualTo("Htc One M7 Negro");
	}

}
