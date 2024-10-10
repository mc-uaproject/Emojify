package org.technicfox.emojify.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.technicfox.emojify.Emojify;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        try {
            if (Emojify.getConfigUtil().getConfig().getBoolean("resourcepack.enabled")) {
                String url = Emojify.getConfigUtil().getConfig().getString("resourcepack.url");
                String input = Emojify.getConfigUtil().getConfig().getString("resourcepack.sha1");
                byte[] bytes = new byte[input.length() / 2];
                for (int i = 0; i < input.length(); i += 2) {
                    bytes[i / 2] = (byte) Integer.parseInt(input.substring(i, i + 2), 16);
                }
                String prompt = ChatColor.translateAlternateColorCodes('&', Emojify.getConfigUtil().getConfig().getString("resourcepack.prompt"));
                Boolean force = Emojify.getConfigUtil().getConfig().getBoolean("resourcepack.force");
                event.getPlayer().setResourcePack(url, bytes, prompt, force);
            }
        } catch (Exception e) {
            Bukkit.getLogger().severe("Error loading resource pack: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
