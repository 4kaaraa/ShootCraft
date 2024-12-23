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
import fr.kara.heria.shootcraft.enums.SongEnum;
import fr.kara.heria.shootcraft.gui.SettingsGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class SongGUI extends HeriaMenu {
    private final int[] slots = new int[]{
            4,21,22,23,30,31,32
    };

    public SongGUI(Player player) {
        super(player, "Son", 54, true);
    }

    HeriaPlayer heriaPlayer = HeriaAPI.get().getPlayerManager().get(getPlayer().getUniqueId());
    HeriaUnlockable heriaUnlockable = HeriaAPI.get().getUnlockableManager().get(getPlayer().getUniqueId());

    @Override
    public void contents(Inventory inventory) {
        setBorder(inventory, 2);

        int index = 0;
        for (SongEnum value : SongEnum.values()) {
            this.insertInteractItem(inventory, slots[index], new ItemBuilder(value.getItem()).setName(value.getName()).setLoreWithList("", "§8» §7Song " + value.getName(), "§8» §7Prix: " + value.getPrice(), "", "§e❱ Clique pour selectionner")
                    .onClick(inventoryClickEvent -> {
                        ShootcraftData shootcraftData = Shootcraft.getInstance().getShootcraftDataManager().get(getPlayer().getUniqueId());
                        getPlayer().closeInventory();
                        if (value == SongEnum.PLING) {
                            shootcraftData.setActualSong(value.getSong());
                            getPlayer().playSound(getPlayer().getLocation(), value.getSong(), 10, 1);
                            Shootcraft.getInstance().getShootcraftDataManager().save(shootcraftData);
                        } else if (value == SongEnum.BURP) {
                            if (heriaPlayer.getRank().getPower() > 1) {
                                shootcraftData.setActualSong(value.getSong());
                                getPlayer().sendMessage(MessageConfigEnum.PREFIX + " §fTu as équipé " + value.getName());
                                getPlayer().playSound(getPlayer().getLocation(), value.getSong(), 10, 1);
                                Shootcraft.getInstance().getShootcraftDataManager().save(shootcraftData);
                            }
                        } else if (value == SongEnum.EXP) {
                            if (heriaPlayer.getRank().getPower() > 1) {
                                shootcraftData.setActualSong(value.getSong());
                                getPlayer().sendMessage(MessageConfigEnum.PREFIX + " §fTu as équipé " + value.getName());
                                getPlayer().playSound(getPlayer().getLocation(), value.getSong(), 10, 1);
                                Shootcraft.getInstance().getShootcraftDataManager().save(shootcraftData);
                            }
                        } else if (value == SongEnum.WATER) {
                            if (heriaPlayer.getRank().getPower() > 1) {
                                shootcraftData.setActualSong(value.getSong());
                                getPlayer().sendMessage(MessageConfigEnum.PREFIX + " §fTu as équipé " + value.getName());
                                getPlayer().playSound(getPlayer().getLocation(), value.getSong(), 10, 1);
                                Shootcraft.getInstance().getShootcraftDataManager().save(shootcraftData);
                            }
                        } else if (value == SongEnum.ANVIL) {
                            if (heriaPlayer.getRank().getPower() > 2) {
                                shootcraftData.setActualSong(value.getSong());
                                getPlayer().sendMessage(MessageConfigEnum.PREFIX + " §fTu as équipé " + value.getName());
                                getPlayer().playSound(getPlayer().getLocation(), value.getSong(), 10, 1);
                                Shootcraft.getInstance().getShootcraftDataManager().save(shootcraftData);
                            }
                        } else if (value == SongEnum.SKELETON) {
                            if (heriaPlayer.getRank().getPower() > 2) {
                                shootcraftData.setActualSong(value.getSong());
                                getPlayer().sendMessage(MessageConfigEnum.PREFIX + " §fTu as équipé " + value.getName());
                                getPlayer().playSound(getPlayer().getLocation(), value.getSong(), 10, 1);
                                Shootcraft.getInstance().getShootcraftDataManager().save(shootcraftData);
                            }
                        } else if (value == SongEnum.BLAZE) {
                            if (heriaPlayer.getRank().getPower() > 2) {
                                shootcraftData.setActualSong(value.getSong());
                                getPlayer().sendMessage(MessageConfigEnum.PREFIX + " §fTu as équipé " + value.getName());
                                getPlayer().playSound(getPlayer().getLocation(), value.getSong(), 10, 1);
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
