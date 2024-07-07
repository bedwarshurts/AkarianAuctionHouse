package net.akarian.auctionhouse.utils;

import net.akarian.auctionhouse.AuctionHouse;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.UUID;


public class ItemBuilder {

    public static @NotNull ItemStack build(Material material, int amount, String name, List<String> lore, String... settings) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        Objects.requireNonNull(meta).setLore(AuctionHouse.getInstance().getChat().formatList(lore));
        meta.setDisplayName(AuctionHouse.getInstance().getChat().format(name));
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        for(String s : settings) {
            switch (s) {
                case "hide_attributes":
                case "h_a":
                    meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                    break;
                case "hide_enchants":
                case "h_e":
                    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    break;
                case "hide_potion_effects":
                case "h_pe":
                    meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
                    break;
                case "unbreakable":
                case "u":
                    meta.setUnbreakable(true);
                    break;
                case "glow":
                    meta.addEnchant(Enchantment.DURABILITY, 1, true);
                    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    break;
            }
            if (s.contains("uuid_")) {
                NamespacedKey key = new NamespacedKey(AuctionHouse.getInstance(), "builder-uuid");
                meta = item.getItemMeta();
                meta.getPersistentDataContainer().set(key, new UUIDDataType(), UUID.fromString(s.split("_")[1]));
                item.setItemMeta(meta);
            }
        }
        return item;
    }

}
