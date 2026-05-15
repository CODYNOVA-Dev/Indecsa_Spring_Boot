package com.example.demo.repository;

import com.example.demo.model.Cuadrilla;
import com.example.demo.model.Cuadrilla.EstatusCuadrilla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuadrillaRepository extends JpaRepository<Cuadrilla, Integer> {

    List<Cuadrilla> findByProyecto_IdProyecto(Integer idProyecto);

    List<Cuadrilla> findByEstatusCuadrilla(EstatusCuadrilla estatus);

    boolean existsByNombreCuadrillaAndProyecto_IdProyecto(String nombreCuadrilla, Integer idProyecto);
}
