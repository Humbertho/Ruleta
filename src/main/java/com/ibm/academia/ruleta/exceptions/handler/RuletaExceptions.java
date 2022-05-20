package com.ibm.academia.ruleta.exceptions.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ibm.academia.ruleta.exceptions.NotFoundException;

@RestControllerAdvice
public class RuletaExceptions 
{
	@ExceptionHandler(value = NotFoundException.class)
	public ResponseEntity<?> noExisteException(NotFoundException exception)
	{
		Map<String, Object> respuesta = new HashMap<String, Object>();
		respuesta.put("error", exception.getMessage());
		
		return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
	}
}
