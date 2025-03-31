package com.mx.Clientes.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mx.Clientes.Entity.Clientesv;

public interface IClientesDao extends JpaRepository<Clientesv, Long> {
	
	
	
	public Clientesv findByNombreIgnoreCaseAndDireccionIgnoreCase(String nombre, String direccion);

}
