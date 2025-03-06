package org.fullcycle.catalog.admin.domain.validation;

import org.fullcycle.catalog.admin.domain.validation.rule.Required;
import org.fullcycle.catalog.admin.domain.validation.rule.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RulesBag {

    private final String field;
    private final List<Rule> rules;
    private final List<String> messages;

    public RulesBag(final String field) {
        this.field = field;
        rules = new ArrayList<>();
        messages = new ArrayList<>();
    }

    public RulesBag required() {
        rules.add(new Required());
        return this;
    }

    public <T> void apply(final T value) {
        for (Rule rule : rules) {
            Optional<String> message = rule.apply(field, value);
            if (message.isPresent()) {
                messages.add(message.get());
            }
        }
    }

    public List<String> getMessages() {
        return messages;
    }

    public String getField() {
        return field;
    }
}
