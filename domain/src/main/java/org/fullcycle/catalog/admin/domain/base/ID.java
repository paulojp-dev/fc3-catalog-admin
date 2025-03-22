package org.fullcycle.catalog.admin.domain.base;

import java.util.Objects;

public class ID extends Identifier {

    private final String $value;

    private ID(final String value) {
        Objects.requireNonNull(value, "Value cannot be null");
        this.$value = value;
    }

    public static ID of(final String id) {
        return new ID(id);
    }

    public static ID of(final java.util.UUID id) {
        return new ID(id.toString().toLowerCase());
    }

    public static ID unique() {
        return ID.of(java.util.UUID.randomUUID());
    }

    public String getValue() {
        return $value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final ID that = (ID) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }
}
