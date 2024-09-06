package org.technicfox.emojiesFox;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.technicfox.emojiesFox.handlers.CommandHandler;
import org.technicfox.emojiesFox.util.ConfigUtil;

import java.util.Arrays;


public final class EmojiesFox extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();


        ConfigUtil config = new ConfigUtil(this, "config.yml");
        if (!config.getFile().exists()) {
            config.getConfig().setDefaults(getConfig().getDefaults());
            config.save();
        }



        Bukkit.getLogger().info("Starting Emojies by TECHNICFOX");
        this.getCommand("emojis").setExecutor(new CommandHandler(this, config));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info("Ending Emojies by TECHNICFOX");
    }
}
