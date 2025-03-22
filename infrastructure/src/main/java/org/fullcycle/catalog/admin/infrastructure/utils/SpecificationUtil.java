package org.fullcycle.catalog.admin.infrastructure.utils;

import org.springframework.data.jpa.domain.Specification;

public final class SpecificationUtil {

    public SpecificationUtil() {
    }

    public static <T> Specification<T> like(final String property, final String terms) {
        return (root, query, cb) -> cb.like(cb.upper(root.get("name")), like(terms.toUpperCase()));
    }

    public static <T> Specification<T> conjunction() {
        return (root, query, cb) -> cb.conjunction();
    }

    private static String like(String terms) {
        return "%" + terms + "%";
    }
}
