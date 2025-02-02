package dev.soulfur.skyblock.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import dev.soulfur.skyblock.Skyblock;
import dev.soulfur.skyblock.profiles.ProfileManager;
import dev.soulfur.skyblock.utils.ColorUtils;

import java.util.UUID;

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
    }
}