package dev.soulfur.skyblock.listeners;

import dev.soulfur.skyblock.islands.SkyblockGenerator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import dev.soulfur.skyblock.Skyblock;
import dev.soulfur.skyblock.profiles.ProfileManager;
import dev.soulfur.skyblock.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import java.util.UUID;
import java.io.File;

public class PlayerJoinListener implements Listener {
    private final Skyblock plugin;

    public PlayerJoinListener(Skyblock plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        ProfileManager profileManager = plugin.getProfileManager();
        UUID playerUUID = event.getPlayer().getUniqueId();

        // Create a default profile if the player doesn't have one
        if (!profileManager.hasProfile(playerUUID)) {
            profileManager.createDefaultProfile(playerUUID);
        }

        // Send a message to the player about their active profile and profile ID
        String activeProfile = profileManager.getActiveProfileName(playerUUID);
        String activeProfileId = profileManager.getActiveProfileId(playerUUID);
        event.getPlayer().sendMessage(ColorUtils.translateColorCodes("&aYou are playing on profile: &e" + activeProfile));
        event.getPlayer().sendMessage(ColorUtils.translateColorCodes("&8Profile ID: &7" + activeProfileId));

        // Teleport the player to their island
        teleportToIsland(event.getPlayer(), activeProfileId);
    }

    private void teleportToIsland(Player player, String profileId) {
        String worldName = "island_" + profileId;
        World world = Bukkit.getWorld(worldName);

        // Load the world if it exists
        if (world == null) {
            File islandDir = new File(plugin.getIslandsDir(), worldName);
            if (islandDir.exists()) {
                WorldCreator creator = new WorldCreator(worldName);
                creator.generator(new SkyblockGenerator());
                world = creator.createWorld();
            } else {
                // Create a new island world if it doesn't exist
                world = Bukkit.createWorld(new WorldCreator(worldName).generator(new SkyblockGenerator()));
            }
        }

        if (world != null) {
            player.teleport(world.getSpawnLocation());
        } else {
            player.sendMessage(ColorUtils.translateColorCodes("&cFailed to load your island. Please contact an admin."));
        }
    }
}