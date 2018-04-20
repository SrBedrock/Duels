package me.realized._duels.utilities.inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.realized._duels.Core;
import me.realized._duels.utilities.Helper;
import me.realized._duels.utilities.compat.Attributes;
import me.realized._duels.utilities.compat.Potions;
import me.realized._duels.utilities.compat.SpawnEggs;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class JSONItem {

    private final String material;
    private final int amount;
    private final short data;

    private String itemData;
    private List<Attributes.AttributeModifier> attributeModifiers;

    private Map<String, Integer> enchantments;
    private String displayName;
    private List<String> lore;
    private List<String> flags;

    private String owner;

    private String author;
    private String title;
    private List<String> contents;

    private Map<String, String> effects;

    private String color;

    private JSONItem(ItemStack item) {
        this.material = item.getType().name();
        this.amount = item.getAmount() == 0 ? 1 : item.getAmount();
        this.data = item.getDurability();

        Attributes attributes = new Attributes(item);
        List<Attributes.AttributeModifier> modifiers = attributes.getModifiers();

        if (!modifiers.isEmpty()) {
            this.attributeModifiers = new ArrayList<>();
            attributeModifiers.addAll(modifiers);
        }

        if (!Helper.isPre1_9()) {
            if (material.contains("POTION")) {
                Potions potion = Potions.fromItemStack(item);

                if (potion == null) {
                    return;
                }

                StringBuilder data = new StringBuilder();
                data.append(potion.getType().name()).append("-");

                if (potion.extended()) {
                    data.append("extended-");
                }

                if (potion.linger()) {
                    data.append("linger-");
                }

                if (potion.splash()) {
                    data.append("splash-");
                }

                if (potion.strong()) {
                    data.append("strong-");
                }

                this.itemData = data.toString();
            } else if (material.equals("MONSTER_EGG")) {
                SpawnEggs spawnEgg = SpawnEggs.fromItemStack(item);

                if (spawnEgg == null) {
                    return;
                }

                this.itemData = spawnEgg.getType().name() + "-";
            } else if (material.equals("TIPPED_ARROW")) {
                PotionMeta meta = (PotionMeta) item.getItemMeta();
                PotionData potionData = meta.getBasePotionData();

                if (potionData.getType().getEffectType() == null) {
                    return;
                }

                StringBuilder data = new StringBuilder();
                data.append(potionData.getType().name()).append("-");

                if (potionData.isExtended()) {
                    data.append("extended-");
                }

                if (potionData.isUpgraded()) {
                    data.append("upgraded-");
                }

                this.itemData = data.toString();
            }
        }
    }

    private void addEnchantment(String type, int level) {
        if (type == null) {
            return;
        }

        if (enchantments == null) {
            enchantments = new HashMap<>();
        }

        enchantments.put(type, level);
    }

    private void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    private void setLore(List<String> lore) {
        this.lore = lore;
    }

    private void addFlag(String flag) {
        if (flags == null) {
            flags = new ArrayList<>();
        }

        flags.add(flag);
    }

    private void setOwner(String owner) {
        this.owner = owner;
    }

    private void setAuthor(String author) {
        this.author = author;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    private void setContents(List<String> contents) {
        this.contents = contents;
    }

    private void addEffect(String type, String data) {
        if (effects == null) {
            effects = new HashMap<>();
        }

        effects.put(type, data);
    }

    private void setColor(String color) {
        this.color = color;
    }

    public ItemStack construct() {
        Material type = Material.getMaterial(this.material);

        if (type == null) {
            Core.getInstance().warn("Error while loading kits: Failed to find material " + this.material + "!");
            return null;
        }

        ItemStack item = new ItemStack(type, this.amount, this.data);

        if (attributeModifiers != null) {
            Attributes attributes = new Attributes(item);

            for (Attributes.AttributeModifier modifier : attributeModifiers) {
                item = attributes.addModifier(modifier.getName(), modifier.getAttrName(), modifier.getOperation(), modifier.getAmount(), modifier.getSlot());
            }
        }

        if (!Helper.isPre1_9() && itemData != null) {
            List<String> itemData = Arrays.asList(this.itemData.split("-"));

            if (material.contains("POTION")) {
                Potions potion = new Potions(Potions.PotionType.valueOf(itemData.get(0)), itemData.contains("strong"), itemData.contains("extended"), itemData.contains("linger"), itemData.contains("splash"));
                item = potion.toItemStack(amount);
            } else if (material.equals("MONSTER_EGG")) {
                SpawnEggs spawnEgg1_9 = new SpawnEggs(EntityType.valueOf(itemData.get(0)));
                item = spawnEgg1_9.toItemStack(amount);
            } else if (material.equals("TIPPED_ARROW")) {
                PotionMeta potionMeta = (PotionMeta) item.getItemMeta();
                PotionData data = new PotionData(PotionType.valueOf(itemData.get(0)), itemData.contains("extended"), itemData.contains("upgraded"));
                potionMeta.setBasePotionData(data);
                item.setItemMeta(potionMeta);
            }
        }

        if (enchantments != null && !enchantments.isEmpty()) {
            for (Map.Entry<String, Integer> entry : enchantments.entrySet()) {
                item.addUnsafeEnchantment(Enchantment.getByName(entry.getKey()), entry.getValue());
            }
        }

        ItemMeta meta = item.getItemMeta();

        if (displayName != null) {
            meta.setDisplayName(displayName);
        }

        if (lore != null && !lore.isEmpty()) {
            meta.setLore(lore);
        }

        if (!Helper.isPre1_8() && flags != null && !flags.isEmpty()) {
            for (String flag : flags) {
                meta.addItemFlags(ItemFlag.valueOf(flag));
            }
        }

        item.setItemMeta(meta);

        if (item.getType() == Material.SKULL_ITEM && item.getDurability() == 3 && owner != null) {
            SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
            skullMeta.setOwner(owner);
            item.setItemMeta(skullMeta);
        }

        if (item.getType() == Material.POTION && effects != null && !effects.isEmpty()) {
            PotionMeta potionMeta = (PotionMeta) item.getItemMeta();

            for (Map.Entry<String, String> entry : effects.entrySet()) {
                int duration = Integer.valueOf(entry.getValue().split("-")[0]);
                int amplifier = Integer.valueOf(entry.getValue().split("-")[1]);
                potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.getByName(entry.getKey()), duration, amplifier), true);
            }

            item.setItemMeta(potionMeta);
        }

        if (item.getType() == Material.WRITTEN_BOOK) {
            BookMeta bookMeta = (BookMeta) item.getItemMeta();

            if (author != null) {
                bookMeta.setAuthor(author);
            }

            if (title != null) {
                bookMeta.setTitle(title);
            }

            if (contents != null && !contents.isEmpty()) {
                bookMeta.setPages(contents);
            }

            item.setItemMeta(bookMeta);
        }

        if (item.getType().name().contains("LEATHER_")) {
            LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) item.getItemMeta();

            if (color != null) {
                leatherArmorMeta.setColor(DyeColor.valueOf(color).getColor());
            }

            item.setItemMeta(leatherArmorMeta);
        }

        return item;
    }

    public static JSONItem fromItemStack(ItemStack item) {
        if (item == null) {
            return null;
        }

        JSONItem result = new JSONItem(item);

        if (!item.getEnchantments().isEmpty()) {
            for (Map.Entry<Enchantment, Integer> entry : item.getEnchantments().entrySet()) {
                result.addEnchantment(entry.getKey().getName(), entry.getValue());
            }
        }

        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();

            if (meta.hasDisplayName()) {
                result.setDisplayName(meta.getDisplayName());
            }

            if (meta.hasLore()) {
                result.setLore(meta.getLore());
            }

            if (!Helper.isPre1_8() && !meta.getItemFlags().isEmpty()) {
                for (ItemFlag flag : meta.getItemFlags()) {
                    result.addFlag(flag.name());
                }
            }

            if (item.getType() == Material.SKULL_ITEM && item.getDurability() == 3) {
                SkullMeta skullMeta = (SkullMeta) item.getItemMeta();

                if (skullMeta.hasOwner()) {
                    result.setOwner(skullMeta.getOwner());
                }
            }

            if (item.getType() == Material.POTION) {
                PotionMeta potionMeta = (PotionMeta) item.getItemMeta();

                if (potionMeta.hasCustomEffects()) {
                    for (PotionEffect effect : potionMeta.getCustomEffects()) {
                        result.addEffect(effect.getType().getName(), effect.getDuration() + "-" + effect.getAmplifier());
                    }
                }
            }

            if (item.getType() == Material.WRITTEN_BOOK) {
                BookMeta bookMeta = (BookMeta) item.getItemMeta();

                if (bookMeta.hasAuthor()) {
                    result.setAuthor(bookMeta.getAuthor());
                }

                if (bookMeta.hasTitle()) {
                    result.setTitle(bookMeta.getTitle());
                }

                if (bookMeta.hasPages()) {
                    result.setContents(bookMeta.getPages());
                }
            }

            if (item.getType().name().contains("LEATHER_")) {
                LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) item.getItemMeta();

                if (DyeColor.getByColor(leatherArmorMeta.getColor()) != null) {
                    result.setColor(DyeColor.getByColor(leatherArmorMeta.getColor()).name());
                }
            }
        }

        return result;
    }
}