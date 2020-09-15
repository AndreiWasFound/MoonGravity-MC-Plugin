package xyz.andreiwasfound.moongravity;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.andreiwasfound.moongravity.utilities.MetricsLite;
import xyz.andreiwasfound.moongravity.utilities.UpdateChecker;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {

    private List<Player> moonGravityPlayers = new ArrayList<Player>();

    @Override
    public void onEnable() {
        printToConsole("Events are trying to register");
        getServer().getPluginManager().registerEvents(new Events(this), this);
        getServer().getPluginManager().registerEvents(new UpdateChecker(this, 83895), this);
        printToConsole("Events have been registered successfully");
        printToConsole("Commands are trying to register");
        getCommand("moongravity").setExecutor(new Commands(this));
        getCommand("moongravity").setTabCompleter(new CommandTabCompleter());
        printToConsole("Commands have been registered successfully");
        printToConsole("bStats is trying to register");
        int pluginId = 8858;
        MetricsLite metrics = new MetricsLite(this, pluginId);
        printToConsole("bStats has been registered successfully");
        printToConsole("UpdateChecker is trying to register");
        UpdateChecker updateChecker = new UpdateChecker(this, 83895);
        updateChecker.registerUpdateChecker(updateChecker);
        printToConsole("UpdateChecker has been registered successfully");
    }

    public void addMoonGravityPlayers(Player player) {
        PotionEffect slowFallingEffect = new PotionEffect(PotionEffectType.SLOW_FALLING, Integer.MAX_VALUE, 0, true, false, false);
        PotionEffect jumpBoostEffect = new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 2, true, false, false);
        PotionEffect slownessEffect = new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 2, true, false, false);
        player.addPotionEffect(slowFallingEffect);
        player.addPotionEffect(jumpBoostEffect);
        player.addPotionEffect(slownessEffect);
        moonGravityPlayers.add(player);
    }

    public void removeMoonGravityPlayers(Player player) {
        player.removePotionEffect(PotionEffectType.SLOW_FALLING);
        player.removePotionEffect(PotionEffectType.JUMP);
        player.removePotionEffect(PotionEffectType.SLOW);
        moonGravityPlayers.remove(player);
    }

    public List<Player> getMoonGravityPlayers() {
        return moonGravityPlayers;
    }

    public boolean hasMoonGravityPlayers() {
        if (moonGravityPlayers.isEmpty())
            return false;
        return true;
    }

    public void printToConsole(String msg) {
        this.getServer().getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + this.getDescription().getName() + ChatColor.DARK_GRAY + "]" + ChatColor.RESET + " " + msg);
    }
}
