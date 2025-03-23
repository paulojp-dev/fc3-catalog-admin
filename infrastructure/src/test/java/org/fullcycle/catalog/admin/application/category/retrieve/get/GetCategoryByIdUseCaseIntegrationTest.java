package org.fullcycle.catalog.admin.application.category.retrieve.get;

import org.fullcycle.catalog.admin.IntegrationTest;
import org.fullcycle.catalog.admin.application.exception.CategoryNotFoundException;
import org.fullcycle.catalog.admin.domain.category.Category;
import org.fullcycle.catalog.admin.infrastructure.category.persistence.CategoryJpaEntity;
import org.fullcycle.catalog.admin.infrastructure.category.persistence.CategoryJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@IntegrationTest
public class GetCategoryByIdUseCaseIntegrationTest {

    @Autowired
    private GetCategoryByIdUseCase useCase;

    @Autowired
    private CategoryJpaRepository repository;

    @Test
    public void givenAnIdOfExistingCategory_whenExecute_theReturnACategory() {
        final var expectedName = "Category Name";
        final var expectedDescription = "Category Description";
        final var expectedIsActive = true;
        final var existingCategory = Category.of(expectedName, expectedDescription, expectedIsActive);
        final var expectedId = existingCategory.getId();
        repository.saveAndFlush(CategoryJpaEntity.from(existingCategory));
        final var output = useCase.execute(expectedId.getValue());
        Assertions.assertEquals(expectedId.getValue(), output.id());
        Assertions.assertEquals(expectedName, output.name());
        Assertions.assertEquals(expectedDescription, output.description());
        Assertions.assertEquals(expectedIsActive, output.isActive());
    }

    @Test
    public void givenAnIdOfNonExistingCategory_whenExecute_theThrowsException() {
        final var existingCategory = Category.of("Name", "Description", Boolean.TRUE);
        final var expectedId = existingCategory.getId();
        final var expectedException = CategoryNotFoundException.byId(expectedId.getValue());
        Executable executable = () -> useCase.execute(expectedId.getValue());
        final var exception = Assertions.assertThrows(expectedException.getClass(), executable);
        Assertions.assertEquals(expectedException.getMessage(), exception.getMessage());
    }
}
