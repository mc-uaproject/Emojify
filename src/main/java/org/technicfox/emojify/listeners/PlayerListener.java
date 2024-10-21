package org.technicfox.emojify.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.technicfox.emojify.Emojify;

public class PlayerListener implements Listener {
    private final YamlConfiguration configuration = Emojify.getConfiguration();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        MiniMessage mm = MiniMessage.miniMessage();
        boolean enabled = configuration.getBoolean("resourcepack.enabled");
        String url = configuration.getString("resourcepack.url");
        String input = configuration.getString("resourcepack.sha1");
        Component prompt = configuration.getComponent("resourcepack.prompt", mm);
        boolean force = configuration.getBoolean("resourcepack.force");
        if (enabled && url != null && input != null && prompt != null) {
            byte[] bytes = new byte[input.length() / 2];
            for (int i = 0; i < input.length(); i += 2) {
                bytes[i / 2] = (byte) Integer.parseInt(input.substring(i, i + 2), 16);
            }
            event.getPlayer().setResourcePack(url, bytes, prompt, force);
        }else {
            Emojify.getEmojifyLogger().warning("Error loading resourcepack config");
        }

    }
}
