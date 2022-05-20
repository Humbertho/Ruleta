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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.academia.ruleta.entities.Ruleta;
import com.ibm.academia.ruleta.enums.Color;
import com.ibm.academia.ruleta.exceptions.NotFoundException;
import com.ibm.academia.ruleta.services.RuletaDAO;

@RestController
@RequestMapping("ruleta")
public class RuletaController 
{
	@Autowired
	private RuletaDAO ruletaDao;
	
	@GetMapping("/todos")
	public ResponseEntity<?> obtenerTodo()
	{
		List<Ruleta> ruletas = (List<Ruleta>) ruletaDao.buscarTodos();
		
		if(ruletas.isEmpty())
		{
			throw new NotFoundException("No existe ninguna ruleta");
		}
		
		return new ResponseEntity<List<Ruleta>>(ruletas, HttpStatus.OK);
	}
	
	@GetMapping("/{idRuleta}")
	public ResponseEntity<?> buscarPorId(@PathVariable Integer idRuleta)
	{
		Optional<Ruleta> oRuleta = ruletaDao.buscarPorId(idRuleta);
		
		if(oRuleta.isEmpty())
		{
			throw new NotFoundException("No existe ninguna ruleta con el Id: " + idRuleta);
		}
		
		return new ResponseEntity<Ruleta>(oRuleta.get(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<?> crearRuleta(@Valid @RequestBody Ruleta ruleta, BindingResult result)
	{
		Color color = null;
		Map<String, Object> validaciones = new HashMap<>();
		List<String> listaErrores = new ArrayList<>();
		
		if(ruleta.getNumeroGanador() == null || ruleta.getColorGanador() == null)
		{
			listaErrores.add("No pueden haber valores nulos");
			validaciones.put("Lista de errores", listaErrores);
			return new ResponseEntity<Map<String, Object>>(validaciones, HttpStatus.BAD_REQUEST);
		}
		
		if(result.hasErrors())
		{
			listaErrores = result.getFieldErrors()
					.stream()
					.map(errores -> "Campo: " + errores.getField() + " " + errores.getDefaultMessage())
					.collect(Collectors.toList());
		}
		
		if(ruleta.getNumeroGanador() > 36)
		{
			listaErrores.add("Valor del nuemro ganador debe ser menor o igual a 36");
		}
		
		if(!listaErrores.isEmpty())
		{
			validaciones.put("Lista de errores", listaErrores);
			return new ResponseEntity<Map<String, Object>>(validaciones, HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<String>("Id de ruleta creada es: " + ruletaDao.guardarRuleta(ruleta), HttpStatus.CREATED);
	}
	
	@PutMapping("/activarRuleta/{idRuleta}")
	public ResponseEntity<String> activarRuleta(@PathVariable Integer idRuleta)
	{
		Optional<Ruleta> oRuleta = ruletaDao.buscarPorId(idRuleta);
		
		if(oRuleta.isEmpty())
		{
			throw new NotFoundException("Operacion no exitosa, la ruleta ingresada no existe");
		}
		
		if(oRuleta.get().getEstadoRuleta())
		{
			throw new NotFoundException("Operacion no exitosa, la ruleta ingresada ya se encuentra activada");
		}
		
		Ruleta ruleta = oRuleta.get();
		ruletaDao.activarRuleta(ruleta);
		
		return new ResponseEntity<String>("Operacion exitosa", HttpStatus.OK);
	}
	
	@PutMapping("/cerrarRuleta/{idRuleta}")
	public ResponseEntity<?> cerrarRuleta(@PathVariable Integer idRuleta)
	{
		Optional<Ruleta> oRuleta = ruletaDao.buscarPorId(idRuleta);
		
		if(oRuleta.isEmpty())
		{
			throw new NotFoundException("Operacion no exitosa. No existe ninguna ruleta con el Id: " + idRuleta);
		}
		
		if(!oRuleta.get().getEstadoRuleta())
		{
			throw new NotFoundException("Operacion no exitosa, la ruleta ingresada ya se encuentra desactivada");
		}
		
		Ruleta ruleta = oRuleta.get();
		ruletaDao.cerrarRuleta(ruleta);
		
		//return new ResponseEntity<String>("Operacion exitosa", HttpStatus.OK);
		return new ResponseEntity<Ruleta>(oRuleta.get(), HttpStatus.OK);
	}
}
