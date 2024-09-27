package org.technicfox.emojify.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;
import org.technicfox.emojify.menusystem.Menu;

public class MenuListener implements Listener {

    @EventHandler
    public void onMenuClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        InventoryHolder inventoryHolder = event.getInventory().getHolder();

        if (inventoryHolder instanceof Menu) {
            if(event.getCurrentItem() == null){return;}

            event.setCancelled(true);
            Menu menu = (Menu) inventoryHolder;
            menu.handleMenu(event);
        }
    }
}
