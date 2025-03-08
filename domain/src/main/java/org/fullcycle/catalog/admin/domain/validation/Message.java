package org.fullcycle.catalog.admin.domain.validation;

public class Message {

    public static final String NOT_NULL = "'${attribute}' should not be null";
    public static final String NOT_EMPTY = "'${attribute}' should not be empty";
    public static final String MIN_STRING = "'${attribute}' must be longer than '${param}' characters";
    public static final String MAX_STRING = "'${attribute}' cannot be longer than '${param}' characters";

    public static String resolve(final String attribute, final String message) {
        return message.replace("${attribute}", attribute);
    }

    public static String resolve(final String attribute, final String message, Integer param) {
        return Message
                .resolve(attribute, message)
                .replace("${param}", param.toString());
    }
}
