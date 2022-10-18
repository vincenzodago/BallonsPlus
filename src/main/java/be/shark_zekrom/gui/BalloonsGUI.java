package be.shark_zekrom.gui;

import be.shark_zekrom.balloons.BallonService;
import be.shark_zekrom.balloons.BalloonHandler;
import be.shark_zekrom.config.ConfigManager;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BalloonsGUI {

    public BalloonsGUI(Player player) {
        ConfigManager configManager = ConfigManager.getInstance();
        ConfigurationSection configurationSection = configManager.getConfig("Settings.yml")
                .getConfigurationSection("Messages");

        ChestGui gui = new ChestGui(6, configurationSection.getString("BalloonsMenuName"));

        PaginatedPane pages = new PaginatedPane(0, 0, 9, 5);

        if (configManager.getBoolean(configManager.getConfig("Settings.yml"),"ShowOnlyBalloonsWithPermission"))
            pages.populateWithItemStacks(BalloonHandler.getInstance().getAllowedSkulls(player));
        else
            pages.populateWithItemStacks(BalloonHandler.getInstance().getAllSkulls());


        pages.setOnClick(event -> {
            if (event.getCurrentItem() !=  null) {
                BallonService.getInstance().summonBalloon(player,event.getCurrentItem());
                event.getWhoClicked().closeInventory();
            }
        });

        gui.addPane(pages);

        OutlinePane background = new OutlinePane(0, 5, 9, 1);

        ItemStack black_glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        background.addItem(new GuiItem(black_glass));
        background.setRepeat(true);
        background.setPriority(Pane.Priority.LOWEST);
        background.setOnClick(event -> event.setCancelled(true));

        gui.addPane(background);

        StaticPane navigation = new StaticPane(0, 5, 9, 1);

        ItemStack red_wool = new ItemStack(Material.RED_WOOL);
        ItemMeta im_red_wool = red_wool.getItemMeta();
        im_red_wool.displayName(Component.text(configurationSection.getString("BalloonsMenuPrevious")));
        red_wool.setItemMeta(im_red_wool);


        navigation.addItem(new GuiItem(red_wool, event -> {
            if (pages.getPage() > 0) {
                pages.setPage(pages.getPage() - 1);
                gui.update();
            }
            event.setCancelled(true);
        }), 0, 0);

        ItemStack green_wool = new ItemStack(Material.GREEN_WOOL);
        ItemMeta im_green_wool = green_wool.getItemMeta();
        im_green_wool.displayName(Component.text(configurationSection.getString("BalloonsMenuNext")));
        green_wool.setItemMeta(im_green_wool);

        navigation.addItem(new GuiItem(green_wool, event -> {
            if (pages.getPage() < pages.getPages() - 1) {
                pages.setPage(pages.getPage() + 1);
                gui.update();
            }
            event.setCancelled(true);
        }), 8, 0);

        ItemStack barrier_icon = new ItemStack(Material.BARRIER);
        ItemMeta im_barrier_icon = barrier_icon.getItemMeta();
        im_barrier_icon.displayName(Component.text(configurationSection.getString("BalloonsMenuRemove")));
        barrier_icon.setItemMeta(im_barrier_icon);
        navigation.addItem(new GuiItem(barrier_icon, event -> {
            BallonService.getInstance().removeBalloon(player);
            event.getWhoClicked().closeInventory();
        }), 4, 0);

        gui.addPane(navigation);
        gui.show(player);

    }


}
