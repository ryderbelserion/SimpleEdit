package com.ryderbelserion.gradle.paper;

import org.bukkit.plugin.java.JavaPlugin;

public class GradleTemplate extends JavaPlugin {

    @Override
    public void onEnable() {
        getComponentLogger().warn("The plugin has enabled.");
    }

    @Override
    public void onDisable() {
        getComponentLogger().warn("The plugin has disabled.");
    }
}