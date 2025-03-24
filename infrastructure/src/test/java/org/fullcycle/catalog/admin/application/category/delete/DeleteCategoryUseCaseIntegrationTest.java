package org.fullcycle.catalog.admin.application.category.delete;

import org.fullcycle.catalog.admin.IntegrationTest;
import org.fullcycle.catalog.admin.domain.category.Category;
import org.fullcycle.catalog.admin.domain.category.CategoryGateway;
import org.fullcycle.catalog.admin.domain.category.CategoryID;
import org.fullcycle.catalog.admin.infrastructure.category.persistence.CategoryJpaEntity;
import org.fullcycle.catalog.admin.infrastructure.category.persistence.CategoryJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@IntegrationTest
public class DeleteCategoryUseCaseIntegrationTest {

    @Autowired
    private DeleteCategoryUseCase useCase;

    @Autowired
    private CategoryJpaRepository repository;

    @Spy
    private CategoryGateway gateway;

    @Test
    public void givenAnIdOfExistingCategory_whenExecute_thenDeleteCategory() {
        final var existingCategory = Category.of("Name", "Description", true);
        final var expectedId = existingCategory.getId();
        repository.saveAndFlush(CategoryJpaEntity.from(existingCategory));
        Assertions.assertEquals(1, repository.count());
        useCase.execute(expectedId.getValue());
        Assertions.assertEquals(0, repository.count());
    }

    @Test
    public void givenAnIdOfNonExistingCategory_whenExecute_thenDoNothing() {
        final var id = CategoryID.unique();
        Assertions.assertEquals(0, repository.count());
        useCase.execute(id.getValue());
        Assertions.assertEquals(0, repository.count());
        Mockito.verify(gateway, Mockito.never()).deleteById(Mockito.any());
    }
}
