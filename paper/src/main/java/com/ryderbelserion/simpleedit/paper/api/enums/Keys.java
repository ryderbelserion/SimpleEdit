package com.ryderbelserion.simpleedit.paper.api.enums;

import com.ryderbelserion.simpleedit.paper.SimpleEdit;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("rawtypes")
public enum Keys {

    schematic_menu_button("schematic_menu", PersistentDataType.STRING),
    schematic_button("schematic_button", PersistentDataType.STRING),
    exit_button("exit_button", PersistentDataType.STRING);

    private final SimpleEdit plugin = SimpleEdit.getPlugin();

    private final String NamespacedKey;
    private final PersistentDataType type;

    Keys(@NotNull final String NamespacedKey, @NotNull final PersistentDataType type) {
        this.NamespacedKey = NamespacedKey;
        this.type = type;
    }

    public @NotNull final NamespacedKey getNamespacedKey() {
        return new NamespacedKey(this.plugin, this.plugin.getName().toLowerCase() + "_" + this.NamespacedKey);
    }

    public @NotNull final PersistentDataType getType() {
        return this.type;
    }
}