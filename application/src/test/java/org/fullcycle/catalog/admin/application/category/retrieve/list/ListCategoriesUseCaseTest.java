package org.fullcycle.catalog.admin.application.category.retrieve.list;

import org.fullcycle.catalog.admin.domain.category.Category;
import org.fullcycle.catalog.admin.domain.category.CategoryGateway;
import org.fullcycle.catalog.admin.domain.pagination.SearchQuery;
import org.fullcycle.catalog.admin.domain.pagination.Pagination;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ListCategoriesUseCaseTest {

    @InjectMocks
    private ListCategoriesUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    public void cleanUp() {
        Mockito.reset(categoryGateway);
    }

    @Test
    public void givenExistingCategories_whenExecute_thenReturnCategories() {
        final var expectedCategories = List.of(
                Category.of("Category 1", "...", true),
                Category.of("Category 2", "...", false),
                Category.of("Category 3", "...", true)
        );
        final var expectedPage = 1;
        final var expectedPerPage = 1;
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
                expectedCategories.stream().count(),
                expectedCategories
        );
        final var expectedOutput = expectedPagination.map(ListCategoriesOutput::from);

        Mockito.when(categoryGateway.findAll(Mockito.eq(query)))
                .thenReturn(expectedPagination);

        final var output = useCase.execute(query);

        Assertions.assertEquals(expectedOutput, output);
        Assertions.assertEquals(expectedPage, output.currentPage());
        Assertions.assertEquals(expectedPerPage, output.perPage());
        Assertions.assertEquals(expectedCategories.size(), output.total());
        Assertions.assertEquals(expectedOutput.items(), output.items());
    }

    @Test
    public void givenNotExistingCategories_whenExecute_thenReturnEmptyList() {
        final var expectedPage = 1;
        final var expectedPerPage = 1;
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
        final var expectedPagination = new Pagination<Category>(
                expectedPage,
                expectedPerPage,
                0L,
                List.of()
        );
        final var expectedOutput = expectedPagination.map(ListCategoriesOutput::from);

        Mockito.when(categoryGateway.findAll(Mockito.eq(query)))
                .thenReturn(expectedPagination);

        final var output = useCase.execute(query);

        Assertions.assertEquals(expectedOutput, output);
        Assertions.assertEquals(expectedPage, output.currentPage());
        Assertions.assertEquals(expectedPerPage, output.perPage());
        Assertions.assertEquals(0, output.total());
        Assertions.assertEquals(expectedOutput.items(), output.items());
    }
}
