package me.realized.duels.gui.inventory.buttons;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.realized.duels.DuelsPlugin;
import me.realized.duels.gui.BaseButton;
import me.realized.duels.util.StringUtil;
import me.realized.duels.util.compat.Items;
import me.realized.duels.util.inventory.ItemBuilder;

public class EffectsButton extends BaseButton {

	public EffectsButton(final DuelsPlugin plugin, final Player player) {
		super(plugin, createItem(plugin, player));
	}

	private static ItemStack createItem(DuelsPlugin plugin, Player player) {
		return ItemBuilder
				.of(Items.WATER_BREATHING_POTION.clone())
				.name(plugin.getLang().getMessage("GUI.inventory-view.buttons.effects.name"))
				.lore(getPotionEffectsLore(plugin, player))
				.build();
	}

	private static List<String> getPotionEffectsLore(DuelsPlugin plugin, Player player) {
		return player.getActivePotionEffects().stream()
				.map(effect -> plugin.getLang().getMessage("GUI.inventory-view.buttons.effects.lore-format",
				"type", StringUtil.capitalize(effect.getType().getName().replace("_", " ").toLowerCase()),
				"amplifier", StringUtil.toRoman(effect.getAmplifier() + 1),
				"duration", (effect.getDuration() / 20)))
				.collect(Collectors.toList());
	}

}