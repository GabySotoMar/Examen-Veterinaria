package com.mx.Mascotas.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.Mascotas.Entity.Mascotas;
import com.mx.Mascotas.Service.MascotasServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/Mascotas")
//@CrossOrigin("*")
@Tag(name = "MASCOTAS", description ="Api para gestionar mascotas de veterinaria")
public class MascotasWS {

	@Autowired
	private MascotasServiceImpl service;

	// http://localhost:8041/Mascotas
	//ACCESO A SWAGGER ES http://localhost:8041/swagger-ui.html

	// L I S T A R
	@GetMapping
	@Operation(summary = "MOSTRAR MASCOTAS", description = "Devuelve una lista de las  mascotas almacenadas")
	public ResponseEntity<?> listarMascotas() {
		List<Mascotas> mascotas = service.listar();
		if (mascotas.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(mascotas);
		}
	}

	// B U S C A R
	@PostMapping("/{idMascota}")
	@Operation(summary = "BUSCAR UNA MASCOTA", description = "Busca informacion de una mascota mediante su ID")
	public ResponseEntity<?> obtenerMascota(@PathVariable Long idMascota) {
		Mascotas mascota = service.buscar(idMascota);
		if (mascota != null) {
			return ResponseEntity.ok(mascota);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// G U A R D A R
	@PostMapping
	@Operation(summary = "GUARDAR MASCOTA", description = "Recibe un objeto de mascota para almacenar")
	public ResponseEntity<?> guardarMascota(@RequestBody Mascotas mascota) {
		return ResponseEntity.ok(service.guardar(mascota));
	}

	// E D I T A R
	@PutMapping
	@Operation(summary = "EDITAR MASCOTA", description = "Edita un objeto de mascota, para modificar en la base de datos, excepto el ID")
	public ResponseEntity<?> actualizarMascotas(@RequestBody Mascotas mascota) {
		Mascotas mascotasBD = service.buscar(mascota.getIdMascota());
		if (mascotasBD != null) {
			return ResponseEntity.ok(service.editar(mascota));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// E L I M I N A R
	@DeleteMapping("/{idMascota}")
	@Operation(summary = "ELIMINAR MASCOTA", description = "Elimina un objeto de mascota mediante su ID")
	public ResponseEntity<?> eliminarMascota(@PathVariable Long idMascota) {
		Mascotas mascota = service.buscar(idMascota);
		if (mascota != null) {
			service.eliminar(idMascota);
			return ResponseEntity.ok(mascota);
		} else {
			return ResponseEntity.ok(mascota);
		}
	}


	// B U S C A R - P O R - R E S P O N S A B L E I D

	@PostMapping("/buscarResponsable/{responsableId}")
	@Operation(summary = "BUSCAR UNA  MASCOTA POR ID RESPONSABLE", 
	description = "Recupera la información de las mascitas de un responsable por su ID")
	public List<Mascotas> buscarPorResponsable(@PathVariable int responsableId) {
		return service.buscarPorResponsable(responsableId);
	}
	
	// B U S C A R - P O R - R E S P O N S A B L E I D

	@PostMapping("/buscarPorCliente/{clienteId}")
	@Operation(summary = "BUSCAR UNA  MASCOTA POR ID CLIENTE", 
	description = "Recupera la información de las mascitas de un cliente por su ID")
	public List<Mascotas> buscarPorCliente(@PathVariable int clienteId) {
		return service.buscarPorClienteIde(clienteId);
	}
}
