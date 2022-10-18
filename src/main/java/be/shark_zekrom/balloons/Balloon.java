package be.shark_zekrom.balloons;

import be.shark_zekrom.balloons.utils.Skulls;
import org.bukkit.inventory.ItemStack;

public class Balloon {

    private final String name;
    private final String permission;
    private final ItemStack skull;

    private final boolean isDefault;

    public Balloon(String name,boolean isDefault,String key) {
        this.name = name;
        this.isDefault = isDefault;
        if (!isDefault)
            this.permission = "balloonplus." + name + ".balloon";
        else
            this.permission = "balloonsplus.default.balloon";

        this.skull = Skulls.createSkull(key,name);
    }

    public String getName() {
        return this.name;
    }

    public String getPermission() {
        return this.permission;
    }

    public ItemStack getSkull() {
        return this.skull;
    }

    public boolean isDefault() {
        return this.isDefault;
    }

}
