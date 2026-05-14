package com.example.demo.repository;

import com.example.demo.model.Trabajador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrabajadorRepository extends JpaRepository<Trabajador, Integer>,
        TrabajadorRepositoryCustom {

    Optional<Trabajador> findByCurp(String curp);
    Optional<Trabajador> findByRfc(String rfc);
    Optional<Trabajador> findByCorreoTrabajador(String correo);
    boolean existsByCurp(String curp);
    boolean existsByRfc(String rfc);
    boolean existsByCorreoTrabajador(String correo);
    List<Trabajador> findByEstadoTrabajador(Trabajador.EstadoTrabajador estado);
    List<Trabajador> findByEspecialidadTrabajadorContainingIgnoreCase(String especialidad);
}
