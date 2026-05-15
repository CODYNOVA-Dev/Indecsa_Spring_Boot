package com.example.demo.repository;

import com.example.demo.model.Proyecto;
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
            String cliente,
            Pageable pageable
    ) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Proyecto> query = cb.createQuery(Proyecto.class);
        Root<Proyecto> root = query.from(Proyecto.class);
        List<Predicate> predicates = buildPredicates(cb, root, nombre, tipo, estatus, cliente);
        query.where(predicates.toArray(new Predicate[0]))
             .orderBy(cb.asc(root.get("nombreProyecto")));

        TypedQuery<Proyecto> typedQuery = em.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Proyecto> countRoot = countQuery.from(Proyecto.class);
        List<Predicate> countPredicates = buildPredicates(cb, countRoot, nombre, tipo, estatus, cliente);
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
        if (cliente != null && !cliente.isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("cliente")),
                    "%" + cliente.toLowerCase() + "%"));
        }

        return predicates;
    }
}
