package com.example.demo.service.impl;

import com.example.demo.dto.request.ProyectoRequestDTO;
import com.example.demo.dto.response.DomicilioResponseDTO;
import com.example.demo.dto.response.ProyectoResponseDTO;
import com.example.demo.model.Domicilio;
import com.example.demo.model.Proyecto;
import com.example.demo.model.Proyecto.EstatusProyecto;
import com.example.demo.model.Proyecto.TipoProyecto;
import com.example.demo.repository.DomicilioRepository;
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
public class ProyectoServiceImpl implements ProyectoService {

    private final ProyectoRepository proyectoRepository;
    private final DomicilioRepository domicilioRepository;

    // ─── FIND ALL ─────────────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<ProyectoResponseDTO> findAll() {
        return proyectoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─── FIND BY ID ───────────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public ProyectoResponseDTO findById(Integer id) {
        return toResponse(getProyectoOrThrow(id));
    }

    // ─── FIND BY ESTATUS ──────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<ProyectoResponseDTO> findByEstatus(EstatusProyecto estatus) {
        return proyectoRepository.findByEstatusProyecto(estatus)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─── FIND BY TIPO ─────────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<ProyectoResponseDTO> findByTipo(TipoProyecto tipo) {
        return proyectoRepository.findByTipoProyecto(tipo)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─── FIND BY CLIENTE ──────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<ProyectoResponseDTO> findByCliente(String cliente) {
        return proyectoRepository.findByClienteContainingIgnoreCase(cliente)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─── CREATE ───────────────────────────────────────────────────────────────
    @Override
    @Transactional
    public ProyectoResponseDTO create(ProyectoRequestDTO dto) {
        validarFechas(dto);
        Domicilio domicilio = getDomicilioOrThrow(dto.getIdDomicilio());
        Proyecto proyecto = new Proyecto();
        mapDtoToEntity(dto, proyecto, domicilio);
        proyecto.setEstatusProyecto(EstatusProyecto.PLANEACION);
        return toResponse(proyectoRepository.save(proyecto));
    }

    // ─── UPDATE ───────────────────────────────────────────────────────────────
    @Override
    @Transactional
    public ProyectoResponseDTO update(Integer id, ProyectoRequestDTO dto) {
        Proyecto proyecto = getProyectoOrThrow(id);
        validarFechas(dto);
        Domicilio domicilio = getDomicilioOrThrow(dto.getIdDomicilio());
        mapDtoToEntity(dto, proyecto, domicilio);
        return toResponse(proyectoRepository.save(proyecto));
    }

    // ─── CAMBIAR ESTATUS ──────────────────────────────────────────────────────
    @Override
    @Transactional
    public ProyectoResponseDTO cambiarEstatus(Integer id, EstatusProyecto estatus) {
        Proyecto proyecto = getProyectoOrThrow(id);
        proyecto.setEstatusProyecto(estatus);
        return toResponse(proyectoRepository.save(proyecto));
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────
    @Override
    @Transactional
    public void delete(Integer id) {
        if (!proyectoRepository.existsById(id)) {
            throw new EntityNotFoundException("Proyecto no encontrado con id: " + id);
        }
        proyectoRepository.deleteById(id);
    }

    // ─── HELPERS ──────────────────────────────────────────────────────────────
    private Proyecto getProyectoOrThrow(Integer id) {
        return proyectoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Proyecto no encontrado con id: " + id));
    }

    private Domicilio getDomicilioOrThrow(Integer id) {
        return domicilioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Domicilio no encontrado con id: " + id));
    }

    private void validarFechas(ProyectoRequestDTO dto) {
        if (dto.getFechaEstimadaInicio() != null && dto.getFechaEstimadaFin() != null
                && dto.getFechaEstimadaFin().isBefore(dto.getFechaEstimadaInicio())) {
            throw new IllegalArgumentException(
                    "La fecha de fin estimada no puede ser anterior a la fecha de inicio.");
        }
    }

    private void mapDtoToEntity(ProyectoRequestDTO dto, Proyecto proyecto, Domicilio domicilio) {
        proyecto.setNombreProyecto(dto.getNombreProyecto());
        proyecto.setTipoProyecto(dto.getTipoProyecto());
        proyecto.setOfertaTrabajo(dto.getOfertaTrabajo());
        proyecto.setCliente(dto.getCliente());
        proyecto.setDomicilio(domicilio);
        proyecto.setFechaEstimadaInicio(dto.getFechaEstimadaInicio());
        proyecto.setFechaEstimadaFin(dto.getFechaEstimadaFin());
        proyecto.setCalificacionProyecto(dto.getCalificacionProyecto());
        proyecto.setDescripcionProyecto(dto.getDescripcionProyecto());
    }

    // ─── MAPPER ───────────────────────────────────────────────────────────────
    public ProyectoResponseDTO toResponse(Proyecto p) {
        DomicilioResponseDTO domicilioDTO = DomicilioResponseDTO.builder()
                .idDomicilio(p.getDomicilio().getIdDomicilio())
                .calle(p.getDomicilio().getCalle())
                .numExt(p.getDomicilio().getNumExt())
                .numInt(p.getDomicilio().getNumInt())
                .colonia(p.getDomicilio().getColonia())
                .codPost(p.getDomicilio().getCodPost())
                .munAlc(p.getDomicilio().getMunAlc())
                .idEstado(p.getDomicilio().getEstado().getIdEstado())
                .nombreEstado(p.getDomicilio().getEstado().getNombreEst())
                .build();

        return ProyectoResponseDTO.builder()
                .idProyecto(p.getIdProyecto())
                .nombreProyecto(p.getNombreProyecto())
                .tipoProyecto(p.getTipoProyecto())
                .ofertaTrabajo(p.getOfertaTrabajo())
                .cliente(p.getCliente())
                .domicilio(domicilioDTO)
                .fechaEstimadaInicio(p.getFechaEstimadaInicio())
                .fechaEstimadaFin(p.getFechaEstimadaFin())
                .calificacionProyecto(p.getCalificacionProyecto())
                .estatusProyecto(p.getEstatusProyecto())
                .descripcionProyecto(p.getDescripcionProyecto())
                .build();
    }
}
