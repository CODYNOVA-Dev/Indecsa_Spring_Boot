package com.indecsa.repository;

import com.indecsa.model.AsignacionProyectoContratista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AsignacionProyectoContratistaRepository
        extends JpaRepository<AsignacionProyectoContratista, Integer> {

    List<AsignacionProyectoContratista> findByProyecto_IdProyecto(Integer idProyecto);
    List<AsignacionProyectoContratista> findByContratista_IdContratista(Integer idContratista);
    Optional<AsignacionProyectoContratista> findByProyecto_IdProyectoAndContratista_IdContratista(
            Integer idProyecto, Integer idContratista);
    boolean existsByProyecto_IdProyectoAndContratista_IdContratista(
            Integer idProyecto, Integer idContratista);
}
