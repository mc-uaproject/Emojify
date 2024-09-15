package org.technicfox.emojiesFox.menusystem.menu;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.technicfox.emojiesFox.EmojiesFox;
import org.technicfox.emojiesFox.menusystem.Menu;
import org.technicfox.emojiesFox.menusystem.PlayerMenuUtility;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class EmojiSelectorPage extends Menu {
    public EmojiSelectorPage(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ChatColor.WHITE+"七七七七七七七七ㇺ";
    }

    @Override
    public int getSlots() {
        return 9*6;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        if (e.getSlot() == 53){
            new EmojiHomePage(EmojiesFox.getPlayerMenuUtility((Player) e.getWhoClicked())).open();
            return;
        }

        try {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Clipboard clipboard = toolkit.getSystemClipboard();

            String data = this.playerMenuUtility.getConfig().getConfig().getString(this.playerMenuUtility.getEmojiMenuName()+".slot"+e.getSlot()+".name");
            if (data == null) return;

            StringSelection strSel = new StringSelection(data);
            clipboard.setContents(strSel, null);
            e.getWhoClicked().closeInventory();
            e.getWhoClicked().sendMessage(ChatColor.GREEN + "Скопійовано у буфер обміну");
        }catch (Exception ignored){}
    }

    @Override
    public void setMenuItems() {
        for (int i = 0; i < 45; i++) {
            try {
                this.inventory.setItem(i, getEmoji(this.playerMenuUtility.getConfig().getConfig().getInt(this.playerMenuUtility.getEmojiMenuName()+".slot"+i+".id"),
                        this.playerMenuUtility.getConfig().getConfig().getString(this.playerMenuUtility.getEmojiMenuName()+".slot"+i+".name")));
            }catch (Exception ignored){}
        }
        ItemStack glass = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta2 = glass.getItemMeta();
        meta2.setHideTooltip(true);
        glass.setItemMeta(meta2);

        for (int i = 45; i < 53; i++) {
            this.inventory.setItem(i, glass);
        }

        ItemStack exit = new ItemStack(Material.MAP);
        ItemMeta meta = exit.getItemMeta();
        meta.setCustomModelData(1010);
        meta.setItemName("§c§lПовернутись");
        exit.setItemMeta(meta);
        this.inventory.setItem(53, exit);
    }


}
