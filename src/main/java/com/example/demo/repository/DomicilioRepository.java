package com.example.demo.repository;

import com.example.demo.model.Domicilio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DomicilioRepository extends JpaRepository<Domicilio, Integer> {

    List<Domicilio> findByEstado_IdEstado(Integer idEstado);
}
