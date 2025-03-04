package org.fullcycle.catalog.admin.domain.category;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CategoryTest {

    @Test
    public void givenValidParams_whenNewCategory_thenInstantiateACategory() {
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
}
