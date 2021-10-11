package com.compras.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ProductosRequest {
  @Schema(example = "14", description = "")
  private Long idProducto;
  @Schema(example = "1", description = "")
  private Integer idCliente;
  @Schema(example = "10", description = "")
  private Integer cantidadCompra;
}
