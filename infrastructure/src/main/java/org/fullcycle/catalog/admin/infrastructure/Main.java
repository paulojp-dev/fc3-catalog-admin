package org.fullcycle.catalog.admin.infrastructure;

import org.fullcycle.catalog.admin.application.UseCase;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello and welcome!");
        System.out.println(new UseCase().execute());
    }
}