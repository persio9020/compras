package com.compras.model.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
@Data
public class ProductosCarritoResponse {
  @Schema(example = "1", description = "Id del producto")
  private String id;
  
  @Schema(example = "Huawei Psmart 128gb", description = "Nombre del producto")
  private String nombre;
  
  @Schema(example = "Samnsung", description = "Marca del producto")
  private String marca;
  
  @Schema(example = "624900", description = "Precio del producto")
  private Double precio;
  
  @Schema(example = "100", description = "Cantidad a comprar")
  private Integer cantidadCompra;

  @Schema(example = "Nuevo", description = "Estado del producto")
  private String estado;
  
  @Schema(example = "15", description = "Porcentaje descuento")
  private Double porcentajeDescuento;
}
