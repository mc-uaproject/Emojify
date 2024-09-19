package org.technicfox.emojisFox.util;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class ConfigUtil {
    private final File file;
    private final FileConfiguration config;

    public ConfigUtil(Plugin plugin, String path) {
        this(plugin.getDataFolder().getAbsolutePath()+"/"+path);
    }

    public ConfigUtil(String path) {
        this.file = new File(path);
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    public void save(){
        try {
            this.config.save(this.file);
        } catch (Exception e){
            Bukkit.getLogger().warning("Error saving config: " + e.getMessage());
        }
    }

    public File getFile() {
        return this.file;
    }

    public FileConfiguration getConfig(){
        return this.config;
    }
}
