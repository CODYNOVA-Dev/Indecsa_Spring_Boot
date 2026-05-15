package com.example.demo.repository;

import com.example.demo.model.Domicilio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DomicilioRepository extends JpaRepository<Domicilio, Integer> {

    List<Domicilio> findByEstado_IdEstado(Integer idEstado);
}
