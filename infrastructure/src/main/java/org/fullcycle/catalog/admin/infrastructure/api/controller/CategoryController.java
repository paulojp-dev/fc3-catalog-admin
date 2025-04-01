package org.fullcycle.catalog.admin.infrastructure.api.controller;

import org.fullcycle.catalog.admin.application.category.create.CreateCategoryCommand;
import org.fullcycle.catalog.admin.application.category.create.CreateCategoryUseCase;
import org.fullcycle.catalog.admin.application.category.retrieve.list.ListCategoriesOutput;
import org.fullcycle.catalog.admin.application.category.retrieve.list.ListCategoriesUseCase;
import org.fullcycle.catalog.admin.domain.pagination.Pagination;
import org.fullcycle.catalog.admin.domain.pagination.SearchQuery;
import org.fullcycle.catalog.admin.infrastructure.api.CategoryAPI;
import org.fullcycle.catalog.admin.infrastructure.api.output.CreateCategoryApiOutput;
import org.fullcycle.catalog.admin.infrastructure.category.model.CreateCategoryApiInput;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(value = "categories")
public class CategoryController implements CategoryAPI {

    private final CreateCategoryUseCase createCategoryUseCase;
    private final ListCategoriesUseCase listCategoriesUseCase;

    public CategoryController(
        final CreateCategoryUseCase createCategoryUseCase,
        final ListCategoriesUseCase listCategoriesUseCase
    ) {
        this.createCategoryUseCase = createCategoryUseCase;
        this.listCategoriesUseCase = listCategoriesUseCase;
    }

    @Override
    public ResponseEntity<CreateCategoryApiOutput> createCategory(CreateCategoryApiInput input) {
        final var command = CreateCategoryCommand.of(
            input.name(),
            input.description(),
            input.active()
        );
        final var output = createCategoryUseCase.execute(command);
        final var location = URI.create("/categories/" + output.id());
        return ResponseEntity.created(location).body(CreateCategoryApiOutput.from(output));
    }

    @GetMapping
    public ResponseEntity<Pagination<ListCategoriesOutput>> listCategories(
        String search,
        Integer page,
        Integer quantityPerPage,
        String sortField,
        String sortDirection
    ) {
        final var query = new SearchQuery(page, quantityPerPage, search, sortField, sortDirection);
        final var output = listCategoriesUseCase.execute(query);
        return ResponseEntity.ok(output);
    }
}
