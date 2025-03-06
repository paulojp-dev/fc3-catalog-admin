package org.fullcycle.catalog.admin.domain.validation.rule;

import java.util.Optional;

public abstract class Rule {

    public abstract <T> Optional<String> apply(final String attribute, final T value);
}
