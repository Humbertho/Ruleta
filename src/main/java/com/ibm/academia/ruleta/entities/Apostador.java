package com.ibm.academia.ruleta.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibm.academia.ruleta.enums.Color;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "apostadores", schema = "ruleta")
public class Apostador implements Serializable
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull(message = "No puede ser nulo")
	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@NotNull(message = "No puede ser nulo")
	@Max(value = 36, message = "El valor no puede ser mayor a 36")
	@Min(value = 0, message = "El valor no puede ser menor a 0")
	@Column(name = "apuesta_numero", nullable = false)
	private Integer apuestaNumero;
	
	@NotNull(message = "No puede ser nulo")
	@Max(value = 10000, message = "El valor no puede ser mayor a 10000")
	@Min(value = 0, message = "El valor no puede ser menor a 0")
	@Column(name = "apuesta_cantidad", nullable = false)
	private Double apuestaCantidad;
	
	@NotNull(message = "No puede ser nulo")
	@Column(name = "apuesta_color", nullable = false)
	private Color apuestaColor;
	
	@ManyToOne(optional = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "ruleta_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "apuestas" })
	private Ruleta ruleta;
	
	@Column(name = "fecha_Creacion")
	private Date fechaCreacion;
	
	@Column(name = "fecha_modificacion")
	private Date fechaModificacion;
	
	public Apostador(Integer id, String nombre, Integer apuestaNumero, Double apuestaCantidad, Color apuestaColor) 
	{		
		this.id = id;
		this.nombre = nombre;
		this.apuestaNumero = apuestaNumero;
		this.apuestaCantidad = apuestaCantidad;
		this.apuestaColor = apuestaColor;
	}
	
	@PrePersist
	private void prePersist() 
	{
		this.fechaCreacion = new Date();
	}

	@PreUpdate
	private void preUpdate() 
	{
		this.fechaModificacion = new Date();
	}

	private static final long serialVersionUID = 1L;	
}
