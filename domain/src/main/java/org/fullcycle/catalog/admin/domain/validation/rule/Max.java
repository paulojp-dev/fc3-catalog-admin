package org.fullcycle.catalog.admin.domain.validation.rule;

import org.fullcycle.catalog.admin.domain.validation.Message;

import java.util.Optional;

public class Max extends Rule {

    private final Integer size;

    public Max(final Integer size) {
        this.size = size;
    }

    @Override
    public <T> Optional<String> apply(String attribute, T value) {
        if (value instanceof String && ((String) value).trim().length() > size) {
            return Optional.of(Message.resolve(attribute, Message.MAX_STRING, size));
        }
        return Optional.empty();
    }
}
