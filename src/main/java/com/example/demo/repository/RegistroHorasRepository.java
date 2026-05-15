package com.example.demo.repository;

import com.example.demo.model.RegistroHoras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RegistroHorasRepository extends JpaRepository<RegistroHoras, Integer> {

    List<RegistroHoras> findByAsignacionTrabajadorProyecto_IdAsignacionTp(Integer idAsignacionTp);

    List<RegistroHoras> findByCuadrilla_IdCuadrilla(Integer idCuadrilla);

    List<RegistroHoras> findByFechaRegistro(LocalDate fechaRegistro);

    boolean existsByAsignacionTrabajadorProyecto_IdAsignacionTpAndFechaRegistro(
            Integer idAsignacionTp, LocalDate fechaRegistro);
}
