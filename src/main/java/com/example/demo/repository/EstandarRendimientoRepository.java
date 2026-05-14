package com.example.demo.repository;

import com.example.demo.model.EstandarRendimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstandarRendimientoRepository extends JpaRepository<EstandarRendimiento, Integer> {

    List<EstandarRendimiento> findByUnidadMedida(EstandarRendimiento.UnidadMedida unidadMedida);

    Optional<EstandarRendimiento> findByNombreActividad(String nombreActividad);
}
