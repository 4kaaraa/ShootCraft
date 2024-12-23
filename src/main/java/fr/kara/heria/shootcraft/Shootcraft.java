package fr.kara.heria.shootcraft;

import fr.heriamc.bukkit.HeriaBukkit;
import fr.kara.heria.shootcraft.config.GameState;
import fr.kara.heria.shootcraft.config.MessageConfigEnum;
import fr.kara.heria.shootcraft.config.SpawnLocation;
import fr.kara.heria.shootcraft.config.Team;
import fr.kara.heria.shootcraft.data.PlayerInfo;
import fr.kara.heria.shootcraft.data.api.ShootcraftDataManager;
import fr.kara.heria.shootcraft.listeners.PlayerEvent;
import fr.kara.heria.shootcraft.listeners.PlayerJoin;
import fr.kara.heria.shootcraft.listeners.PlayerQuit;
import fr.kara.heria.shootcraft.scorboard.ScoreboardManager;
import fr.kara.heria.shootcraft.task.TerminatedGameTask;
import fr.kara.heria.shootcraft.task.WaitingTask;
import fr.kara.heria.shootcraft.utils.Title;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;

@Getter
public class Shootcraft extends JavaPlugin {

    @Getter
    private static Shootcraft instance;

    @Setter
    @Getter
    private WaitingTask waitingTask;

    private ShootcraftDataManager shootcraftDataManager;

    private ScoreboardManager scoreboardManager;
    private ScheduledExecutorService executorMonoThread;
    private ScheduledExecutorService scheduledExecutorService;

    @Override
    public void onEnable() {
        instance = this;

        GameState.setCurrentStep(GameState.WAITING);
        System.out.println("Shootcraft Loaded - made by kara");

        this.registerListeners();

        Bukkit.getWorld("world").setTime(6000);
        Bukkit.getWorld("world").setDifficulty(Difficulty.HARD);

        scheduledExecutorService = Executors.newScheduledThreadPool(16);
        executorMonoThread = Executors.newScheduledThreadPool(1);
        scoreboardManager = new ScoreboardManager();

        HeriaBukkit bukkit = HeriaBukkit.get();
        this.shootcraftDataManager = new ShootcraftDataManager(bukkit.getApi().getRedisConnection(), bukkit.getApi().getMongoConnection());
    }

    public void registerListeners() {
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();

        pluginManager.registerEvents(new PlayerJoin(this), this);
        pluginManager.registerEvents(new PlayerQuit(this), this);
        pluginManager.registerEvents(new PlayerEvent(), this);
    }

    @Override
    public void onDisable() {
        getScoreboardManager().onDisable();
    }

    public void stop() {
        GameState.setCurrentStep(GameState.RESTARTING);
    }

    public void removePlayer(final Player player) {
        final PlayerInfo data = PlayerInfo.getPlayerData(player);
        final Team team = data.getTeam();
        if (GameState.isStep(GameState.INGAME)) {
            if (Team.SURVIVOR.getOnlinePlayers().size() == 1) {
                Player winnerPlayer = Team.SURVIVOR.getOnlinePlayers().get(0);
                for (Player spec : Bukkit.getOnlinePlayers()) {
                    for (Player survivor : Team.SURVIVOR.getOnlinePlayers()) {
                        String PlayerWinner = survivor.getName();
                        Title.sendFullTitle(spec, 20, 100, 20, "§c§lDEFAITE", "§f" + survivor.getDisplayName() + " §7a gagné la partie !");
                        Title.sendFullTitle(survivor, 20, 100, 20, "§a§lWINNER", "§fvous avez §agagné");
                    }
                }
                final Player winnerSureTeam = stopGame(winnerPlayer);
            }
        }
    }

    public Player stopGame(Player winnerPlayer) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            online.setGameMode(GameMode.ADVENTURE);
            online.setAllowFlight(true);
            online.setFlying(true);
            online.teleport(SpawnLocation.HUB.getLocation());
        }
        new TerminatedGameTask(this);
        if (!Bukkit.getOnlinePlayers().isEmpty()) {
            for (Map.Entry<OfflinePlayer, PlayerInfo> entry : PlayerInfo.getEntries()) {
                final OfflinePlayer offPlayer = entry.getKey();
                final Player player = entry.getValue().getPlayer().getPlayer();
                final PlayerInfo data = entry.getValue();

                player.sendMessage("");
                player.sendMessage("§f§l➨ CLASSEMENT");
                player.sendMessage("");

                if (player == winnerPlayer) {
                    player.sendMessage(" §6§lMéDAIL D'OR ! §fFélicitations vous êtes un concurrent de taille");
                    data.increaseWins(1);
                    int randomNumber2 = ThreadLocalRandom.current().nextInt(30, 41);
                    Title.sendFullTitle(player, 20, 100, 20, "§6§l1er", "§fvous avez §agagné");
                    player.sendMessage(" §fVous avez gagné §a" + randomNumber2 + " §fpoints");

                } else if (Objects.equals(player.getName(), getTopKill(1))) {
                    player.sendMessage(" §6§lMéDAIL D'OR !  §Vous êtes l'un des meilleurs athlètes que l'on n'ait jamais connus");
                    Title.sendFullTitle(player, 20, 100, 20, "§7§l2eme", "§fvous etes un §bvainceur");

                    int randomNumber = ThreadLocalRandom.current().nextInt(20, 31);
                    player.sendMessage(" §fVous avez gagné §a" + randomNumber + " §fpoints");
                } else if (Objects.equals(player.getName(), getTopKill(2))) {
                    player.sendMessage(" §c§lMéDAIL DE BRONZE §f! Félicitations pour cette partie");
                    Title.sendFullTitle(player, 20, 100, 20, "§c§l3eme", "§fvous etes sur le §bpodium");

                    int randomNumber = ThreadLocalRandom.current().nextInt(10, 21);
                    player.sendMessage(" §fVous avez gagné §a" + randomNumber + " §fpoints");
                } else {
                    Title.sendFullTitle(player, 20, 100, 20, "§c§lDEFAITE", "§f" + winnerPlayer.getName() + " §7a gagné la partie !");
                    int randomNumber1 = ThreadLocalRandom.current().nextInt(1, 11);
                    entry.getValue().getPlayer().getPlayer().sendMessage(MessageConfigEnum.PREFIX + " §fVous avez gagné §a" + randomNumber1 + " §fpoints");
                }

                player.sendMessage("");
                player.sendMessage("§a① §f" + getTopKill(0));
                player.sendMessage("§e② §f" + getTopKill(1));
                player.sendMessage("§c③ §f" + getTopKill(2));
                player.sendMessage("");
                player.sendMessage("§c§lFIN DE LA PARTIE §f!");
            }
        }

        stop();
        return winnerPlayer;
    }

    public boolean hasWaitingTaskStarted() {
        return this.waitingTask != null;
    }

    public String getTopKill(int position) {
        List<PlayerInfo> playersData = new ArrayList<>();

        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerInfo data = PlayerInfo.getPlayerData(player);
            playersData.add(data);
        }

        Collections.sort(playersData, new Comparator<PlayerInfo>() {
            @Override
            public int compare(PlayerInfo p1, PlayerInfo p2) {
                return Integer.compare(p2.getKill(), p1.getKill());
            }
        });

        if (position < 0 || position >= playersData.size()) {
            return "personne";
        }

        PlayerInfo topPlayer = playersData.get(position);

        return topPlayer.getPlayer().getName() + " §8(§b" + topPlayer.getKill() + "§8)";
    }
}
