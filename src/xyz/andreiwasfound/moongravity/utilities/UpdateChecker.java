/*
 * Copyright (c) BGHDDevelopment.
 * Please refer to the plugin page or GitHub page for our open-source license.
 * If you have any questions please email ceo@bghddevelopment or reach us on Discord
 */

package xyz.andreiwasfound.moongravity.utilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import xyz.andreiwasfound.moongravity.Main;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

public class UpdateChecker implements Listener {
    private Main plugin;
    private int resourceId;

    public UpdateChecker(Main plugin, int resourceId) {
        this.plugin = plugin;
        this.resourceId = resourceId;
    }

    public void getLatestVersion(Consumer<String> consumer) {
       Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
           try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId).openStream(); Scanner scanner = new Scanner(inputStream)) {
               if (scanner.hasNext()) {
                   consumer.accept(scanner.next());
               }
           } catch (IOException exception) {
               this.plugin.getLogger().info("Cannot look for updates: " + exception.getMessage());
           }
       });
   }

    public void registerUpdateChecker(UpdateChecker updateChecker) {
        new UpdateChecker(plugin, resourceId).getLatestVersion(version -> {
            if (!(plugin.getDescription().getVersion().equalsIgnoreCase(version))) {
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + plugin.getDescription().getName() + ChatColor.DARK_GRAY + "]" + ChatColor.RESET + " " + plugin.getDescription().getName() + " is outdated!");
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + plugin.getDescription().getName() + ChatColor.DARK_GRAY + "]" + ChatColor.RESET + " " + "Newest version: " + version);
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + plugin.getDescription().getName() + ChatColor.DARK_GRAY + "]" + ChatColor.RESET + " " + "Server version: " + plugin.getDescription().getVersion());
                plugin.getServer().getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + plugin.getDescription().getName() + ChatColor.DARK_GRAY + "]" + ChatColor.RESET + " " + "Please Update Here: " + plugin.getDescription().getWebsite());
            }
        });
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (player.hasPermission(plugin.getDescription().getName().toLowerCase() + ".updatemessage")) {
            new UpdateChecker(plugin, resourceId).getLatestVersion(version -> {
                if (!(plugin.getDescription().getVersion().equalsIgnoreCase(version))) {
                    player.sendMessage(plugin.getDescription().getName() + " is outdated!");
                    player.sendMessage("Newest version: " + version);
                    player.sendMessage("Server version: " + plugin.getDescription().getVersion());
                    player.sendMessage("Please Update Here: " + plugin.getDescription().getWebsite());
                }
            });
        }
    }
}

