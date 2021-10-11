package com.compras.model.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
@Data
public class ProductosResponse {
  @Schema(example = "1", description = "Id del producto")
  private String id;
  
  @Schema(example = "Huawei Psmart 128gb", description = "Nombre del producto")
  private String nombre;
  
  @Schema(example = "Samnsung", description = "Marca del producto")
  private String marca;
  
  @Schema(example = "624900", description = "Precio del producto")
  private Double precio;
  
  @Schema(example = "100", description = "Cantidad en stock")
  private Integer cantidadStock;

  @Schema(example = "Nuevo", description = "Estado del producto")
  private String estado;
  
  @Schema(example = "15", description = "Porcentaje descuento")
  private Double porcentajeDescuento;
}
