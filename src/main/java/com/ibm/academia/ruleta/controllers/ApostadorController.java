package com.ibm.academia.ruleta.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.academia.ruleta.entities.Apostador;
import com.ibm.academia.ruleta.entities.Ruleta;
import com.ibm.academia.ruleta.exceptions.NotFoundException;
import com.ibm.academia.ruleta.services.ApostadorDAO;
import com.ibm.academia.ruleta.services.RuletaDAO;

@RestController
@RequestMapping("/apostador")
public class ApostadorController 
{
	@Autowired
	private ApostadorDAO apostadorDao;
	
	@Autowired
	private RuletaDAO ruletaDao;
	
	/**
	 * 
	 * @param apuesta
	 * @param result
	 * @param idRuleta
	 * @return
	 * @author Humberto
	 */
	@PostMapping("/{idRuleta}")
	public ResponseEntity<?> guardarApuesta(@Valid @RequestBody Apostador apuesta, BindingResult result, @PathVariable Integer idRuleta)
	{
		Map<String, Object> validaciones = new HashMap<>();
		List<String> listaErrores = new ArrayList<>();
		
		if(result.hasErrors())
		{
			listaErrores = result.getFieldErrors()
					.stream()
					.map(errores -> "Campo:" + errores.getField() + " " + errores.getDefaultMessage())
					.collect(Collectors.toList());
			
			validaciones.put("Lista Errores", listaErrores);
			
			return new ResponseEntity<Map<String, Object>>(validaciones, HttpStatus.BAD_REQUEST);
		}
		
		Optional<Ruleta> oRuleta = ruletaDao.buscarPorId(idRuleta);
		
		if(oRuleta.isEmpty() || !oRuleta.get().getEstadoRuleta())
		{
			throw new NotFoundException("No existe ninguna ruleta disponible para la nueva apuesta");
		}
		
		Apostador nuevaApuestaRealizada = apostadorDao.guardarApuesta(apuesta, oRuleta.get());
		
		return new ResponseEntity<Apostador>(nuevaApuestaRealizada, HttpStatus.CREATED);
	}
}
