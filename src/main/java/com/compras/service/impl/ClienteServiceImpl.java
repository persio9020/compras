package com.compras.service.impl;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.compras.repository.ClienteRepository;
import com.compras.service.ClienteService;

import reactor.core.publisher.Mono;
@Service
public class ClienteServiceImpl implements ClienteService {
  
  private ClienteRepository clienteRepository;
  
  public ClienteServiceImpl(ClienteRepository clienteRepository) {
    this.clienteRepository = clienteRepository;
  }
  @Override
  public Mono<ServerResponse> listarTodos() {
    return Mono.just(clienteRepository.findAll()).flatMap(p -> ServerResponse.ok().contentType(APPLICATION_JSON).bodyValue(p).switchIfEmpty(ServerResponse.notFound().build()));
  }

}
