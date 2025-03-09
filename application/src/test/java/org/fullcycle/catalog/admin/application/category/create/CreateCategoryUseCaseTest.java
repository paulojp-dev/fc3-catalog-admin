package org.fullcycle.catalog.admin.application.category.create;

import org.fullcycle.catalog.admin.domain.category.CategoryGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class CreateCategoryUseCaseTest {

    @Test
    public void givenAValidCommand_whenCreateACategory_thenReturnAValidCategory() {
        final var name = "Category";
        final var description = "Description";
        final var isActive = true;
        final var command = CreateCategoryCommand.of(name, description, isActive);
        final var categoryGateway = Mockito.mock(CategoryGateway.class);
        Mockito.when(categoryGateway.create(Mockito.any()))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());
        final var useCase = new CreateCategoryUseCase(categoryGateway);
        final var output = useCase.execute(command);
        Assertions.assertNotNull(output);
        Assertions.assertNotNull(output.id());
        Mockito.verify(categoryGateway, times(1)).create(argThat(
                category -> Objects.equals(name, category.getName())
                        && Objects.equals(description, category.getDescription())
                        && Objects.equals(isActive, category.isActive())
                        && Objects.nonNull(category.getId())
                        && Objects.nonNull(category.getCreatedAt())
                        && Objects.nonNull(category.getUpdatedAt())
                        && Objects.equals(true, category.getDeletedAt().isEmpty())
        ));
    }
}
