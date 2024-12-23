package fr.kara.heria.shootcraft.listeners;

import fr.heriamc.api.HeriaAPI;
import fr.heriamc.api.user.HeriaPlayer;
import fr.kara.heria.shootcraft.data.PlayerInfo;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

public class PlayerChat implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Player player = e.getPlayer();
        final PlayerInfo data = PlayerInfo.getPlayerData(player);
        e.setCancelled(true);
        UUID chatID = UUID.randomUUID();

        HeriaPlayer heriaPlayer = HeriaAPI.get().getPlayerManager().get(player.getUniqueId());

        TextComponent reportSymbol = new TextComponent(TextComponent.fromLegacyText("⚠"));
        reportSymbol.setColor(ChatColor.RED);
        reportSymbol.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/reportchat " + chatID));
        reportSymbol.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder("Cliquez pour signaler " + player.getName()).color(ChatColor.RED).create()));


        if (data.hasTeam()){
            TextComponent message = new TextComponent(TextComponent.fromLegacyText(heriaPlayer.getRank().getPrefix() + data.getName() + " §8» §f" + e.getMessage()));

            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(reportSymbol, message);
            }
        } else {
            TextComponent message = new TextComponent(TextComponent.fromLegacyText(heriaPlayer.getRank().getPrefix() + data.getName() + " §8» §f" + e.getMessage()));

            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(reportSymbol, message);
            }
        }
    }
}
