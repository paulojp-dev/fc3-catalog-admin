package org.fullcycle.catalog.admin.application.category.retrieve.list;

import org.fullcycle.catalog.admin.IntegrationTest;
import org.fullcycle.catalog.admin.domain.category.Category;
import org.fullcycle.catalog.admin.domain.pagination.Pagination;
import org.fullcycle.catalog.admin.domain.pagination.SearchQuery;
import org.fullcycle.catalog.admin.infrastructure.category.persistence.CategoryJpaEntity;
import org.fullcycle.catalog.admin.infrastructure.category.persistence.CategoryJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@IntegrationTest
public class ListCategoriesUseCaseIntegrationTest {

    @Autowired
    private ListCategoriesUseCase useCase;

    @Autowired
    private CategoryJpaRepository repository;

    @Test
    public void givenExistingCategories_whenExecute_thenReturnCategories() {
        final var expectedCategories = List.of(
            Category.of("Category 1", "...", true),
            Category.of("Category 2", "...", false),
            Category.of("Category 3", "...", true)
        );
        repository.saveAllAndFlush(expectedCategories.stream().map(CategoryJpaEntity::from).toList());
        Assertions.assertEquals(expectedCategories.size(), repository.count());
        final var expectedPage = 0;
        final var expectedPerPage = 3;
        final var expectedTerms = "";
        final var expectedSort = "createdAt";
        final var expectedDirection = "asc";
        final var query = new SearchQuery(
            expectedPage,
            expectedPerPage,
            expectedTerms,
            expectedSort,
            expectedDirection
        );
        final var expectedPagination = new Pagination<>(
            expectedPage,
            expectedPerPage,
            (long) expectedCategories.size(),
            expectedCategories
        );
        final var expectedOutput = expectedPagination.map(ListCategoriesOutput::from);
        final var output = useCase.execute(query);
        Assertions.assertEquals(expectedOutput, output);
        Assertions.assertEquals(expectedPage, output.currentPage());
        Assertions.assertEquals(expectedPerPage, output.perPage());
        Assertions.assertEquals(expectedCategories.size(), output.total());
        Assertions.assertEquals(expectedOutput.items(), output.items());
    }

    @Test
    public void givenNotExistingCategories_whenExecute_thenReturnEmptyList() {
        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTerms = "";
        final var expectedSort = "createdAt";
        final var expectedDirection = "asc";
        Assertions.assertEquals(0, repository.count());
        final var query = new SearchQuery(
            expectedPage,
            expectedPerPage,
            expectedTerms,
            expectedSort,
            expectedDirection
        );
        final var expectedPagination = new Pagination<Category>(
            expectedPage,
            expectedPerPage,
            0L,
            List.of()
        );
        final var expectedOutput = expectedPagination.map(ListCategoriesOutput::from);
        final var output = useCase.execute(query);
        Assertions.assertEquals(expectedOutput, output);
        Assertions.assertEquals(expectedPage, output.currentPage());
        Assertions.assertEquals(expectedPerPage, output.perPage());
        Assertions.assertEquals(0, output.total());
        Assertions.assertEquals(expectedOutput.items(), output.items());
    }
}
