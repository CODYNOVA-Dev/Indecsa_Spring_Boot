package com.example.demo.repository;

import com.example.demo.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstadoRepository extends JpaRepository<Estado, Integer> {

    Optional<Estado> findByNombreEst(String nombreEst);
}
