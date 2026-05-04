package com.example.demo.repository;

import com.example.demo.model.AvancePartida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AvancePartidaRepository extends JpaRepository<AvancePartida, Integer> {

    List<AvancePartida> findByProyecto_IdProyecto(Integer idProyecto);

    List<AvancePartida> findByProyecto_IdProyectoAndFechaRegistroBetween(
            Integer idProyecto, LocalDate inicio, LocalDate fin);

    List<AvancePartida> findByCuadrilla_IdCuadrilla(Integer idCuadrilla);
}
