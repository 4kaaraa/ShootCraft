package fr.kara.heria.shootcraft.listeners;

import fr.heriamc.api.HeriaAPI;
import fr.heriamc.api.user.HeriaPlayer;
import fr.kara.heria.shootcraft.Shootcraft;
import fr.kara.heria.shootcraft.config.GameState;
import fr.kara.heria.shootcraft.config.MessageConfigEnum;
import fr.kara.heria.shootcraft.config.SpawnLocation;
import fr.kara.heria.shootcraft.config.Team;
import fr.kara.heria.shootcraft.data.PlayerInfo;
import fr.kara.heria.shootcraft.data.api.ShootcraftData;
import fr.kara.heria.shootcraft.task.WaitingTask;
import fr.kara.heria.shootcraft.utils.ItemBuilder;
import fr.kara.heria.shootcraft.utils.Nametag;
import fr.kara.heria.shootcraft.utils.Title;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    private final Shootcraft plugin;

    public PlayerJoin(Shootcraft plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        final PlayerInfo data = PlayerInfo.getPlayerData(player);
        e.setJoinMessage(null);

        ShootcraftData rushFFAData = Shootcraft.getInstance().getShootcraftDataManager().createOrLoad(player.getUniqueId());

        for (int i = 0; i < 100; i++) {
            player.sendMessage("");
        }

        player.sendMessage(MessageConfigEnum.PREFIX + " §f§lINFO");
        player.sendMessage(" §8■ §7Le jeu est encore en §bBêta§7.");
        player.sendMessage(" §8■ §7Merci de pas abusé du §eBaton §8(§7Spam-Clic§8)");
        player.sendMessage(" §8■ §7Les alliances sont strictement §cinterdites§7.");
        player.sendMessage("");
        player.sendMessage(" §8■ §7Développeur: §bkara");
        player.sendMessage(" §8■ §fBon jeu §6!");
        player.sendMessage(" ");

        try {
            Shootcraft.getInstance().getScoreboardManager().onLogin(player);
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        if (GameState.isStep(GameState.WAITING)){
            player.teleport(SpawnLocation.HUB.getLocation());
            HeriaPlayer heriaPlayer = HeriaAPI.get().getPlayerManager().get(player.getUniqueId());
            Nametag.setNameTag(player, heriaPlayer.getRank().getPrefix(), "", heriaPlayer.getRank().getTabPriority());

            data.setWaiting(true);

            player.setGameMode(GameMode.ADVENTURE);
            player.getInventory().clear();
            player.setMaxHealth(20);
            player.setHealth(20);
            player.setExp(0);
            player.setLevel(0);
            player.setWalkSpeed(0.2F);
            player.setHealth(player.getMaxHealth());
            player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));


            player.setAllowFlight(false);
            player.setFlying(false);

            player.getInventory().setBoots(null);
            player.getInventory().setChestplate(null);
            player.getInventory().setLeggings(null);
            player.getInventory().setHelmet(null);

            player.getInventory().setHeldItemSlot(1);
            player.getInventory().setItem(2, new ItemBuilder(Material.PAPER, 1).setName("§aSetting §8▸ §7Clic-Droit").build());
            player.getInventory().setItem(8, new ItemBuilder(Material.BED, 1).setName("§cQuitter §8▸ §7Clic-Droit").build());

            for (Player online : Bukkit.getOnlinePlayers()) {
                online.showPlayer(player);
                Title.sendActionBar(online, MessageConfigEnum.PREFIX + " §7" + player.getName() + " a rejoint la partie §8(§f" + Bukkit.getOnlinePlayers().size() + "§7/§f16§8)");
            }

            if (Bukkit.getOnlinePlayers().size() >= 2 && !this.plugin.hasWaitingTaskStarted()){
                new WaitingTask(this.plugin);
                this.plugin.getWaitingTask().forceStarting();
            }
        } else {
            player.setMaxHealth(20);
            player.setHealth(player.getMaxHealth());
            data.setTeam(Team.SPEC);
            Nametag.setNameTag(player, "§7Spectateur ", "", 0);
            data.setWaiting(true);
            player.teleport(SpawnLocation.SPEC.getLocation());
            player.setGameMode(GameMode.ADVENTURE);
            player.setAllowFlight(true);
            player.setFlying(true);
            for (Player p1 : Bukkit.getOnlinePlayers()) {
                if (p1 == player) continue;
                p1.hidePlayer(player);
            }
        }
    }
}
