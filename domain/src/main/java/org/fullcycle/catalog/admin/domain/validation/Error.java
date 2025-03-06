package org.fullcycle.catalog.admin.domain.validation;

import java.util.List;

public record Error(String field, List<String> messages) {
}
