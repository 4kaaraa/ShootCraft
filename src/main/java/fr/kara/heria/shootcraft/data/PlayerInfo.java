package fr.kara.heria.shootcraft.data;

import fr.kara.heria.shootcraft.config.Team;
import org.bukkit.OfflinePlayer;

import java.util.*;

public class PlayerInfo {

    private static final Map<OfflinePlayer, PlayerInfo> dataMap;
    static {
        dataMap = new HashMap<>();
    }

    private boolean loaded = false;
    private boolean spectateur = false;
    private boolean waiting = false;

    private UUID uuid;
    private String name;
    private OfflinePlayer player;
    private float exp;
    private int kills;
    private int point;
    private int actualKills;
    private int deaths;
    private int actualDeaths;
    private int games;
    private int totalTime;
    private int wins;

    private Team team;


    public PlayerInfo(OfflinePlayer player) {
        this.uuid = player.getUniqueId();
        this.name = player.getName();
        this.player = player;
        this.exp = 0;
        this.point = 0;
        this.wins = 0;
        this.kills = 0;
        this.actualKills = 0;
        this.deaths = 0;
        this.actualDeaths = 0;
        this.games = 0;
        this.totalTime = 0;
        this.spectateur = false;
        this.waiting = false;
    }

    public static PlayerInfo getPlayerData(final OfflinePlayer player) {
        PlayerInfo playerInfo = new PlayerInfo(player);

        if (!dataMap.containsKey(player))
            playerInfo.loadData();
        return dataMap.get(player);
    }

    public static Set<OfflinePlayer> getPlayers() {
        return dataMap.keySet();
    }

    public static Set<Map.Entry<OfflinePlayer, PlayerInfo>> getEntries() {
        return dataMap.entrySet();
    }

    public static Collection<PlayerInfo> getDatas() {
        return dataMap.values();
    }

    public OfflinePlayer getOfflinePlayer() {
        return this.player;
    }

    // Get
    public boolean isLoaded() {
        return loaded;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public OfflinePlayer getPlayer() {
        return player;
    }

    public float getExp() {
        return exp;
    }

    public int getKill() {
        return kills;
    }

    public int getPoint() {
        return point;
    }

    public int getDeath() {
        return deaths;
    }

    public int getWins() {
        return wins;
    }
    public boolean isSpectateur() {
        return spectateur;
    }

    public boolean isWaiting() {
        return waiting;
    }

    public boolean hasTeam() {
        return (this.team != null && this.team != Team.SPEC);
    }

    public String getRatio() {
        double ratio = (double) getKill() / getDeath();
        return String.format("%.2f", ratio);
    }


    //Set
    public Team getTeam() {
        return this.team;
    }

    public void setTeam(Team team) {
        if (this.player.isOnline()) {
            if (this.team != null)
                this.team.removePlayer(this.player.getPlayer());
            this.team = team;
            team.addPlayer(this.player.getPlayer());
        }
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlayer(OfflinePlayer player) {
        this.player = player;
    }

    public void setExp(float exp) {
        this.exp = exp;
    }

    public void setKill(int kill) {
        this.kills = kill;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public void setDeath(int death) {
        this.deaths = death;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }
    public void setSpectateur(boolean spectateur) {
        this.spectateur = spectateur;
    }

    public void setWaiting(boolean waiting) {
        this.waiting = waiting;
    }

    public void increaseWins(final int wins) {
        this.wins += wins;
    }

    public void increaseKills(final int kills) {
        this.kills += kills;
        this.actualKills += kills;
    }

    public void increaseDeaths(final int deaths) {
        this.deaths += deaths;
        this.actualDeaths += deaths;
    }

    public void increaseGames(final int games) {
        this.games += games;
    }

    public void increaseTotalTime(final int totalTime) {
        this.totalTime += totalTime;
    }

    public void loadData() {
        this.loaded = true;
        dataMap.put(player, this);
    }
}
