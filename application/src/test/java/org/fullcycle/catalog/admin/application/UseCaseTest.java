package org.fullcycle.catalog.admin.application;

import org.fullcycle.catalog.admin.domain.category.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UseCaseTest {

    @Test
    public void testCreateUseCase() {
        Assertions.assertInstanceOf(Category.class, new UseCase().execute());
    }
}
