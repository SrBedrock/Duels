package me.realized.duels.data;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import me.realized.duels.util.EnumUtil;
import me.realized.duels.util.collection.StreamUtil;
import me.realized.duels.util.compat.CompatUtil;
import me.realized.duels.util.compat.Identifiers;
import me.realized.duels.util.inventory.ItemBuilder;
import me.realized.duels.util.inventory.ItemUtil;
import me.realized.duels.util.json.DefaultBasedDeserializer;

public class ItemData {

	public static ItemData fromItemStack(final ItemStack item) {
		return new ItemData(item);
	}

	private Map<String, Object> item;

	private ItemData() {}

	private ItemData(ItemStack item) {
		item = Identifiers.removeIdentifier(item);
		this.item = item.serialize();
	}

	public ItemStack toItemStack(final boolean kitItem) {
		if (item == null || item.isEmpty()) {
			return null;
		}

		ItemStack itemStack = ItemStack.deserialize(item);
		return kitItem ? Identifiers.addIdentifier(itemStack) : itemStack;
	}

	public ItemStack toItemStack() {
		return toItemStack(true);
	}

	public static class ItemDataDeserializer extends DefaultBasedDeserializer<ItemData> {

		private static final long serialVersionUID = 2L;
		private static boolean checkOldJson = true;

		public ItemDataDeserializer(final JsonDeserializer<?> defaultDeserializer) {
			super(ItemData.class, defaultDeserializer);
		}

		@Override
		public ItemData deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
			JsonNode node = null;
			JsonParser actual = parser;

			// Create a copy of current json as node tree in case old json version is detected.
			if (checkOldJson) {
				node = parser.readValueAsTree();
				actual = parser.getCodec().treeAsTokens(node);
				actual.nextToken();
			}

			ItemData data = (ItemData) defaultDeserializer.deserialize(actual, context);

			if (data.item != null) {
				// If an item was successfully parsed to new json, disable old json check (assume kit file is in new json format) to reduce overhead.
				checkOldJson = false;
			} else if (node != null) {
				if (! node.isObject()) {
					return null;
				}

				if (node.has("serializedItem")) {
					return new ItemData(ItemUtil.itemFrom64(node.get("serializedItem").textValue()));
				}

				final String material = node.get("material").textValue();
				final int amount = node.has("amount") ? node.get("amount").intValue() : 1;
				final short damage = node.has("data") ? node.get("data").shortValue() : 0;
				final Material type = Material.getMaterial(material);

				if (type == null) {
					return null;
				}

				final ItemBuilder builder = ItemBuilder.of(type, amount, damage);

				if (node.has("displayName")) {
					builder.name(node.get("displayName").textValue());
				}

				if (node.has("lore")) {
					builder.lore(StreamUtil.asStream(node.get("lore")).map(JsonNode::textValue).collect(Collectors.toList()));
				}

				if (node.has("enchantments")) {
					final JsonNode enchantments = node.get("enchantments");

					StreamUtil.asStream(enchantments.fieldNames()).forEach(entry -> {
						final NamespacedKey key = NamespacedKey.minecraft(entry);
						final Enchantment enchantment = Enchantment.getByKey(key);

						if (enchantment == null) {
							return;
						}

						builder.enchant(enchantment, enchantments.get(entry).asInt());
					});
				}

				if (node.has("flags") && CompatUtil.hasItemFlag()) {
					final JsonNode flags = node.get("flags");
					StreamUtil.asStream(flags).forEach(flagNode -> {
						final ItemFlag flag = EnumUtil.getByName(flagNode.textValue(), ItemFlag.class);

						if (flag == null) {
							return;
						}

						builder.editMeta(meta -> meta.addItemFlags(flag));
					});
				}

				if (node.has("unbreakable") && node.get("unbreakable").booleanValue()) {
					builder.unbreakable();
				}

				if (node.has("owner")) {
					String ownerIdentifier = node.get("owner").textValue();
					OfflinePlayer owner;

					try {
						// Try to parse the identifier as a UUID.
						UUID ownerUUID = UUID.fromString(ownerIdentifier);
						owner = Bukkit.getOfflinePlayer(ownerUUID);
					} catch (IllegalArgumentException e) {
						// If parsing fails, assume it's a player name.
						owner = Bukkit.getOfflinePlayer(ownerIdentifier);
					}

					builder.head(owner);
				}

				if (node.has("color")) {
					builder.leatherArmorColor(node.get("color").textValue());
				}

				if (node.has("effects")) {
					final JsonNode effects = node.get("effects");
					builder.editMeta(meta -> {
						final PotionMeta potionMeta = (PotionMeta) meta;

						StreamUtil.asStream(effects.fieldNames()).forEach(entry -> {
							final String[] split = effects.get(entry).textValue().split("-");
							final int duration = Integer.parseInt(split[0]);
							final int amplifier = Integer.parseInt(split[1]);
							final PotionEffectType effectType = PotionEffectType.getByName(entry);

							if (effectType == null) {
								return;
							}

							potionMeta.addCustomEffect(new PotionEffect(effectType, duration, amplifier), true);
						});
					});
				}

				if (node.has("itemData") && !CompatUtil.isPre1_9()) {
					final List<String> args = Arrays.asList(node.get("itemData").textValue().split("-"));
					final PotionType potionType = EnumUtil.getByName(args.get(0), PotionType.class);

					if (potionType != null) {
						builder.potion(potionType, args.contains("extended"), args.contains("strong"));
					}
				}

				if (node.has("attributeModifiers") && CompatUtil.hasAttributes()) {
					final JsonNode attributes = node.get("attributeModifiers");
					StreamUtil.asStream(attributes).forEach(attributeNode -> builder.attribute(
							attributeNode.get("name").textValue(),
							attributeNode.get("operation").intValue(),
							attributeNode.get("amount").doubleValue(),
							attributeNode.has("slot") ? attributeNode.get("slot").textValue() : null
							));
				}

				return new ItemData(builder.build());
			}

			return data;
		}
	}

}