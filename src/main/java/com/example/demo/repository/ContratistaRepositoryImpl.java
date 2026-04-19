package com.indecsa.repository.impl;

import com.indecsa.model.Contratista;
import com.indecsa.repository.ContratistaRepositoryCustom;
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
public class ContratistaRepositoryImpl implements ContratistaRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<Contratista> findByFiltros(
            String nombre,
            Contratista.EstadoContratista estado,
            Contratista.EntidadFederativa ubicacion,
            Byte calificacionMin,
            Pageable pageable
    ) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        // --- Query de datos ---
        CriteriaQuery<Contratista> query = cb.createQuery(Contratista.class);
        Root<Contratista> root = query.from(Contratista.class);
        List<Predicate> predicates = buildPredicates(cb, root, nombre, estado, ubicacion, calificacionMin);
        query.where(predicates.toArray(new Predicate[0]))
             .orderBy(cb.asc(root.get("nombreContratista")));

        TypedQuery<Contratista> typedQuery = em.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        // --- Query de conteo ---
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Contratista> countRoot = countQuery.from(Contratista.class);
        List<Predicate> countPredicates = buildPredicates(cb, countRoot, nombre, estado, ubicacion, calificacionMin);
        countQuery.select(cb.count(countRoot)).where(countPredicates.toArray(new Predicate[0]));
        Long total = em.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(typedQuery.getResultList(), pageable, total);
    }

    private List<Predicate> buildPredicates(
            CriteriaBuilder cb,
            Root<Contratista> root,
            String nombre,
            Contratista.EstadoContratista estado,
            Contratista.EntidadFederativa ubicacion,
            Byte calificacionMin
    ) {
        List<Predicate> predicates = new ArrayList<>();

        if (nombre != null && !nombre.isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("nombreContratista")),
                    "%" + nombre.toLowerCase() + "%"));
        }
        if (estado != null) {
            predicates.add(cb.equal(root.get("estadoContratista"), estado));
        }
        if (ubicacion != null) {
            predicates.add(cb.equal(root.get("ubicacionContratista"), ubicacion));
        }
        if (calificacionMin != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("calificacionContratista"), calificacionMin));
        }

        return predicates;
    }
}
