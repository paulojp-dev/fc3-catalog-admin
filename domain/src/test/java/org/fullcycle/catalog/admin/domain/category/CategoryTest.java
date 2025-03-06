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
                CategoryParams.byName("   ", Message.NOT_EMPTY)
        );
    }

    @Test
    public void givenValidParams_whenCreateNewCategory_thenInstantiateACategory() {
        final var name = "Category";
        final var description = "Description";
        final var isActive = true;

        Category category = Category.of(name, description, isActive);

        Assertions.assertNotNull(category.getId());
        Assertions.assertEquals(name, category.getName());
        Assertions.assertEquals(description, category.getDescription());
        Assertions.assertEquals(isActive, category.isActive());
        Assertions.assertNotNull(category.getCreatedAt());
        Assertions.assertNotNull(category.getUpdatedAt());
        Assertions.assertTrue(category.getDeletedAt().isEmpty());
    }

    @ParameterizedTest
    @MethodSource("invalidCategoryData")
    public void givenInvalidParam_whenCreateNewCategory_thenThrowsException(CategoryParams params) {
        Executable executable = () -> Category.of(params.name, params.description, params.isActive);

        final var exception = Assertions.assertThrows(DomainValidationException.class, executable);
        Assertions.assertEquals(params.messages.size(), exception.getErrors().getFirst().messages().size());
        Assertions.assertEquals(params.messages, exception.getErrors().getFirst().messages());
    }

    private record CategoryParams(String name, String description, boolean isActive, List<String> messages) {

        public static CategoryParams byName(String name, String message) {
            String error = Message.resolve("name", message);
            return new CategoryParams(name, "Description", true, List.of(error));
        }

        public static CategoryParams byDescription(String description, String message) {
            String error = Message.resolve("name", message);
            return new CategoryParams("Name", description,  true, List.of(error));
        }

        public static CategoryParams allNull(List<String> messages) {
            return new CategoryParams(null, null, false, messages);
        }
    }
}
