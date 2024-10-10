package org.technicfox.emojify.menusystem.menu;

import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.technicfox.emojify.Emojify;
import org.technicfox.emojify.menusystem.Menu;
import org.technicfox.emojify.menusystem.PlayerMenuUtility;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class EmojiSelectorMenu extends Menu {
    public EmojiSelectorMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        try {
            String name = ChatColor.translateAlternateColorCodes('&', Emojify.getConfigUtil().getConfig().getString("inventories."+this.playerMenuUtility.getEmojiSlot()+".invName"));
            return name;
        }catch (Exception e){
            Bukkit.getLogger().severe("Error loading name of emoji menu: " + e.getMessage());
            e.printStackTrace();
        }
        return "Oops! Something went wrong! Please contact the developer.";
    }

    @Override
    public int getSlots() {
        try{
            return Emojify.getConfigUtil().getConfig().getInt( "inventories."+this.playerMenuUtility.getEmojiSlot()+".slots");
        }catch (Exception e){
            Bukkit.getLogger().severe("Error loading number of slots in one of emoji menus. Setting to 54: " + e.getMessage());
            e.printStackTrace();
        }
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        if (event.getSlot() == getSlots()-1){
            new EmojiHomeMenu(Emojify.getPlayerMenuUtility((Player) event.getWhoClicked())).open();
            return;
        }
        if (!Objects.requireNonNull(event.getCurrentItem()).getType().equals(Material.ENCHANTED_BOOK)) return;
        try {
            String name = event.getCurrentItem().getItemMeta().getItemName();
            Player player = (Player) event.getWhoClicked();

            TextComponent messege = new TextComponent(ChatColor.translateAlternateColorCodes('&', "&a&l&nClick me to copy emoji:&r ") + name);

            messege.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.translateAlternateColorCodes('&', "&a&l&nClick me to copy emoji:&r ") + name)));
            messege.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, name));
            player.spigot().sendMessage(messege);

            event.getWhoClicked().closeInventory();
        }catch (Exception e){
            Bukkit.getLogger().severe("Error getting emoji in a menu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void setMenuItems() {
        try {
            for (int i = 0; i < getSlots()-1; i++) {
                getEmoji(i, Emojify.getConfigUtil().getConfig().getInt(this.playerMenuUtility.getEmojiSlot()+".slot"+i+".id"),
                        Emojify.getConfigUtil().getConfig().getString(this.playerMenuUtility.getEmojiSlot()+".slot"+i+".name"),true);
            }
            ItemStack exit = new ItemStack(Material.MAP);
            ItemMeta meta = exit.getItemMeta();
            meta.setCustomModelData(1010);
            meta.setItemName("§c§lBack");
            exit.setItemMeta(meta);
            this.inventory.setItem(getSlots()-1, exit);
        }catch (Exception e){
            Bukkit.getLogger().severe("Error loading items in emoji menu: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
