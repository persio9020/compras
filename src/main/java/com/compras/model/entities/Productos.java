package com.compras.model.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Productos {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "nombre")
  private String nombre;
  @Column(name = "marca")
  private String marca;
  @Column(name = "precio")
  private Double precio;
  @Column(name = "cantidad_stock")
  private Integer cantidadStock;
  @Column(name = "estado")
  private String estado;
  @Column(name = "porcentaje_descuento")
  private Double porcentajeDescuento;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "productos")
  private List<Carrito> carritoList;
  public Productos() {
    
  }
  public Productos(Long id) {
    this.id = id;
  }
  public Productos(String arg1, String arg2) {
    
  }
  
  public Productos(String nombre, String marca, Double precio, Integer cantidadStock, String estado, Double porcentajeDescuento) {
    super();
    this.nombre = nombre;
    this.marca = marca;
    this.precio = precio;
    this.cantidadStock = cantidadStock;
    this.estado = estado;
    this.porcentajeDescuento = porcentajeDescuento;
  }

  @Override
  public String toString() {
    return "Productos [nombre=" + nombre + ", marca=" + marca + ", precio=" + precio + ", cantidadStock=" + cantidadStock + ", estado=" + estado + ", porcentajeDescuento=" + porcentajeDescuento + "]";
  }
  
  
}
