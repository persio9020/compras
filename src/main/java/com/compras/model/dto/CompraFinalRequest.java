package com.compras.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CompraFinalRequest {
  @Schema(example = "1", description = "Id del cliente que quiere finalizar la compra")
  private Integer idCliente;
}
