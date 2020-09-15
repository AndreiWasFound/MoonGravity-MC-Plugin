package xyz.andreiwasfound.moongravity;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

    private Main plugin;
    public Commands(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (label.equalsIgnoreCase("moongravity")) {
            if (!sender.hasPermission("moongravity.use")) {
                player.sendMessage("You don't have permission to perform this command");
            }
            if (sender.hasPermission("moongravity.use")) {
                if (args[0].equalsIgnoreCase("on")) {
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        if (plugin.hasMoonGravityPlayers()) {
                            plugin.removeMoonGravityPlayers(players);
                        }
                        plugin.addMoonGravityPlayers(players);
                    }
                    player.sendMessage(ChatColor.GREEN + "Every player on the server now has moon gravity");
                }
                if (args[0].equalsIgnoreCase("off")) {
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        plugin.removeMoonGravityPlayers(players);
                    }
                    player.sendMessage(ChatColor.RED + "Every player on the server now has normal gravity");
                }
                if (args[0].equalsIgnoreCase("list")) {
                    player.sendMessage(plugin.getMoonGravityPlayers().toString());
                }
            }
        }
        return false;
    }
}
