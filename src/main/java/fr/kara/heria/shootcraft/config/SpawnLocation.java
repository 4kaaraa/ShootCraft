package fr.kara.heria.shootcraft.config;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum SpawnLocation {
    HUB(new Location(Bukkit.getWorld("world"), 1.5, 88, 236.5, -180, -2)),
    SPEC(new Location(Bukkit.getWorld("world"), -480.5, 75, 399.5, -1, 89)),

    //Bleu
    SPAWN1(new Location(Bukkit.getWorld("world"), 123.5, 19, -87.5, 0, 0)),
    SPAWN2(new Location(Bukkit.getWorld("world"), 171.5, 19, -87.5, 0, 0)),
    SPAWN3(new Location(Bukkit.getWorld("world"), 162.5, 4, -57.5, 0, 0)),
    SPAWN4(new Location(Bukkit.getWorld("world"), 132.5, 4, -116.5, 0, 0)),
    SPAWN5(new Location(Bukkit.getWorld("world"), 166.5, 4, -112.5, 0, 0)),
    SPAWN6(new Location(Bukkit.getWorld("world"), 128.5, 4, -61.5, 0, 0)),
    SPAWN7(new Location(Bukkit.getWorld("world"), 144.5, 4, -82.5, 0, 0)),
    SPAWN8(new Location(Bukkit.getWorld("world"), 150.5, 4, -91.5, 0, 0)),
    ;


    private final Location location;

    SpawnLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public static Location getRandomSpawnLocation() {
        List<SpawnLocation> spawnLocations = Stream.of(SpawnLocation.values())
                .filter(loc -> loc.name().contains("SPAWN"))
                .collect(Collectors.toList());

        int randomIndex = ThreadLocalRandom.current().nextInt(spawnLocations.size());
        return spawnLocations.get(randomIndex).getLocation();
    }
}
