package com.example.demo.repository;

import com.example.demo.model.Contratista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContratistaRepository extends JpaRepository<Contratista, Integer>,
        ContratistaRepositoryCustom {

    Optional<Contratista> findByRfcContratista(String rfc);
    Optional<Contratista> findByCorreoContratista(String correo);
    boolean existsByRfcContratista(String rfc);
    boolean existsByCorreoContratista(String correo);
    List<Contratista> findByEstadoContratista(Contratista.EstadoContratista estado);
}
