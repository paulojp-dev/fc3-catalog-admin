package org.fullcycle.catalog.admin.application;

import org.fullcycle.catalog.admin.domain.category.Category;

public class UseCase {

    public Category execute() {
        return new Category();
    }
}