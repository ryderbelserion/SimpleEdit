package com.ryderbelserion.simpleedit.paper.api;

import com.ryderbelserion.fusion.api.utils.FileUtils;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import org.bukkit.plugin.java.JavaPlugin;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SchematicManager {

    private final WorldEditPlugin instance = JavaPlugin.getPlugin(WorldEditPlugin.class);

    private final Path path = instance.getDataPath().resolve("schematics");

    public List<Path> getPaths() {
        final List<Path> paths = new ArrayList<>();

        paths.addAll(FileUtils.getFiles(this.path, ".schematic"));
        paths.addAll(FileUtils.getFiles(this.path, ".schem"));

        return paths;
    }

    public Path getPath(final String fileName) {
        return this.path.resolve(fileName);
    }
}