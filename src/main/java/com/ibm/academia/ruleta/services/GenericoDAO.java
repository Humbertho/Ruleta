package com.ibm.academia.ruleta.services;

import java.util.Optional;

public interface GenericoDAO<E> 
{
	public Optional<E> buscarPorId(Integer id);
	
	public Iterable<E> buscarTodos();
	
	public void eliminarPorId(Integer id);
}
