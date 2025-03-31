package com.mx.Clientes.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.mx.Clientes.Dao.IClientesDao;
import com.mx.Clientes.Entity.Clientesv;
import com.mx.Clientes.FeignClient.IMascotasFeign;
import com.mx.Clientes.Models.Mascotas;

@Service
public class ClienteServiceImpl implements IClientesService {

	@Autowired
	private IClientesDao dao;

	@Autowired
	private IMascotasFeign mascotasFC;

	@Override
	public Clientesv guardar(Clientesv cliente) {
		Clientesv clienteExist = dao.findByNombreIgnoreCaseAndDireccionIgnoreCase(cliente.getNombre(),
				cliente.getDireccion());

		if (clienteExist != null) {
			throw new RuntimeException("El cliente con el nombre '" + cliente.getNombre() + "' y la dirección '"
					+ cliente.getDireccion() + "' ya existe.");
		}
		return dao.save(cliente);
	}

	@Override
	public Clientesv editar(Clientesv cliente) {
		Clientesv clienteExist = dao.findByNombreIgnoreCaseAndDireccionIgnoreCase(cliente.getNombre(),
				cliente.getDireccion());

		if (clienteExist != null && clienteExist.getIdCliente() != cliente.getIdCliente())
			throw new RuntimeException("El cliente con el nombre '" + cliente.getNombre() + "' y la dirección '"
					+ cliente.getDireccion() + "' ya existe.");
		return dao.save(cliente);

	}

	@Override
	public Clientesv eliminar(Long idCliente) {
		Clientesv aux = this.buscar(idCliente);
		if (aux != null) {
			dao.deleteById(idCliente);

		}
		return aux;
	}

	@Override
	public Clientesv buscar(Long idCliente) {
		// TODO Auto-generated method stub
		return dao.findById(idCliente).orElse(null);
	}

	@Override
	public List<Clientesv> listar() {
		// TODO Auto-generated method stub
		return dao.findAll(Sort.by(Direction.DESC, "idCliente"));
	}

	// SERVICIO PARA OBTERNER LAS MASCOTAS DE CADA CLIENTE
	public List<Mascotas> getMascotas(int clienteId) {
		return mascotasFC.buscarPorCliente(clienteId);
	}

	public Map<String, Object> obtenerMascotasPorCliente(int clienteId) {
		Map<String, Object> resultado = new HashMap<>();
		Clientesv cliente = this.buscar((long) clienteId);
		if (cliente == null) {
			resultado.put("mensaje", "El cliente con ID :" + clienteId + " no existe");
			return resultado;
		}
		resultado.put("Cliente", cliente);

		List<Mascotas> mascota = this.getMascotas(clienteId);
		if (mascota == null || mascota.isEmpty()) {
			resultado.put("Mascotas", "El cliente no tiene mascotas");
		} else {
			resultado.put("Mascotas", mascota);
		}
		return resultado;
	}

}
