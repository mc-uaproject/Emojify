package org.technicfox.emojiesFox;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.technicfox.emojiesFox.listeners.MenuListener;
import org.technicfox.emojiesFox.menusystem.PlayerMenuUtility;
import org.technicfox.emojiesFox.handlers.CommandHandler;
import org.technicfox.emojiesFox.util.ConfigUtil;

import java.util.HashMap;


public final class EmojiesFox extends JavaPlugin implements Listener {
    private static EmojiesFox plugin;
    private static final HashMap<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<>();
    @Override
    public void onEnable() {
        plugin = this;

        saveDefaultConfig();
        ConfigUtil config = new ConfigUtil(this, "config.yml");
        if (!config.getFile().exists()) {
            config.getConfig().setDefaults(getConfig().getDefaults());
            config.save();
        }


        Bukkit.getLogger().info("Starting Emojies by TECHNICFOX");
        this.getCommand("emojis").setExecutor(new CommandHandler());
        getServer().getPluginManager().registerEvents(new MenuListener(), this);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info("Ending Emojies by TECHNICFOX");
    }

    /**
     * Returns the PlayerMenuUtility associated with the given player. If no PlayerMenuUtility has been created yet,
     * a new one is created and stored in the map for future retrieval.
     *
     * @param p the player to retrieve a PlayerMenuUtility for
     * @return the PlayerMenuUtility associated with the given player
     */
    public static PlayerMenuUtility getPlayerMenuUtility(Player p) {
        PlayerMenuUtility playerMenuUtility;
        if (!(playerMenuUtilityMap.containsKey(p))) {
            playerMenuUtility = new PlayerMenuUtility(p, new ConfigUtil(getPlugin(), "config.yml"));
            playerMenuUtilityMap.put(p, playerMenuUtility);

            return playerMenuUtility;
        } else {
            return playerMenuUtilityMap.get(p);
        }
    }

    public static EmojiesFox getPlugin() {
        return plugin;
    }
}
