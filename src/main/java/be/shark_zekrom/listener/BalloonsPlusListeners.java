package be.shark_zekrom.listener;

import be.shark_zekrom.balloons.BalloonService;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerUnleashEntityEvent;

public class BalloonsPlusListeners implements org.bukkit.event.Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if ((event.getEntity() instanceof Parrot)
                && BalloonService.getInstance().isBalloon((Parrot) entity))
            event.setCancelled(true);
    }


    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {

        Player player = event.getPlayer();
        BalloonService balloonService = BalloonService.getInstance();
        if (!balloonService.hasBalloon(player))
            return;
        balloonService.removeBalloon(player);
    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        BalloonService.getInstance().removeBalloon(player);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        BalloonService.getInstance().removeBalloon(player);
    }

    @EventHandler
    public void onLeash(PlayerLeashEntityEvent event) {
        Player player = event.getPlayer();
        BalloonService balloonService = BalloonService.getInstance();
        if (balloonService.hasBalloon(player)) {
            balloonService.removeBalloon(player);
        }
    }

    @EventHandler
    public void onUnLeash(PlayerUnleashEntityEvent event) {
        BalloonService balloonService = BalloonService.getInstance();
        if ((event.getEntity() instanceof Parrot)
                && balloonService.isBalloon((Parrot) event.getEntity()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent event) {
        BalloonService balloonService = BalloonService.getInstance();
        Entity clickedEntity = event.getRightClicked();
        if ((clickedEntity instanceof ArmorStand)
                && balloonService.isArmorStand(event.getPlayer()))
            event.setCancelled(true);
    }

}

