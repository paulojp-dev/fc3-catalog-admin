package org.fullcycle.catalog.admin.application.category.update;

import org.fullcycle.catalog.admin.application.exception.ResourceNotFoundException;
import org.fullcycle.catalog.admin.domain.category.Category;
import org.fullcycle.catalog.admin.domain.category.CategoryGateway;
import org.fullcycle.catalog.admin.domain.category.CategoryID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UpdateCategoryUseCaseTest {

    @InjectMocks
    private UpdateCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Test
    public void givenAValidCommand_whenExecute_thenUpdateCategory() {
        final var existingCategory = Category.of("Old Name", "Old Description", false);
        final var expectedName = "New Name";
        final var expectedDescription = "New Description";
        final var expectedIsActive = false;
        final var command = UpdateCategoryCommand.of(existingCategory.getId(), expectedName, expectedDescription);

        Mockito.when(categoryGateway.findById(existingCategory.getId()))
                .thenReturn(Optional.of(existingCategory));
        Mockito.when(categoryGateway.update(existingCategory))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        final var output = useCase.execute(command);

        Assertions.assertNotNull(output);
        Assertions.assertNotNull(output.id());
        Mockito.verify(categoryGateway, Mockito.times(1))
                .update(Mockito.argThat(
                        category -> Objects.equals(expectedName, category.getName())
                                && Objects.equals(expectedDescription, category.getDescription())
                                && Objects.equals(expectedIsActive, category.isActive())
                                && Objects.equals(command.id(), category.getId())
                                && Objects.nonNull(category.getCreatedAt())
                                && Objects.nonNull(category.getUpdatedAt())
                                && Objects.equals(true, category.getCreatedAt().isBefore(category.getUpdatedAt()))
                                && Objects.equals(true, category.getDeletedAt().isEmpty())
                ));
    }

    @Test
    public void givenACommandWithInvalidId_whenExecute_thenThrowException() {
        final var expectedName = "New Name";
        final var expectedDescription = "New Description";
        final var invalidId = CategoryID.of("invalid_id");
        final var command = UpdateCategoryCommand.of(invalidId, expectedName, expectedDescription);
        final var expectedException = ResourceNotFoundException.byId("Category", invalidId.getValue());

        Mockito.when(categoryGateway.findById(invalidId))
                .thenReturn(Optional.empty());

        Executable executable = () -> useCase.execute(command);

        final var actualException = Assertions.assertThrows(expectedException.getClass(), executable);
        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedException.getMessage(), actualException.getMessage());
        Mockito.verify(categoryGateway, Mockito.never()).update(Mockito.any());
    }
}
