package org.technicfox.emojify.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.technicfox.emojify.Emojify;

import java.util.UUID;

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
                var mm = MiniMessage.miniMessage();
                Component prompt = mm.deserialize(Emojify.getConfigUtil().getConfig().getString("resourcepack.prompt"));
                boolean force = Emojify.getConfigUtil().getConfig().getBoolean("resourcepack.force");
                event.getPlayer().setResourcePack(url, bytes, prompt, force);
            }
        } catch (Exception e) {
            Emojify.getLoggerEmojify().severe("Error loading resource pack: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
