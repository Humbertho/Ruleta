package com.ibm.academia.ruleta.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.academia.ruleta.entities.Apostador;
import com.ibm.academia.ruleta.entities.Ruleta;
import com.ibm.academia.ruleta.repositories.ApostadorRepository;

@Service
public class ApostadorDAOImpl extends GenericoDAOImpl<Apostador, ApostadorRepository> implements ApostadorDAO
{
	
	public ApostadorDAOImpl(ApostadorRepository repository) 
	{
		super(repository);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Apostador guardarApuesta(Apostador apuesta, Ruleta ruleta) 
	{
		Apostador apuestaGuardada = repository.save(apuesta);
		apuestaGuardada.setRuleta(ruleta);
		return repository.save(apuestaGuardada);
	}
}
