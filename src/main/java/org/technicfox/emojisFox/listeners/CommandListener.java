package org.technicfox.emojisFox.listeners;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.technicfox.emojisFox.EmojisFox;
import org.technicfox.emojisFox.menusystem.menu.EmojiHomePage;




public class CommandListener implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Тільки гравці можуть користуватися цією командою");
            return false;
        }

        new EmojiHomePage(EmojisFox.getPlayerMenuUtility((Player) sender)).open();
        return true;
    }


}

