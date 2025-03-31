package com.mx.Responsables.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.mx.Responsables.Dao.IResponsablesDao;
import com.mx.Responsables.Entity.Responsables;
import com.mx.Responsables.FeignClient.IMascotasFeign;
import com.mx.Responsables.Models.Mascotas;

@Service
public class ResponsablesServiceImpl implements IResponsablesService {

	@Autowired
	private IResponsablesDao dao;
	@Autowired
	private IMascotasFeign mascotasFC;

	@Override
	public Responsables guardar(Responsables responsable) {
		Responsables responsableExist = dao.findByNombreIgnoreCaseAndVeterinariaId(responsable.getNombre(),
				responsable.getVeterinariaId());

		if (responsableExist != null) {
			throw new RuntimeException("El responsable con el nombre '" + responsable.getNombre()
					+ "' ya existe en la veterinaria '" + responsable.getVeterinariaId());
		}
		return dao.save(responsable);
	}

	@Override
	public Responsables editar(Responsables responsable) {
		Responsables responsableExist = dao.findByNombreIgnoreCaseAndVeterinariaId(responsable.getNombre(),
				responsable.getVeterinariaId());

		if (responsableExist != null && responsableExist.getIdResponsable() != responsable.getIdResponsable()) {
			throw new RuntimeException("El responsable con el nombre '" + responsable.getNombre()
					+ "' ya existe en la veterinaria '" + responsable.getVeterinariaId());
		}
		return dao.save(responsable);
	}

	@Override
	public Responsables eliminar(Long idResponsable) {
		Responsables aux = this.buscar(idResponsable);
		if (aux != null) {
			dao.deleteById(idResponsable);
		}
		return aux;
	}

	@Override
	public Responsables buscar(Long idResponsable) {
		// TODO Auto-generated method stub
		return dao.findById(idResponsable).orElse(null);
	}

	@Override
	public List<Responsables> listar() {
		// TODO Auto-generated method stub
		return dao.findAll(Sort.by(Direction.DESC, "idResponsable"));
	}

	public List<Responsables> buscarveterinaria(int veterinariaId) {
		return dao.findByveterinariaId(veterinariaId);
	}

	// SERVICIO PARA OBTERNER LAS MASCOTAS DE CADA RESPONSABLE
	public List<Mascotas> getMascotas(int responsableId) {
		return mascotasFC.buscarPorResponsable(responsableId);
	}

	public Map<String, Object> obtenerconMascotas(int responsableId) {
		Map<String, Object> resultado = new HashMap<>();
		Responsables responsable = this.buscar((long) responsableId);
		if (responsable == null) {
			resultado.put("mensaje", "El responsable con ID :" + responsableId + " no existe");
			return resultado;
		}
		resultado.put("Responsable", responsable);

		List<Mascotas> mascota = this.getMascotas(responsableId);
		if (mascota == null || mascota.isEmpty()) {
			resultado.put("Mascotas", "El responsable no tiene mascotas");
		} else {
			resultado.put("Mascotas", mascota);
		}
		return resultado;
	}

}
