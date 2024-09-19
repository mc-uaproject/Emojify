package org.technicfox.emojisFox.menusystem.menu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.technicfox.emojisFox.EmojisFox;
import org.technicfox.emojisFox.menusystem.Menu;
import org.technicfox.emojisFox.menusystem.PlayerMenuUtility;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class EmojiSelectorPage extends Menu {
    public EmojiSelectorPage(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return  ChatColor.translateAlternateColorCodes('&',EmojisFox.getConfigUtil().getConfig().getString("inventories."+this.playerMenuUtility.getEmojiSlot()+".invName"));
    }

    @Override
    public int getSlots() {
        return EmojisFox.getConfigUtil().getConfig().getInt( "inventories."+this.playerMenuUtility.getEmojiSlot()+".slots");
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        if (e.getSlot() == getSlots()-1){
            new EmojiHomePage(EmojisFox.getPlayerMenuUtility((Player) e.getWhoClicked())).open();
            return;
        }

        try {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Clipboard clipboard = toolkit.getSystemClipboard();

            if (e.getCurrentItem().getType().equals(Material.AIR) || e.getCurrentItem().getType().equals(Material.GRAY_STAINED_GLASS_PANE)) return;

            StringSelection strSel = new StringSelection(e.getCurrentItem().getItemMeta().getItemName());
            clipboard.setContents(strSel, null);
            e.getWhoClicked().closeInventory();
            e.getWhoClicked().sendMessage(ChatColor.GREEN + "Скопійовано у буфер обміну");
        }catch (Exception ignored){}
    }

    @Override
    public void setMenuItems() {
        for (int i = 0; i < getSlots()-9; i++) {
            try {
                this.inventory.setItem(i, getEmoji(EmojisFox.getConfigUtil().getConfig().getInt(this.playerMenuUtility.getEmojiSlot()+".slot"+i+".id"),
                        EmojisFox.getConfigUtil().getConfig().getString(this.playerMenuUtility.getEmojiSlot()+".slot"+i+".name")));
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
        meta.setItemName("§c§lПовернутись");
        exit.setItemMeta(meta);
        this.inventory.setItem(getSlots()-1, exit);
    }


}
