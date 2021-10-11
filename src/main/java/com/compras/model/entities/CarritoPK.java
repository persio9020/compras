package com.compras.model.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class CarritoPK implements Serializable{
  private static final long serialVersionUID = 1L;
  @Column(name = "id_productos")
  private Long idProductos;
  @Column(name = "id_cliente")
  private Integer idCliente;
  public CarritoPK() {
  }
  public CarritoPK(Long idProductos, Integer idCliente) {
    this.idProductos = idProductos;
    this.idCliente = idCliente;
  }
}
