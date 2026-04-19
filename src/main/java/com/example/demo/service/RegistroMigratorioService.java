package com.indecsa.service;

import com.indecsa.dto.migratorio.RegistroMigratorioRequest;
import com.indecsa.dto.migratorio.RegistroMigratorioResponse;

import java.util.List;

public interface RegistroMigratorioService {
    List<RegistroMigratorioResponse> findAll();
    RegistroMigratorioResponse findById(Integer id);
    RegistroMigratorioResponse create(RegistroMigratorioRequest request);
    RegistroMigratorioResponse update(Integer id, RegistroMigratorioRequest request);
    void delete(Integer id);
}
