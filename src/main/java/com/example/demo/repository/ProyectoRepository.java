package com.example.demo.repository;

import com.example.demo.model.Proyecto;
import com.example.demo.model.Proyecto.EstatusProyecto;
import com.example.demo.model.Proyecto.TipoProyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Integer>,
        ProyectoRepositoryCustom {

    List<Proyecto> findByEstatusProyecto(EstatusProyecto estatusProyecto);

    List<Proyecto> findByTipoProyecto(TipoProyecto tipoProyecto);

    List<Proyecto> findByClienteContainingIgnoreCase(String cliente);

    List<Proyecto> findByFechaEstimadaInicioAfter(LocalDate fecha);

    List<Proyecto> findByFechaEstimadaFinBefore(LocalDate fecha);

    boolean existsByNombreProyecto(String nombreProyecto);
}
