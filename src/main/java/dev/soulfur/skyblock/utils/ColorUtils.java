package dev.soulfur.skyblock.utils;

import org.bukkit.ChatColor;

public class ColorUtils {

    /**
     * Translates color codes in a string (e.g., "&aHello" -> "§aHello").
     * This can be used for item names, lore, and other places.
     *
     * @param text The text to translate.
     * @return The translated text with color codes.
     */
    public static String translateColorCodes(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}