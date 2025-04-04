package com.mx.Responsables.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "RESPONSABLES")
@Data

public class Responsables {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idResponsable;
	private String nombre;
	private Long contacto;
	private int veterinariaId;

}
/*
 * {"idResponsable": ,
        "nombre": "LORENA MARTINEZ CRUZ",
        "contacto": 2223242526,
        "veterinariaId": 1}
        */
