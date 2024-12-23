package fr.kara.heria.shootcraft.task;

import fr.kara.heria.shootcraft.Shootcraft;
import fr.kara.heria.shootcraft.config.*;
import fr.kara.heria.shootcraft.data.PlayerInfo;
import fr.kara.heria.shootcraft.utils.Title;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


public class WaitingTask extends BukkitRunnable {
    private final Shootcraft plugin;
    private boolean started = false;
    private boolean forced = false;
    private int timeUntilStart;

    public WaitingTask(final Shootcraft plugin) {
        this.plugin = plugin;
        this.timeUntilStart = 60;
        plugin.setWaitingTask(this);
        start();
        this.runTaskTimer(plugin, 0L, 20L);
    }

    public void run() {
        if (this.timeUntilStart == 0) {
            this.cancel();
            if (Bukkit.getOnlinePlayers().size() >= 2) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    final PlayerInfo data = PlayerInfo.getPlayerData(player);

                    data.setTeam(Team.SURVIVOR);
                    final Team team = data.getTeam();

                    //Ajout de la game
                    data.increaseGames(1);

                    //Setup de l'inv
                    player.getInventory().clear();
                    player.setExp(0);
                    player.setGameMode(GameMode.ADVENTURE);
                    player.setAllowFlight(false);
                    player.setFlying(false);
                    player.setHealth(player.getMaxHealth());
                    data.setWaiting(false);
                }
                GameState.setCurrentStep(GameState.INGAME);

                for (Player player : Bukkit.getOnlinePlayers()) {
                    final PlayerInfo data = PlayerInfo.getPlayerData(player);
                    final Team team = data.getTeam();

                    //Inventaire
                    player.teleport(SpawnLocation.getRandomSpawnLocation());
                    player.setWalkSpeed(0.3F);
                    player.setMaxHealth(2);
                    player.setHealth(player.getMaxHealth());
                    player.getActivePotionEffects().clear();
                    player.getInventory().clear();
                    player.setMaxHealth(20);
                    player.setHealth(player.getMaxHealth());
                    player.setGameMode(GameMode.SURVIVAL);
                    player.getInventory().setItem(0, ItemStorage.stick);
                }
                new GameTask(this.plugin);
                new EndGameTask(this.plugin);
            } else {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    Title.sendActionBar(player, MessageConfigEnum.PREFIX + " §cPas assez de joueurs");
                }
                this.timeUntilStart = 60;
                started = false;
            }
            return;
        }
        int remainingMins = this.timeUntilStart / 60 % 60;
        int remainingSecs = this.timeUntilStart % 60;
        float progress = (float) this.timeUntilStart / (float) 60;
        for (Player online : Bukkit.getOnlinePlayers()) {
            online.setExp(progress);
            online.setLevel(remainingSecs);
        }
        if (this.timeUntilStart % 30 == 0 || (remainingMins == 0 && (remainingSecs % 10 == 0 || remainingSecs < 10))) {
            for (Player online : Bukkit.getOnlinePlayers()) {
                online.sendMessage(MessageConfigEnum.PREFIX + " §fLa partie commence dans §6" + remainingSecs + " secondes");
                Title.sendFullTitle(online, 20, 100, 20, "§6" + remainingSecs + "s", "§fDémarrage de la partie:");
                online.playSound(online.getLocation(), "note.pling", 10, 1);
            }
        }
        --this.timeUntilStart;
    }

    public void shortenCountdown() {
        forceStarting();
        if (this.timeUntilStart > 10)
            this.timeUntilStart = 10;
    }

    public int getRemainingSeconds() {
        return Math.max(0, this.timeUntilStart);
    }

    public void start() {
        started = true;
    }

    public void forceStarting() {
        forced = true;
    }

    public boolean hasStarted() {
        return started;
    }

    public boolean wasForced() {
        return forced;
    }
}
