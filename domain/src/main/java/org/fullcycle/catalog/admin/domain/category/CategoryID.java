package org.fullcycle.catalog.admin.domain.category;

import org.fullcycle.catalog.admin.domain.base.ID;

public class CategoryID extends ID {

    private CategoryID(String value) {
        super(value);
    }

    public static CategoryID of(String id) {
        return new CategoryID(id);
    }

    public static CategoryID unique() {
        return new CategoryID(makeUUID());
    }
}
