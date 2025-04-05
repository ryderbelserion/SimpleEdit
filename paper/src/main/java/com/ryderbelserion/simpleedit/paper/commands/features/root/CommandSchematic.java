package com.ryderbelserion.simpleedit.paper.commands.features.root;

import com.ryderbelserion.simpleedit.paper.api.enums.State;
import com.ryderbelserion.simpleedit.paper.api.objects.User;
import com.ryderbelserion.simpleedit.paper.commands.BaseCommand;
import com.ryderbelserion.simpleedit.paper.menus.SchematicMenu;
import dev.triumphteam.cmd.core.annotations.Command;
import dev.triumphteam.cmd.core.annotations.Permission;
import dev.triumphteam.cmd.core.annotations.Syntax;
import dev.triumphteam.cmd.core.enums.Mode;
import org.bukkit.entity.Player;

public class CommandSchematic extends BaseCommand {

    @Command("schematic")
    @Permission(value = "simpleedit.schematic", def = Mode.OP)
    @Syntax("/simpleedit schematic")
    public void schematic(final Player player) {
        final User user = this.userManager.getUser(player);

        user.addState(State.editor_mode);

        new SchematicMenu(player).build();
    }
}