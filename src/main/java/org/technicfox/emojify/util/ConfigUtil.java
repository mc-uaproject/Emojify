package org.technicfox.emojify.util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class ConfigUtil {

    protected final boolean createIfNotExist, resource;
    protected final JavaPlugin plugin;

    protected FileConfiguration config;
    protected File file, path;
    protected String name;

    public ConfigUtil(JavaPlugin instance, File path, String name, boolean createIfNotExist, boolean resource) {
        this.plugin = instance;
        this.path = path;
        this.name = name + ".yml";
        this.createIfNotExist = createIfNotExist;
        this.resource = resource;
        create();
    }

    public ConfigUtil(JavaPlugin instance, String path, String name, boolean createIfNotExist, boolean resource) {
        this(instance, new File(path), name, createIfNotExist, resource);
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void save() {
        try {
            config.save(file);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public File reloadFile() {
        file = new File(path, name);
        return file;
    }

    public FileConfiguration reloadConfig() {
        config = YamlConfiguration.loadConfiguration(file);
        return config;
    }

    public void reload() {
        reloadFile();
        reloadConfig();
    }

    public void create() {
        if (file == null) {
            reloadFile();
        }
        if (!createIfNotExist || file.exists()) {
            return;
        }
        file.getParentFile().mkdirs();
        if (resource) {
            plugin.saveResource(name, false);
        } else {
            try {
                file.createNewFile();
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
        reloadConfig();
    }
}
