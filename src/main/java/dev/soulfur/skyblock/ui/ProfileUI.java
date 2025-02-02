package dev.soulfur.skyblock.ui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import dev.soulfur.skyblock.Skyblock;
import dev.soulfur.skyblock.profiles.ProfileManager;
import dev.soulfur.skyblock.utils.ColorUtils;
import java.util.UUID;

import java.util.Arrays;

public class ProfileUI {
    public static void openProfileMenu(Player player, Skyblock plugin) {
        Inventory inv = Bukkit.createInventory(null, 36, ColorUtils.translateColorCodes("Profile Management"));

        ProfileManager profileManager = plugin.getProfileManager();
        UUID playerUUID = player.getUniqueId();
        int maxSlots = profileManager.getMaxProfileSlots(player);

        // Fill the GUI with glass panes (empty names)
        ItemStack glassPane = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta glassMeta = glassPane.getItemMeta();
        glassMeta.setDisplayName(" "); // Empty name
        glassPane.setItemMeta(glassMeta);

        // Fill all slots with glass panes
        for (int i = 0; i < 36; i++) {
            inv.setItem(i, glassPane);
        }

        // Render profile slots in the second row (slots 10-16)
        for (int i = 0; i < 7; i++) {
            int slot = 10 + i; // Second row, slots 10-16
            if (i < maxSlots) {
                if (profileManager.getProfileCount(playerUUID) > i) {
                    // Render active profile (Emerald Block)
                    String activeProfile = profileManager.getActiveProfileName(playerUUID);
                    ItemStack activeProfileItem = new ItemStack(Material.EMERALD_BLOCK);
                    ItemMeta activeMeta = activeProfileItem.getItemMeta();
                    activeMeta.setDisplayName(ColorUtils.translateColorCodes("&aActive Profile: &e" + activeProfile));
                    activeMeta.setLore(Arrays.asList(
                            ColorUtils.translateColorCodes("&7This is your current profile."),
                            ColorUtils.translateColorCodes("&7Switch to another profile to manage this one.")
                    ));
                    activeProfileItem.setItemMeta(activeMeta);
                    inv.setItem(slot, activeProfileItem);
                } else {
                    // Render empty slot (Oak Button)
                    ItemStack emptySlot = new ItemStack(Material.OAK_BUTTON);
                    ItemMeta emptyMeta = emptySlot.getItemMeta();
                    emptyMeta.setDisplayName(ColorUtils.translateColorCodes("&aCreate New Profile"));
                    emptyMeta.setLore(Arrays.asList(
                            ColorUtils.translateColorCodes("&7Click to create a new Skyblock profile."),
                            ColorUtils.translateColorCodes("&eEach profile has its own island, inventory, and more!")
                    ));
                    emptySlot.setItemMeta(emptyMeta);
                    inv.setItem(slot, emptySlot);
                }
            } else {
                // Render locked slot (Bedrock)
                ItemStack lockedSlot = new ItemStack(Material.BEDROCK);
                ItemMeta lockedMeta = lockedSlot.getItemMeta();
                lockedMeta.setDisplayName(ColorUtils.translateColorCodes("&cLocked Slot"));
                lockedMeta.setLore(Arrays.asList(
                        ColorUtils.translateColorCodes("&7You do not have permission to create more profiles.")
                ));
                lockedSlot.setItemMeta(lockedMeta);
                inv.setItem(slot, lockedSlot);
            }
        }

        // Go Back button (Arrow) in the middle of the 4th row (slot 31)
        ItemStack goBackButton = new ItemStack(Material.ARROW);
        ItemMeta goBackMeta = goBackButton.getItemMeta();
        goBackMeta.setDisplayName(ColorUtils.translateColorCodes("&cGo Back"));
        goBackButton.setItemMeta(goBackMeta);
        inv.setItem(31, goBackButton);

        player.openInventory(inv);
    }
}