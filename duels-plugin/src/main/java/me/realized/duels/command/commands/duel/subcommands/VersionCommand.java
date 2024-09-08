package me.realized.duels.command.commands.duel.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.realized.duels.DuelsPlugin;
import me.realized.duels.Permissions;
import me.realized.duels.command.BaseCommand;
import me.realized.duels.util.StringUtil;
import me.realized.duels.util.TextBuilder;
import net.md_5.bungee.api.chat.ClickEvent.Action;

public class VersionCommand extends BaseCommand {

	private static final String WEBSITE_URL = "https://github.com/true-og/Duels-OG/";

	public VersionCommand(final DuelsPlugin plugin) {
		super(plugin, "version", null, null, Permissions.DUEL, 1, true, "v");
	}

	@Override
	protected void execute(final CommandSender sender, final String label, final String[] args) {
		TextBuilder
		.of(StringUtil.color("&b" + plugin.getName() + " by Realized" + " &l[Click]"))
		.setClickEvent(Action.OPEN_URL, WEBSITE_URL)
		.send((Player) sender);
	}

}