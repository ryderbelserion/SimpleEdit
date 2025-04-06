package com.ryderbelserion.simpleedit.paper.listeners;

import com.ryderbelserion.simpleedit.paper.SimpleEdit;
import com.ryderbelserion.simpleedit.paper.api.UserManager;
import com.ryderbelserion.simpleedit.paper.api.enums.State;
import com.ryderbelserion.simpleedit.paper.api.objects.User;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class TrafficListener implements Listener {

    private final SimpleEdit plugin = SimpleEdit.getPlugin();

    private final UserManager userManager = this.plugin.getUserManager();

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        this.userManager.addUser(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();

        final User user = this.userManager.getUser(player);

        if (user == null || !user.getStates().contains(State.editor_mode.getName())) return;

        user.restoreInventory(player);

        player.setGameMode(GameMode.SURVIVAL);

        this.userManager.removeUser(player);
    }
}