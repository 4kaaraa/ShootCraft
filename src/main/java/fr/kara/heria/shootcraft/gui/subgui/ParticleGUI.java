package fr.kara.heria.shootcraft.gui.subgui;

import fr.heriamc.api.HeriaAPI;
import fr.heriamc.api.user.HeriaPlayer;
import fr.heriamc.api.user.unlock.HeriaUnlockable;
import fr.heriamc.bukkit.HeriaBukkit;
import fr.heriamc.bukkit.menu.HeriaMenu;
import fr.heriamc.bukkit.utils.ItemBuilder;
import fr.kara.heria.shootcraft.Shootcraft;
import fr.kara.heria.shootcraft.config.MessageConfigEnum;
import fr.kara.heria.shootcraft.data.api.ShootcraftData;
import fr.kara.heria.shootcraft.enums.ParticlesEnums;
import fr.kara.heria.shootcraft.gui.SettingsGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class ParticleGUI extends HeriaMenu {

    private final int[] slots = new int[]{
            21,22,23
    };

    public ParticleGUI(Player player) {
        super(player, "Effet", 54, true);
    }

    HeriaPlayer heriaPlayer = HeriaAPI.get().getPlayerManager().get(getPlayer().getUniqueId());
    HeriaUnlockable heriaUnlockable = HeriaAPI.get().getUnlockableManager().get(getPlayer().getUniqueId());

    @Override
    public void contents(Inventory inventory) {
        setBorder(inventory, 3);

        int index = 0;
        for (ParticlesEnums value : ParticlesEnums.values()) {
            this.insertInteractItem(inventory, slots[index], new ItemBuilder(value.getItem()).setName(value.getName()).setLoreWithList("", "§8» §7Particule: " + value.getName(), "§8» §7Prix: " + value.getPrice(), "", "§e❱ Clique pour selectionner")
                    .onClick(inventoryClickEvent -> {
                        ShootcraftData shootcraftData = Shootcraft.getInstance().getShootcraftDataManager().get(getPlayer().getUniqueId());
                        getPlayer().closeInventory();
                        if (value == ParticlesEnums.DEFAULT) {
                            shootcraftData.setActualParticle(value.getParticle());
                            Shootcraft.getInstance().getShootcraftDataManager().save(shootcraftData);
                        } else {
                            if (heriaPlayer.getRank().getPower() > 2) {
                                shootcraftData.setActualParticle(value.getParticle());
                                getPlayer().sendMessage(MessageConfigEnum.PREFIX + " §fTu as équipé " + value.getName());
                                Shootcraft.getInstance().getShootcraftDataManager().save(shootcraftData);
                            }
                        }
                    }));

            index++;
        }

        inventory.setItem(49, new ItemBuilder(Material.ARROW, 1).setName("§8» §cRetour").build());
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        if (event.getSlot() == 49) {
            HeriaBukkit.get().getMenuManager().open(new SettingsGUI(getPlayer()));
        }
    }
}
