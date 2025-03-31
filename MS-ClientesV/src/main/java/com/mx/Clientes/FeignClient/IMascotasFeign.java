package com.mx.Clientes.FeignClient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.mx.Clientes.Models.Mascotas;




@FeignClient(name = "MS-MASCOTAS", url = "http://localhost:8041", path = "/Mascotas")
public interface IMascotasFeign {

	@PostMapping("/buscarPorCliente/{clienteId}")
	public List<Mascotas> buscarPorCliente(@PathVariable int clienteId);
}
