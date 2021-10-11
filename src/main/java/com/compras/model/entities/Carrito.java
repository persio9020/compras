package com.compras.model.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class Carrito {
  @EmbeddedId
  protected CarritoPK carritoPK;
  @Column(name = "cantidad_compra")
  private int cantidadCompra;
  @JoinColumn(name = "id_cliente", referencedColumnName = "id", insertable = false, updatable = false)
  @ManyToOne(optional = false)
  private Cliente cliente;
  @JoinColumn(name = "id_productos", referencedColumnName = "id", insertable = false, updatable = false)
  @ManyToOne(optional = false)
  private Productos productos;
}
