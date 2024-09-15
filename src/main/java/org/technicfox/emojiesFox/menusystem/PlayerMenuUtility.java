package org.technicfox.emojiesFox.menusystem;

import org.bukkit.entity.Player;
import org.technicfox.emojiesFox.util.ConfigUtil;


public class PlayerMenuUtility {

    private Player owner;
    private ConfigUtil config;
    private String EmojiMenuName;

    public String getEmojiMenuName() {
        return EmojiMenuName;
    }

    public void setEmojiMenuName(String emojiMenuName) {
        EmojiMenuName = emojiMenuName;
    }

    public PlayerMenuUtility(Player p, ConfigUtil config) {
        this.config = config;
        this.owner = p;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public ConfigUtil getConfig() {
        return config;
    }

    public void setConfig(ConfigUtil config) {
        this.config = config;
    }
}