package com.indecsa.service.impl;

import com.indecsa.dto.proyecto.ProyectoRequest;
import com.indecsa.dto.proyecto.ProyectoResponse;
import com.indecsa.model.Proyecto;
import com.indecsa.model.UbicacionProyecto;
import com.indecsa.repository.ProyectoRepository;
import com.indecsa.repository.UbicacionProyectoRepository;
import com.indecsa.service.ProyectoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProyectoServiceImpl implements ProyectoService {

    private final ProyectoRepository proyectoRepository;
    private final UbicacionProyectoRepository ubicacionRepository;

    @Override
    public Page<ProyectoResponse> findByFiltros(
            String nombre,
            Proyecto.TipoProyecto tipo,
            Proyecto.EstatusProyecto estatus,
            Proyecto.EntidadFederativa estadoGeo,
            String cliente,
            Pageable pageable
    ) {
        return proyectoRepository
                .findByFiltros(nombre, tipo, estatus, estadoGeo, cliente, pageable)
                .map(ProyectoResponse::from);
    }

    @Override
    public ProyectoResponse findById(Integer id) {
        return ProyectoResponse.from(getOrThrow(id));
    }

    @Override
    @Transactional
    public ProyectoResponse create(ProyectoRequest request) {
        UbicacionProyecto ubicacion = ubicacionRepository.findById(request.getIdUbicacion())
                .orElseThrow(() -> new EntityNotFoundException("Ubicación no encontrada con id: " + request.getIdUbicacion()));

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
                .municipioProyecto(request.getMunicipioProyecto())
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

        UbicacionProyecto ubicacion = ubicacionRepository.findById(request.getIdUbicacion())
                .orElseThrow(() -> new EntityNotFoundException("Ubicación no encontrada con id: " + request.getIdUbicacion()));

        if (request.getFechaEstimadaFin() != null
                && request.getFechaEstimadaInicio() != null
                && request.getFechaEstimadaFin().isBefore(request.getFechaEstimadaInicio())) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio.");
        }

        proyecto.setNombreProyecto(request.getNombreProyecto());
        proyecto.setTipoProyecto(request.getTipoProyecto());
        proyecto.setOfertaTrabajo(request.getOfertaTrabajo());
        proyecto.setCliente(request.getCliente());
        proyecto.setUbicacion(ubicacion);
        proyecto.setMunicipioProyecto(request.getMunicipioProyecto());
        proyecto.setEstadoProyectoGeo(request.getEstadoProyectoGeo());
        proyecto.setFechaEstimadaInicio(request.getFechaEstimadaInicio());
        proyecto.setFechaEstimadaFin(request.getFechaEstimadaFin());
        proyecto.setCalificacionProyecto(request.getCalificacionProyecto());
        if (request.getEstatusProyecto() != null) {
            proyecto.setEstatusProyecto(request.getEstatusProyecto());
        }
        proyecto.setDescripcionProyecto(request.getDescripcionProyecto());

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
