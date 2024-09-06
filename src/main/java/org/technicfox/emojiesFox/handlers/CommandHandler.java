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
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;
import org.technicfox.emojiesFox.EmojiesFox;
import org.technicfox.emojiesFox.util.ConfigUtil;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.net.URI;
import java.util.List;
import java.util.UUID;



public class CommandHandler implements Listener, CommandExecutor {
    private final String invName = "Панель Емодзі";
    private final ConfigUtil config;

    public CommandHandler(EmojiesFox plugin, ConfigUtil configuration) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        config = configuration;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals(invName)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();
        try {
            List<String> data = (List<String>) config.getConfig().getList("slot"+slot);


            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Clipboard clipboard = toolkit.getSystemClipboard();
            StringSelection strSel = new StringSelection(data.getFirst());
            clipboard.setContents(strSel, null);

            player.sendMessage(ChatColor.GREEN + "Скопійовано у буфер обміну");

            player.closeInventory();
        }catch (Exception ignored){}


        event.setCancelled(true);

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Тільки гравці можуть користуватися цією командою");
            return true;
        }


        Player player_who_sent_cmd = (Player) sender;
        Inventory inv = Bukkit.createInventory(player_who_sent_cmd, 9 * 3, invName);
        for (int i = 0; i < 27; i++) {
            try {
                Bukkit.getLogger().info(String.valueOf(i));
                List<String> data = (List<String>) config.getConfig().getList("slot" + i);
                inv.setItem(i, getHead(data.get(0), data.get(1)));
            }catch (Exception ignored){

            }

        }


        player_who_sent_cmd.openInventory(inv);


        return true;

    }


    public ItemStack getHead(String name, String url) {
        UUID uuid = UUID.randomUUID();

        final ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
        skullMeta.setDisplayName(name);

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

