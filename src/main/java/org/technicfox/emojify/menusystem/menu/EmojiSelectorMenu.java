package org.technicfox.emojify.menusystem.menu;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.technicfox.emojify.Emojify;
import org.technicfox.emojify.menusystem.Menu;
import org.technicfox.emojify.menusystem.PlayerMenuUtility;

import java.util.Objects;

public class EmojiSelectorMenu extends Menu {
    private static final int DEFAULT_SLOTS = 54;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    public EmojiSelectorMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public Component getMenuName() {
        try {
            return Component.text(Emojify.getConfigUtil().getConfig().getString("inventories."+ this.playerMenuUtility.getEmojiSlot()+".invName")).color(NamedTextColor.WHITE);
        }catch (Exception e) {
            Emojify.getLoggerEmojify().severe("Error loading name of emoji menu: " + e.getMessage());
            e.printStackTrace();
            return Emojify.getLangConfigUtil().getConfig().getComponent("Error.MenuName", miniMessage);
        }
    }

    @Override
    public int getSlots() {
        try{
            return Emojify.getConfigUtil().getConfig().getInt( "inventories."+this.playerMenuUtility.getEmojiSlot()+".slots");
        }catch (Exception e) {
            Emojify.getLoggerEmojify().severe("Error loading number of slots in one of emoji menus: " + e.getMessage());
            e.printStackTrace();
            return DEFAULT_SLOTS;
        }
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        if (event.getSlot() == getSlots()-1){
            new EmojiHomeMenu(Emojify.getPlayerMenuUtility((Player) event.getWhoClicked())).open();
            return;
        }
        if (!Objects.requireNonNull(event.getCurrentItem()).getType().equals(Material.ENCHANTED_BOOK)) return;
        try {
            String name =  PlainTextComponentSerializer.plainText().serialize(event.getCurrentItem().getItemMeta().itemName());

            Player player = (Player) event.getWhoClicked();
            Component message = Emojify.getLangConfigUtil().getConfig().getComponent("Item.CopyToClipboard", miniMessage)
                    .append(Component.text(" "+name).color(NamedTextColor.WHITE)
                            .decoration(TextDecoration.BOLD,TextDecoration.State.FALSE)
                            .decoration(TextDecoration.UNDERLINED,TextDecoration.State.FALSE));
            message = message.hoverEvent(HoverEvent.showText(message));
            message = message.clickEvent(ClickEvent.copyToClipboard(name));

            player.sendMessage(message);

            event.getWhoClicked().closeInventory();
        }catch (Exception e){
            Emojify.getLoggerEmojify().severe("Error getting emoji in a menu: " + e.getMessage());
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
            meta.itemName(Emojify.getLangConfigUtil().getConfig().getComponent("Item.Exit", miniMessage));
            exit.setItemMeta(meta);
            this.inventory.setItem(getSlots()-1, exit);
        }catch (Exception e){
            Emojify.getLoggerEmojify().severe("Error loading items in emoji menu: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
