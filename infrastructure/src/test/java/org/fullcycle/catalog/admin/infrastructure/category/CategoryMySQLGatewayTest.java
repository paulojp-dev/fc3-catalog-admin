package org.fullcycle.catalog.admin.infrastructure.category;

import org.fullcycle.catalog.admin.domain.category.Category;
import org.fullcycle.catalog.admin.infrastructure.MySQLGatewayTest;
import org.fullcycle.catalog.admin.infrastructure.category.persistence.CategoryJpaEntity;
import org.fullcycle.catalog.admin.infrastructure.category.persistence.CategoryJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

@MySQLGatewayTest
@Import({CategoryMySQLGateway.class})
public class CategoryMySQLGatewayTest {

    @Autowired
    private CategoryMySQLGateway gateway;

    @Autowired
    private CategoryJpaRepository repository;

    @Test
    public void givenAValidCategory_whenCallsCreate_thenReturnANewCategory() {
        final var expectedCategory = Category.of("Category Name", "Category Description", true);
        Assertions.assertEquals(0, repository.count());
        final var actualCategory = gateway.create(expectedCategory);
        Assertions.assertEquals(1, repository.count());
        Assertions.assertNotSame(expectedCategory, actualCategory);
        Assertions.assertEquals(expectedCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedCategory.getName(), actualCategory.getName());
        Assertions.assertEquals(expectedCategory.getDescription(), actualCategory.getDescription());
        Assertions.assertEquals(expectedCategory.isActive(), actualCategory.isActive());
        Assertions.assertEquals(expectedCategory.getCreatedAt(), actualCategory.getCreatedAt());
        Assertions.assertEquals(expectedCategory.getUpdatedAt(), actualCategory.getUpdatedAt());
        Assertions.assertTrue(actualCategory.getDeletedAt().isEmpty());
    }

    @Test
    public void givenAPersistedCategory_whenCallsUpdate_thenReturnAUpdatedCategory() {
        final var expectedCategory = Category.of("Old Name", "Old Description", false);
        final var existingCategory  =repository.saveAndFlush(CategoryJpaEntity.from(expectedCategory)).toDomain();
        expectedCategory.update("New Name", "New Description", true);
        Assertions.assertEquals(1, repository.count());
        final var actualCategory = gateway.update(expectedCategory);
        Assertions.assertEquals(1, repository.count());
        Assertions.assertNotSame(expectedCategory, actualCategory);
        Assertions.assertEquals(expectedCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedCategory.getName(), actualCategory.getName());
        Assertions.assertEquals(expectedCategory.getDescription(), actualCategory.getDescription());
        Assertions.assertEquals(expectedCategory.isActive(), actualCategory.isActive());
        Assertions.assertEquals(expectedCategory.getCreatedAt(), actualCategory.getCreatedAt());
        Assertions.assertTrue(actualCategory.getCreatedAt().isBefore(actualCategory.getUpdatedAt()));
        Assertions.assertTrue(actualCategory.getDeletedAt().isEmpty());
    }

    @Test
    public void givenAPersistedCategory_whenCallsDelete_thenDeletesCategory() {
        final var category = Category.of("Name", "Description", true);
        repository.saveAndFlush(CategoryJpaEntity.from(category));
        Assertions.assertEquals(1, repository.count());
        gateway.deleteById(category.getId());
        Assertions.assertEquals(0, repository.count());
    }

    @Test
    public void givenNoCategory_whenCallsDelete_thenNothing() {
        final var category = Category.of("Name", "Description", true);
        gateway.deleteById(category.getId());
        Assertions.assertEquals(0, repository.count());
    }

    @Test
    public void givenAPersistedCategory_whenCallsFindById_thenReturnAPersistedCategory() {
        final var expectedCategory = Category.of("Name", "Description", true);
        repository.saveAndFlush(CategoryJpaEntity.from(expectedCategory));
        final var actualCategory = gateway.findById(expectedCategory.getId()).orElseThrow();
        Assertions.assertNotSame(expectedCategory, actualCategory);
        Assertions.assertEquals(expectedCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedCategory.getName(), actualCategory.getName());
        Assertions.assertEquals(expectedCategory.getDescription(), actualCategory.getDescription());
        Assertions.assertEquals(expectedCategory.isActive(), actualCategory.isActive());
        Assertions.assertEquals(expectedCategory.getCreatedAt(), actualCategory.getCreatedAt());
        Assertions.assertEquals(expectedCategory.getUpdatedAt(), actualCategory.getUpdatedAt());
        Assertions.assertTrue(actualCategory.getDeletedAt().isEmpty());
    }
}
