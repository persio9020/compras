package com.compras.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;

import com.compras.model.dto.CompraFinalRequest;
import com.compras.model.entities.Carrito;
import com.compras.model.entities.Productos;
import com.compras.repository.CarritoRepository;
import com.compras.repository.ProductosRepository;
import com.compras.service.CarritoService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
@Slf4j
@Service
public class CarritoServiceImpl implements CarritoService {
  
  private CarritoRepository carritoRepository;
  private ProductosRepository productosRepository;
  @Autowired
  public CarritoServiceImpl(CarritoRepository carritoRepository, ProductosRepository productosRepository) {
    this.carritoRepository = carritoRepository;
    this.productosRepository = productosRepository;
  }
  
  @Override
  public Mono<ServerResponse> vaciar(Integer idCliente) {
    return Mono.just(carritoRepository.deleteCarritoByCliente(idCliente))
    .flatMap(p->ServerResponse.ok().body(Mono.just("Se vacio correctamente el carrito"), String.class))
    .onErrorResume(ResponseStatusException.class, error -> {
      log.info(error.getMessage());
      return ServerResponse.status(error.getStatus()).bodyValue(error.getMessage());
    });
  }

  @Override
  @Transactional
  public Mono<ServerResponse> finalizarCompra(Mono<CompraFinalRequest> compra) {
    return compra.flatMap(com->{
      List<Carrito> compras = this.carritoRepository.findByCliente(com.getIdCliente());
      for(Carrito c : compras){
        Productos consultaProd = productosRepository.findById(c.getCarritoPK().getIdProductos()).get();
        Integer cantidad = consultaProd.getCantidadStock()-c.getCantidadCompra();
        consultaProd.setCantidadStock(cantidad);
        productosRepository.saveAndFlush(consultaProd);
      }
      this.vaciar(com.getIdCliente());
      return ServerResponse.ok().body(Mono.just("Se finalizo la cmpra correctamente."), String.class);
    }).onErrorResume(ResponseStatusException.class, error -> {
      log.info(error.getMessage());
      return ServerResponse.status(error.getStatus()).bodyValue(error.getMessage());
    });

  }

}
