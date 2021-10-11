package com.compras.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;

import com.compras.handler.CarritoHandler;
import com.compras.handler.ClienteHandler;
import com.compras.handler.ProductosHandler;
import com.compras.service.impl.CarritoServiceImpl;
import com.compras.service.impl.ProductosServiceImpl;

import static org.springdoc.core.fn.builders.apiresponse.Builder.responseBuilder;
import static org.springdoc.core.fn.builders.parameter.Builder.parameterBuilder;
import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;
@Configuration
public class RouterConfig {
  @Bean
  public RouterFunction<?> rutas(ProductosHandler productoHandler, ClienteHandler clienteHandler, CarritoHandler carritoHandler){
    return route().GET("/api/listarProductos", productoHandler::listar, ops -> ops
        .operationId("listarProductos")
        .parameter(parameterBuilder().name("nombre").description("Nombre del producto").example("Iphone"))
        .parameter(parameterBuilder().name("precioDesde").description("Rango de precio desde (minimo)").example("1000000"))
        .parameter(parameterBuilder().name("precioHasta").description("Rango de precio hasta (maximo)").example("5000000.0"))
        .parameter(parameterBuilder().name("marca").description("Marca del producto").example("Apple"))
        .parameter(parameterBuilder().name("page").description("Numero de página (paginacion)").example("0"))
        .parameter(parameterBuilder().name("size").description("Cantidad de registros por página").example("5"))
        .response(responseBuilder().responseCode("200").description("La solicitud ha tenido éxito."))
        .response(responseBuilder().responseCode("404").description("El servidor no pudo encontrar el contenido solicitado."))
    ).build()
    .and(route().POST("/api/agregarProductoCarrito", productoHandler::agregarProductoCarritoCompras,
        ops -> ops.beanClass(ProductosServiceImpl.class).beanMethod("agregarProductoCarritoCompras")).build())
    .and(route().GET("/api/listarClientes", clienteHandler::listar, ops -> ops
        .operationId("listarClientes")).build())
    .and(route().GET("/api/listarProductosAgregadosCarrito", productoHandler::listarProductosAgregadosCarrito, ops -> ops
        .operationId("listarProductosAgregadosCarrito")
        .parameter(parameterBuilder().name("idCliente").description("Id del cliente al que se le va consultar los productos del carrito").example("1"))
        ).build())
    .and(route().GET("/api/listarProductosAgregadosCarrito", productoHandler::listarProductosAgregadosCarrito, ops -> ops
        .operationId("listarProductosAgregadosCarrito")
        .parameter(parameterBuilder().name("idCliente").description("Id del cliente al que se le va consultar los productos del carrito").example("1"))
        ).build())
    .and(route().GET("/api/vaciarCarrito", carritoHandler::vaciar, ops -> ops
        .operationId("vaciarCarrito")
        .parameter(parameterBuilder().name("idCliente").description("Id del cliente al que se le va dejar valicio el carrito").example("1"))
        ).build())
    .and(route().PATCH("/api/finalizarCompra", carritoHandler::finalizarCompra,
        ops -> ops.beanClass(CarritoServiceImpl.class).beanMethod("finalizarCompra")).build());
  }
}
