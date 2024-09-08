package me.realized.duels.gui.inventory.buttons;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import me.realized.duels.DuelsPlugin;
import me.realized.duels.gui.BaseButton;
import me.realized.duels.util.compat.Items;
import net.kyori.adventure.text.Component;

public class PotionCounterButton extends BaseButton {

	public PotionCounterButton(final DuelsPlugin plugin, final int count) {
		super(plugin, buildPotionItem(plugin, count));
	}

	private static ItemStack buildPotionItem(final DuelsPlugin plugin, final int count) {
		ItemStack item = Items.HEAL_SPLASH_POTION.clone(); // Using a specific potion item

		if (item.getItemMeta() instanceof PotionMeta) {
			PotionMeta potionMeta = (PotionMeta) item.getItemMeta();
			// Use a custom potion type to avoid showing actual effects.
			potionMeta.setBasePotionData(new PotionData(PotionType.WATER));

			potionMeta.displayName(Component.text(plugin.getLang().getMessage("GUI.inventory-view.buttons.potion-counter.name", "potions", count)));
			item.setItemMeta(potionMeta);
		}

		return item;
	}

}