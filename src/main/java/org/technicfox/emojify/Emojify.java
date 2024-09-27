package org.technicfox.emojify;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.technicfox.emojify.listeners.MenuListener;
import org.technicfox.emojify.menusystem.PlayerMenuUtility;
import org.technicfox.emojify.listeners.CommandListener;
import org.technicfox.emojify.util.ConfigUtil;

import java.util.HashMap;


public final class Emojify extends JavaPlugin implements Listener {
    private static ConfigUtil config;
    private static final HashMap<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = new ConfigUtil(this, "config.yml");
        if (!config.getFile().exists()) {
            config.getConfig().setDefaults(getConfig().getDefaults());
            config.save();
        }


        Bukkit.getLogger().info("Starting Emojis by TECHNICFOX");
        this.getCommand("emojis").setExecutor(new CommandListener());
        getServer().getPluginManager().registerEvents(new MenuListener(), this);


    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Ending Emojis by TECHNICFOX");
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
            playerMenuUtility = new PlayerMenuUtility(p);
            playerMenuUtilityMap.put(p, playerMenuUtility);

            return playerMenuUtility;
        } else {
            return playerMenuUtilityMap.get(p);
        }
    }
    public static ConfigUtil getConfigUtil(){
        return config;
    }
}