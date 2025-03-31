package com.mx.Responsables.Controller;

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

import com.mx.Responsables.Entity.Responsables;
import com.mx.Responsables.Service.ResponsablesServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/Responsables")
//@CrossOrigin("*")
@Tag(name = "RESPONSABLES", description ="Api para gestionar mascotas de veterinaria")
public class ResponsablesWS {

	@Autowired
	private ResponsablesServiceImpl service;

	// http://localhost:8040/Responsables
	//ACCESO A SWAGGER ES http://localhost:8040/swagger-ui.html

	// L I S T A R
	@GetMapping
	@Operation(summary = "MOSTRAR RESPONSABLES", description = "Devuelve una lista de los responsables almacenados")
	public ResponseEntity<?> listarResponsables() {
		List<Responsables> responsables = service.listar();
		if (responsables.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(responsables);
		}
	}

	// B U S C A R
	@PostMapping("/{idResponsable}")
	@Operation(summary = "BUSCAR RESPONSABLES", description = "Devuelve informacion de un responsble, se busca por ID")
	public ResponseEntity<?> obtenerResponsable(@PathVariable Long idResponsable) {
		Responsables responsable = service.buscar(idResponsable);
		if (responsable != null) {
			return ResponseEntity.ok(responsable);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// G U A R D A R
	@PostMapping
	@Operation(summary = "GUARDAR RESPONSABLE", description = "Recibe un objeto de mascota para almacenar")
	public ResponseEntity<?> guardarResponsable(@RequestBody Responsables responsable) {
		return ResponseEntity.ok(service.guardar(responsable));
	}

	// E D I T A R
	@PutMapping
	@Operation(summary = "EDITAR RESPONSABLE", 
	description = "Edita un objeto de responsable para modificar en la base de datos, excepto el ID")
	public ResponseEntity<?> actualizarResponsable(@RequestBody Responsables responsable) {
		Responsables responsableBD = service.buscar(responsable.getIdResponsable());
		if (responsableBD != null) {
			return ResponseEntity.ok(service.editar(responsable));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// E L I M I N A R
	@DeleteMapping("/{idResponsable}")
	@Operation(summary = "ELIMINAR RESPONSABLE", description = "Elimina un objeto de responsable mediante su ID")
	public ResponseEntity<?> eliminarResponsable(@PathVariable Long idResponsable) {
		Responsables responsable = service.buscar(idResponsable);
		if (responsable != null) {
			service.eliminar(idResponsable);
			return ResponseEntity.ok(responsable);
		} else {
			return ResponseEntity.ok(responsable);
		}
	}

	// B U S C A R - P O R - V E T E R I N A R I A I D
	@PostMapping("/buscaridVeterinaria/{veterinariaId}")
	@Operation(summary = "BUSCAR UN RESPONSABLE POR ID VETERINARIA", 
	description = "Recupera la información de los responsables de una veterinaria por su ID")
	public List<Responsables> buscarveterinaria(@PathVariable int veterinariaId) {
		return service.buscarveterinaria(veterinariaId);
	}

	// SERVICIO PARA OBTERNER LAS MASCOTAS DE CADA RESPONSABLE
	@GetMapping("/mascotaresponsable/{responsableId}")
	@Operation(summary = "BUSCAR LAS MASCOTAS POR ID RESPONSABLE", 
	description = "Recupera la información de las mascotas de un responsable por su ID")
	public ResponseEntity<?> obtenerTodoslosModulos(@PathVariable int responsableId) {
		Map<String, Object> modulos = service.obtenerconMascotas(responsableId);
		return ResponseEntity.status(HttpStatus.OK).body(modulos);
	}

}
