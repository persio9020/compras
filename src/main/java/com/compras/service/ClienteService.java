package com.compras.service;

import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;


public interface ClienteService {
  public Mono<ServerResponse> listarTodos();
}
