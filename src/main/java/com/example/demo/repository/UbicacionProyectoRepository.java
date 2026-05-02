package com.example.demo.repository;

import com.example.demo.model.UbicacionProyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UbicacionProyectoRepository extends JpaRepository<UbicacionProyecto, Integer> {
    List<UbicacionProyecto> findByEstado(UbicacionProyecto.EntidadFederativa estado);
}
