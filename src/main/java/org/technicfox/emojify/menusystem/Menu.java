package org.technicfox.emojify.menusystem;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import static org.bukkit.Bukkit.createInventory;

public abstract class Menu implements InventoryHolder {


    protected PlayerMenuUtility playerMenuUtility;
    protected Inventory inventory;


    public Menu(PlayerMenuUtility playerMenuUtility) {
        this.playerMenuUtility = playerMenuUtility;
    }

    public abstract Component getMenuName();

    public abstract int getSlots();

    public abstract void handleMenu(InventoryClickEvent e);

    public abstract void setMenuItems();

    public void open() {
        inventory = createInventory(this, this.getSlots(), this.getMenuName());

        this.setMenuItems();

        playerMenuUtility.getOwner().openInventory(inventory);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }


    public void getEmoji(Integer item, Integer id, String name, boolean hideTooltip) {
        try {
            final ItemStack emoji = new ItemStack(Material.ENCHANTED_BOOK);
            ItemMeta itemMeta = emoji.getItemMeta();

            var mm = MiniMessage.miniMessage();

            itemMeta.itemName(mm.deserialize(name));
            itemMeta.setEnchantmentGlintOverride(false);
            if (hideTooltip) {
                itemMeta.setHideTooltip(true);
            }
            itemMeta.setCustomModelData(id);

            emoji.setItemMeta(itemMeta);
            this.inventory.setItem(item, emoji);
        } catch (Exception ignored) {}
    }

}
