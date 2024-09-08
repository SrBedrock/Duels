package me.realized.duels.command.commands.duels.subcommands;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import me.realized.duels.DuelsPlugin;
import me.realized.duels.command.BaseCommand;
import me.realized.duels.util.Loadable;
import me.realized.duels.util.Reloadable;
import me.realized.duels.util.StringUtil;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class ReloadCommand extends BaseCommand {

	private final MiniMessage miniMessage = MiniMessage.miniMessage();

	public ReloadCommand(final DuelsPlugin plugin) {
		super(plugin, "reload", null, null, 1, false, "rl");
	}

	@Override
	protected void execute(final CommandSender sender, final String label, final String[] args) {
		if (args.length > getLength()) {
			final Loadable target = plugin.find(args[1]);

			if (!(target instanceof Reloadable)) {
				sender.sendMessage(miniMessage.deserialize("<red>Invalid module. The following modules are available for reload: " +
						StringUtil.join(plugin.getReloadables(), ", ") + "</red>"));
				return;
			}

			final String name = target.getClass().getSimpleName();

			if (plugin.reload(target)) {
				sender.sendMessage(miniMessage.deserialize("<green>[" + plugin.getName() + "] Successfully reloaded " + name + ".</green>"));
			} else {
				sender.sendMessage(miniMessage.deserialize("<red>An error occurred while reloading " + name + "! Please check the console for more information.</red>"));
			}

			return;
		}

		if (plugin.reload()) {
			sender.sendMessage(miniMessage.deserialize("<green>[" + plugin.getName() + "] Reload complete.</green>"));
		} else {
			sender.sendMessage(miniMessage.deserialize("<red>An error occurred while reloading the plugin! Please check the console for more information.</red>"));
		}
	}

	@Override
	public List<String> onTabComplete(final CommandSender sender, final Command command, final String alias, final String[] args) {
		if (args.length == 2) {
			return plugin.getReloadables().stream()
					.filter(name -> name.toLowerCase().startsWith(args[1].toLowerCase()))
					.collect(Collectors.toList());
		}

		return null;
	}

}