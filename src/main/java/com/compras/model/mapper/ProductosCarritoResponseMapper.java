package com.compras.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.compras.model.dto.ProductosCarritoResponse;
import com.compras.model.entities.Carrito;

@Mapper(componentModel = "spring")
public interface ProductosCarritoResponseMapper {
  
  @Mappings({
    @Mapping(source="productos.id", target="id"),
    @Mapping(source="productos.nombre", target="nombre"),
    @Mapping(source="productos.marca", target="marca"),
    @Mapping(source="productos.precio", target="precio"),
    @Mapping(source="cantidadCompra", target="cantidadCompra"),
    @Mapping(source="productos.porcentajeDescuento", target="porcentajeDescuento"),
    @Mapping(source="productos.estado", target="estado")
  })
  ProductosCarritoResponse carritoToProductosCarritoResponse(Carrito carrito);
  
  List<ProductosCarritoResponse> carritoToProductosCarritoResponseList(List<Carrito> carritos);
}
