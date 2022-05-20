package com.ibm.academia.ruleta.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ibm.academia.ruleta.entities.Apostador;

@Repository
public interface ApostadorRepository extends JpaRepository<Apostador, Integer>
{

}
