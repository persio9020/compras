package com.compras.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.compras.model.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

}
