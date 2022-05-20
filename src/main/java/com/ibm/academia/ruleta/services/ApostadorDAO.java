package com.ibm.academia.ruleta.services;

import com.ibm.academia.ruleta.entities.Apostador;
import com.ibm.academia.ruleta.entities.Ruleta;

public interface ApostadorDAO extends GenericoDAO<Apostador>
{
	public Apostador guardarApuesta(Apostador apuesta, Ruleta ruleta);
}
