package com.compras.service;

import org.springframework.web.reactive.function.server.ServerResponse;

import com.compras.model.dto.CompraFinalRequest;

import reactor.core.publisher.Mono;

public interface CarritoService {
  
  public Mono<ServerResponse> vaciar(Integer idCliente);
  
  public Mono<ServerResponse> finalizarCompra(Mono<CompraFinalRequest> compra);
}
