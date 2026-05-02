package com.example.demo.service.impl;

import com.example.demo.dto.proyecto.ProyectoRequest;
import com.example.demo.dto.proyecto.ProyectoResponse;
import com.example.demo.model.Proyecto;
import com.example.demo.model.UbicacionProyecto;
import com.example.demo.repository.ProyectoRepository;
import com.example.demo.repository.UbicacionProyectoRepository;
import com.example.demo.service.ProyectoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProyectoServiceImpl implements ProyectoService {

    private final ProyectoRepository proyectoRepository;
    private final UbicacionProyectoRepository ubicacionRepository;

    @Override
    public List<ProyectoResponse> findAll() {
        return proyectoRepository.findAll()
                .stream().map(ProyectoResponse::from).collect(Collectors.toList());
    }

    @Override
    public List<ProyectoResponse> findByEstatus(Proyecto.EstatusProyecto estatus) {
        return proyectoRepository.findByEstatusProyecto(estatus)
                .stream().map(ProyectoResponse::from).collect(Collectors.toList());
    }

    @Override
    public List<ProyectoResponse> findByMunicipio(String municipio) {
        return proyectoRepository.findByMunicipioProyectoContainingIgnoreCase(municipio)
                .stream().map(ProyectoResponse::from).collect(Collectors.toList());
    }

    @Override
    public ProyectoResponse findById(Integer id) {
        return ProyectoResponse.from(getOrThrow(id));
    }

    @Override
    @Transactional
    public ProyectoResponse create(ProyectoRequest request) {
        UbicacionProyecto ubicacion = null;
        if (request.getIdUbicacion() != null) {
            ubicacion = ubicacionRepository.findById(request.getIdUbicacion())
                    .orElse(null);
        }

        if (request.getFechaEstimadaFin() != null
                && request.getFechaEstimadaInicio() != null
                && request.getFechaEstimadaFin().isBefore(request.getFechaEstimadaInicio())) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio.");
        }

        Proyecto proyecto = Proyecto.builder()
                .nombreProyecto(request.getNombreProyecto())
                .tipoProyecto(request.getTipoProyecto())
                .ofertaTrabajo(request.getOfertaTrabajo())
                .cliente(request.getCliente())
                .ubicacion(ubicacion)
                .municipioProyecto(request.getLugarProyecto())
                .estadoProyectoGeo(request.getEstadoProyectoGeo())
                .fechaEstimadaInicio(request.getFechaEstimadaInicio())
                .fechaEstimadaFin(request.getFechaEstimadaFin())
                .calificacionProyecto(request.getCalificacionProyecto())
                .estatusProyecto(request.getEstatusProyecto() != null
                        ? request.getEstatusProyecto()
                        : Proyecto.EstatusProyecto.PLANEACION)
                .descripcionProyecto(request.getDescripcionProyecto())
                .build();

        return ProyectoResponse.from(proyectoRepository.save(proyecto));
    }

    @Override
    @Transactional
    public ProyectoResponse update(Integer id, ProyectoRequest request) {
        Proyecto proyecto = getOrThrow(id);

        UbicacionProyecto ubicacion = null;
        if (request.getIdUbicacion() != null) {
            ubicacion = ubicacionRepository.findById(request.getIdUbicacion()).orElse(null);
        }

        if (request.getFechaEstimadaFin() != null
                && request.getFechaEstimadaInicio() != null
                && request.getFechaEstimadaFin().isBefore(request.getFechaEstimadaInicio())) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio.");
        }

        if (request.getNombreProyecto() != null) proyecto.setNombreProyecto(request.getNombreProyecto());
        if (request.getTipoProyecto() != null)   proyecto.setTipoProyecto(request.getTipoProyecto());
        if (request.getOfertaTrabajo() != null)  proyecto.setOfertaTrabajo(request.getOfertaTrabajo());
        if (request.getCliente() != null)        proyecto.setCliente(request.getCliente());
        if (ubicacion != null)                   proyecto.setUbicacion(ubicacion);
        if (request.getLugarProyecto() != null)  proyecto.setMunicipioProyecto(request.getLugarProyecto());
        if (request.getEstadoProyectoGeo() != null) proyecto.setEstadoProyectoGeo(request.getEstadoProyectoGeo());
        if (request.getFechaEstimadaInicio() != null) proyecto.setFechaEstimadaInicio(request.getFechaEstimadaInicio());
        if (request.getFechaEstimadaFin() != null)    proyecto.setFechaEstimadaFin(request.getFechaEstimadaFin());
        if (request.getCalificacionProyecto() != null) proyecto.setCalificacionProyecto(request.getCalificacionProyecto());
        if (request.getEstatusProyecto() != null)     proyecto.setEstatusProyecto(request.getEstatusProyecto());
        if (request.getDescripcionProyecto() != null) proyecto.setDescripcionProyecto(request.getDescripcionProyecto());

        return ProyectoResponse.from(proyectoRepository.save(proyecto));
    }

    @Override
    @Transactional
    public ProyectoResponse cambiarEstatus(Integer id, Proyecto.EstatusProyecto estatus) {
        Proyecto proyecto = getOrThrow(id);
        proyecto.setEstatusProyecto(estatus);
        return ProyectoResponse.from(proyectoRepository.save(proyecto));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        getOrThrow(id);
        proyectoRepository.deleteById(id);
    }

    private Proyecto getOrThrow(Integer id) {
        return proyectoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proyecto no encontrado con id: " + id));
    }
}
