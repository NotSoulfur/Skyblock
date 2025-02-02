package dev.soulfur.skyblock.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import dev.soulfur.skyblock.Skyblock;
import dev.soulfur.skyblock.profiles.ProfileManager;
import dev.soulfur.skyblock.ui.ProfileUI;
import dev.soulfur.skyblock.utils.ColorUtils;

import java.util.UUID;

public class ProfileListener implements Listener {
    private final Skyblock plugin;

    public ProfileListener(Skyblock plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(ColorUtils.translateColorCodes("Profile Management"))) {
            event.setCancelled(true);
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem == null) return;

            Player player = (Player) event.getWhoClicked();
            ProfileManager profileManager = plugin.getProfileManager();
            UUID playerUUID = player.getUniqueId();

            // Handle "Go Back" button
            if (clickedItem.getType() == Material.ARROW) {
                player.closeInventory();
                return;
            }

            // Handle empty profile slot (Oak Button)
            if (clickedItem.getType() == Material.OAK_BUTTON) {
                if (profileManager.getProfileCount(playerUUID) < profileManager.getMaxProfileSlots(player)) {
                    profileManager.createProfile(playerUUID);
                    player.sendMessage(ColorUtils.translateColorCodes("&aNew profile created!"));
                    ProfileUI.openProfileMenu(player, plugin); // Refresh the GUI
                } else {
                    player.sendMessage(ColorUtils.translateColorCodes("&cYou cannot create more profiles."));
                }
            }
        }
    }
}