package com.ryderbelserion.simpleedit.paper.commands.features.root;

import com.ryderbelserion.fusion.paper.api.builder.items.modern.ItemBuilder;
import com.ryderbelserion.simpleedit.paper.api.enums.Keys;
import com.ryderbelserion.simpleedit.paper.api.enums.State;
import com.ryderbelserion.simpleedit.paper.api.objects.User;
import com.ryderbelserion.simpleedit.paper.commands.BaseCommand;
import dev.triumphteam.cmd.core.annotations.Command;
import dev.triumphteam.cmd.core.annotations.Flag;
import dev.triumphteam.cmd.core.annotations.Permission;
import dev.triumphteam.cmd.core.annotations.Syntax;
import dev.triumphteam.cmd.core.argument.keyed.Flags;
import dev.triumphteam.cmd.core.enums.Mode;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemType;

public class CommandSchematic extends BaseCommand {

    private final NamespacedKey schematic_menu_button = Keys.schematic_menu_button.getNamespacedKey();
    private final NamespacedKey exit_button = Keys.exit_button.getNamespacedKey();

    @Command("schematic")
    @Permission(value = "simpleedit.schematic", def = Mode.OP)
    @Syntax("/simpleedit schematic")
    @Flag(flag = "e", longFlag = "exit")
    public void schematic(final Player player, final Flags flag) {
        final User user = this.userManager.getUser(player);

        if (flag.hasFlag("e") && user.getStates().contains(State.editor_mode.getName())) {
            user.restoreInventory(player);

            user.removeState(State.editor_mode);

            return;
        }

        user.storeInventory(player);

        final Inventory inventory = player.getInventory();

        final ItemBuilder exit_button = ItemBuilder.from(ItemType.BARRIER).setPersistentString(this.exit_button, "1").setDisplayName("<red>Exit Mode");
        final ItemBuilder schematic_button = ItemBuilder.from(ItemType.CHEST).setPersistentString(this.schematic_menu_button, "1").setDisplayName("<yellow>Schematic Menu");

        inventory.setItem(0, exit_button.asItemStack());
        inventory.setItem(8, schematic_button.asItemStack());

        user.addState(State.editor_mode);
    }
}