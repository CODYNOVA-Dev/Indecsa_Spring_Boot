package com.example.demo.service;

import com.example.demo.dto.migratorio.RegistroMigratorioRequest;
import com.example.demo.dto.migratorio.RegistroMigratorioResponse;

import java.util.List;

public interface RegistroMigratorioService {
    List<RegistroMigratorioResponse> findAll();
    RegistroMigratorioResponse findById(Integer id);
    RegistroMigratorioResponse create(RegistroMigratorioRequest request);
    RegistroMigratorioResponse update(Integer id, RegistroMigratorioRequest request);
    void delete(Integer id);
}
