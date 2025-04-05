package com.ryderbelserion.simpleedit.paper.listeners;

import com.ryderbelserion.simpleedit.paper.SimpleEdit;
import com.ryderbelserion.simpleedit.paper.api.UserManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class TrafficListener implements Listener {

    private final SimpleEdit plugin = SimpleEdit.getPlugin();

    private final UserManager userManager = this.plugin.getUserManager();

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        this.userManager.addUser(event.getPlayer());
    }
}