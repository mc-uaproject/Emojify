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
        for (int i = 0; i < 27; i++){
            config.getConfig().set("slot"+i , Arrays.asList("Default emote", "http://textures.minecraft.net/texture/5b1abe4a4acfc878cbb88ff1eb6d23b6fec94bfd9327ab11a6d929ca677a0a19"));
        }
        config.save();


        Bukkit.getLogger().info("Starting Emojies by TECHNICFOX");
        this.getCommand("emojis").setExecutor(new CommandHandler(this, config));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info("Ending Emojies by TECHNICFOX");
    }
}
