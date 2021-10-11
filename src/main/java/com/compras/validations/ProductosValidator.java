package com.compras.validations;

import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;

import com.compras.model.entities.Productos;
import com.compras.repository.ProductosRepository;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class ProductosValidator implements Validator<Productos> {
  
  private ProductosRepository productosRepository;
  
  public ProductosValidator(ProductosRepository productosRepository) {
    this.productosRepository = productosRepository;
  }
  
  public ProductosValidator() {
  }
  @Override
  public void validate(Productos producto) throws ValidationException {
    if (producto.getNombre().isEmpty()) {
      log.info("Falta el nombre del producto: {}", producto);
      throw new ValidationException("Falta el nombre del producto: " + producto);
    }

    if (producto.getMarca().isEmpty()) {
      log.info("Falta la marca del producto: {}", producto);
      throw new ValidationException("Falta la marca del producto: " + producto);
    }

    if (producto.getPrecio() == null) {
      log.info("Falta el precio del producto: {}", producto);
      throw new ValidationException("Falta el precio del producto: " + producto);
    }

    if (producto.getCantidadStock() == null) {
      log.info("Falta la cantidad en stock del producto: {}", producto);
      throw new ValidationException("Falta la cantidad en stock del producto: " + producto);
    }

    if (producto.getEstado().isEmpty()) {
      log.info("Falta el estado del producto: {}", producto);
      throw new ValidationException("Falta el estado del producto: " + producto);
    }

    if (producto.getPorcentajeDescuento() == null) {
      log.info("Falta el porcentaje de descuento del producto: {}", producto);
      throw new ValidationException("Falta el porcentaje de descuento del producto: " + producto);
    }
    
    Long cantidad = productosRepository.countExistingProducts(producto.getNombre(), producto.getMarca());
    if(cantidad > 0) {
      log.info("El producto ya existe en la base de datos : {}", producto);
      throw new ValidationException("El producto ya existe en la base de datos: " + producto);
    }
  }

}
