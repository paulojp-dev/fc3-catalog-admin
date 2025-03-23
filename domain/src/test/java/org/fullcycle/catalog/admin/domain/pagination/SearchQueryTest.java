package org.fullcycle.catalog.admin.domain.pagination;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class SearchQueryTest {

    @ParameterizedTest
    @MethodSource("validCategoryData")
    public void givenAllParams_whenInstantiate_thenValidQuery(Integer page,
                                                              Integer quantityPerPage,
                                                              String terms,
                                                              String sortField,
                                                              String sortDirection
    ) {
        final var actualQuery = new SearchQuery(
            page,
            quantityPerPage,
            terms,
            sortField,
            sortDirection
        );
        Assertions.assertEquals(page, actualQuery.page());
        Assertions.assertEquals(quantityPerPage, actualQuery.quantityPerPage());
        Assertions.assertEquals(terms, actualQuery.whereFilterTerms());
        Assertions.assertEquals(sortField, actualQuery.sortField());
        Assertions.assertEquals(sortDirection, actualQuery.sortDirection());
    }

    @ParameterizedTest
    @MethodSource("invalidCategoryData")
    public void givenNullPageParam_whenInstantiate_thenValidQuery(
        Integer page,
        Integer quantityPerPage,
        String terms,
        String sortField,
        String sortDirection,
        String errorMessage
    ) {
        Executable executable = () -> new SearchQuery(page, quantityPerPage, terms, sortField, sortDirection);
        final var exception = Assertions.assertThrows(IllegalArgumentException.class, executable);
        Assertions.assertEquals(errorMessage, exception.getMessage());
    }

    static Stream<Arguments> invalidCategoryData() {
        final var pageError = "Page must be a non-null, non-negative integer";
        final var quantityError = "Quantity per page must be a non-null, positive integer";
        return Stream.of(
            Arguments.of(null, 10, "terms", "name", "asc", pageError),
            Arguments.of(-1, 10, "terms", "name", "asc", pageError),
            Arguments.of(10, null, "terms", "name", "asc", quantityError),
            Arguments.of(10, 0, "terms", "name", "asc", quantityError),
            Arguments.of(10, -1, "terms", "name", "asc", quantityError));
    }

    static Stream<Arguments> validCategoryData() {
        return Stream.of(
            Arguments.of(0, 1, "terms", "name", "asc"),
            Arguments.of(10, 100, null, null, null));
    }
}
