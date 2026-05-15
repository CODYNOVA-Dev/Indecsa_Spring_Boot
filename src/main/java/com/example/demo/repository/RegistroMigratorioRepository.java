package com.example.demo.repository;

import com.example.demo.model.RegistroMigratorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegistroMigratorioRepository extends JpaRepository<RegistroMigratorio, Integer> {

    Optional<RegistroMigratorio> findByFolioDocumento(String folioDocumento);

    boolean existsByFolioDocumento(String folioDocumento);
}
