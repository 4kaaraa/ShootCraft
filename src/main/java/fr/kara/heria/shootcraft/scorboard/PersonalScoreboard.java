package fr.kara.heria.shootcraft.scorboard;

import fr.kara.heria.shootcraft.Shootcraft;
import fr.kara.heria.shootcraft.config.GameState;
import fr.kara.heria.shootcraft.data.PlayerInfo;
import fr.kara.heria.shootcraft.task.EndGameTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class PersonalScoreboard {
    private final Player player;
    private final UUID uuid;
    private final ObjectiveSign objectiveSign;

    public PersonalScoreboard(Player player) {
        this.player = player;
        uuid = player.getUniqueId();
        objectiveSign = new ObjectiveSign("sidebar", "HeriaMC");
        reloadData();
        objectiveSign.addReceiver(player);
    }

    public void reloadData() {
    }

    public void setLines(String ip) {
        String temps = EndGameTask.getTempsFormate();
        PlayerInfo data = PlayerInfo.getPlayerData(player);
        objectiveSign.setDisplayName("§f» §6§lShootCraft §f«");

        if (GameState.isStep(GameState.WAITING)) {
            objectiveSign.setLine(0, "§1");
            objectiveSign.setLine(1, "§8■ §fJeu: §7Shootcraft (Bêta)");
            objectiveSign.setLine(2, "§8■ §fDébut dans: §3" + (Bukkit.getOnlinePlayers().size() < 2 ? "1 joueur" : Shootcraft.getInstance().getWaitingTask().getRemainingSeconds()));
            objectiveSign.setLine(3, "§2");
            objectiveSign.setLine(4, "§8■ §fJoueurs: §a" + Bukkit.getOnlinePlayers().size());
            objectiveSign.setLine(5, "§8");
            objectiveSign.setLine(6, ip);
        }

        if (GameState.isStep(GameState.INGAME)) {
            objectiveSign.clearScores();
            objectiveSign.setLine(0, "§1");
            objectiveSign.setLine(1, "§8■ §fTué(s) : §a" + data.getKill());
            objectiveSign.setLine(2, "§8■ §fFin : §b" + temps);
            objectiveSign.setLine(3, "§2");
            objectiveSign.setLine(4, "§8■ §fClassement:");
            objectiveSign.setLine(5, "  §a① §f" + Shootcraft.getInstance().getTopKill(0));
            objectiveSign.setLine(6, "  §e② §f" + Shootcraft.getInstance().getTopKill(1));
            objectiveSign.setLine(7, "  §c③ §f" + Shootcraft.getInstance().getTopKill(2));
            objectiveSign.setLine(8, "§3");
            objectiveSign.setLine(9, ip);
        }

        objectiveSign.updateLines();
    }

    public void onLogout() {
        objectiveSign.removeReceiver(Bukkit.getServer().getOfflinePlayer(uuid));
    }
}

