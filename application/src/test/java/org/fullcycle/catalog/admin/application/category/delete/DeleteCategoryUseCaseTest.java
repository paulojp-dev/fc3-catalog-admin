package org.fullcycle.catalog.admin.application.category.delete;

import org.fullcycle.catalog.admin.domain.category.Category;
import org.fullcycle.catalog.admin.domain.category.CategoryGateway;
import org.fullcycle.catalog.admin.domain.category.CategoryID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class DeleteCategoryUseCaseTest {

    @InjectMocks
    private DeleteCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    public void cleanUp() {
        Mockito.reset(categoryGateway);
    }

    @Test
    public void givenAnIdOfExistingCategory_whenExecute_thenDeleteCategory() {
        final var existingCategory = Category.of("Name", "Description", true);
        final var expectedId = existingCategory.getId();

        Mockito.when(categoryGateway.findById(Mockito.eq(expectedId)))
                .thenReturn(Optional.of(existingCategory));
        Mockito.doNothing()
                .when(categoryGateway)
                .deleteById(Mockito.eq(expectedId));

        useCase.execute(expectedId.getValue());

        Mockito.verify(categoryGateway, Mockito.times(1))
                .deleteById(Mockito.eq(expectedId));
    }

    @Test
    public void givenAnIdOfNonExistingCategory_whenExecute_thenDoNothing() {
        final var id = CategoryID.of(UUID.randomUUID());
        Mockito.when(categoryGateway.findById(Mockito.eq(id))).thenReturn(Optional.empty());
        useCase.execute(id.getValue());
        Mockito.verify(categoryGateway, Mockito.never()).deleteById(Mockito.any());
    }
}
