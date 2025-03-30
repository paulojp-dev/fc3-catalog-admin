package org.fullcycle.catalog.admin.infrastructure.api.controller;

import org.fullcycle.catalog.admin.application.category.create.CreateCategoryCommand;
import org.fullcycle.catalog.admin.application.category.create.CreateCategoryUseCase;
import org.fullcycle.catalog.admin.infrastructure.api.CategoryAPI;
import org.fullcycle.catalog.admin.infrastructure.category.model.CreateCategoryApiInput;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(value = "categories")
public class CategoryController implements CategoryAPI {

    private final CreateCategoryUseCase createCategoryUseCase;

    public CategoryController(final CreateCategoryUseCase createCategoryUseCase) {
        this.createCategoryUseCase = createCategoryUseCase;
    }

    @Override
    public ResponseEntity<?> createCategory(CreateCategoryApiInput input) {
        final var command = CreateCategoryCommand.of(
            input.name(),
            input.description(),
            input.active()
        );
        final var output = createCategoryUseCase.execute(command);
        final var location = URI.create("/categories/" + output.id());
        return ResponseEntity.created(location).body(output);
    }
}
