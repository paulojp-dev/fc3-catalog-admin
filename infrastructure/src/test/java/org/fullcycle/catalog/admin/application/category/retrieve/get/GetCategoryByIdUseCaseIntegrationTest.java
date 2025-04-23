package org.fullcycle.catalog.admin.application.category.retrieve.get;

import org.fullcycle.catalog.admin.IntegrationTest;
import org.fullcycle.catalog.admin.domain.category.Category;
import org.fullcycle.catalog.admin.domain.exception.NotFoundException;
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
        final var expectedCategory = Category.of("Category Name", "Category Description", true);

        repository.saveAndFlush(CategoryJpaEntity.from(expectedCategory));

        final var output = useCase.execute(expectedCategory.getId().getValue());

        Assertions.assertEquals(expectedCategory.getId().getValue(), output.id());
        Assertions.assertEquals(expectedCategory.getName(), output.name());
        Assertions.assertEquals(expectedCategory.getDescription(), output.description());
        Assertions.assertEquals(expectedCategory.isActive(), output.isActive());
        Assertions.assertEquals(expectedCategory.getCreatedAt(), output.createdAt());
        Assertions.assertEquals(expectedCategory.getUpdatedAt(), output.updatedAt());
        Assertions.assertEquals(expectedCategory.getDeletedAt().orElse(null), output.deletedAt());
    }

    @Test
    public void givenAnIdOfNonExistingCategory_whenExecute_theThrowsException() {
        final var expectedCategory = Category.of("Name", "Description", Boolean.TRUE);
        final var expectedId = expectedCategory.getId();
        final var expectedException = NotFoundException.with(Category.class, expectedId);

        Executable executable = () -> useCase.execute(expectedId.getValue());

        final var exception = Assertions.assertThrows(expectedException.getClass(), executable);
        Assertions.assertEquals(expectedException.getMessage(), exception.getMessage());
    }
}
