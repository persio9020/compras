package com.compras.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;

import com.compras.model.dto.ProductosCarritoResponse;
import com.compras.model.dto.ProductosRequest;
import com.compras.model.dto.ProductosResponse;
import com.compras.model.entities.Carrito;
import com.compras.model.entities.CarritoPK;
import com.compras.model.entities.Cliente;
import com.compras.model.entities.Productos;
import com.compras.model.mapper.ProductosCarritoResponseMapper;
import com.compras.model.mapper.ProductosResponseMapper;
import com.compras.repository.CarritoRepository;
import com.compras.repository.ProductosRepository;
import com.compras.service.ProductosService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
@Slf4j
@Service
public class ProductosServiceImpl implements ProductosService {
  private ProductosResponseMapper productosResponseMapper;
  private ProductosCarritoResponseMapper productosCarritoResponseMapper;
  private ProductosRepository productosRepository;
  private CarritoRepository carritoRepository;

  @Autowired
  public ProductosServiceImpl(ProductosResponseMapper productosResponseMapper, ProductosRepository productosRepository, CarritoRepository carritoRepository, ProductosCarritoResponseMapper productosCarritoResponseMapper) {
    this.productosResponseMapper = productosResponseMapper;
    this.productosRepository = productosRepository;
    this.carritoRepository = carritoRepository;
    this.productosCarritoResponseMapper = productosCarritoResponseMapper;
  }

  @Override
  public Mono<List<ProductosResponse>> listarProductos(String nombre, Double precioDesde, Double precioHasta, String marca, Integer page,Integer size) {
    Pageable paginacion = PageRequest.of(page, size);
    List<ProductosResponse> pr = productosResponseMapper.productosToProductosResponseList(productosRepository.findByNombreContainingAndPrecioBetweenAndMarca(nombre,precioDesde,precioHasta,marca, paginacion));
    return Mono.just(pr);
  }

  @Override
  public Mono<ServerResponse> agregarProductoCarritoCompras(Mono<ProductosRequest> productoRequest) {
    return productoRequest.
        flatMap(this::validarProductoCarritoCompras).
        flatMap(this::guardarProductoCarrito)
        .onErrorResume(ResponseStatusException.class, error -> {
          log.info(error.getMessage());
          return ServerResponse.status(error.getStatus()).bodyValue(error.getMessage());
        });
  }
  
  @Override
  public Mono<ProductosRequest> validarProductoCarritoCompras(ProductosRequest productoRequest) {
    Integer cantidadStock = productosRepository.findCantidadStockById(productoRequest.getIdProducto());
    if (cantidadStock == 0) {
      Exception ex = new ResponseStatusException(HttpStatus.FORBIDDEN, "No hay productos en existencia");
      return Mono.error(ex);
    }
    if (cantidadStock < productoRequest.getCantidadCompra()) {
      Exception ex = new ResponseStatusException(HttpStatus.FORBIDDEN, "No hay productos suficientes");
      return Mono.error(ex);
    }
    return Mono.just(productoRequest);
  }
  
  
  public Mono<ServerResponse> guardarProductoCarrito(ProductosRequest productoRequest){
    Carrito carrito = new Carrito();
    carrito.setCarritoPK(new CarritoPK(productoRequest.getIdProducto(), productoRequest.getIdCliente()));
    carrito.setCantidadCompra(productoRequest.getCantidadCompra());
    carrito.setCliente(new Cliente(productoRequest.getIdCliente()));
    carrito.setProductos(new Productos(productoRequest.getIdProducto()));
    carritoRepository.save(carrito);
    return ServerResponse.ok().body(Mono.just("Producto guardado correctamente en el carrito"), String.class);
  }


  @Override
  public Mono<List<ProductosCarritoResponse>> listarProductosCarritoCompras(Integer idCliente) {
    List<ProductosCarritoResponse> carrito = productosCarritoResponseMapper.carritoToProductosCarritoResponseList(carritoRepository.findByCliente(idCliente));
    return Mono.just(carrito);
  }

  @Override
  public Mono<Void> vaciarCarritoCompras(Long idCliente) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Mono<Void> FinalizarCompra(Long idCliente) {

    return null;
  }

}
