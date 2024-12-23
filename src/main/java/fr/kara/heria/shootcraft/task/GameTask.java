package fr.kara.heria.shootcraft.task;

import fr.kara.heria.shootcraft.Shootcraft;
import fr.kara.heria.shootcraft.config.Team;
import fr.kara.heria.shootcraft.data.PlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GameTask extends BukkitRunnable {
    private static int tempsTotal = 0;
    public final Shootcraft plugin;

    public GameTask(final Shootcraft plugin) {
        this.plugin = plugin;
        tempsTotal = 0;
        this.runTaskTimer(plugin, 0, 20);
    }

    public static int getTempsTotal() {
        return tempsTotal;
    }

    public static String getTempsFormate() {
        return formatTemps(tempsTotal);
    }

    public static String formatTemps(int secondes) {
        int minutes = (secondes % 3600) / 60;
        int secondesRestantes = secondes % 60;

        return String.format("%02d:%02d", minutes, secondesRestantes);
    }

    public void run() {
        tempsTotal++;
        String tempsFormate = formatTemps(tempsTotal);

        for (Player online : Bukkit.getOnlinePlayers()) {
            final PlayerInfo data = PlayerInfo.getPlayerData(online);
            if (data.getTeam() != Team.SPEC) {
                //data.increaseTotalTime(1);
            }
        }
    }
}
