/*
package com.example.testgritsee.data;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class DataSpecification {
    //Filtro x sucursal
    public static Specification<Data> contieneSucursal(String sucursal){
       return (root, query, criteriaBuilder) -> sucursal == null ? null
               : criteriaBuilder.like(criteriaBuilder.lower(root.get("url")), "%" + sucursal.toLowerCase() + "%");
    }

    //Filtro rango de fechas
    public static Specification<Data> entreFechas(LocalDate inicioFecha, LocalDate finFecha){
        return (root, query, criteriaBuilder) -> {
            if(inicioFecha != null && finFecha != null){
                return criteriaBuilder.between(root.get("fecha"), inicioFecha, finFecha);
            } else if (inicioFecha != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("fecha"), inicioFecha);
            } else if (finFecha != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("fecha"), finFecha);
            }
            return criteriaBuilder.conjunction();
        };
    }

    //Filtro fecha exacta
    public static Specification<Data> fechaExacta(LocalDate fecha){
        return (root, query, criteriaBuilder) -> fecha == null ? null
                : criteriaBuilder.equal(root.get("fecha"), fecha);
    }
}
*/