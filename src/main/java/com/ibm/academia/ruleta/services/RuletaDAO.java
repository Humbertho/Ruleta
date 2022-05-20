package com.ibm.academia.ruleta.services;

import com.ibm.academia.ruleta.entities.Ruleta;

public interface RuletaDAO extends GenericoDAO<Ruleta>
{
	public Integer guardarRuleta(Ruleta ruleta);
	public Ruleta activarRuleta(Ruleta ruletaSeleccionada);
	public Ruleta cerrarRuleta(Ruleta ruletaSeleccionada);
}
