package org.technicfox.emojify.menusystem.menu;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.technicfox.emojify.Emojify;
import org.technicfox.emojify.menusystem.Menu;
import org.technicfox.emojify.menusystem.PlayerMenuUtility;
import org.technicfox.emojify.util.LocalizationUtil;

import java.util.logging.Logger;

public class EmojiHomeMenu extends Menu {
    private static final int DEFAULT_SLOTS = 54;
    private final LocalizationUtil langConfigUtil = Emojify.getLocalizationUtil();
    private final YamlConfiguration configuration = Emojify.getConfiguration();
    private final Logger logger = Emojify.getEmojifyLogger();
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    public EmojiHomeMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public Component getMenuName() {
        String name = configuration.getString("MainConfig.invName");
        if (name != null) {
            return Component.text(name).color(NamedTextColor.WHITE);
        } else{
            logger.severe("Error loading name of emoji menu: ");
            return Component.text(langConfigUtil.getTranslation("Error.MenuName")).color(NamedTextColor.RED);
        }
    }

    @Override
    public int getSlots() {
        int slots = configuration.getInt( "MainConfig.slots");
        if (slots != 0 && slots % 9 == 0) {
            return slots;
        } else {
            logger.severe("Error loading number of slots in one of emoji menus");
            return DEFAULT_SLOTS;
        }
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        if (event.getSlot() == getSlots()-1){
            event.getWhoClicked().closeInventory();
            return;
        }
        if (event.getCurrentItem() == null || !event.getCurrentItem().getType().equals(Material.ENCHANTED_BOOK)) return;

        Emojify.getPlayerMenuUtility((Player) event.getWhoClicked()).setEmojiSlot("slot"+event.getSlot());
        new EmojiSelectorMenu(Emojify.getPlayerMenuUtility((Player) event.getWhoClicked())).open();
    }

    @Override
    public void setMenuItems() {
        for (int i = 0; i < getSlots()-1; i++) {
            int id = configuration.getInt("inventories.slot" + i + ".id");
            String name = configuration.getString("inventories.slot" + i + ".name");
            if (name == null || id == 0) continue;
            getEmoji(i, id, name,false);
        }
        ItemStack exit = new ItemStack(Material.MAP);
        ItemMeta meta = exit.getItemMeta();
        meta.setCustomModelData(1010);
        meta.itemName(miniMessage.deserialize(langConfigUtil.getTranslation("Item.Exit")));
        exit.setItemMeta(meta);
        this.inventory.setItem(getSlots()-1, exit);
    }

}
