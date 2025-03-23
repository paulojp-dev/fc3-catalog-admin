package org.fullcycle.catalog.admin.domain.base;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

public abstract class Entity<ID extends Identifier> {

    protected final ID id;
    protected Instant createdAt;
    protected Instant updatedAt;
    protected Instant deletedAt;

    protected Entity(
        final ID id,
        final Instant createdAt,
        final Instant updatedAt,
        final Instant deletedAt
    ) {
        Objects.requireNonNull(id, "'id' should not be null");
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public ID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Entity<?> entity = (Entity<?>) o;
        return Objects.equals(getId(), entity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Optional<Instant> getDeletedAt() {
        return Optional.ofNullable(deletedAt);
    }

    protected abstract void validate();
}
