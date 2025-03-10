package org.fullcycle.catalog.admin.application.base;

public abstract class NoOutputUseCase<IN> {

    public abstract void execute(final IN command);
}