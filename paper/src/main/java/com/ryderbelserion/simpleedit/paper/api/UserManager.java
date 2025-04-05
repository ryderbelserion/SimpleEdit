package com.ryderbelserion.simpleedit.paper.api;

import com.ryderbelserion.simpleedit.paper.api.objects.User;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserManager {

    private final Map<UUID, User> users = new HashMap<>();

    public void addUser(@NotNull final Player player) {
        this.users.putIfAbsent(player.getUniqueId(), new User());
    }

    public void removeUser(@NotNull final Player player) {
        this.users.remove(player.getUniqueId());
    }

    public User getUser(@NotNull final Player player) {
        return this.users.get(player.getUniqueId());
    }
}