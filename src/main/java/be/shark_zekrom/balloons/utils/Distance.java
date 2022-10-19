package be.shark_zekrom.balloons.utils;

import be.shark_zekrom.balloons.BalloonService;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;

public class Distance {
    public static void line(Entity entity, Entity players) {
        Player player = (Player) players;

            if (entity.getLocation().distance(player.getLocation()) > 3.8D) {
                Vector direction = player.getLocation().toVector().subtract(entity.getLocation().toVector())
                        .normalize();
                entity.setVelocity(entity.getVelocity().add(direction.multiply(0.2D)));
            }

            if (entity.getLocation().distance(player.getLocation()) < 3.0D) {
                entity.setVelocity(entity.getVelocity().add(new Vector(0.0D, 0.3D, 0.0D)));
            }

            entity.getLocation().setDirection(player.getLocation().getDirection());

            BalloonService balloonService = BalloonService.getInstance();

            ArmorStand armorStand = balloonService.getArmorStand(player);
            armorStand.teleport(entity.getLocation().add(0, -1.3, 0));
            armorStand.getLocation().setDirection(player.getLocation().getDirection());
    }
}
