package com.compras.handler;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.compras.model.dto.ProductosRequest;
import com.compras.service.ProductosService;

import reactor.core.publisher.Mono;

@Component
public class ProductosHandler {

  @Autowired
  private ProductosService productosService;

  public Mono<ServerResponse> listar(ServerRequest request) {
    String nombre = request.queryParam("nombre").orElse("");
    Double precioDesde = request.queryParam("precioDesde").map(Double::parseDouble).orElse(0.0);
    Double precioHasta = request.queryParam("precioHasta").map(Double::parseDouble).orElse(Double.MAX_VALUE);
    String marca = request.queryParam("marca").orElse("");
    Integer page = request.queryParam("page").map(Integer::parseInt).orElse(0);
    Integer size = request.queryParam("size").map(Integer::parseInt).orElse(100);
    return productosService.listarProductos(nombre, precioDesde, precioHasta, marca, page, size)
        .flatMap(p -> ServerResponse.ok().contentType(APPLICATION_JSON).bodyValue(p).switchIfEmpty(ServerResponse.notFound().build()));
  }

  public Mono<ServerResponse> agregarProductoCarritoCompras(ServerRequest request) {
    Mono<ProductosRequest> producto = request.bodyToMono(ProductosRequest.class);
    return productosService.agregarProductoCarritoCompras(producto);
  }
  
  public Mono<ServerResponse> listarProductosAgregadosCarrito(ServerRequest request){
    Integer idCliente = request.queryParam("idCliente").map(Integer::parseInt).orElse(0);
    return productosService.listarProductosCarritoCompras(idCliente)
        .flatMap(p -> ServerResponse.ok().contentType(APPLICATION_JSON).bodyValue(p).switchIfEmpty(ServerResponse.notFound().build()));
  }


}
