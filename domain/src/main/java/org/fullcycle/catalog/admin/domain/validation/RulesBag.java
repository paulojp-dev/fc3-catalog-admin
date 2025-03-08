package org.fullcycle.catalog.admin.domain.validation;

import org.fullcycle.catalog.admin.domain.validation.rule.Min;
import org.fullcycle.catalog.admin.domain.validation.rule.Required;
import org.fullcycle.catalog.admin.domain.validation.rule.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RulesBag {

    private final String field;
    private final List<Rule> rules;
    private String message;

    public RulesBag(final String field) {
        this.field = field;
        rules = new ArrayList<>();
    }

    public RulesBag required() {
        rules.add(new Required());
        return this;
    }

    public RulesBag min(final Integer size) {
        rules.add(new Min(size));
        return this;
    }

    public <T> void apply(final T value) {
        for (Rule rule : rules) {
            if (hasError()) {
                break;
            }
            Optional<String> message = rule.apply(field, value);
            message.ifPresent(s -> this.message = s);
        }
    }

    public Boolean hasError() {
        return message != null;
    }

    public String getMessage() {
        return message;
    }

    public String getField() {
        return field;
    }
}
