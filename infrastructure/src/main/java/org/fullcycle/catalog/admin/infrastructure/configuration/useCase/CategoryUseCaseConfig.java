package org.fullcycle.catalog.admin.infrastructure.configuration.useCase;

import org.fullcycle.catalog.admin.application.category.create.CreateCategoryUseCase;
import org.fullcycle.catalog.admin.application.category.delete.DeleteCategoryUseCase;
import org.fullcycle.catalog.admin.application.category.retrieve.get.GetCategoryByIdUseCase;
import org.fullcycle.catalog.admin.application.category.retrieve.list.ListCategoriesUseCase;
import org.fullcycle.catalog.admin.application.category.update.UpdateCategoryUseCase;
import org.fullcycle.catalog.admin.domain.category.CategoryGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryUseCaseConfig {

    private final CategoryGateway categoryGateway;

    public CategoryUseCaseConfig(CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Bean
    public CreateCategoryUseCase createCategoryUseCase() {
        return new CreateCategoryUseCase(categoryGateway);
    }

    @Bean
    public UpdateCategoryUseCase updateCategoryUseCase() {
        return new UpdateCategoryUseCase(categoryGateway);
    }

    @Bean
    public DeleteCategoryUseCase deleteCategoryUseCase() {
        return new DeleteCategoryUseCase(categoryGateway);
    }

    @Bean
    public GetCategoryByIdUseCase getCategoryByIdUseCase() {
        return new GetCategoryByIdUseCase(categoryGateway);
    }

    @Bean
    public ListCategoriesUseCase listCategoriesUseCase() {
        return new ListCategoriesUseCase(categoryGateway);
    }
}
