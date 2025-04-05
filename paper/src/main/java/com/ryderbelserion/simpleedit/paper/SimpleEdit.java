package com.ryderbelserion.simpleedit.paper;

import com.ryderbelserion.fusion.api.files.FileManager;
import com.ryderbelserion.fusion.paper.FusionPaper;
import com.ryderbelserion.simpleedit.paper.api.UserManager;
import com.ryderbelserion.simpleedit.paper.commands.features.CommandHandler;
import com.ryderbelserion.simpleedit.paper.listeners.TrafficListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleEdit extends JavaPlugin {

    public static SimpleEdit getPlugin() {
        return JavaPlugin.getPlugin(SimpleEdit.class);
    }

    private FileManager fileManager;
    private UserManager userManager;
    private FusionPaper api;

    @Override
    public void onEnable() {
        this.api = new FusionPaper(getComponentLogger(), getDataPath());
        this.api.enable(this);

        this.fileManager = this.api.getFileManager();

        this.userManager = new UserManager();

        final PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new TrafficListener(), this);

        new CommandHandler();
    }

    @Override
    public void onDisable() {
        if (this.api != null) {
            this.api.save();
        }
    }

    public final FileManager getFileManager() {
        return this.fileManager;
    }

    public final UserManager getUserManager() {
        return this.userManager;
    }

    public final FusionPaper getFusion() {
        return this.api;
    }
}