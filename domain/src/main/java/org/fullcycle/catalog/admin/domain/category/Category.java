package org.fullcycle.catalog.admin.domain.category;

import org.fullcycle.catalog.admin.domain.base.AggregateRoot;

import java.time.Instant;

public class Category extends AggregateRoot<CategoryID> {

    private String name;
    private String description;
    private boolean isActive;

    private Category(
        final CategoryID id,
        final String name,
        final String description,
        final boolean isActive,
        final Instant createdAt,
        final Instant updatedAt,
        final Instant deletedAt
    ) {
        super(id, createdAt, updatedAt, deletedAt);
        this.name = name;
        this.description = description;
        this.isActive = isActive;
        validate();
    }

    public static Category of(
        final CategoryID id,
        final String name,
        final String description,
        final boolean isActive,
        final Instant createdAt,
        final Instant updatedAt,
        final Instant deletedAt
    ) {
        return new Category(id, name, description, isActive, createdAt, updatedAt, deletedAt);
    }

    public static Category of(final String name, final String description, final boolean isActive) {
        final var now = Instant.now();
        return Category.of(CategoryID.unique(), name, description, isActive, now, now, null);
    }

    public Category deactivate() {
        isActive = false;
        updatedAt = Instant.now();
        return this;
    }

    public Category activate() {
        isActive = true;
        updatedAt = Instant.now();
        return this;
    }

    public Category update(final String name, final String description, Boolean isActive) {
        this.name = name;
        this.description = description;
        this.updatedAt = Instant.now();
        if (isActive) {
            activate();
        } else {
            deactivate();
        }
        validate();
        return this;
    }

    public CategoryID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return isActive;
    }

    @Override
    protected void validate() {
        new CategoryValidation(this).validate();
    }
}
