package dev.soulfur.skyblock;

import dev.soulfur.skyblock.database.Database;
import dev.soulfur.skyblock.profiles.ProfileManager;
import org.bukkit.plugin.java.JavaPlugin;
import dev.soulfur.skyblock.commands.ProfileCommand;
import dev.soulfur.skyblock.commands.IslandCommand;
import dev.soulfur.skyblock.commands.HubCommand;
import dev.soulfur.skyblock.listeners.ProfileListener;
import dev.soulfur.skyblock.listeners.PlayerJoinListener;

import java.io.File;

public class Skyblock extends JavaPlugin {

    private Database database;
    private ProfileManager profileManager;

    @Override
    public void onEnable() {
        // Ensure the data folder exists
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        // Ensure the Islands directory exists
        File islandsDir = new File(getDataFolder(), "Islands");
        if (!islandsDir.exists()) {
            islandsDir.mkdirs();
        }

        this.database = new Database(this);
        this.database.initialize();
        this.profileManager = new ProfileManager(this);

        // Register commands
        getCommand("profiles").setExecutor(new ProfileCommand(this));
        getCommand("is").setExecutor(new IslandCommand(this));
        getCommand("hub").setExecutor(new HubCommand(this));

        // Register listeners
        getServer().getPluginManager().registerEvents(new ProfileListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
    }

    @Override
    public void onDisable() {
        this.database.close();
    }

    public Database getDatabase() {
        return database;
    }

    public ProfileManager getProfileManager() {
        return profileManager;
    }

    public File getIslandsDir() {
        return new File(getDataFolder(), "Islands");
    }
}