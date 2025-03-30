package org.fullcycle.catalog.admin.infrastructure.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fullcycle.catalog.admin.application.category.create.CreateCategoryOutput;
import org.fullcycle.catalog.admin.application.category.create.CreateCategoryUseCase;
import org.fullcycle.catalog.admin.application.category.retrieve.list.ListCategoriesOutput;
import org.fullcycle.catalog.admin.application.category.retrieve.list.ListCategoriesUseCase;
import org.fullcycle.catalog.admin.domain.category.Category;
import org.fullcycle.catalog.admin.domain.category.CategoryID;
import org.fullcycle.catalog.admin.domain.pagination.Pagination;
import org.fullcycle.catalog.admin.infrastructure.ControllerTest;
import org.fullcycle.catalog.admin.infrastructure.category.model.CreateCategoryApiInput;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@ControllerTest(controllers = CategoryAPI.class)
public class CategoryAPITest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private CreateCategoryUseCase createCategoryUseCase;

    @MockitoBean
    private ListCategoriesUseCase listCategoriesUseCase;

    @Test
    public void givenAValidInput_whenCallsCreateCategory_shouldReturnCategoryId() throws Exception {
        final var input = new CreateCategoryApiInput("Valid Name", "Valid Description", true);
        final var request = MockMvcRequestBuilders
            .post("/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(input));
        final var expectedID = CategoryID.unique().getValue();
        Mockito.when(createCategoryUseCase.execute(Mockito.any()))
            .thenReturn(CreateCategoryOutput.from(expectedID));
        mvc.perform(request)
            .andDo(MockMvcResultHandlers.print())
            .andExpectAll(
                MockMvcResultMatchers.status().isCreated(),
                MockMvcResultMatchers.header().string("Location", "/categories/" + expectedID),
                MockMvcResultMatchers.header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE),
                MockMvcResultMatchers.jsonPath("$.id", Matchers.equalTo(expectedID))
            );
        Mockito
            .verify(createCategoryUseCase, Mockito.times(1))
            .execute(Mockito.argThat(
                cmd -> Objects.equals(input.name(), cmd.name())
                    && Objects.equals(input.description(), cmd.description())
                    && Objects.equals(input.active(), cmd.isActive())
            ));
    }

    @Test
    public void givenAInvalidInput_whenCallsCreateCategory_shouldReturnError() throws Exception {
        final var input = new CreateCategoryApiInput(null, null, null);
        final var request = MockMvcRequestBuilders
            .post("/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(input));
        mvc.perform(request)
            .andDo(MockMvcResultHandlers.print())
            .andExpectAll(
                MockMvcResultMatchers.status().isBadRequest(),
                MockMvcResultMatchers.jsonPath("name", Matchers.equalTo("Name cannot be null")),
                MockMvcResultMatchers.jsonPath("active", Matchers.equalTo("Active flag cannot be null"))
            );
    }

    @Test
    public void givenCategories_whenCallsListCategoriesWithoutParams_shouldReturnCategories() throws Exception {
        final var expectedSearch = "";
        final var expectedPage = 0;
        final var expectedQuantityPerPage = 10;
        final var expectedSortField = "name";
        final var expectedSortDirection = "asc";
        final var expectedCategories = Stream.of(
            Category.of("Category 1", "Description 1", true),
            Category.of("Category 2", "Description 2", false),
            Category.of("Category 3", "Description 3", true)
        ).map(ListCategoriesOutput::from).toList();
        final Pagination<ListCategoriesOutput> pagination = new Pagination<ListCategoriesOutput>(
            expectedPage,
            expectedQuantityPerPage,
            (long) expectedCategories.size(),
            expectedCategories
        );
        Mockito.when(listCategoriesUseCase.execute(Mockito.any()))
            .thenReturn(pagination);
        final var request = MockMvcRequestBuilders
            .get("/categories")
            .contentType(MediaType.APPLICATION_JSON);
        final var result = mvc.perform(request)
            .andDo(MockMvcResultHandlers.print())
            .andExpectAll(
                MockMvcResultMatchers.status().isOk(),
                MockMvcResultMatchers.jsonPath("$.currentPage", Matchers.is(expectedPage)),
                MockMvcResultMatchers.jsonPath("$.perPage", Matchers.is(expectedQuantityPerPage)),
                MockMvcResultMatchers.jsonPath("$.total", Matchers.is(expectedCategories.size()))
            );
        assertCategoryResult(result, expectedCategories.get(0), 0);
        assertCategoryResult(result, expectedCategories.get(1), 1);
        assertCategoryResult(result, expectedCategories.get(2), 2);
        Mockito.verify(listCategoriesUseCase, Mockito.times(1))
            .execute(Mockito.argThat(
                searchQuery -> Objects.equals(searchQuery.page(), expectedPage)
                    && Objects.equals(searchQuery.quantityPerPage(), expectedQuantityPerPage)
                    && Objects.equals(searchQuery.sortField(), expectedSortField)
                    && Objects.equals(searchQuery.sortDirection(), expectedSortDirection)
                    && Objects.equals(searchQuery.whereFilterTerms(), expectedSearch)
            ));
    }

    @Test
    public void givenNotCategories_whenCallsListCategoriesWithParams_shouldReturnCategories() throws Exception {
        final var expectedSearch = "Master Category";
        final var expectedPage = 1;
        final var expectedQuantityPerPage = 5;
        final var expectedSortField = "createdAt";
        final var expectedSortDirection = "desc";
        final List<ListCategoriesOutput> expectedCategories = List.of();
        final Pagination<ListCategoriesOutput> pagination = new Pagination<>(
            expectedPage,
            expectedQuantityPerPage,
            (long) 0,
            expectedCategories
        );
        Mockito.when(listCategoriesUseCase.execute(Mockito.any()))
            .thenReturn(pagination);
        final var request = MockMvcRequestBuilders
            .get("/categories")
            .param("search", expectedSearch)
            .param("page", String.valueOf(expectedPage))
            .param("quantityPerPage", String.valueOf(expectedQuantityPerPage))
            .param("sortField", expectedSortField)
            .param("sortDirection", expectedSortDirection)
            .contentType(MediaType.APPLICATION_JSON);
        final var result = mvc.perform(request)
            .andDo(MockMvcResultHandlers.print())
            .andExpectAll(
                MockMvcResultMatchers.status().isOk(),
                MockMvcResultMatchers.jsonPath("$.currentPage", Matchers.is(expectedPage)),
                MockMvcResultMatchers.jsonPath("$.perPage", Matchers.is(expectedQuantityPerPage)),
                MockMvcResultMatchers.jsonPath("$.total", Matchers.is(0))
            );
        Mockito.verify(listCategoriesUseCase, Mockito.times(1))
            .execute(Mockito.argThat(
                searchQuery -> Objects.equals(searchQuery.page(), expectedPage)
                    && Objects.equals(searchQuery.quantityPerPage(), expectedQuantityPerPage)
                    && Objects.equals(searchQuery.sortField(), expectedSortField)
                    && Objects.equals(searchQuery.sortDirection(), expectedSortDirection)
                    && Objects.equals(searchQuery.whereFilterTerms(), expectedSearch)
            ));
    }

    private void assertCategoryResult(
        ResultActions result,
        ListCategoriesOutput expectedCategory,
        Integer position
    ) throws Exception {
        result
            .andExpect(MockMvcResultMatchers.jsonPath(
                "$.items.[" + position + "].name",
                Matchers.equalTo(expectedCategory.name())
            ))
            .andExpect(MockMvcResultMatchers.jsonPath(
                "$.items.[" + position + "].description",
                Matchers.equalTo(expectedCategory.description())
            ))
            .andExpect(MockMvcResultMatchers.jsonPath(
                "$.items.[" + position + "].isActive",
                Matchers.equalTo(expectedCategory.isActive())
            ));
    }
}
