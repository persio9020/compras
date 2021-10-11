package com.compras.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.compras.model.dto.CompraFinalRequest;
import com.compras.service.CarritoService;

import reactor.core.publisher.Mono;

@Component
public class CarritoHandler {
  
  @Autowired
  private CarritoService carritoService;
  
  public Mono<ServerResponse> vaciar(ServerRequest request){
    Integer idCliente = request.queryParam("idCliente").map(Integer::parseInt).orElse(null);
    return carritoService.vaciar(idCliente);
  }
  
  public Mono<ServerResponse> finalizarCompra(ServerRequest request){
    Mono<CompraFinalRequest> compra = request.bodyToMono(CompraFinalRequest.class);
    return carritoService.finalizarCompra(compra);
  }
}
