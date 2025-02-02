package dev.soulfur.skyblock.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import dev.soulfur.skyblock.Skyblock;
import dev.soulfur.skyblock.profiles.ProfileManager;
import dev.soulfur.skyblock.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class IslandCommand implements CommandExecutor {
    private final Skyblock plugin;

    public IslandCommand(Skyblock plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            ProfileManager profileManager = plugin.getProfileManager();
            String activeProfileId = profileManager.getActiveProfileId(player.getUniqueId());

            if (activeProfileId != null) {
                String worldName = "island_" + activeProfileId;
                World world = Bukkit.getWorld(worldName);

                if (world != null) {
                    player.teleport(world.getSpawnLocation());
                    player.sendMessage(ColorUtils.translateColorCodes("&aTeleported to your island!"));
                } else {
                    player.sendMessage(ColorUtils.translateColorCodes("&cYour island could not be found. Please contact an admin."));
                }
            } else {
                player.sendMessage(ColorUtils.translateColorCodes("&cYou do not have an active profile."));
            }
            return true;
        }
        return false;
    }
}