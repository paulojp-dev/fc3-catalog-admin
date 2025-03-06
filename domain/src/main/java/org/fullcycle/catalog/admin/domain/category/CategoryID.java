package org.fullcycle.catalog.admin.domain.category;

import org.fullcycle.catalog.admin.domain.base.Identifier;

import java.util.Objects;
import java.util.UUID;

public class CategoryID extends Identifier {

    private final String $value;

    private CategoryID(final String value) {
        Objects.requireNonNull(value, "Value cannot be null");
        this.$value = value;
    }

    public static CategoryID of(final String id) {
        return new CategoryID(id);
    }

    public static CategoryID of(final UUID id) {
        return new CategoryID(id.toString().toLowerCase());
    }

    public static CategoryID unique() {
        return CategoryID.of(UUID.randomUUID());
    }

    public String get$value() {
        return $value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final CategoryID that = (CategoryID) o;
        return Objects.equals(get$value(), that.get$value());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(get$value());
    }
}
