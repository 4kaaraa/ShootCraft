package fr.kara.heria.shootcraft.listeners;

import fr.kara.heria.shootcraft.Shootcraft;
import fr.kara.heria.shootcraft.config.GameState;
import fr.kara.heria.shootcraft.data.PlayerInfo;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    private final Shootcraft plugin;

    public PlayerQuit(Shootcraft plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        final PlayerInfo data = PlayerInfo.getPlayerData(player);
        e.setQuitMessage(null);

        Shootcraft.getInstance().getScoreboardManager().onLogout(player);

        if (GameState.isStep(GameState.WAITING)){
            if (data.hasTeam()){
                plugin.removePlayer(player);
            }
        } else if (GameState.isStep(GameState.INGAME)) {
            if (data.hasTeam()) {
                plugin.removePlayer(player);
            }
        }
    }
}
