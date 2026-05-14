package com.example.demo.repository;

import com.example.demo.model.Cuadrilla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuadrillaRepository extends JpaRepository<Cuadrilla, Integer> {

    List<Cuadrilla> findByProyecto_IdProyecto(Integer idProyecto);

    List<Cuadrilla> findByProyecto_IdProyectoAndEstatusCuadrilla(
            Integer idProyecto, Cuadrilla.EstatusCuadrilla estatusCuadrilla);

    boolean existsByNombreCuadrillaAndProyecto_IdProyecto(String nombreCuadrilla, Integer idProyecto);
}
