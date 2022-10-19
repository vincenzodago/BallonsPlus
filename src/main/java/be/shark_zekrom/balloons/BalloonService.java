package be.shark_zekrom.balloons;

import be.shark_zekrom.Main;
import be.shark_zekrom.balloons.utils.Distance;
import be.shark_zekrom.messages.Message;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class BalloonService {

    private static BalloonService instance;
    public final HashMap<UUID, Parrot> playerParrot;
    public final HashMap<UUID, ArmorStand> playerArmorStand;

    private BalloonService() {
        if(instance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }

        this.playerParrot = new HashMap<>();
        this.playerArmorStand = new HashMap<>();

    }

    public static BalloonService getInstance() {
        if(instance == null)
            instance = new BalloonService();
        return instance;
    }

    public boolean isBalloon(Parrot parrot) {
        return this.playerParrot.containsValue(parrot);
    }

    public boolean hasBalloon(Player player) {
        return this.playerParrot.containsKey(player.getUniqueId());
    }

    public ArmorStand getArmorStand(Player player) {
        return this.playerArmorStand.get(player.getUniqueId());
    }

    public void summonBalloon(Player player, ItemStack item) {

        if (this.playerParrot.containsKey(player.getUniqueId())) {
            removeBalloon(player);
        }

        @SuppressWarnings("RegExpRedundantEscape")
        String itemName = PlainTextComponentSerializer.plainText()
                .serialize(item.displayName()).replaceAll("(^\\[|\\]$)","");

        Balloon balloon = BalloonHandler.getInstance().getBalloon(itemName);

        if (balloon == null)
            return;

        if (!player.hasPermission(balloon.getPermission())) {
            Message.BALLOON_NO_PERM.send(player);
            return;
        }

        Parrot parrot  = (Parrot) player.getWorld()
                .spawnEntity(player.getLocation().add(0,2,0), EntityType.PARROT);
        parrot.setInvisible(true);
        parrot.setSilent(true);
        parrot.addScoreboardTag("Balloons+");
        parrot.setLeashHolder(player);

        UUID playerUUID = player.getUniqueId();

        this.playerParrot.put(playerUUID, parrot);

        Location loc = player.getLocation().add(0,2,0);
        ArmorStand armorStand = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
        armorStand.addScoreboardTag("Balloons+");
        armorStand.setVisible(false);
        armorStand.setCustomNameVisible(false);
        armorStand.setGravity(false);
        armorStand.setCanPickupItems(false);
        armorStand.setArms(true);
        armorStand.setBasePlate(false);
        armorStand.getEquipment().setHelmet(item);
        armorStand.addEquipmentLock(EquipmentSlot.HEAD, ArmorStand.LockType.ADDING_OR_CHANGING);
        armorStand.addEquipmentLock(EquipmentSlot.CHEST, ArmorStand.LockType.ADDING_OR_CHANGING);
        armorStand.addEquipmentLock(EquipmentSlot.LEGS, ArmorStand.LockType.ADDING_OR_CHANGING);
        armorStand.addEquipmentLock(EquipmentSlot.FEET, ArmorStand.LockType.ADDING_OR_CHANGING);
        armorStand.addEquipmentLock(EquipmentSlot.HAND, ArmorStand.LockType.ADDING_OR_CHANGING);
        armorStand.addEquipmentLock(EquipmentSlot.OFF_HAND, ArmorStand.LockType.ADDING_OR_CHANGING);

        playerArmorStand.put(playerUUID, armorStand);

        Message.BALLOON_SUMMONED.send(player);

    }

    public void removeBalloon(Player player) {

        UUID playerUUID = player.getUniqueId();

        ArmorStand armorStand = this.playerArmorStand.get(playerUUID);

        if (Objects.isNull(armorStand)) return;

        FileConfiguration fc = Main.getPlugin(Main.class).getConfig();

        if (fc.getBoolean("ShowParticlesBalloonsOnRemove"))
            armorStand.getWorld().spawnParticle(Particle.CLOUD,
                    armorStand
                            .getLocation().add(0, 2, 0), 5, 0.1, 0.1, 0.1, 0.1);

        this.playerArmorStand.remove(playerUUID);
        armorStand.remove();

        Parrot parrot = this.playerParrot.get(playerUUID);

        if (Objects.isNull(parrot)) return;

        this.playerParrot.remove(playerUUID);
        parrot.remove();

        Message.BALLOON_REMOVED.send(player);

    }
    public void removeAllBalloon() {
        for (Parrot parrot : this.playerParrot.values())
            parrot.remove();

        FileConfiguration fc = Main.getPlugin(Main.class).getConfig();
        boolean showParticles = fc.getBoolean("ShowParticlesBalloonsOnRemove");

        for (ArmorStand armorStand : this.playerArmorStand.values()) {
            if (showParticles)
                armorStand.getWorld()
                        .spawnParticle(Particle.CLOUD,
                                armorStand
                                        .getLocation()
                                        .add(0, 2, 0), 5, 0.1, 0.1, 0.1, 0.1);
            armorStand.remove();
        }

    }
    public boolean isArmorStand(Player player) {
        return this.playerArmorStand.containsKey(player.getUniqueId());
    }

    public void startRunnable() {
        new BukkitRunnable() {
            public void run() {

                for (UUID playerUUID : playerParrot.keySet()) {

                    Player player = Bukkit.getPlayer(playerUUID);

                    if (Objects.isNull(player)) continue;

                    Parrot parrot = playerParrot.get(playerUUID);

                    if (parrot.getWorld().equals(player.getWorld())
                            && parrot.getLocation().distance(player.getLocation()) < 6D) {
                        if ((parrot).isLeashed())
                            Distance.line(parrot, (parrot).getLeashHolder());
                    } else {
                        removeBalloon(player);
                    }
                }
            }
        }.runTaskTimer(Main.getPlugin(Main.class), 0L, 2L).getTaskId();
    }

}
