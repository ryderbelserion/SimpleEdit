package com.ryderbelserion.simpleedit.paper.api.objects;

import com.ryderbelserion.simpleedit.paper.api.enums.State;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

public class User {

    private final List<String> states = new ArrayList<>();

    private ItemStack[] armorContents;
    private ItemStack[] contents;

    public void storeInventory(@NotNull final Player player) {
        final PlayerInventory inventory = player.getInventory();

        this.armorContents = inventory.getArmorContents();
        this.contents = inventory.getContents();

        inventory.clear();
    }

    public void restoreInventory(@NotNull final Player player) {
        final PlayerInventory inventory = player.getInventory();

        inventory.clear();

        if (this.armorContents != null) {
            inventory.setArmorContents(this.armorContents);
        }

        if (this.contents != null) {
            inventory.setContents(this.contents);
        }

        this.armorContents = null;
        this.contents = null;
    }

    public void addState(@NotNull final State state) {
        this.states.add(state.getName());
    }

    public void removeState(@NotNull final State state) {
        this.states.remove(state.getName());
    }

    public final List<String> getStates() {
        return this.states;
    }
}