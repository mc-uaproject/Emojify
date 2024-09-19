package org.technicfox.emojisFox.menusystem;

import org.bukkit.entity.Player;


public class PlayerMenuUtility {

    private Player owner;
    private String EmojiMenuName;

    public String getEmojiSlot() {
        return EmojiMenuName;
    }

    public void setEmojiSlot(String emojiMenuName) {
        EmojiMenuName = emojiMenuName;
    }

    public PlayerMenuUtility(Player p) {
        this.owner = p;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}