package org.fullcycle.catalog.admin.infrastructure.category;

import org.fullcycle.catalog.admin.domain.category.Category;
import org.fullcycle.catalog.admin.infrastructure.MySQLGatewayTest;
import org.fullcycle.catalog.admin.infrastructure.category.persistence.CategoryJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

@MySQLGatewayTest
@Import(CategoryMySQLGateway.class)
public class CategoryMySQLGatewayTest {

    @Autowired
    private CategoryMySQLGateway gateway;

    @Autowired
    private CategoryJpaRepository repository;

    @Test
    public void givenAValidCategory_whenCallsCreate_thenReturnANewCategory() {
        final var expectedName = "Category Name";
        final var expectedDescription = "Category Description";
        final var expectedIsActive = true;
        final var expectedCategory = Category.of(expectedName, expectedDescription, expectedIsActive);

        final var actualCategory = gateway.create(expectedCategory);

        Assertions.assertEquals(expectedCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertEquals(expectedCategory.getCreatedAt(), actualCategory.getCreatedAt());
        Assertions.assertEquals(expectedCategory.getUpdatedAt(), actualCategory.getUpdatedAt());
        Assertions.assertEquals(expectedCategory.getDeletedAt(), actualCategory.getDeletedAt());
        Assertions.assertTrue(actualCategory.getDeletedAt().isEmpty());
    }
}
