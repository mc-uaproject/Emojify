package org.technicfox.emojify.listeners;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.technicfox.emojify.Emojify;
import org.technicfox.emojify.menusystem.menu.EmojiHomeMenu;


public class CommandListener implements CommandExecutor {

    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(miniMessage.deserialize(Emojify.getLocalizationUtil().getTranslation("Command.OnlyForPlayers")));
            return false;
        }

        new EmojiHomeMenu(Emojify.getPlayerMenuUtility((Player) sender)).open();
        return true;
    }


}

