package com.ryderbelserion.simpleedit.paper.menus;

import com.ryderbelserion.fusion.api.utils.FileUtils;
import com.ryderbelserion.fusion.paper.api.builder.gui.interfaces.Gui;
import com.ryderbelserion.fusion.paper.api.builder.gui.types.PaginatedGui;
import com.ryderbelserion.fusion.paper.api.builder.items.modern.ItemBuilder;
import com.ryderbelserion.simpleedit.paper.SimpleEdit;
import com.ryderbelserion.simpleedit.paper.api.UserManager;
import com.ryderbelserion.simpleedit.paper.api.enums.State;
import com.ryderbelserion.simpleedit.paper.api.objects.User;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import io.papermc.paper.persistence.PersistentDataContainerView;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SchematicMenu {

    private final List<String> schematics = new ArrayList<>();

    private final SimpleEdit plugin = SimpleEdit.getPlugin();

    private final UserManager userManager = this.plugin.getUserManager();

    private final ComponentLogger logger = this.plugin.getComponentLogger();

    private final Player player;

    public SchematicMenu(final Player player) {
        final Path path = JavaPlugin.getPlugin(WorldEditPlugin.class).getDataPath();

        this.schematics.addAll(FileUtils.getNamesByExtension(Optional.of("schematics"), path, ".schematic"));
        this.schematics.addAll(FileUtils.getNamesByExtension(Optional.of("schematics"), path, ".schem"));

        this.player = player;
    }

    public void build() {
        final PaginatedGui gui = Gui.paginated().setTitle("<red>Schematic List").setRows(6).disableInteractions().create();

        final NamespacedKey key = new NamespacedKey(this.plugin, "schematic_button");

        for (final String schematic : this.schematics) {
            final ItemBuilder itemBuilder = ItemBuilder.from(ItemType.ARROW).setDisplayName("<red>%schematic%").addPlaceholder("%schematic%", schematic).setPersistentString(key, schematic).build();

            gui.addItem(itemBuilder.asGuiItem(action -> {
                final ItemStack itemStack = action.getCurrentItem();

                if (itemStack == null || itemStack.isEmpty()) return;

                final PersistentDataContainerView container = itemStack.getPersistentDataContainer();

                if (!container.has(key, PersistentDataType.STRING)) return;

                final String schematic_name = container.get(key, PersistentDataType.STRING);
            }));
        }

        gui.setItem(6, 4, ItemBuilder.from(ItemType.BARRIER).setDisplayName("<red>Exit").build().asGuiItem(action -> {
            final User user = this.userManager.getUser(this.player);

            user.removeState(State.editor_mode);

            gui.close(this.player, true);
        }));

        gui.open(this.player);
    }

    public final List<String> getSchematics() {
        return this.schematics;
    }
}