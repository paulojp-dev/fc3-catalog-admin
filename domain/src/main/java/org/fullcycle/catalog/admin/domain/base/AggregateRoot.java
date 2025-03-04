package org.fullcycle.catalog.admin.domain.base;

public class AggregateRoot<ID extends Identifier> extends Entity<ID> {

    protected AggregateRoot(ID id) {
        super(id);
    }
}
