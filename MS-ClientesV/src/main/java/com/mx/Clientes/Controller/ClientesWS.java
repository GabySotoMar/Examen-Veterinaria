package com.mx.Clientes.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.Clientes.Entity.Clientesv;
import com.mx.Clientes.Service.ClienteServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/Clientes")
//@CrossOrigin("*")
@Tag(name = "CLIENTES", description ="Api para gestionar clientes de veterinaria")
public class ClientesWS {

	@Autowired
	private ClienteServiceImpl service;

	// http://Localhost:8042/Clientes
	//ACCESO A SWAGGER ES http://localhost:8042/swagger-ui.html

	// LISTAR
	@GetMapping
	@Operation(summary = "MOSTRAR CLIENTES", description = "Devuelve una lista de los clientes almacenados")
	public ResponseEntity<?> listar() {
		List<Clientesv> clientes = service.listar();

		if (clientes.isEmpty())
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

		return ResponseEntity.status(HttpStatus.OK).body(clientes);
	}

	// BUSCAR
	@PostMapping("/{idCliente}")
	@Operation(summary = "BUSCAR CLIENTES", description = "Busca informacion de un cliente mediante su ID")
	public ResponseEntity<?> buscar(@PathVariable Long idCliente) {
		Clientesv cliente = service.buscar(idCliente);
		if (cliente != null)
			return ResponseEntity.status(HttpStatus.OK).body(cliente);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	// GUARDAR
	@PostMapping
	@Operation(summary = "GUARDAR CLIENTES", description = "Recibe un objeto de cliente para almacenar")
	public ResponseEntity<?> guardar(@RequestBody Clientesv cliente) {
		try {
			Clientesv nuevoCliente = service.guardar(cliente);
				return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente);
		} catch(RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		
	}

	// EDITAR
	@PutMapping
	@Operation(summary = "EDITAR CLIENTES", description = "Edita un objeto de cliente, excepto el ID para modificar en la base de datos")
	public ResponseEntity<?> editar(@RequestBody Clientesv cliente) {
		Clientesv aux = service.buscar(cliente.getIdCliente());

		if (aux != null)
			if (service.editar(cliente) != null) {
				return ResponseEntity.status(HttpStatus.OK).body(cliente);
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	// ELIMINAR
	@DeleteMapping("/{idCliente}")
	@Operation(summary = "ELIMINAR CLIENTE", description = "Elimina un objeto de cliente mediante su ID")
	public ResponseEntity<?> eliminar(@PathVariable Long idCliente) {
		Clientesv aux = service.buscar(idCliente);
		if (aux != null)
			if (service.eliminar(idCliente) != null) {
				return ResponseEntity.status(HttpStatus.OK).body(aux);
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	// SERVICIO PARA OBTERNER LAS MASCOTAS DE CADA CLIENTE
		@GetMapping("/mascotacliente/{clienteId}")
		@Operation(summary = "BUSCAR UNA  MASCOTA POR ID CLIENTE", description = "Recupera la información de las mascitas de un cliente por su ID")
		public ResponseEntity<?> obtenerMascotasporCliente(@PathVariable int clienteId) {
			Map<String, Object> modulos = service.obtenerMascotasPorCliente(clienteId);
			return ResponseEntity.status(HttpStatus.OK).body(modulos);
		}
	
	

}
