package com.compras.service;

import java.util.List;

import org.springframework.web.reactive.function.server.ServerResponse;

import com.compras.model.dto.ProductosCarritoResponse;
import com.compras.model.dto.ProductosRequest;
import com.compras.model.dto.ProductosResponse;

import reactor.core.publisher.Mono;

public interface ProductosService {
  
  public Mono<List<ProductosResponse>> listarProductos(String nombre, Double precioDesde, Double precioHasta, String marca, Integer page,Integer size);
  
  public Mono<ServerResponse> agregarProductoCarritoCompras(Mono<ProductosRequest> productoRequest);
  
  public Mono<ProductosRequest> validarProductoCarritoCompras(ProductosRequest productoRequest);
  
  public Mono<List<ProductosCarritoResponse>> listarProductosCarritoCompras(Integer idCliente);
  
  public Mono<Void> vaciarCarritoCompras(Long idCliente);
  
  public Mono<Void> FinalizarCompra(Long idCliente);
  
  
}
