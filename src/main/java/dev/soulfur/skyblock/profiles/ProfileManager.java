package dev.soulfur.skyblock.profiles;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import dev.soulfur.skyblock.islands.SkyblockGenerator;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
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
        return plugin.getDatabase().getActiveProfileId(playerUUID);
    }

    public void setActiveProfile(UUID playerUUID, String profileName) {
        // Unload the previous active profile's island
        String previousProfileId = getActiveProfileId(playerUUID);
        if (previousProfileId != null) {
            unloadIsland(previousProfileId);
        }

        // Set the new active profile
        plugin.getDatabase().setActiveProfile(playerUUID, profileName);

        // Load the new active profile's island
        String newProfileId = getActiveProfileId(playerUUID);
        if (newProfileId != null) {
            loadIsland(newProfileId);
        }
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

    private void unloadIsland(String profileId) {
        String worldName = "island_" + profileId;
        World world = Bukkit.getWorld(worldName);
        if (world != null) {
            Bukkit.unloadWorld(world, true); // Unload the world and save changes
        }
    }

    private void loadIsland(String profileId) {
        String worldName = "island_" + profileId;
        File islandDir = new File(plugin.getIslandsDir(), worldName);
        if (islandDir.exists()) {
            WorldCreator creator = new WorldCreator(worldName);
            creator.generator(new SkyblockGenerator());
            Bukkit.createWorld(creator);
        }
    }
}