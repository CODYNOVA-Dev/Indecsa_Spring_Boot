package com.indecsa.repository;

import com.indecsa.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
    Optional<Empleado> findByCorreoEmpleado(String correoEmpleado);
    boolean existsByCorreoEmpleado(String correoEmpleado);
    boolean existsByCurp(String curp);
}
