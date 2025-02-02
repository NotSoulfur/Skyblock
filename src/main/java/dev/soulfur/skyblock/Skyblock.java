package dev.soulfur.skyblock;

import org.bukkit.plugin.java.JavaPlugin;
import dev.soulfur.skyblock.database.Database;
import dev.soulfur.skyblock.commands.ProfileCommand;
import dev.soulfur.skyblock.listeners.ProfileListener;
import dev.soulfur.skyblock.listeners.PlayerJoinListener;
import dev.soulfur.skyblock.profiles.ProfileManager;

public class Skyblock extends JavaPlugin {

    private Database database;
    private ProfileManager profileManager;

    @Override
    public void onEnable() {
        // Ensure the data folder exists
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        this.database = new Database(this);
        this.database.initialize();
        this.profileManager = new ProfileManager(this);

        // Register commands
        getCommand("profiles").setExecutor(new ProfileCommand(this));

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
}