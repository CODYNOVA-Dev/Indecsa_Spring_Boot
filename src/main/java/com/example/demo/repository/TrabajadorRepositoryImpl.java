package com.indecsa.repository.impl;

import com.indecsa.model.Trabajador;
import com.indecsa.repository.TrabajadorRepositoryCustom;
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
public class TrabajadorRepositoryImpl implements TrabajadorRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<Trabajador> findByFiltros(
            String nombre,
            Trabajador.EstadoTrabajador estado,
            String especialidad,
            String puesto,
            Trabajador.EntidadFederativa calidadVida,
            Pageable pageable
    ) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        // --- Query de datos ---
        CriteriaQuery<Trabajador> query = cb.createQuery(Trabajador.class);
        Root<Trabajador> root = query.from(Trabajador.class);
        List<Predicate> predicates = buildPredicates(cb, root, nombre, estado, especialidad, puesto, calidadVida);
        query.where(predicates.toArray(new Predicate[0]))
             .orderBy(cb.asc(root.get("nombreTrabajador")));

        TypedQuery<Trabajador> typedQuery = em.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        // --- Query de conteo ---
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Trabajador> countRoot = countQuery.from(Trabajador.class);
        List<Predicate> countPredicates = buildPredicates(cb, countRoot, nombre, estado, especialidad, puesto, calidadVida);
        countQuery.select(cb.count(countRoot)).where(countPredicates.toArray(new Predicate[0]));
        Long total = em.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(typedQuery.getResultList(), pageable, total);
    }

    private List<Predicate> buildPredicates(
            CriteriaBuilder cb,
            Root<Trabajador> root,
            String nombre,
            Trabajador.EstadoTrabajador estado,
            String especialidad,
            String puesto,
            Trabajador.EntidadFederativa calidadVida
    ) {
        List<Predicate> predicates = new ArrayList<>();

        if (nombre != null && !nombre.isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("nombreTrabajador")),
                    "%" + nombre.toLowerCase() + "%"));
        }
        if (estado != null) {
            predicates.add(cb.equal(root.get("estadoTrabajador"), estado));
        }
        if (especialidad != null && !especialidad.isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("especialidadTrabajador")),
                    "%" + especialidad.toLowerCase() + "%"));
        }
        if (puesto != null && !puesto.isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("puesto")),
                    "%" + puesto.toLowerCase() + "%"));
        }
        if (calidadVida != null) {
            predicates.add(cb.equal(root.get("calidadVida"), calidadVida));
        }

        return predicates;
    }
}
