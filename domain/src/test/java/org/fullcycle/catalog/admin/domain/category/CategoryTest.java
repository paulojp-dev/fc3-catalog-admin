package org.fullcycle.catalog.admin.domain.category;

import org.fullcycle.catalog.admin.domain.exception.DomainValidationException;
import org.fullcycle.catalog.admin.domain.validation.Message;
import org.junit.jupiter.api.Assertions;
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
        Category category = Category.of(params.name, params.description, params.isActive);
        Assertions.assertNotNull(category.getId());
        Assertions.assertEquals(params.name, category.getName());
        Assertions.assertEquals(params.description, category.getDescription());
        Assertions.assertEquals(params.isActive, category.isActive());
        Assertions.assertNotNull(category.getCreatedAt());
        Assertions.assertNotNull(category.getUpdatedAt());
        Assertions.assertTrue(category.getDeletedAt().isEmpty());
    }

    @ParameterizedTest
    @MethodSource("invalidCategoryData")
    public void givenInvalidParam_whenCreateNewCategory_thenThrowsException(CategoryParams params) {
        Executable executable = () -> Category.of(params.name, params.description, params.isActive);
        final var exception = Assertions.assertThrows(DomainValidationException.class, executable);
        Assertions.assertEquals(params.message, exception.getErrors().getFirst().message());
    }

    public record CategoryParams(String name, String description, boolean isActive, String message) {

        public static CategoryParams of(String name, String description, Boolean isActive) {
            return new CategoryParams(name, description, isActive, null);
        }

        public static CategoryParams byName(String name, String message) {
            String error = Message.resolve("name", message);
            return new CategoryParams(name, "Description", true, error);
        }

        public static CategoryParams byName(String name, String message, Integer size) {
            String error = Message.resolve("name", message, size);
            return new CategoryParams(name, "Description", true, error);
        }

        public static CategoryParams byDescription(String description, String message) {
            String error = Message.resolve("description", message);
            return new CategoryParams("Name", description, true, error);
        }

        public static CategoryParams byDescription(String description, String message, Integer size) {
            String error = Message.resolve("description", message, size);
            return new CategoryParams("Name", description, true, error);
        }
    }
}
