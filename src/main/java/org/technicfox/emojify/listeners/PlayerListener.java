package org.technicfox.emojify.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.technicfox.emojify.Emojify;

import java.util.HexFormat;
import java.util.UUID;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        try {
            if (Emojify.getConfigUtil().getConfig().getBoolean("resourcepack.enabled")) {
                String url = Emojify.getConfigUtil().getConfig().getString("resourcepack.url");
                byte[] hash = HexFormat.of().parseHex(Emojify.getConfigUtil().getConfig().getString("resourcepack.sha1"));
                String prompt = ChatColor.translateAlternateColorCodes('&', Emojify.getConfigUtil().getConfig().getString("resourcepack.prompt"));
                Boolean force = Emojify.getConfigUtil().getConfig().getBoolean("resourcepack.force");
                event.getPlayer().addResourcePack(UUID.randomUUID(), url, hash, prompt, force);
            }
        } catch (Exception e) {
            Bukkit.getLogger().severe("Error loading resource pack: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
