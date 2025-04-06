package com.ryderbelserion.simpleedit.paper.menus;

import com.ryderbelserion.fusion.paper.api.builder.gui.interfaces.Gui;
import com.ryderbelserion.fusion.paper.api.builder.gui.types.PaginatedGui;
import com.ryderbelserion.fusion.paper.api.builder.items.modern.ItemBuilder;
import com.ryderbelserion.simpleedit.paper.SimpleEdit;
import com.ryderbelserion.simpleedit.paper.api.UserManager;
import com.ryderbelserion.simpleedit.paper.api.enums.Keys;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import io.papermc.paper.persistence.PersistentDataContainerView;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
public class SchematicMenu {

    private final SimpleEdit plugin = SimpleEdit.getPlugin();

    private final UserManager userManager = this.plugin.getUserManager();

    private final List<Path> paths = new ArrayList<>();

    private final PaginatedGui gui;
    private final Player player;
    private final Path path;
    private final int rows;

    public SchematicMenu(final Player player) {
        this.path = JavaPlugin.getPlugin(WorldEditPlugin.class).getDataPath();

        this.paths.addAll(this.plugin.getSchematicManager().getPaths());

        this.gui = Gui.paginated().setTitle("<red>Schematic List").setRows(6).disableInteractions().create();
        this.rows = this.gui.getRows();
        this.player = player;
    }

    public void build() {
        final NamespacedKey key = Keys.schematic_button.getNamespacedKey();

        for (final Path path : this.paths) {
            final String schematic = path.getFileName().toString();

            final ItemBuilder itemBuilder = ItemBuilder.from(ItemType.ARROW).setPersistentString(key, schematic).setDisplayName("<red>%schematic%").addPlaceholder("%schematic%", schematic);

            this.gui.addItem(itemBuilder.asGuiItem(event -> {
                final ItemStack itemStack = event.getCurrentItem();

                if (itemStack == null || itemStack.isEmpty()) return;

                final PersistentDataContainerView container = itemStack.getPersistentDataContainer();

                if (!container.has(key, PersistentDataType.STRING)) return;

                player.getInventory().setItem(5, itemStack);
            }));
        }

        this.gui.setItem(6, 5, ItemBuilder.from(ItemType.BARRIER).setDisplayName("<red>Exit").asGuiItem(event -> this.gui.close(this.player, InventoryCloseEvent.Reason.CANT_USE, true)));

        handleBackButton(6, 4);

        handleNextButton(6, 6);

        this.gui.open(this.player);
    }

    private void handleBackButton(final int row, final int column) {
        this.gui.setItem(row, column, ItemBuilder.from(ItemType.PAPER).setDisplayName("<red>Previous").asGuiItem(event -> { // next
            this.gui.previous();

            final int page = this.gui.getCurrentPageNumber();

            if (page <= 1) {
                this.gui.removeItem(row, column);

                return;
            }

            if (page < this.gui.getMaxPages()) {
                handleNextButton(row, column);
            }
        }));
    }

    private void handleNextButton(final int row, final int column) {
        this.gui.setItem(row, column, ItemBuilder.from(ItemType.PAPER).setDisplayName("<red>Next").asGuiItem(event -> { // next
            this.gui.next();

            final int page = this.gui.getCurrentPageNumber();

            if (page >= this.gui.getMaxPages()) {
                this.gui.removeItem(row, column);
            } else {
                handleNextButton(row, column);
            }

            if (page <= 1) {
                this.gui.removeItem(row, column);
            } else {
                handleBackButton(row, column);
            }
        }));
    }

    public List<Path> getPaths() {
        return this.paths;
    }

    public Path getPath() {
        return this.path;
    }

    public int getRows() {
        return this.rows;
    }
}