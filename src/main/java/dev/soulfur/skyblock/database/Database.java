package dev.soulfur.skyblock.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;
import org.bukkit.plugin.java.JavaPlugin;

public class Database {
    private final JavaPlugin plugin;
    private Connection connection;

    public Database(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void initialize() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + plugin.getDataFolder().getAbsolutePath() + "/profiles.db");
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("CREATE TABLE IF NOT EXISTS profiles (" +
                        "id INTEGER PRIMARY KEY, " +
                        "player_uuid TEXT, " +
                        "profile_id TEXT, " + // Unique profile ID
                        "profile_name TEXT, " +
                        "active INTEGER DEFAULT 0)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean hasProfile(UUID playerUUID) {
        String query = "SELECT * FROM profiles WHERE player_uuid = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, playerUUID.toString());
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void createProfile(UUID playerUUID, String profileName, String profileId) {
        String query = "INSERT INTO profiles (player_uuid, profile_id, profile_name) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, playerUUID.toString());
            stmt.setString(2, profileId);
            stmt.setString(3, profileName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getActiveProfileName(UUID playerUUID) {
        String query = "SELECT profile_name FROM profiles WHERE player_uuid = ? AND active = 1";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, playerUUID.toString());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("profile_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getActiveProfileId(UUID playerUUID) {
        String query = "SELECT profile_id FROM profiles WHERE player_uuid = ? AND active = 1";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, playerUUID.toString());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("profile_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setActiveProfile(UUID playerUUID, String profileName) {
        String query = "UPDATE profiles SET active = 0 WHERE player_uuid = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, playerUUID.toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        query = "UPDATE profiles SET active = 1 WHERE player_uuid = ? AND profile_name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, playerUUID.toString());
            stmt.setString(2, profileName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getProfileCount(UUID playerUUID) {
        String query = "SELECT COUNT(*) AS count FROM profiles WHERE player_uuid = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, playerUUID.toString());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}