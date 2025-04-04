package com.mx.Veterinaria.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.mx.Veterinaria.Dao.IVeterinariaDao;
import com.mx.Veterinaria.Entity.Veterinaria;

@Service

public class VeterinariaServiceImpl implements IVeterinariaService {

	// I N Y E C C I O N D E P E N D E N C I A S
	@Autowired
	private IVeterinariaDao dao;


	// ----------------------------------------------------------------
	// -------S E R V I C I O S - D E - V E T E R I N A R I A ---------

	@Override
	public Veterinaria guardar(Veterinaria veterinaria) {
		Veterinaria veterinariaExist = dao.findByNombreIgnoreCaseAndDireccionIgnoreCase(veterinaria.getNombre(),
				veterinaria.getDireccion());

		if (veterinariaExist != null) {
			throw new RuntimeException("La veterinaria con el nombre '" + veterinaria.getNombre() + "' en '"
					+ veterinaria.getDireccion() + "' ya existe.");
		}
		return dao.save(veterinaria);
	}

	@Override
	public Veterinaria editar(Veterinaria veterinaria) {
		Veterinaria veterinariaExist = dao.findByNombreIgnoreCaseAndDireccionIgnoreCase(veterinaria.getNombre(),
				veterinaria.getDireccion());

		if (veterinariaExist != null && veterinariaExist.getIdVeterinaria() != veterinaria.getIdVeterinaria())
			throw new RuntimeException("La veterinaria con el nombre '" + veterinaria.getNombre() + "' en '"
					+ veterinaria.getDireccion() + "' ya existe.");
		return dao.save(veterinaria);
	}

	@Override
	public Veterinaria eliminar(int idVeterinaria) {
		Veterinaria aux = this.buscar(idVeterinaria);
		if (aux != null) {
			dao.deleteById(idVeterinaria);
		}
		return aux;
	}

	@Override
	public Veterinaria buscar(int idVeterinaria) {
		return dao.findById(idVeterinaria).orElse(null);
	}

	@Override
	public List<Veterinaria> listar() {
		return dao.findAll(Sort.by(Direction.DESC, "idVeterinaria"));
	}


}// END
