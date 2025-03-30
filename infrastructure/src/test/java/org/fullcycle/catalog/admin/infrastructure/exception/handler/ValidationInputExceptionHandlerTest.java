package org.fullcycle.catalog.admin.infrastructure.exception.handler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

public class ValidationInputExceptionHandlerTest {

    private final ValidationInputExceptionHandler handler = new ValidationInputExceptionHandler();

    @Test
    public void givenAMethodArgumentNotValidException_whenHandled_shouldReturnErrorsMap() throws Exception {
        final var exception = Mockito.mock(MethodArgumentNotValidException.class);
        final var bindingResult = Mockito.mock(BindingResult.class);
        Mockito.when(exception.getBindingResult()).thenReturn(bindingResult);
        Mockito.when(bindingResult.getFieldErrors()).thenReturn(
            List.of(
                new FieldError("name", "name", "Name cannot be null"),
                new FieldError("is_active", "is_active", "Active flag cannot be null")
            )
        );
        final var response = handler.handleValidationExceptions(exception);
        Assertions.assertEquals("Name cannot be null", response.get("name"));
        Assertions.assertEquals("Active flag cannot be null", response.get("is_active"));
    }
}
