package fr.kara.heria.shootcraft.utils;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.UUID;

public class Nametag {

    public static void setNameTag(Player player, String prefix, String suffix, int sortPriority) {
        Scoreboard manager = player.getScoreboard();
        String priority = ((sortPriority < 10) ? "0" : "") + sortPriority;
        Team team = null;
        for(Team t : manager.getTeams()) {
            if (t.getPrefix().equals(prefix) && t.getSuffix().equals(suffix) && t.getName().startsWith(priority)) {
                team = t;
                break;
            }
        }
        while(team == null) {
            String tn = priority + UUID.randomUUID().toString().substring(30);
            if (manager.getTeam(tn) == null) {
                team = manager.registerNewTeam(tn);
                team.setPrefix(prefix);
                team.setSuffix(suffix);
            }
        }
        team.addEntry(player.getName());
    }
}
