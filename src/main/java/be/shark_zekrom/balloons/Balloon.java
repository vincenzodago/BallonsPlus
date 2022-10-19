package be.shark_zekrom.balloons;

import be.shark_zekrom.balloons.utils.Skulls;
import org.bukkit.inventory.ItemStack;

public class Balloon {
    private final String name;
    private final boolean isDefault;
    private final String permission;
    private final ItemStack skull;
    public Balloon(String name,boolean isDefault,String key) {
        this.name = name;
        this.isDefault = isDefault;

        if (!isDefault)
            this.permission = "balloonsplus." + name + ".balloon";
        else
            this.permission = "balloonsplus.default.balloon";

        this.skull = Skulls.createSkull(key,name);
    }

    @SuppressWarnings("unused")
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
