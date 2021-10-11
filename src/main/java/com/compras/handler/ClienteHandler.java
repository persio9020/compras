package com.compras.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.compras.service.ClienteService;

import reactor.core.publisher.Mono;

@Component
public class ClienteHandler {
  @Autowired
  private ClienteService clienteService;
  
  public Mono<ServerResponse> listar(ServerRequest request) {
    return clienteService.listarTodos();
  }
}
