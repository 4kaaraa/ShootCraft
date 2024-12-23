package fr.kara.heria.shootcraft.task;

import fr.kara.heria.shootcraft.Shootcraft;
import fr.kara.heria.shootcraft.config.Team;
import fr.kara.heria.shootcraft.data.PlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class EndGameTask extends BukkitRunnable {
    private static int endtime = 121;
    public final Shootcraft plugin;

    public EndGameTask(final Shootcraft plugin) {
        this.plugin = plugin;
        endtime = 121;
        this.runTaskTimer(plugin, 0, 20);
    }

    public static int getTempsTotal() {
        return endtime;
    }

    public static String getTempsFormate() {
        return formatTemps(endtime);
    }

    public static String formatTemps(int secondes) {
        int minutes = (secondes % 3600) / 60;
        int secondesRestantes = secondes % 60;

        return String.format("%02d:%02d", minutes, secondesRestantes);
    }

    public void run() {
        if (endtime > 1) {
            endtime--;
        } else {
            this.cancel();

            Player topPlayer = null;
            int maxKills = -1;

            for (Player player : Bukkit.getOnlinePlayers()) {
                PlayerInfo data = PlayerInfo.getPlayerData(player);
                int kills = data.getKill();
                if (kills > maxKills) {
                    maxKills = kills;
                    topPlayer = player;
                }
            }

            if (topPlayer != null) {
                plugin.stopGame(topPlayer);
            }
        }

        String tempsFormate = formatTemps(endtime);
    }
}
