package com.ryderbelserion.simpleedit.paper.api.enums;

public enum State {

    editor_mode("editor_mode");

    private final String name;

    State(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}