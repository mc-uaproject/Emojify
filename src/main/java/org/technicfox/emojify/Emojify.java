package org.technicfox.emojify;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.technicfox.emojify.listeners.MenuListener;
import org.technicfox.emojify.listeners.PlayerListener;
import org.technicfox.emojify.menusystem.PlayerMenuUtility;
import org.technicfox.emojify.listeners.CommandListener;
import org.technicfox.emojify.util.LocalizationUtil;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Logger;


public final class Emojify extends JavaPlugin implements Listener {
    private static YamlConfiguration config;
    private static LocalizationUtil localizationConfig;
    private static final HashMap<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<>();
    private static Logger logger;

    @Override
    public void onEnable() {
        logger = getLogger();

        logger.info("Starting Emojify by UAP");

        saveResource("config.yml", false);
        saveResource("langs/en_us.yml", false);
        saveResource("langs/uk_ua.yml", false);


        File configFile = new File(getDataFolder(), "config.yml");
        if (configFile.exists()) {
            config = YamlConfiguration.loadConfiguration(configFile);
            logger.info("Loaded config" );
        } else {
            logger.severe("Config file not found for: " + configFile.getName());
        }

        localizationConfig = new LocalizationUtil(new File(getDataFolder(), "langs"));
        localizationConfig.setCurrentLang(config.getString("MainConfig.lang"));

        this.getCommand("emoji").setExecutor(new CommandListener());
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);


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
    public static YamlConfiguration getConfiguration(){
        return config;
    }
    public static LocalizationUtil getLocalizationUtil(){return localizationConfig;}
    public static Logger getEmojifyLogger(){return logger;}
}