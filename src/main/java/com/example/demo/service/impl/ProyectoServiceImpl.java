package com.example.demo.service.impl;

import com.example.demo.dto.request.ProyectoRequestDTO;
import com.example.demo.dto.response.ProyectoResponseDTO;
import com.example.demo.model.Proyecto;
import com.example.demo.repository.ProyectoRepository;
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

    @Override
    public List<ProyectoResponseDTO> findAll() {
        return proyectoRepository.findAll()
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<ProyectoResponseDTO> findByEstatus(Proyecto.EstatusProyecto estatus) {
        return proyectoRepository.findByEstatusProyecto(estatus)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<ProyectoResponseDTO> findByMunicipio(String municipio) {
        return proyectoRepository.findByMunicipioProyectoContainingIgnoreCase(municipio)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public ProyectoResponseDTO findById(Integer id) {
        return toResponse(getOrThrow(id));
    }

    @Override
    @Transactional
    public ProyectoResponseDTO create(ProyectoRequestDTO request) {
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

        return toResponse(proyectoRepository.save(proyecto));
    }

    @Override
    @Transactional
    public ProyectoResponseDTO update(Integer id, ProyectoRequestDTO request) {
        Proyecto proyecto = getOrThrow(id);

        if (request.getFechaEstimadaFin() != null
                && request.getFechaEstimadaInicio() != null
                && request.getFechaEstimadaFin().isBefore(request.getFechaEstimadaInicio())) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio.");
        }

        if (request.getNombreProyecto() != null)      proyecto.setNombreProyecto(request.getNombreProyecto());
        if (request.getTipoProyecto() != null)        proyecto.setTipoProyecto(request.getTipoProyecto());
        if (request.getOfertaTrabajo() != null)       proyecto.setOfertaTrabajo(request.getOfertaTrabajo());
        if (request.getCliente() != null)             proyecto.setCliente(request.getCliente());
        if (request.getMunicipioProyecto() != null)   proyecto.setMunicipioProyecto(request.getMunicipioProyecto());
        if (request.getEstadoProyectoGeo() != null)   proyecto.setEstadoProyectoGeo(request.getEstadoProyectoGeo());
        if (request.getFechaEstimadaInicio() != null) proyecto.setFechaEstimadaInicio(request.getFechaEstimadaInicio());
        if (request.getFechaEstimadaFin() != null)    proyecto.setFechaEstimadaFin(request.getFechaEstimadaFin());
        if (request.getCalificacionProyecto() != null) proyecto.setCalificacionProyecto(request.getCalificacionProyecto());
        if (request.getEstatusProyecto() != null)     proyecto.setEstatusProyecto(request.getEstatusProyecto());
        if (request.getDescripcionProyecto() != null) proyecto.setDescripcionProyecto(request.getDescripcionProyecto());

        return toResponse(proyectoRepository.save(proyecto));
    }

    @Override
    @Transactional
    public ProyectoResponseDTO cambiarEstatus(Integer id, Proyecto.EstatusProyecto estatus) {
        Proyecto proyecto = getOrThrow(id);
        proyecto.setEstatusProyecto(estatus);
        return toResponse(proyectoRepository.save(proyecto));
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

    public ProyectoResponseDTO toResponse(Proyecto p) {
        return ProyectoResponseDTO.builder()
                .idProyecto(p.getIdProyecto())
                .nombreProyecto(p.getNombreProyecto())
                .tipoProyecto(p.getTipoProyecto())
                .ofertaTrabajo(p.getOfertaTrabajo())
                .cliente(p.getCliente())
                .municipioProyecto(p.getMunicipioProyecto())
                .estadoProyectoGeo(p.getEstadoProyectoGeo())
                .fechaEstimadaInicio(p.getFechaEstimadaInicio())
                .fechaEstimadaFin(p.getFechaEstimadaFin())
                .calificacionProyecto(p.getCalificacionProyecto())
                .estatusProyecto(p.getEstatusProyecto())
                .descripcionProyecto(p.getDescripcionProyecto())
                .build();
    }
}
