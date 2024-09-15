package org.technicfox.emojiesFox.handlers;



import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.technicfox.emojiesFox.EmojiesFox;
import org.technicfox.emojiesFox.menusystem.menu.EmojiHomePage;




public class CommandHandler implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Тільки гравці можуть користуватися цією командою");
            return false;
        }

        Player player = (Player) sender;
        new EmojiHomePage(EmojiesFox.getPlayerMenuUtility(player)).open();
        return true;
    }


}

