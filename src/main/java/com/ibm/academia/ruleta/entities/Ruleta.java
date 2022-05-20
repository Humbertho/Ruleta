package com.ibm.academia.ruleta.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibm.academia.ruleta.enums.Color;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="ruletas", schema = "ruleta")
public class Ruleta implements Serializable
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull(message = "No puede ser nulo")
	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@NotNull(message = "No puede ser nulo")
	@Positive(message = "El valor no puede ser un numero negativo")
	@Column(name = "numero_ganador", nullable = false)
	private Integer numeroGanador;
	
	@NotNull(message = "No puede ser nulo")	
	@Enumerated(EnumType.STRING)
	@Column(name = "color_ganador", nullable = false)
	private Color colorGanador;
	
	@Column(name = "estado_ruleta")
	private Boolean estadoRuleta = false;
	
	@OneToMany(mappedBy = "ruleta", fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "ruleta"})
	private List<Apostador> apuestasRealizadas;
	
	@Column(name = "fecha_creacion")
	private Date fechaCreacion;
	
	@Column(name = "fecha_modificacion")
	private Date fechaModificacion;
	
	
	
	public Ruleta(Integer id, String nombre, Integer numeroGanador, Color colorGanador, Boolean estadoRuleta, List<Apostador> apuestasRealizadas) 
	{		
		this.id = id;
		this.nombre = nombre;
		this.numeroGanador = numeroGanador;
		this.colorGanador = colorGanador;
		this.estadoRuleta = estadoRuleta;
		this.apuestasRealizadas = apuestasRealizadas;
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
