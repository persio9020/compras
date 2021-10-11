package com.compras.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.compras.model.entities.Carrito;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
  
  @Query("SELECT c FROM Carrito c INNER JOIN c.carritoPK pk WHERE pk.idCliente=?1")
  public List<Carrito> findByCliente(Integer idCliente);
  
  @Transactional
  @Modifying
  @Query("DELETE FROM Carrito c WHERE c.carritoPK.idCliente=?1")
  public Integer deleteCarritoByCliente(Integer idCliente);
}
