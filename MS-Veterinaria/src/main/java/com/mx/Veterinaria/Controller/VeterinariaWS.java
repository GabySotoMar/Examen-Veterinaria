package com.mx.Veterinaria.Controller;

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

import com.mx.Veterinaria.Entity.Veterinaria;
import com.mx.Veterinaria.Service.VeterinariaServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/Veterinaria")
//@CrossOrigin("*")
@Tag(name = "VETERINARIA", description ="Api para gestionar veterinarias")
public class VeterinariaWS {

	@Autowired
	private VeterinariaServiceImpl service;

	// http://localhost:8043/Veterinaria
	//ACCESO A SWAGGER ES http://localhost:8043/swagger-ui.html
	
	// L I S T A R
	@GetMapping
	@Operation(summary = "MOSTRAR VETERINARIAS", description = "Devuelve una lista de veterinarias almacenadas")
	public ResponseEntity<?> listarVeterinarias() {
		List<Veterinaria> veterinaria = service.listar();
		if (veterinaria.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(veterinaria);
		}
	}

	// B U S C A R
	@PostMapping("/{idVeterinaria}")
	@Operation(summary = "BUSCAR VETERINARIAS", description = "Busca informacion de veterinaria mediante su ID")
	public ResponseEntity<?> obtenerVeterinaria(@PathVariable int idVeterinaria) {
		Veterinaria veterinaria = service.buscar(idVeterinaria);
		if (veterinaria != null) {
			return ResponseEntity.ok(veterinaria);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// G U A R D A R
	@PostMapping
	@Operation(summary = "GUARDAR VETERINARIAS", description = "Recibe un objeto de veterinaria para almacenar")
	public ResponseEntity<?> guardarveterinaria(@RequestBody Veterinaria veterinaria) {
		return ResponseEntity.ok(service.guardar(veterinaria));
	}

	// E D I T A R
	@PutMapping
	@Operation(summary = "EDITAR VETERINARIAS", description = "Edita un objeto de veterinaria para modificar en la base de datos")
	public ResponseEntity<?> actualizarVeterinaria(@RequestBody Veterinaria veterinaria) {
		Veterinaria veterinariaBD = service.buscar(veterinaria.getIdVeterinaria());
		if (veterinariaBD != null) {
			return ResponseEntity.ok(service.editar(veterinaria));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// E L I M I N A R
	@DeleteMapping("/{idVeterinaria}")
	@Operation(summary = "ELIMINAR VETERINARIAS", description = "Elimina un objeto de veterinaria mediante su ID")
	public ResponseEntity<?> eliminarVeterinaria(@PathVariable int idVeterinaria) {
		Veterinaria veterinaria = service.buscar(idVeterinaria);
		if (veterinaria != null) {
			service.eliminar(idVeterinaria);
			return ResponseEntity.ok(veterinaria);
		} else {
			return ResponseEntity.ok(veterinaria);
		}
	}
	//ACCESO A SWAGGER ES http://localhost:8043/swagger-ui.html
}
