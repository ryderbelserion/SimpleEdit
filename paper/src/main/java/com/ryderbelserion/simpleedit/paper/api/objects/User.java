package com.ryderbelserion.simpleedit.paper.api.objects;

import com.ryderbelserion.simpleedit.paper.api.enums.State;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

public class User {

    private final List<State> states = new ArrayList<>();

    public void addState(@NotNull final State state) {
        this.states.add(state);
    }

    public void removeState(@NotNull final State state) {
        this.states.remove(state);
    }

    public final List<State> getStates() {
        return this.states;
    }
}