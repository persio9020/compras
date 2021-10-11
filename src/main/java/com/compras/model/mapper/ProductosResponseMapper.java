package com.compras.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.compras.model.dto.ProductosResponse;
import com.compras.model.entities.Productos;

@Mapper(componentModel = "spring")
public interface ProductosResponseMapper {
  
  @Mappings({
    @Mapping(source="id", target="id"),
    @Mapping(source="nombre", target="nombre"),
    @Mapping(source="marca", target="marca"),
    @Mapping(source="precio", target="precio"),
    @Mapping(source="cantidadStock", target="cantidadStock"),
    @Mapping(source="porcentajeDescuento", target="porcentajeDescuento")
  })
  ProductosResponse productosToProductosResponse(Productos productos);
  
  List<ProductosResponse> productosToProductosResponseList(List<Productos> productos);
  
}
