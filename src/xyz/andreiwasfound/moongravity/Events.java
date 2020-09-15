package xyz.andreiwasfound.moongravity;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Events implements Listener {

    private Main plugin;
    public Events(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (plugin.hasMoonGravityPlayers()) {
            plugin.addMoonGravityPlayers(e.getPlayer());
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        if (plugin.getMoonGravityPlayers().contains(e.getPlayer())) {
            plugin.removeMoonGravityPlayers(e.getPlayer());
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player player = e.getPlayer();
        if (plugin.getMoonGravityPlayers().contains(player)) {
            PotionEffect slowFallingEffect = new PotionEffect(PotionEffectType.SLOW_FALLING, Integer.MAX_VALUE, 0, true, false, false);
            PotionEffect jumpBoostEffect = new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 2, true, false, false);
            PotionEffect slownessEffect = new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 2, true, false, false);
            player.addPotionEffect(slowFallingEffect);
            player.addPotionEffect(jumpBoostEffect);
            player.addPotionEffect(slownessEffect);
        }
    }
}
