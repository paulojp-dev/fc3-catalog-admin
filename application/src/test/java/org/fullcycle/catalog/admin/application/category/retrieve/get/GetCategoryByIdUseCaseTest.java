package org.fullcycle.catalog.admin.application.category.retrieve.get;

import org.fullcycle.catalog.admin.domain.category.Category;
import org.fullcycle.catalog.admin.domain.category.CategoryGateway;
import org.fullcycle.catalog.admin.domain.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class GetCategoryByIdUseCaseTest {

    @InjectMocks
    private GetCategoryByIdUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    public void cleanUp() {
        Mockito.reset(categoryGateway);
    }

    @Test
    public void givenAnIdOfExistingCategory_whenExecute_theReturnACategory() {
        final var expectedCategory = Category.of("Category Name", "Category Description", false);

        Mockito.when(categoryGateway.findById(Mockito.any()))
            .thenReturn(Optional.of(expectedCategory));

        final var output = useCase.execute(expectedCategory.getId().getValue());

        Assertions.assertEquals(expectedCategory.getId().getValue(), output.id());
        Assertions.assertEquals(expectedCategory.getName(), output.name());
        Assertions.assertEquals(expectedCategory.getDescription(), output.description());
        Assertions.assertEquals(expectedCategory.isActive(), output.isActive());
        Assertions.assertEquals(expectedCategory.getCreatedAt(), output.createdAt());
        Assertions.assertEquals(expectedCategory.getUpdatedAt(), output.updatedAt());
        Assertions.assertEquals(expectedCategory.getDeletedAt().orElse(null), output.deletedAt());

        Mockito.verify(categoryGateway, Mockito.times(1))
            .findById(Mockito.eq(expectedCategory.getId()));
    }

    @Test
    public void givenAnIdOfNonExistingCategory_whenExecute_theThrowsException() {
        final var existingCategory = Category.of("Name", "Description", Boolean.TRUE);
        final var expectedId = existingCategory.getId();
        final var expectedException = NotFoundException.with(Category.class, expectedId);

        Mockito.when(categoryGateway.findById(Mockito.eq(expectedId)))
            .thenReturn(Optional.empty());

        Executable executable = () -> useCase.execute(expectedId.getValue());

        final var exception = Assertions.assertThrows(expectedException.getClass(), executable);
        Assertions.assertEquals(expectedException.getMessage(), exception.getMessage());
    }
}
