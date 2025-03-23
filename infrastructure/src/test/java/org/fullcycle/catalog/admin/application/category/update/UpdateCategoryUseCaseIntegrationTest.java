package org.fullcycle.catalog.admin.application.category.update;

import org.fullcycle.catalog.admin.IntegrationTest;
import org.fullcycle.catalog.admin.application.exception.CategoryNotFoundException;
import org.fullcycle.catalog.admin.domain.base.ID;
import org.fullcycle.catalog.admin.domain.category.Category;
import org.fullcycle.catalog.admin.domain.exception.DomainValidationException;
import org.fullcycle.catalog.admin.domain.validation.Message;
import org.fullcycle.catalog.admin.infrastructure.category.persistence.CategoryJpaEntity;
import org.fullcycle.catalog.admin.infrastructure.category.persistence.CategoryJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection", "OptionalGetWithoutIsPresent"})
@IntegrationTest
public class UpdateCategoryUseCaseIntegrationTest {

    @Autowired
    private UpdateCategoryUseCase useCase;

    @Autowired
    private CategoryJpaRepository repository;

    @Test
    public void givenAValidCommand_whenExecute_thenUpdateCategory() {
        final var existingCategory = Category.of("Old Name", "Old Description", true);
        final var command = UpdateCategoryCommand.of(
            existingCategory.getId().getValue(),
            "New Name",
            "New Description",
            false
        );
        repository.saveAndFlush(CategoryJpaEntity.from(existingCategory));
        Assertions.assertEquals(1, repository.count());
        final var output = useCase.execute(command);
        final var actualCategory = repository.findById(output.id().getValue()).get();
        Assertions.assertEquals(command.id(), actualCategory.getId());
        Assertions.assertEquals(command.name(), actualCategory.getName());
        Assertions.assertEquals(command.description(), actualCategory.getDescription());
        Assertions.assertEquals(command.isActive(), actualCategory.getActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertTrue(actualCategory.getCreatedAt().isBefore(actualCategory.getUpdatedAt()));
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenACommandWithInvalidId_whenExecute_thenThrowException() {
        final var invalidId = ID.of("invalid_id");
        final var command = UpdateCategoryCommand.of(
            invalidId.getValue(),
            "New Name",
            "New Description",
            false
        );
        final var expectedException = CategoryNotFoundException.byId(invalidId.getValue());
        Assertions.assertEquals(0, repository.count());
        Executable executable = () -> useCase.execute(command);
        final var actualException = Assertions.assertThrows(expectedException.getClass(), executable);
        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedException.getMessage(), actualException.getMessage());
        Assertions.assertEquals(0, repository.count());
    }

    @Test
    public void givenACommandWithInvalidParams_whenExecute_thenThrowException() {
        final var existingCategory = Category.of("Name", "Description", true);
        repository.saveAndFlush(CategoryJpaEntity.from(existingCategory));
        Assertions.assertEquals(1, repository.count());
        final var command = UpdateCategoryCommand.of(existingCategory.getId().getValue(), null, null, false);
        final var expectedMessage = Message.resolve("name", Message.NOT_NULL);
        Executable executable = () -> useCase.execute(command);
        final var exception = Assertions.assertThrows(DomainValidationException.class, executable);
        Assertions.assertNotNull(exception);
        Assertions.assertEquals(expectedMessage, exception.getErrors().get(0).message());
        final var actualCategory = repository.findById(existingCategory.getId().getValue()).get();
        Assertions.assertEquals(existingCategory.getId().getValue(), actualCategory.getId());
        Assertions.assertEquals(existingCategory.getName(), actualCategory.getName());
        Assertions.assertEquals(existingCategory.getDescription(), actualCategory.getDescription());
        Assertions.assertEquals(existingCategory.isActive(), actualCategory.getActive());
        Assertions.assertEquals(existingCategory.getCreatedAt(), actualCategory.getCreatedAt());
        Assertions.assertEquals(existingCategory.getUpdatedAt(), actualCategory.getUpdatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());
    }
}
