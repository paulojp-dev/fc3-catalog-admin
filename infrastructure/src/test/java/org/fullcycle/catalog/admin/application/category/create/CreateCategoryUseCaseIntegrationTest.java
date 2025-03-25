package org.fullcycle.catalog.admin.application.category.create;

import org.fullcycle.catalog.admin.IntegrationTest;
import org.fullcycle.catalog.admin.domain.category.CategoryGateway;
import org.fullcycle.catalog.admin.domain.exception.DomainValidationException;
import org.fullcycle.catalog.admin.domain.validation.Message;
import org.fullcycle.catalog.admin.infrastructure.category.persistence.CategoryJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings({
    "SpringJavaInjectionPointsAutowiringInspection",
    "OptionalGetWithoutIsPresent"
})
@IntegrationTest
public class CreateCategoryUseCaseIntegrationTest {

    @Autowired
    private CreateCategoryUseCase useCase;

    @Autowired
    private CategoryJpaRepository repository;

    @Spy
    private CategoryGateway categoryGateway;

    @Test
    public void givenAValidCommand_whenExecute_thenReturnTheCreatedCategory() {
        final var expectedName = "Category Name";
        final var expectedDescription = "Category Description";
        final var expectedIsActive = true;
        Assertions.assertEquals(0, repository.count());
        final var command = CreateCategoryCommand.of(expectedName, expectedDescription, expectedIsActive);
        final var result = useCase.execute(command);
        final var actualCategory = repository.findById(result.id()).get();
        Assertions.assertEquals(1, repository.count());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.getActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());
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
