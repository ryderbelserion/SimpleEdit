package com.ryderbelserion.simpleedit.paper.commands.features;

import com.ryderbelserion.simpleedit.paper.SimpleEdit;
import com.ryderbelserion.simpleedit.paper.commands.features.root.CommandSchematic;
import dev.triumphteam.cmd.bukkit.BukkitCommandManager;
import dev.triumphteam.cmd.core.suggestion.SuggestionKey;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;

public class CommandHandler {

    private final SimpleEdit plugin = SimpleEdit.getPlugin();

    private final BukkitCommandManager<CommandSender> commandManager = BukkitCommandManager.create(this.plugin);

    public CommandHandler() {
        load();
    }

    public void load() {
        final Server server = this.plugin.getServer();

        this.commandManager.registerSuggestion(SuggestionKey.of("players"), (sender, context) -> server.getOnlinePlayers().stream().map(Player::getName).toList());

        this.commandManager.registerSuggestion(SuggestionKey.of("numbers"), (sender, context) -> {
            final List<String> numbers = new ArrayList<>();

            for (int i = 1; i <= 100; i++) numbers.add(String.valueOf(i));

            return numbers;
        });

        this.commandManager.registerSuggestion(SuggestionKey.of("doubles"), (sender, context) -> {
            final List<String> numbers = new ArrayList<>();

            int count = 0;

            while (count <= 1000) {
                double x = count / 10.0;

                numbers.add(String.valueOf(x));

                count++;
            }

            return numbers;
        });


        List.of(
                new CommandSchematic()
        ).forEach(this.commandManager::registerCommand);
    }

    public final BukkitCommandManager<CommandSender> getCommandManager() {
        return this.commandManager;
    }
}