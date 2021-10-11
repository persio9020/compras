package com.compras.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import com.compras.model.entities.Productos;

public interface ProductosRepository extends JpaRepository<Productos, Long> {
  
  @Query("SELECT COUNT(p.id) FROM Productos p WHERE p.nombre=?1 AND p.marca=?2")
  public Long countExistingProducts(String nombre, String marca);
  
  public List<Productos> findByNombreContainingAndPrecioBetweenAndMarca(String nombre, Double precioDesde, Double precioHasta, String marca, Pageable pageable);
  
  @Query("SELECT p.cantidadStock FROM Productos p WHERE p.id=?1")
  public Integer findCantidadStockById(Long id);
  
  @Query("update Productos p set p.cantidadStock = :cantidadStock WHERE p.id = :id")
  void setCantidadStock(@Param("cantidadStock") Integer cantidadStock, @Param("id") Long id);
  
}
