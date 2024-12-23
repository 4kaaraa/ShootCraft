package fr.kara.heria.shootcraft.enums;

import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Effect;
import org.bukkit.Material;

public enum ParticlesEnums {
    DEFAULT("firespark", EnumParticle.FIREWORKS_SPARK, "§fDefault", "Gratuit", Material.PAPER),
    FLAME("flame", EnumParticle.FLAME, "§6Flamme", "§eVIP", Material.PAPER),
    HEART("hearth", EnumParticle.HEART, "§cCoeur", "§3VIP+", Material.PAPER),
    ;

    private final String id;
    private final EnumParticle particle;
    private final String name;
    private final String price;
    private final Material item;

    ParticlesEnums(String id, EnumParticle particle, String name, String price, Material item) {
        this.id = id;
        this.particle = particle;
        this.name = name;
        this.price = price;
        this.item = item;
    }

    public String getName() {
        return name;
    }

    public EnumParticle getParticle() {
        return particle;
    }

    public Material getItem() {
        return item;
    }

    public String getPrice() {
        return price;
    }

    public String getId() {
        return "shootcraft.particle." + id;
    }
}
