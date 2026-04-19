package com.indecsa.repository.impl;

import com.indecsa.model.Proyecto;
import com.indecsa.repository.ProyectoRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProyectoRepositoryImpl implements ProyectoRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<Proyecto> findByFiltros(
            String nombre,
            Proyecto.TipoProyecto tipo,
            Proyecto.EstatusProyecto estatus,
            Proyecto.EntidadFederativa estadoGeo,
            String cliente,
            Pageable pageable
    ) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        // --- Query de datos ---
        CriteriaQuery<Proyecto> query = cb.createQuery(Proyecto.class);
        Root<Proyecto> root = query.from(Proyecto.class);
        root.fetch("ubicacion", JoinType.LEFT);
        List<Predicate> predicates = buildPredicates(cb, root, nombre, tipo, estatus, estadoGeo, cliente);
        query.where(predicates.toArray(new Predicate[0]))
             .orderBy(cb.asc(root.get("nombreProyecto")));

        TypedQuery<Proyecto> typedQuery = em.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        // --- Query de conteo (sin fetch para evitar duplicados) ---
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Proyecto> countRoot = countQuery.from(Proyecto.class);
        List<Predicate> countPredicates = buildPredicates(cb, countRoot, nombre, tipo, estatus, estadoGeo, cliente);
        countQuery.select(cb.count(countRoot)).where(countPredicates.toArray(new Predicate[0]));
        Long total = em.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(typedQuery.getResultList(), pageable, total);
    }

    private List<Predicate> buildPredicates(
            CriteriaBuilder cb,
            Root<Proyecto> root,
            String nombre,
            Proyecto.TipoProyecto tipo,
            Proyecto.EstatusProyecto estatus,
            Proyecto.EntidadFederativa estadoGeo,
            String cliente
    ) {
        List<Predicate> predicates = new ArrayList<>();

        if (nombre != null && !nombre.isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("nombreProyecto")),
                    "%" + nombre.toLowerCase() + "%"));
        }
        if (tipo != null) {
            predicates.add(cb.equal(root.get("tipoProyecto"), tipo));
        }
        if (estatus != null) {
            predicates.add(cb.equal(root.get("estatusProyecto"), estatus));
        }
        if (estadoGeo != null) {
            predicates.add(cb.equal(root.get("estadoProyectoGeo"), estadoGeo));
        }
        if (cliente != null && !cliente.isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("cliente")),
                    "%" + cliente.toLowerCase() + "%"));
        }

        return predicates;
    }
}
