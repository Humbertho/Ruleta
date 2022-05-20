package com.ibm.academia.ruleta.services;

import org.springframework.stereotype.Service;

import com.ibm.academia.ruleta.entities.Ruleta;
import com.ibm.academia.ruleta.repositories.RuletaRepository;

@Service
public class RuletaDAOImpl extends GenericoDAOImpl<Ruleta, RuletaRepository> implements RuletaDAO
{

	public RuletaDAOImpl(RuletaRepository repository) 
	{
		super(repository);
	}

	@Override
	public Integer guardarRuleta(Ruleta ruleta) 
	{
		return this.repository.save(ruleta).getId();
	}

	@Override
	public Ruleta activarRuleta(Ruleta ruletaSeleccionada) 
	{
		ruletaSeleccionada.setEstadoRuleta(true);
		return repository.save(ruletaSeleccionada);
	}

	@Override
	public Ruleta cerrarRuleta(Ruleta ruletaSeleccionada) 
	{
		ruletaSeleccionada.setEstadoRuleta(false);
		repository.save(ruletaSeleccionada);
		return repository.getById(ruletaSeleccionada.getId());
	}

}
