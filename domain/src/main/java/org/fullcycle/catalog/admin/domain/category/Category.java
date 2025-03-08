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

    public static Category of(final String name, final String description, final boolean isActive) {
        final var id = CategoryID.unique();
        final var now = Instant.now();
        return new Category(id, name, description, isActive, now, now, null);
    }

    @Override
    protected void validate() {
        new CategoryValidation(this).validate();
    }

    public void deactivate() {
        isActive = false;
        updatedAt = Instant.now();
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
}