package be.shark_zekrom.balloons.utils;

import be.shark_zekrom.balloons.BallonService;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;

public class Distance {


    public static void line(Entity entity, Entity players) {
        Player player = (Player) players;
        if (entity.getWorld().equals(player.getWorld())) {

            if (entity.getLocation().distance(player.getLocation()) > 3.8D) {
                Vector direction = player.getLocation().toVector().subtract(entity.getLocation().toVector())
                        .normalize();
                entity.setVelocity(entity.getVelocity().add(direction.multiply(0.2D)));
            }
            if (entity.getLocation().distance(player.getLocation()) < 3.0D) {
                entity.setVelocity(entity.getVelocity().add(new Vector(0.0D, 0.3D, 0.0D)));

            }

            entity.getLocation().setDirection(player.getLocation().getDirection());

            BallonService ballonService = BallonService.getInstance();
            ArmorStand as = ballonService.getArmorStand(player);
            as.teleport(entity.getLocation().add(0, -1.3, 0));
            as.getLocation().setDirection(player.getLocation().getDirection());
        } else {
            entity.teleport(player);
        }
        if (entity.getLocation().distance(player.getLocation()) > 10.0D) {
            entity.teleport(player);
        }

    }

}
