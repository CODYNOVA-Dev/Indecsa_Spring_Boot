package com.example.demo.repository;

import com.example.demo.model.RegistroHoras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RegistroHorasRepository extends JpaRepository<RegistroHoras, Integer> {

    List<RegistroHoras> findByAsignacionTrabajadorProyecto_IdAsignacionTp(Integer idAsignacionTp);

    List<RegistroHoras> findByAsignacionTrabajadorProyecto_Proyecto_IdProyecto(Integer idProyecto);

    List<RegistroHoras> findByAsignacionTrabajadorProyecto_Trabajador_IdTrabajador(Integer idTrabajador);

    List<RegistroHoras> findByFechaRegistroBetween(LocalDate inicio, LocalDate fin);

    List<RegistroHoras> findByAsignacionTrabajadorProyecto_Proyecto_IdProyectoAndFechaRegistroBetween(
            Integer idProyecto, LocalDate inicio, LocalDate fin);

    boolean existsByAsignacionTrabajadorProyecto_IdAsignacionTpAndFechaRegistro(
            Integer idAsignacionTp, LocalDate fechaRegistro);
}
