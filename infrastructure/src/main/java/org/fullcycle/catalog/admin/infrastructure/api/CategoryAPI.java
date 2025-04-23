package org.fullcycle.catalog.admin.infrastructure.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.fullcycle.catalog.admin.application.category.retrieve.list.ListCategoriesOutput;
import org.fullcycle.catalog.admin.domain.pagination.Pagination;
import org.fullcycle.catalog.admin.infrastructure.category.model.GetCategoryApiOutput;
import org.fullcycle.catalog.admin.infrastructure.category.model.CreateCategoryApiOutput;
import org.fullcycle.catalog.admin.infrastructure.category.model.CreateCategoryApiInput;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(value = "categories")
@Tag(name = "Categories")
public interface CategoryAPI {

    @PostMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create a category")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Created successfully"),
        @ApiResponse(responseCode = "422", description = "Validation error for input data"),
        @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    ResponseEntity<CreateCategoryApiOutput> createCategory(@RequestBody @Valid CreateCategoryApiInput input);

    @GetMapping(
        path = "{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Get a category it's identifier")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Category found"),
        @ApiResponse(responseCode = "404", description = "Category not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    ResponseEntity<GetCategoryApiOutput> getCategoryById(@PathVariable("id") String id);

    @GetMapping
    @Operation(summary = "List all categories paginated")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listed successfully"),
        @ApiResponse(responseCode = "422", description = "Validation error for invalid parameters"),
        @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    ResponseEntity<Pagination<ListCategoriesOutput>> listCategories(
        @RequestParam(name = "search", required = false, defaultValue = "") final String search,
        @RequestParam(name = "page", required = false, defaultValue = "0") final Integer page,
        @RequestParam(name = "quantityPerPage", required = false, defaultValue = "10") final Integer quantityPerPage,
        @RequestParam(name = "sortField", required = false, defaultValue = "name") final String sortField,
        @RequestParam(name = "sortDirection", required = false, defaultValue = "asc") final String sortDirection
    );
}
