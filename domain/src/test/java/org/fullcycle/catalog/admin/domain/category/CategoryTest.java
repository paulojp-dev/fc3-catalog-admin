package org.fullcycle.catalog.admin.domain.category;

import org.fullcycle.catalog.admin.domain.exception.DomainException;
import org.fullcycle.catalog.admin.domain.validation.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class CategoryTest {

    @Test
    public void givenValidParams_whenNewCategory_thenInstantiateACategory() {
        final var name = "Category";
        final var description = "Description";
        final var isActive = true;

        Category category = Category.from(name, description, isActive);

        Assertions.assertNotNull(category.getId());
        Assertions.assertEquals(name, category.getName());
        Assertions.assertEquals(description, category.getDescription());
        Assertions.assertEquals(isActive, category.isActive());
        Assertions.assertNotNull(category.getCreatedAt());
        Assertions.assertNotNull(category.getUpdatedAt());
        Assertions.assertTrue(category.getDeletedAt().isEmpty());
    }

    @Test
    public void givenInvalidParams_whenNewCategory_thenThrowsException() {
        final String name = null;
        final var description = "Description";
        final var isActive = true;
        final var errorMessage = "'name' should not be null";
        final var errorCount = 1;

        final var category = Category.from(name, description, isActive);
        Executable executable = () -> category.validate(new ThrowsValidationHandler());

        final var exception = Assertions.assertThrows(DomainException.class, executable);
        Assertions.assertEquals(errorCount, exception.getErrors().size());
        Assertions.assertEquals(errorMessage, exception.getErrors().get(0).message());
    }

    public static Object[][] invalidParamsData() {
        return new Object[][]{{null, null},};
    }
}
