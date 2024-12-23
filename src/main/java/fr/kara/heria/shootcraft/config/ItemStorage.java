package fr.kara.heria.shootcraft.config;

import fr.kara.heria.shootcraft.utils.ItemBuilder;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

@Getter
public class ItemStorage {
    public static final ItemStack sword = (new ItemBuilder(Material.IRON_SWORD)).setName("§7Épée en Fer").flag(ItemFlag.HIDE_ATTRIBUTES).setInfinityDurability().addEnchant(Enchantment.DAMAGE_ALL, 3).flag(ItemFlag.HIDE_UNBREAKABLE).build();

    public static final ItemStack pickaxe = (new ItemBuilder(Material.IRON_PICKAXE)).setName("§7Pioche en Fer").flag(ItemFlag.HIDE_ATTRIBUTES).setInfinityDurability().addEnchant(Enchantment.DIG_SPEED, 4).flag(ItemFlag.HIDE_UNBREAKABLE).build();

    public static final ItemStack apple = (new ItemBuilder(Material.GOLDEN_APPLE, 64)).setName("§7Pomme en Or").flag(ItemFlag.HIDE_ATTRIBUTES).build();
    public static final ItemStack stick = (new ItemBuilder(Material.STICK, 1)).setName("§7Laser").flag(ItemFlag.HIDE_ATTRIBUTES).build();

    public static final ItemStack blocks = (new ItemBuilder(Material.SANDSTONE, 64)).setName("§7Grès Polis").flag(ItemFlag.HIDE_ATTRIBUTES).build();
    public static final ItemStack red_helmet = (new ItemBuilder(Material.LEATHER_HELMET)).setName(ChatColor.RED + "Casque").flag(ItemFlag.HIDE_ATTRIBUTES).setInfinityDurability().setLeatherArmorColor(Color.RED).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).build();

    public static final ItemStack red_chestplate = (new ItemBuilder(Material.LEATHER_CHESTPLATE)).setName(ChatColor.RED + "Plastron").flag(ItemFlag.HIDE_ATTRIBUTES).setInfinityDurability().setLeatherArmorColor(Color.RED).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).build();

    public static final ItemStack red_leggings = (new ItemBuilder(Material.LEATHER_LEGGINGS)).setName(ChatColor.RED + "Jambières").flag(ItemFlag.HIDE_ATTRIBUTES).setInfinityDurability().setLeatherArmorColor(Color.RED).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).build();

    public static final ItemStack red_boots = (new ItemBuilder(Material.LEATHER_BOOTS)).setName(ChatColor.RED + "Bottes").flag(ItemFlag.HIDE_ATTRIBUTES).setInfinityDurability().setLeatherArmorColor(Color.RED).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).build();

    public static final ItemStack blue_helmet = (new ItemBuilder(Material.LEATHER_HELMET)).setName(ChatColor.BLUE + "Casque").flag(ItemFlag.HIDE_ATTRIBUTES).setInfinityDurability().setLeatherArmorColor(Color.BLUE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).build();

    public static final ItemStack blue_chestplate = (new ItemBuilder(Material.LEATHER_CHESTPLATE)).setName(ChatColor.BLUE + "Plastron").flag(ItemFlag.HIDE_ATTRIBUTES).setInfinityDurability().setLeatherArmorColor(Color.BLUE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).build();

    public static final ItemStack blue_leggings = (new ItemBuilder(Material.LEATHER_LEGGINGS)).setName(ChatColor.BLUE + "Jambières").flag(ItemFlag.HIDE_ATTRIBUTES).setInfinityDurability().setLeatherArmorColor(Color.BLUE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).build();

    public static final ItemStack blue_boots = (new ItemBuilder(Material.LEATHER_BOOTS)).setName(ChatColor.BLUE + "Bottes").flag(ItemFlag.HIDE_ATTRIBUTES).setInfinityDurability().setLeatherArmorColor(Color.BLUE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).build();
}

