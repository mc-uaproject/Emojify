package org.technicfox.emojiesFox.handlers;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;
import org.technicfox.emojiesFox.EmojiesFox;
import org.technicfox.emojiesFox.util.ConfigUtil;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



public class CommandHandler implements Listener, CommandExecutor {
    private final String invName = "Панель Емодзі";
    private List<String> avaibleInv = new ArrayList<String>();
    private final ConfigUtil config;

    /**
     * Creates a new instance of the command handler.
     *
     * @param plugin    the plugin instance
     * @param config    the configuration utility
     */
    public CommandHandler(EmojiesFox plugin, ConfigUtil config) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.config = config;
        for (int i = 0; i < 27; i++) {
            try {
                List<String> data = (List<String>) config.getConfig().getList("inventories.slot"+i);
                Bukkit.getLogger().info(data.get(0));
                avaibleInv.add(data.get(0));
                avaibleInv.add(invName);

            }catch (Exception ignored){}
        }
    }

    /**
     * Opens an inventory for the given player with the given inventory name.
     * The inventory contains all the emotes from the configuration.
     *
     * @param player the player to open the inventory for
     * @param invName the name of the inventory
     */
    public void openInventory(Player player, String invName) {
        Inventory inv = Bukkit.createInventory(player, 9 * 4, invName);
        for (int i = 0; i < 27; i++) {
            try {
                List<String> data = (List<String>) config.getConfig().getList( invName + ".slot" + i);
                inv.setItem(i, getHead(data.get(0), data.get(1)));
            }catch (Exception ignored){}
        }
        ItemStack glass = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
        ItemMeta meta2 = glass.getItemMeta();
        meta2.setItemName(" ");
        glass.setItemMeta(meta2);
        for (int i = 27; i < 35; i++) {
            inv.setItem(i, glass);
        }
        ItemStack barrier = new ItemStack(Material.BARRIER);
        ItemMeta meta = barrier.getItemMeta();
        meta.setItemName("§cВийти");
        barrier.setItemMeta(meta);
        inv.setItem(35, barrier);
        player.openInventory(inv);
    }
    /**
     * Called when the player clicks in the emote inventory.
     * Copies the emote from the clicked slot to the player's clipboard.
     *
     * @param event InventoryClickEvent
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!avaibleInv.contains(event.getView().getTitle())) {
            return;
        }
        if (event.getView().getTitle().equals(invName)) {
            Player player = (Player) event.getWhoClicked();
            int slot = event.getSlot();
            if (slot == 35){player.closeInventory();}
            try {
                List<String> data = (List<String>) config.getConfig().getList("inventories.slot"+slot);
                openInventory(player, data.get(0));
            }catch (Exception ignored){}
        }
        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();
        if (slot == 35){player.closeInventory();}

        try {
            List<String> data = (List<String>) config.getConfig().getList(event.getView().getTitle()+".slot"+slot);

            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Clipboard clipboard = toolkit.getSystemClipboard();
            StringSelection strSel = new StringSelection(data.getFirst());
            clipboard.setContents(strSel, null);

            player.sendMessage(ChatColor.GREEN + "Скопійовано у буфер обміну");

            player.closeInventory();
        }catch (Exception ignored){}


        event.setCancelled(true);

    }

    /**
     * Called when the player uses the /emojis command.
     * Opens an inventory with all the emotes.
     *
     * @param sender  the sender of the command
     * @param command the command used
     * @param label   the label of the command
     * @param args    the arguments of the command
     * @return        true if the command was successful
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Тільки гравці можуть користуватися цією командою");
            return false;
        }

        Player player_who_sent_cmd = (Player) sender;
        Inventory inv = Bukkit.createInventory(player_who_sent_cmd, 9 * 4, invName);
        for (int i = 0; i < 27; i++) {
            try {
                List<String> data = (List<String>) config.getConfig().getList("inventories.slot" + i);
                inv.setItem(i, getHead(data.get(0), data.get(1)));
            }catch (Exception ignored){}
        }

        ItemStack barrier = new ItemStack(Material.BARRIER);
        ItemMeta meta = barrier.getItemMeta();
        meta.setItemName("§cВийти");
        barrier.setItemMeta(meta);
        inv.setItem(35, barrier);
        ItemStack glass = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
        ItemMeta meta2 = glass.getItemMeta();
        meta2.setItemName(" ");
        glass.setItemMeta(meta2);

        for (int i = 27; i < 35; i++) {
            inv.setItem(i, glass);
        }
        player_who_sent_cmd.openInventory(inv);
        return true;
    }



        /**
         * Creates a player head item with a given name and texture URL.
         *
         * @param name the name to be displayed on the player head
         * @param url the URL of the player head texture
         * @return a new ItemStack containing the player head
         */
    public ItemStack getHead(String name, String url) {
        UUID uuid = UUID.randomUUID();

        final ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
        skullMeta.setItemName("§f"+name);

        PlayerProfile playerProfile = Bukkit.createPlayerProfile(uuid);
        PlayerTextures playerTextures = playerProfile.getTextures();
        try {
            playerTextures.setSkin(URI.create(url).toURL());
        }catch (Exception e){
            e.printStackTrace();
        }
        playerProfile.setTextures(playerTextures);
        skullMeta.setOwnerProfile(playerProfile);

        playerHead.setItemMeta(skullMeta);
        return playerHead;
    }
}

