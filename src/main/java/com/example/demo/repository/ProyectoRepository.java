package com.indecsa.repository;

import com.indecsa.model.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Integer>,
        ProyectoRepositoryCustom {

    List<Proyecto> findByEstatusProyecto(Proyecto.EstatusProyecto estatus);
    boolean existsByNombreProyectoAndCliente(String nombre, String cliente);
}
