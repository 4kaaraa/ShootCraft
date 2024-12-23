package fr.kara.heria.shootcraft.enums;

import org.bukkit.Material;

public enum SongEnum {
    PLING("default", "note.pling", "§7Default", "§7Gratuit", Material.BARRIER),
    BURP("burp", "random.burp", "§eBURP", "§eVIP",  Material.GLASS_BOTTLE),
    EXP("exp", "random.levelup", "§aEXP", "§eVIP",  Material.EXP_BOTTLE),
    WATER("water", "random.splash", "§9WATER", "§eVIP",  Material.POTION),
    ANVIL("anvil", "random.anvil_break", "§8ANVIL", "§3VIP+",  Material.ANVIL),
    SKELETON("skeleton", "mob.skeleton.death", "§7SKELETON", "§3VIP+",  Material.BONE),
    BLAZE("blaze", "mob.blaze.death", "§6BLAZE", "§3VIP+",  Material.BLAZE_ROD),
    ;

    private final String id;
    private final String song;
    private final String name;
    private final String price;
    private final Material item;

    SongEnum(String id, String song, String name, String price, Material item) {
        this.id = id;
        this.song = song;
        this.name = name;
        this.price = price;
        this.item = item;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return "shootcraft.song." + id;
    }

    public String getSong() {
        return song;
    }

    public Material getItem() {
        return item;
    }

    public String getPrice() {
        return price;
    }
}
