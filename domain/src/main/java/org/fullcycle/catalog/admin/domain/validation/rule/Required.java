package org.fullcycle.catalog.admin.domain.validation.rule;

import org.fullcycle.catalog.admin.domain.validation.Message;

import java.util.Optional;

public final class Required extends Rule {

    @Override
    public <T> Optional<String> apply(final String attribute, final T value) {
        if (value == null) {
            return Optional.of(Message.resolve(attribute, Message.NOT_NULL));
        }
        if (value instanceof String && ((String) value).isBlank()) {
            return Optional.of(Message.resolve(attribute, Message.NOT_EMPTY));
        }
        return Optional.empty();
    }
}
