package com.example.demo.repository;

import com.example.demo.model.Proyecto;
import com.example.demo.model.Proyecto.EstatusProyecto;
import com.example.demo.model.Proyecto.UbicacionGeo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Integer> {

    List<Proyecto> findByEstatusProyecto(EstatusProyecto estatusProyecto);

    List<Proyecto> findByMunicipioProyectoIgnoreCase(String municipioProyecto);

    // Corregido: era findByEstadoProyectoGeoIgnoreCase(String) pero el campo
    // es ENUM('CDMX','Hidalgo','Puebla'), no un String. IgnoreCase no aplica a enums.
    List<Proyecto> findByEstadoProyectoGeo(UbicacionGeo estadoProyectoGeo);

    List<Proyecto> findByTipoProyectoIgnoreCase(String tipoProyecto);

    List<Proyecto> findByFechaEstimadaInicioAfter(LocalDate fecha);

    List<Proyecto> findByFechaEstimadaFinBefore(LocalDate fecha);

    boolean existsByNombreProyecto(String nombreProyecto);
}
