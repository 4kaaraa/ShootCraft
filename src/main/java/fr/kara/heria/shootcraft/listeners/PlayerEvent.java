package fr.kara.heria.shootcraft.listeners;

import fr.heriamc.bukkit.HeriaBukkit;
import fr.kara.heria.shootcraft.Shootcraft;
import fr.kara.heria.shootcraft.config.GameState;
import fr.kara.heria.shootcraft.config.SpawnLocation;
import fr.kara.heria.shootcraft.data.PlayerInfo;
import fr.kara.heria.shootcraft.data.api.ShootcraftData;
import fr.kara.heria.shootcraft.gui.SettingsGUI;
import fr.kara.heria.shootcraft.task.WaitingTask;
import fr.kara.heria.shootcraft.utils.Title;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

public class PlayerEvent implements Listener {

    private HashMap<UUID, Long> cooldowns = new HashMap<>();

    @EventHandler
    public void hungerMeterChange(FoodLevelChangeEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void WeatherChange(WeatherChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void dropItem(PlayerDropItemEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void CancelEvent(BlockBreakEvent e) {
        Player player = (Player) e.getPlayer();
        if (!player.isOp()) {
            if (GameState.isStep(GameState.WAITING)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void voidCancel(EntityDamageEvent e){
        Player victim = (Player) e.getEntity();
        final PlayerInfo data = PlayerInfo.getPlayerData(victim);

        if (data.isSpectateur() || data.isWaiting()){
            e.setCancelled(true);
        }

        if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            e.setCancelled(true);
        }

        if (e.getCause() == EntityDamageEvent.DamageCause.FIRE){
            e.setCancelled(true);
        }

        if (e.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK){
            e.setCancelled(true);
        }

        if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void DamageEvent(EntityDamageByEntityEvent e) {
        Player attacker = (Player) e.getDamager();
        Player victim = (Player) e.getEntity();
        final PlayerInfo data = PlayerInfo.getPlayerData(attacker);
        final PlayerInfo data1 = PlayerInfo.getPlayerData(victim);

        if (data.isSpectateur() || data.isWaiting()) {
            e.setCancelled(true);
        }

        if (GameState.isStep(GameState.INGAME)) {
            if (data.getTeam().name().equals(data1.getTeam().name())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        Player player = (Player) e.getPlayer();
        if (!player.isOp()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerUseStick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        final PlayerInfo data = PlayerInfo.getPlayerData(player);

        if (GameState.isStep(GameState.INGAME)) {
            ShootcraftData shootcraftData = Shootcraft.getInstance().getShootcraftDataManager().get(player.getUniqueId());

            if (player.getInventory().getItemInHand().getType() == Material.STICK) {
                if (GameState.isStep(GameState.INGAME)) {
                    if (cooldowns.containsKey(player.getUniqueId()) && cooldowns.get(player.getUniqueId()) > System.currentTimeMillis()) {
                        long timeLeft = (cooldowns.get(player.getUniqueId()) - System.currentTimeMillis()) / 1000;

                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                long remaining = (cooldowns.get(player.getUniqueId()) - System.currentTimeMillis()) / 1000;
                                if (remaining <= 0) {
                                    this.cancel();
                                } else {
                                    Title.sendActionBar(player, "§cCooldown: §f" + remaining + "s");
                                }
                            }
                        }.runTaskTimer(Shootcraft.getInstance(), 0L, 20L);

                        return;
                    }

                    Vector direction = player.getEyeLocation().getDirection();

                    Location start = player.getEyeLocation().add(direction.clone().multiply(1));

                    EnumParticle enumParticle = shootcraftData.getActualParticle();

                    double maxDistance = 20.0;
                    boolean playerHit = false;
                    double hitDistance = 0;

                    for (double i = 0; i < maxDistance; i += 0.5) {
                        Location point = start.clone().add(direction.clone().multiply(i));

                        Block block = point.getBlock();
                        if (block.getType() != Material.AIR) {
                            break;
                        }

                        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                            sendParticle(onlinePlayer, point, i, enumParticle);
                        }

                        for (Entity entity : player.getWorld().getNearbyEntities(point, 0.5, 0.5, 0.5)) {
                            if (entity.equals(player)) {
                                continue;
                            }

                            if (entity instanceof Player) {
                                Player target = (Player) entity;
                                PlayerInfo data1 = PlayerInfo.getPlayerData(target);

                                hitDistance = start.distance(target.getLocation());
                                for (Player p : Bukkit.getOnlinePlayers()) {
                                    if (hitDistance < 5) {
                                        p.sendMessage("§a" + player.getName() + "§7 a tué §c" + target.getName() + "§8(§b" + String.format("%.2f", hitDistance) + "§fm§8)");
                                    } else if (hitDistance < 8) {
                                        p.sendMessage("§a" + player.getName() + "§7 est survolté ! Il a réussi un tir remarquable à §b§l" + String.format("%.2f", hitDistance) + " mètres §7!");
                                    } else if (hitDistance < 20) {
                                        p.sendMessage("§a" + player.getName() + "§7 §7est en pleine forme ! Un tir magnifique à §b§l" + String.format("%.2f", hitDistance) + " mètres §7vient d'être réalisé !");
                                    } else {
                                        p.sendMessage("§a" + player.getName() + "§7 §7est imparable ! Il vient d'envoyer un tir puissant à §b§l" + String.format("%.2f", hitDistance) + " mètres §7! ");
                                    }
                                }
                                data.increaseKills(1);

                                cooldowns.remove(player.getUniqueId());

                                playerHit = true;
                                player.playSound(player.getLocation(), shootcraftData.getActualSong(), 1.0f, 1.0f);

                                target.teleport(SpawnLocation.getRandomSpawnLocation());
                                data1.increaseDeaths(1);

                                break;
                            }
                        }

                        if (playerHit) {
                            break;
                        }
                    }

                    if (!playerHit) {
                        cooldowns.put(player.getUniqueId(), System.currentTimeMillis() + (3 * 1000));
                    }
                }
            }
        }

        if (data.isWaiting()) {
            if (event.hasItem()) {
                event.setCancelled(true);
                if (event.getAction() != Action.LEFT_CLICK_AIR) {
                    if (event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aSetting §8▸ §7Clic-Droit")) {
                        HeriaBukkit.get().getMenuManager().open(new SettingsGUI(player));
                    }
                }
            }
        }
    }


    private void sendParticle(Player player, Location location, double step, EnumParticle particlePlayer) {
        EnumParticle particle = particlePlayer;

        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
                particle,       // Type de particule
                true,           // Visible de loin
                (float) location.getX(),
                (float) location.getY(),
                (float) location.getZ(),
                0, 0, 0,        // Offsets pour l'effet
                0,              // Vitesse des particules
                1               // Nombre de particules
        );

        // Envoyer le packet au joueur
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }
}
