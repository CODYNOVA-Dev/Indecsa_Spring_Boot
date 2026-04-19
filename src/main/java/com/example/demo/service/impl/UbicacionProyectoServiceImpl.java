package com.indecsa.service.impl;

import com.indecsa.dto.ubicacion.UbicacionProyectoRequest;
import com.indecsa.dto.ubicacion.UbicacionProyectoResponse;
import com.indecsa.model.UbicacionProyecto;
import com.indecsa.repository.UbicacionProyectoRepository;
import com.indecsa.service.UbicacionProyectoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UbicacionProyectoServiceImpl implements UbicacionProyectoService {

    private final UbicacionProyectoRepository ubicacionRepository;

    @Override
    public List<UbicacionProyectoResponse> findAll() {
        return ubicacionRepository.findAll()
                .stream()
                .map(UbicacionProyectoResponse::from)
                .toList();
    }

    @Override
    public List<UbicacionProyectoResponse> findByEstado(UbicacionProyecto.EntidadFederativa estado) {
        return ubicacionRepository.findByEstado(estado)
                .stream()
                .map(UbicacionProyectoResponse::from)
                .toList();
    }

    @Override
    public UbicacionProyectoResponse findById(Integer id) {
        return UbicacionProyectoResponse.from(getOrThrow(id));
    }

    @Override
    @Transactional
    public UbicacionProyectoResponse create(UbicacionProyectoRequest request) {
        UbicacionProyecto ubicacion = UbicacionProyecto.builder()
                .calle(request.getCalle())
                .numExt(request.getNumExt())
                .numInt(request.getNumInt())
                .colonia(request.getColonia())
                .codPost(request.getCodPost())
                .munAlc(request.getMunAlc())
                .estado(request.getEstado())
                .build();
        return UbicacionProyectoResponse.from(ubicacionRepository.save(ubicacion));
    }

    @Override
    @Transactional
    public UbicacionProyectoResponse update(Integer id, UbicacionProyectoRequest request) {
        UbicacionProyecto ubicacion = getOrThrow(id);
        ubicacion.setCalle(request.getCalle());
        ubicacion.setNumExt(request.getNumExt());
        ubicacion.setNumInt(request.getNumInt());
        ubicacion.setColonia(request.getColonia());
        ubicacion.setCodPost(request.getCodPost());
        ubicacion.setMunAlc(request.getMunAlc());
        ubicacion.setEstado(request.getEstado());
        return UbicacionProyectoResponse.from(ubicacionRepository.save(ubicacion));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        getOrThrow(id);
        ubicacionRepository.deleteById(id);
    }

    private UbicacionProyecto getOrThrow(Integer id) {
        return ubicacionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ubicación no encontrada con id: " + id));
    }
}
