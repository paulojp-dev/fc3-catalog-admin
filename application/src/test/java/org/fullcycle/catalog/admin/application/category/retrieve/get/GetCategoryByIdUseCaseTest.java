package org.fullcycle.catalog.admin.application.category.retrieve.get;

import org.fullcycle.catalog.admin.application.exception.CategoryNotFoundException;
import org.fullcycle.catalog.admin.domain.category.Category;
import org.fullcycle.catalog.admin.domain.category.CategoryGateway;
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
        final var expectedName = "Category Name";
        final var expectedDescription = "Category Description";
        final var expectedIsActive = true;
        final var existingCategory = Category.of(expectedName, expectedDescription, expectedIsActive);
        final var expectedId = existingCategory.getId();

        Mockito.when(categoryGateway.findById(Mockito.eq(expectedId)))
                .thenReturn(Optional.of(existingCategory));

        final var output = useCase.execute(expectedId.getValue());

        Assertions.assertEquals(expectedId.getValue(), output.id());
        Assertions.assertEquals(expectedName, output.name());
        Assertions.assertEquals(expectedDescription, output.description());
        Assertions.assertEquals(expectedIsActive, output.isActive());

        Mockito.verify(categoryGateway, Mockito.times(1))
                .findById(Mockito.eq(expectedId));
    }

    @Test
    public void givenAnIdOfNonExistingCategory_whenExecute_theThrowsException() {
        final var existingCategory = Category.of("Name", "Description", Boolean.TRUE);
        final var expectedId = existingCategory.getId();
        final var expectedException = CategoryNotFoundException.byId(expectedId.getValue());

        Mockito.when(categoryGateway.findById(Mockito.eq(expectedId)))
                .thenReturn(Optional.empty());

        Executable executable = () -> useCase.execute(expectedId.getValue());

        final var exception = Assertions.assertThrows(expectedException.getClass(), executable);
        Assertions.assertEquals(expectedException.getMessage(), exception.getMessage());
    }
}
