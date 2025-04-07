package com.ryderbelserion.simpleedit.paper.api;

import com.ryderbelserion.fusion.api.utils.FileUtils;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SchematicManager {

    private final WorldEditPlugin instance = JavaPlugin.getPlugin(WorldEditPlugin.class);

    private final WorldEdit api = WorldEdit.getInstance();

    private final Path path = instance.getDataPath().resolve("schematics");

    public List<Path> getPaths() {
        final List<Path> paths = new ArrayList<>();

        paths.addAll(FileUtils.getFiles(this.path, ".schematic"));
        paths.addAll(FileUtils.getFiles(this.path, ".schem"));

        return paths;
    }

    public void pasteSchematic(final Player player, final Location location, final String fileName) {
        final Clipboard clipboard = getClipboard(fileName);

        if (clipboard == null) return;

        final World world = player.getWorld();

        final double x = location.x();
        final double y = location.y();
        final double z = location.z();

        try (final EditSession session = this.api.newEditSession(BukkitAdapter.adapt(world))) {
            final Operation operation = new ClipboardHolder(clipboard).createPaste(session)
                    .to(BlockVector3.at(x, y, z))
                    .ignoreAirBlocks(false)
                    .build();

            Operations.complete(operation);
        } catch (final WorldEditException exception) {
            exception.printStackTrace();
        }
    }

    public Clipboard getClipboard(final String fileName) {
        final File file = getPath(fileName).toFile();

        final ClipboardFormat format = ClipboardFormats.findByFile(file);

        Clipboard clipboard = null;

        if (format != null) {
            try (final ClipboardReader reader = format.getReader(new FileInputStream(file))) {
                clipboard = reader.read();
            } catch (final Exception exception) {
                exception.printStackTrace();
            }
        }

        return clipboard;
    }

    public Path getPath(final String fileName) {
        return this.path.resolve(fileName);
    }
}