/*
 * Copyright (c) 2023, Thomas Meaney
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */
package de.eintosti.buildsystem.navigator.inventory;

import com.google.common.collect.Sets;
import de.eintosti.buildsystem.BuildSystemPlugin;
import de.eintosti.buildsystem.Messages;
import de.eintosti.buildsystem.api.world.data.Visibility;
import de.eintosti.buildsystem.api.world.data.WorldStatus;
import de.eintosti.buildsystem.player.BuildPlayerManager;
import de.eintosti.buildsystem.util.InventoryUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class PrivateInventory extends FilteredWorldsInventory {

    private final BuildSystemPlugin plugin;
    private final InventoryUtils inventoryUtils;
    private final BuildPlayerManager playerManager;

    public PrivateInventory(BuildSystemPlugin plugin) {
        super(plugin, "private_title", "private_no_worlds", Visibility.PRIVATE,
                Sets.newHashSet(WorldStatus.NOT_STARTED, WorldStatus.IN_PROGRESS, WorldStatus.ALMOST_FINISHED, WorldStatus.FINISHED)
        );

        this.plugin = plugin;
        this.inventoryUtils = plugin.getInventoryUtil();
        this.playerManager = plugin.getPlayerManager();
    }

    @Override
    protected Inventory createInventory(Player player) {
        Inventory inventory = super.createInventory(player);
        if (playerManager.canCreateWorld(player, super.getVisibility())) {
            addWorldCreateItem(inventory, player);
        }
        return inventory;
    }

    private void addWorldCreateItem(Inventory inventory, Player player) {
        if (player.hasPermission("buildsystem.create.private")) {
            inventoryUtils.addUrlSkull(inventory, 49, Messages.getString("private_create_world"), "3edd20be93520949e6ce789dc4f43efaeb28c717ee6bfcbbe02780142f716");
        } else {
            inventoryUtils.addGlassPane(plugin, player, inventory, 49);
        }
    }
}