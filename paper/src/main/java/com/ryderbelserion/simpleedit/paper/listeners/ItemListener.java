package com.ryderbelserion.simpleedit.paper.listeners;

import com.ryderbelserion.fusion.paper.api.enums.Scheduler;
import com.ryderbelserion.fusion.paper.api.scheduler.FoliaScheduler;
import com.ryderbelserion.simpleedit.paper.SimpleEdit;
import com.ryderbelserion.simpleedit.paper.api.UserManager;
import com.ryderbelserion.simpleedit.paper.api.enums.Keys;
import com.ryderbelserion.simpleedit.paper.api.enums.State;
import com.ryderbelserion.simpleedit.paper.api.objects.User;
import com.ryderbelserion.simpleedit.paper.menus.SchematicMenu;
import io.papermc.paper.persistence.PersistentDataContainerView;
import org.bukkit.NamespacedKey;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class ItemListener implements Listener {

    private final SimpleEdit plugin = SimpleEdit.getPlugin();

    private final Server server = this.plugin.getServer();

    private final UserManager user = this.plugin.getUserManager();

    private final NamespacedKey schematic_menu_button = Keys.schematic_menu_button.getNamespacedKey();
    private final NamespacedKey schematic_button = Keys.schematic_button.getNamespacedKey();
    private final NamespacedKey exit_button = Keys.exit_button.getNamespacedKey();
    private final NamespacedKey undo_button = Keys.undo_button.getNamespacedKey();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        final User user = this.user.getUser(player);

        if (user == null || !user.getStates().contains(State.editor_mode.getName())) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onInteractEvent(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final ItemStack item = event.getItem();

        if (item == null || item.isEmpty()) return;

        final PersistentDataContainerView itemData = item.getPersistentDataContainer();

        final User user = this.user.getUser(player);

        if (!user.getStates().contains(State.editor_mode.getName())) return;

        if (itemData.has(this.schematic_menu_button)) {
            new SchematicMenu(player).build();

            event.setCancelled(true);

            return;
        }

        if (itemData.has(this.schematic_button)) {
            final Action action = event.getAction();

            switch (action) {
                case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> {
                    final String schematic_name = itemData.get(this.schematic_button, PersistentDataType.STRING);

                    new FoliaScheduler(Scheduler.global_scheduler) {
                        @Override
                        public void run() {
                            server.dispatchCommand(player, "//paste " + schematic_name);
                        }
                    }.run();
                }

                default -> new SchematicMenu(player).build();
            }

            event.setCancelled(true);

            return;
        }

        if (itemData.has(this.undo_button)) {
            new FoliaScheduler(Scheduler.global_scheduler) {
                @Override
                public void run() {
                    server.dispatchCommand(player, "//undo");
                }
            }.run();

            event.setCancelled(true);

            return;
        }

        if (itemData.has(this.exit_button)) {
            user.restoreInventory(player);

            user.removeState(State.editor_mode);

            event.setCancelled(true);
        }
    }
}