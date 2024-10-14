package org.technicfox.emojify.menusystem.menu;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.technicfox.emojify.Emojify;
import org.technicfox.emojify.menusystem.Menu;
import org.technicfox.emojify.menusystem.PlayerMenuUtility;

import java.util.Objects;

public class EmojiHomeMenu extends Menu {
    private static final int DEFAULT_SLOTS = 54;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();
    public EmojiHomeMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public Component getMenuName() {
        try {
            return Component.text(Emojify.getConfigUtil().getConfig().getString("MainConfig.invName")).color(NamedTextColor.WHITE);
        }catch (Exception e){
            Emojify.getLoggerEmojify().severe("Error loading name of emoji menu: " + e.getMessage());
            e.printStackTrace();
            return Emojify.getLangConfigUtil().getConfig().getComponent("Error.MenuName", miniMessage);
        }
    }

    @Override
    public int getSlots() {
        try{
            return Emojify.getConfigUtil().getConfig().getInt("MainConfig.slots");
        }catch (Exception e){
            Emojify.getLoggerEmojify().severe("Error loading number of slots in the main menu: " + e.getMessage());
            e.printStackTrace();
            return DEFAULT_SLOTS;
        }
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        if (event.getSlot() == getSlots()-1){
            event.getWhoClicked().closeInventory();
            return;
        }
        if (!Objects.requireNonNull(event.getCurrentItem()).getType().equals(Material.ENCHANTED_BOOK)) return;
        try {
            Emojify.getPlayerMenuUtility((Player) event.getWhoClicked()).setEmojiSlot("slot"+event.getSlot());
            new EmojiSelectorMenu(Emojify.getPlayerMenuUtility((Player) event.getWhoClicked())).open();

        }catch (Exception e){
            Emojify.getLoggerEmojify().severe("Error loading name of main menu: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void setMenuItems() {
        try {
            for (int i = 0; i < getSlots()-1; i++) {
                getEmoji(i, Emojify.getConfigUtil().getConfig().getInt("inventories.slot" + i + ".id"),
                        Emojify.getConfigUtil().getConfig().getString("inventories.slot" + i + ".name"), false);
            }
            ItemStack exit = new ItemStack(Material.MAP);
            ItemMeta meta = exit.getItemMeta();
            meta.setCustomModelData(1010);
            meta.itemName(Emojify.getLangConfigUtil().getConfig().getComponent("Item.Exit", miniMessage));
            exit.setItemMeta(meta);
            this.inventory.setItem(getSlots()-1, exit);
        }catch (Exception e){
            Emojify.getLoggerEmojify().severe("Error loading items in main menu: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
