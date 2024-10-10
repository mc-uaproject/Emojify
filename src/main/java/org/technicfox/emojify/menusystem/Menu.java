package org.technicfox.emojify.menusystem;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class Menu implements InventoryHolder {


    protected PlayerMenuUtility playerMenuUtility;
    protected Inventory inventory;


    public Menu(PlayerMenuUtility playerMenuUtility) {
        this.playerMenuUtility = playerMenuUtility;
    }

    public abstract String getMenuName();

    public abstract int getSlots();

    public abstract void handleMenu(InventoryClickEvent e);

    public abstract void setMenuItems();

    public void open() {
        inventory = Bukkit.createInventory(this, getSlots(), getMenuName());

        this.setMenuItems();

        playerMenuUtility.getOwner().openInventory(inventory);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }


    public void getEmoji(Integer item, Integer id, String name, boolean hideTooltip) {
        try {
            final ItemStack emoji = new ItemStack(Material.ENCHANTED_BOOK);
            ItemMeta ItemMeta = emoji.getItemMeta();
            ItemMeta.setItemName(ChatColor.translateAlternateColorCodes('&', name));
            ItemMeta.setEnchantmentGlintOverride(false);
            if (hideTooltip) {
                ItemMeta.setHideTooltip(true);
            }
            ItemMeta.setCustomModelData(id);

            emoji.setItemMeta(ItemMeta);
            this.inventory.setItem(item, emoji);
        } catch (Exception ignored) {}
    }

}
