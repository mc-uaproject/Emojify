package org.technicfox.emojisFox.menusystem.menu;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.technicfox.emojisFox.EmojisFox;
import org.technicfox.emojisFox.menusystem.Menu;
import org.technicfox.emojisFox.menusystem.PlayerMenuUtility;

public class EmojiHomePage extends Menu {

    public EmojiHomePage(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return  ChatColor.translateAlternateColorCodes('&',EmojisFox.getConfigUtil().getConfig().getString("inventories.defaultMenuConfig.invName"));
    }

    @Override
    public int getSlots() {
        return EmojisFox.getConfigUtil().getConfig().getInt("inventories.defaultMenuConfig.slots");
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        if (e.getSlot() == getSlots()-1){
            e.getWhoClicked().closeInventory();
            return;
        }
        if (e.getCurrentItem().getType().equals(Material.AIR) || e.getCurrentItem().getType().equals(Material.GRAY_STAINED_GLASS_PANE)) return;
        try {
            String data = EmojisFox.getConfigUtil().getConfig().getString("inventories.slot"+e.getSlot()+".name");
            if (data == null) return;
            Bukkit.getLogger().info(e.getSlot() + " " + data);
            EmojisFox.getPlayerMenuUtility((Player) e.getWhoClicked()).setEmojiSlot("slot"+e.getSlot());
            new EmojiSelectorPage(EmojisFox.getPlayerMenuUtility((Player) e.getWhoClicked())).open();

        }catch (Exception ignored){}

    }

    @Override
    public void setMenuItems() {
        for (int i = 0; i < getSlots()-9; i++) {
            try {
                this.inventory.setItem(i, getEmoji(EmojisFox.getConfigUtil().getConfig().getInt("inventories.slot"+i+".id"),
                        EmojisFox.getConfigUtil().getConfig().getString("inventories.slot"+i+".name")));
            }catch (Exception ignored){}
        }
        ItemStack glass = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta2 = glass.getItemMeta();
        meta2.setHideTooltip(true);
        glass.setItemMeta(meta2);

        for (int i = getSlots()-9; i < getSlots()-1; i++) {
            this.inventory.setItem(i, glass);
        }

        ItemStack exit = new ItemStack(Material.MAP);
        ItemMeta meta = exit.getItemMeta();
        meta.setCustomModelData(1010);
        meta.setItemName("§c§lВийти");
        exit.setItemMeta(meta);
        this.inventory.setItem(getSlots()-1, exit);
    }

}
