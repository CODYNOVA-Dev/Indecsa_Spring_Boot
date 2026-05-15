package com.example.demo.repository;

import com.example.demo.model.EstandarRendimiento;
import com.example.demo.model.EstandarRendimiento.UnidadMedida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstandarRendimientoRepository extends JpaRepository<EstandarRendimiento, Integer> {

    List<EstandarRendimiento> findByNombreActividadContainingIgnoreCase(String nombre);

    List<EstandarRendimiento> findByUnidadMedida(UnidadMedida unidadMedida);
}
