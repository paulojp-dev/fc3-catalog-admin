package org.fullcycle.catalog.admin.application.category.create;

import org.fullcycle.catalog.admin.domain.category.CategoryGateway;
import org.fullcycle.catalog.admin.domain.exception.DomainValidationException;
import org.fullcycle.catalog.admin.domain.validation.Message;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

@ExtendWith(MockitoExtension.class)
public class CreateCategoryUseCaseTest {

    @InjectMocks
    private CreateCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    public void cleanUp() {
        Mockito.reset(categoryGateway);
    }

    @Test
    public void givenAValidCommand_whenCreateACategory_thenReturnAValidCategory() {
        final var expectedName = "Category";
        final var expectedDescription = "Description";
        final var expectedIsActive = true;
        final var command = CreateCategoryCommand.of(expectedName, expectedDescription, expectedIsActive);

        Mockito.when(categoryGateway.create(Mockito.any()))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        final var output = useCase.execute(command);

        Assertions.assertNotNull(output);
        Assertions.assertNotNull(output.id());
        Mockito.verify(categoryGateway, Mockito.times(1))
                .create(ArgumentMatchers.argThat(
                        category -> Objects.equals(expectedName, category.getName())
                                && Objects.equals(expectedDescription, category.getDescription())
                                && Objects.equals(expectedIsActive, category.isActive())
                                && Objects.nonNull(category.getId())
                                && Objects.nonNull(category.getCreatedAt())
                                && Objects.nonNull(category.getUpdatedAt())
                                && Objects.equals(category.getCreatedAt(), category.getUpdatedAt())
                                && Objects.equals(true, category.getDeletedAt().isEmpty())
                ));
    }

    @Test
    public void givenAInvalidCommand_whenCreateACategory_thenThrowException() {
        final var expectedMessage = Message.resolve("name", Message.NOT_NULL);
        final var command = CreateCategoryCommand.of(null, null, true);
        final var expectedErrorCount = 1;

        Executable executable = () -> useCase.execute(command);
        final var exception = Assertions.assertThrows(DomainValidationException.class, executable);

        Assertions.assertEquals(expectedMessage, exception.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, exception.getErrors().size());
        Mockito.verify(categoryGateway, Mockito.never()).create(ArgumentMatchers.any());
    }
}
