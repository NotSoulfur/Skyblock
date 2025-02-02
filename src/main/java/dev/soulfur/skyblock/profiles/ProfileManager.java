package dev.soulfur.skyblock.profiles;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.bukkit.entity.Player;
import dev.soulfur.skyblock.Skyblock;

public class ProfileManager {
    private final Skyblock plugin;
    private final List<String> profileNames = Arrays.asList(
            "Apple", "Banana", "Carrot", "Date", "Eggplant", "Fig", "Grape", "Honeydew", "Iceberg", "Jackfruit"
    );

    public ProfileManager(Skyblock plugin) {
        this.plugin = plugin;
    }

    public boolean hasProfile(UUID playerUUID) {
        return plugin.getDatabase().hasProfile(playerUUID);
    }

    public void createProfile(UUID playerUUID) {
        String profileName = getRandomProfileName();
        String profileId = UUID.randomUUID().toString(); // Generate a unique profile ID
        plugin.getDatabase().createProfile(playerUUID, profileName, profileId);
    }

    public void createDefaultProfile(UUID playerUUID) {
        String profileName = getRandomProfileName();
        String profileId = UUID.randomUUID().toString(); // Generate a unique profile ID
        plugin.getDatabase().createProfile(playerUUID, profileName, profileId);
        setActiveProfile(playerUUID, profileName);
    }

    public String getActiveProfileName(UUID playerUUID) {
        return plugin.getDatabase().getActiveProfileName(playerUUID);
    }

    public String getActiveProfileId(UUID playerUUID) {
        return plugin.getDatabase().getActiveProfileId(playerUUID); // Call the Database method
    }

    public void setActiveProfile(UUID playerUUID, String profileName) {
        plugin.getDatabase().setActiveProfile(playerUUID, profileName);
    }

    public int getProfileCount(UUID playerUUID) {
        return plugin.getDatabase().getProfileCount(playerUUID);
    }

    public int getMaxProfileSlots(Player player) {
        if (player.hasPermission("profiles.slot.7")) return 7;
        if (player.hasPermission("profiles.slot.5")) return 5;
        if (player.hasPermission("profiles.slot.3")) return 3;
        return 2; // Default slots
    }

    private String getRandomProfileName() {
        Random random = new Random();
        return profileNames.get(random.nextInt(profileNames.size()));
    }
}