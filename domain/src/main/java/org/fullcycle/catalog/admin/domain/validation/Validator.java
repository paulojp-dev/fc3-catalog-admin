package org.fullcycle.catalog.admin.domain.validation;

import org.fullcycle.catalog.admin.domain.exception.DomainValidationException;

import java.util.ArrayList;
import java.util.List;

public abstract class Validator {

    private final List<Error> errors;
    private final List<RulesBag> rulesBags;

    protected Validator() {
        errors = new ArrayList<>();
        rulesBags = new ArrayList<>();
    }

    public abstract void validate();

    protected RulesBag setRules(final String field) {
        RulesBag rulesBag = new RulesBag(field);
        rulesBags.add(rulesBag);
        return rulesBag;
    }

    public void throwIfErrors() {
        for (RulesBag rulesBag : rulesBags) {
            String message = rulesBag.getMessage();
            if (message != null) {
                errors.add(new Error(rulesBag.getField(), message));
            }
        }
        if (!errors.isEmpty()) {
            throw new DomainValidationException(errors);
        }
    }
}
