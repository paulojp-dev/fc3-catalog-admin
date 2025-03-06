package org.fullcycle.catalog.admin.domain.validation;

public class Message {

    public static final String NOT_NULL = "'${attribute}' should not be null";
    public static final String NOT_EMPTY = "'${attribute}' should not be empty";

    public static String resolve(final String field, final String message) {
        return message.replace("${attribute}", field);
    }
}
