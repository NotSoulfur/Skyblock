package dev.soulfur.skyblock.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import dev.soulfur.skyblock.Skyblock;
import dev.soulfur.skyblock.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class HubCommand implements CommandExecutor {
    private final Skyblock plugin;

    public HubCommand(Skyblock plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            World hubWorld = Bukkit.getWorld("world"); // Replace "world" with your hub world name

            if (hubWorld != null) {
                player.teleport(hubWorld.getSpawnLocation());
                player.sendMessage(ColorUtils.translateColorCodes("&aTeleported to the hub!"));
            } else {
                player.sendMessage(ColorUtils.translateColorCodes("&cThe hub world could not be found. Please contact an admin."));
            }
            return true;
        }
        return false;
    }
}