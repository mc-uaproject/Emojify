package org.technicfox.emojify.menusystem.menu;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
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

public class EmojiSelectorMenu extends Menu {
    private static final int DEFAULT_SLOTS = 54;
    private final LocalizationUtil langConfigUtil = Emojify.getLocalizationUtil();
    private final YamlConfiguration configuration = Emojify.getConfiguration();
    private final Logger logger = Emojify.getEmojifyLogger();
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    public EmojiSelectorMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public Component getMenuName() {
        String name = configuration.getString("inventories."+ this.playerMenuUtility.getEmojiSlot()+".invName");
        if (name != null) {
            return Component.text(name).color(NamedTextColor.WHITE);
        } else{
            logger.severe("Error loading name of emoji menu: ");
            return Component.text(langConfigUtil.getTranslation("Error.MenuName")).color(NamedTextColor.RED);
        }
    }

    @Override
    public int getSlots() {
        int slots = configuration.getInt( "inventories."+this.playerMenuUtility.getEmojiSlot()+".slots");
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
            new EmojiHomeMenu(Emojify.getPlayerMenuUtility((Player) event.getWhoClicked())).open();
            return;
        }
        if (event.getCurrentItem() == null || !event.getCurrentItem().getType().equals(Material.ENCHANTED_BOOK)) return;

        String name =  PlainTextComponentSerializer.plainText().serialize(event.getCurrentItem().getItemMeta().itemName());

        Player player = (Player) event.getWhoClicked();
        Component message = miniMessage.deserialize(langConfigUtil.getTranslation("Item.CopyToClipboard"))
                .append(Component.text(" "+name).color(NamedTextColor.WHITE)
                        .decoration(TextDecoration.BOLD,TextDecoration.State.FALSE)
                        .decoration(TextDecoration.UNDERLINED,TextDecoration.State.FALSE));
        message = message.hoverEvent(HoverEvent.showText(message));
        message = message.clickEvent(ClickEvent.copyToClipboard(name));

        player.sendMessage(message);

        event.getWhoClicked().closeInventory();
    }

    @Override
    public void setMenuItems() {
        for (int i = 0; i < getSlots()-1; i++) {
            int id = configuration.getInt(this.playerMenuUtility.getEmojiSlot()+".slot"+i+".id");
            String name = configuration.getString(this.playerMenuUtility.getEmojiSlot()+".slot"+i+".name");
            if (name == null || id == 0) continue;
            getEmoji(i, id, name,true);
        }
        ItemStack exit = new ItemStack(Material.MAP);
        ItemMeta meta = exit.getItemMeta();
        meta.setCustomModelData(1010);
        meta.itemName(miniMessage.deserialize(langConfigUtil.getTranslation("Item.Exit")));
        exit.setItemMeta(meta);
        this.inventory.setItem(getSlots()-1, exit);

    }


}
