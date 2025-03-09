package org.fullcycle.catalog.admin.domain.category;

import org.fullcycle.catalog.admin.domain.exception.DomainValidationException;
import org.fullcycle.catalog.admin.domain.validation.Message;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

public class CategoryTest {

    static List<CategoryParams> invalidCategoryData() {
        return List.of(
                CategoryParams.byName(null, Message.NOT_NULL),
                CategoryParams.byName("   ", Message.NOT_EMPTY),
                CategoryParams.byName("ab   ", Message.MIN_STRING, 3),
                CategoryParams.byName("a".repeat(256), Message.MAX_STRING, 255),
                CategoryParams.byDescription("a".repeat(256), Message.MAX_STRING, 255)
        );
    }

    static List<CategoryParams> validCategoryData() {
        return List.of(
                CategoryParams.of("a".repeat(255), "Description", true),
                CategoryParams.of("a".repeat(3), null, false)
        );
    }

    @ParameterizedTest
    @MethodSource("validCategoryData")
    public void givenValidParams_whenCreateNewCategory_thenInstantiateACategory(CategoryParams params) {
        final var category = Category.of(params.name, params.description, params.isActive);
        Assertions.assertNotNull(category.getId());
        Assertions.assertEquals(params.name, category.getName());
        Assertions.assertEquals(params.description, category.getDescription());
        Assertions.assertEquals(params.isActive, category.isActive());
        Assertions.assertNotNull(category.getCreatedAt());
        Assertions.assertNotNull(category.getUpdatedAt());
        Assertions.assertEquals(category.getCreatedAt(), category.getUpdatedAt());
        Assertions.assertTrue(category.getDeletedAt().isEmpty());
    }

    @ParameterizedTest
    @MethodSource("invalidCategoryData")
    public void givenInvalidParam_whenCreateNewCategory_thenThrowsException(CategoryParams params) {
        Executable executable = () -> Category.of(params.name, params.description, params.isActive);
        final var exception = Assertions.assertThrows(DomainValidationException.class, executable);
        Assertions.assertEquals(params.message, exception.getErrors().get(0).message());
    }

    @ParameterizedTest
    @MethodSource("invalidCategoryData")
    public void givenInvalidParam_whenUpdateCategory_thenThrowsException(CategoryParams params) {
        final var category = Category.of("Old Name", "Old Description", params.isActive);
        Executable executable = () -> category.update(params.name, params.description);
        final var exception = Assertions.assertThrows(DomainValidationException.class, executable);
        Assertions.assertEquals(params.message, exception.getErrors().get(0).message());
    }

    @Test
    public void givenActiveCategory_whenDeactivateCategory_thenDeactivateCategory() {
        final var expectedName = "Name";
        final var expectedDescription = "Description";
        final var category = Category.of(expectedName, expectedDescription, true);

        category.deactivate();

        Assertions.assertEquals(expectedName, category.getName());
        Assertions.assertEquals(expectedDescription, category.getDescription());
        Assertions.assertFalse(category.isActive());
        Assertions.assertNotEquals(category.getCreatedAt(), category.getUpdatedAt());
        Assertions.assertTrue(category.getDeletedAt().isEmpty());
    }

    @Test
    public void givenInactiveCategory_whenActivateCategory_thenActivateCategory() {
        final var expectedName = "Name";
        final var expectedDescription = "Description";
        final var category = Category.of(expectedName, expectedDescription, false);

        category.activate();

        Assertions.assertEquals(expectedName, category.getName());
        Assertions.assertEquals(expectedDescription, category.getDescription());
        Assertions.assertTrue(category.isActive());
        Assertions.assertNotEquals(category.getCreatedAt(), category.getUpdatedAt());
        Assertions.assertTrue(category.getDeletedAt().isEmpty());
    }

    @Test
    public void givenACategory_whenUpdateCategory_thenUpdateCategory() {
        final var expectedName = "New Name";
        final var expectedDescription = "New Description";
        final var category = Category.of("Old Name", "Old Description", true);
        category.update(expectedName, expectedDescription);
        Assertions.assertEquals(expectedName, category.getName());
        Assertions.assertEquals(expectedDescription, category.getDescription());
        Assertions.assertNotEquals(category.getCreatedAt(), category.getUpdatedAt());
        Assertions.assertTrue(category.getDeletedAt().isEmpty());
    }

    public record CategoryParams(String name, String description, boolean isActive, String message) {

        public static CategoryParams of(final String name, final String description, final Boolean isActive) {
            return new CategoryParams(name, description, isActive, null);
        }

        public static CategoryParams byName(final String name, final String message) {
            String error = Message.resolve("name", message);
            return new CategoryParams(name, "Description", true, error);
        }

        public static CategoryParams byName(final String name, final String message, final Integer size) {
            String error = Message.resolve("name", message, size);
            return new CategoryParams(name, "Description", true, error);
        }

        public static CategoryParams byDescription(final String description, final String message) {
            String error = Message.resolve("description", message);
            return new CategoryParams("Name", description, true, error);
        }

        public static CategoryParams byDescription(
                final String description,
                final String message,
                final Integer size
        ) {
            String error = Message.resolve("description", message, size);
            return new CategoryParams("Name", description, true, error);
        }
    }
}
