package dev.soulfur.skyblock.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import dev.soulfur.skyblock.Skyblock;
import dev.soulfur.skyblock.ui.ProfileUI;

public class ProfileCommand implements CommandExecutor {
    private final Skyblock plugin;

    public ProfileCommand(Skyblock plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            ProfileUI.openProfileMenu(player, plugin);
            return true;
        }
        return false;
    }
}