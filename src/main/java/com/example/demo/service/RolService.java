package com.example.demo.service;

import com.example.demo.dto.rol.RolRequest;
import com.example.demo.dto.rol.RolResponse;

import java.util.List;

public interface RolService {
    List<RolResponse> findAll();
    RolResponse findById(Integer id);
    RolResponse create(RolRequest request);
    RolResponse update(Integer id, RolRequest request);
    void delete(Integer id);
}
