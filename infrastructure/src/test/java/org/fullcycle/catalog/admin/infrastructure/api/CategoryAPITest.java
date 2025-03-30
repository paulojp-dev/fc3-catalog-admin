package org.fullcycle.catalog.admin.infrastructure.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fullcycle.catalog.admin.application.category.create.CreateCategoryOutput;
import org.fullcycle.catalog.admin.application.category.create.CreateCategoryUseCase;
import org.fullcycle.catalog.admin.domain.category.CategoryID;
import org.fullcycle.catalog.admin.domain.exception.DomainValidationException;
import org.fullcycle.catalog.admin.domain.validation.Error;
import org.fullcycle.catalog.admin.infrastructure.ControllerTest;
import org.fullcycle.catalog.admin.infrastructure.category.model.CreateCategoryApiInput;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Objects;

@ControllerTest(controllers = CategoryAPI.class)
public class CategoryAPITest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private CreateCategoryUseCase createCategoryUseCase;

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
}
