package fr.kara.heria.shootcraft.gui;

import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.bukkit.menu.HeriaMenu;
import fr.kara.heria.shootcraft.gui.subgui.ParticleGUI;
import fr.kara.heria.shootcraft.gui.subgui.SongGUI;
import fr.kara.heria.shootcraft.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class SettingsGUI extends HeriaMenu {
    public SettingsGUI(Player player) {
        super(player, "Settings", 54, true);
    }

    @Override
    public void contents(Inventory inventory) {
        setBorder(inventory, 14);

        inventory.setItem(21, new ItemBuilder(Material.JUKEBOX, 1).setName("§8» §dSon §8(§7kill§8)").build());
        inventory.setItem(23, new ItemBuilder(Material.BLAZE_POWDER, 1).setName("§8» §bParticule §8(§7kill§8)").build());

        inventory.setItem(49, new ItemBuilder(Material.ARROW, 1).setName("§8» §cFermé").build());
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        if (event.getSlot() == 49) {
            getPlayer().closeInventory();
        }

        if (event.getSlot() == 21) {
            HeriaBukkit.get().getMenuManager().open(new SongGUI(getPlayer()));
        }

        if (event.getSlot() == 23) {
            HeriaBukkit.get().getMenuManager().open(new ParticleGUI(getPlayer()));
        }
    }
}
