package org.fullcycle.catalog.admin.domain.base;

import java.util.Objects;

public abstract class ID extends Identifier {

    private final String $value;

    protected ID(final String value) {
        Objects.requireNonNull(value, "Value cannot be null");
        this.$value = value;
    }

    public String getValue() {
        return $value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ID that = (ID) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }

    protected static String makeUUID() {
        return java.util.UUID.randomUUID().toString();
    }
}
