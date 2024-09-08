package me.realized.duels.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.realized.duels.DuelsPlugin;
import me.realized.duels.util.EnumUtil;
import me.realized.duels.util.config.AbstractConfiguration;

public class Config extends AbstractConfiguration<DuelsPlugin> {

	private String version;
	private boolean ctpPreventDuel;
	private boolean ctpPreventTag;
	private boolean pmPreventDuel;
	private boolean pmPreventTag;
	private boolean clxPreventDuel;
	private boolean clxPreventTag;
	private boolean autoUnvanish;
	private boolean setBackLocation;
	private boolean disableSkills;
	private boolean fuNoPowerLoss;
	private boolean fNoPowerLoss;
	private boolean duelzoneEnabled;
	private List<String> duelzones;
	private boolean myPetDespawn;
	private boolean preventBountyLoss;
	private boolean preventKDRChange;
	private String lhWinsCmd;
	private String lhWinsTitle;
	private String lhLossesCmd;
	private String lhLossesTitle;
	private boolean requiresClearedInventory;
	private boolean preventCreativeMode;
	private boolean ownInventoryEnabled;
	private boolean ownInventoryDropInventoryItems;
	private boolean ownInventoryUsePermission;
	private boolean kitSelectingEnabled;
	private boolean kitSelectingUsePermission;
	private boolean arenaSelectingEnabled;
	private boolean arenaSelectingUsePermission;
	private boolean itemBettingEnabled;
	private boolean itemBettingUsePermission;
	private boolean moneyBettingEnabled;
	private boolean moneyBettingUsePermission;
	private int expiration;
	private int maxDuration;
	private boolean startCommandsEnabled;
	private boolean startCommandsQueueOnly;
	private List<String> startCommands;
	private boolean endCommandsEnabled;
	private boolean endCommandsQueueOnly;
	private List<String> endCommands;
	private boolean projectileHitMessageEnabled;
	private List<String> projectileHitMessageTypes;
	private boolean preventInventoryOpen;
	private boolean protectKitItems;
	private boolean removeEmptyBottle;
	private boolean preventTpToMatchPlayers;
	private boolean forceAllowCombat;
	private boolean cancelIfMoved;
	private List<String> blacklistedWorlds;
	private boolean teleportToLastLocation;
	private int teleportDelay;
	private boolean spawnFirework;
	private boolean arenaOnlyEndMessage;
	private boolean displayInventories;
	private boolean preventItemDrop;
	private boolean preventItemPickup;
	private boolean limitTeleportEnabled;
	private double distanceAllowed;
	private boolean blockAllCommands;
	private List<String> whitelistedCommands;
	private List<String> blacklistedCommands;
	private boolean isVictoryTitleEnabled;
	private List<String> queueBlacklistedCommands;
	private boolean ratingEnabled;
	private int kFactor;
	private int defaultRating;
	private boolean ratingQueueOnly;
	private boolean specRequiresClearedInventory;
	private boolean specUseSpectatorGamemode;
	private boolean specAddInvisibilityEffect;
	private List<String> specWhitelistedCommands;
	private boolean cdEnabled;
	private List<String> cdMessages;
	private List<String> titles;
	private boolean preventMovement;
	private boolean preventLaunchProjectile;
	private boolean preventPvp;
	private boolean preventInteract;
	private boolean displayKitRatings;
	private boolean displayNoKitRating;
	private boolean displayPastMatches;
	private int matchesToDisplay;
	private long topUpdateInterval;
	private String topWinsType;
	private String topWinsIdentifier;
	private String topLossesType;
	private String topLossesIdentifier;
	private String topKitType;
	private String topKitIdentifier;
	private String topNoKitType;
	private String topNoKitIdentifier;
	private int kitSelectorRows;
	private String kitSelectorFillerType;
	private short kitSelectorFillerData;
	private int arenaSelectorRows;
	private String arenaSelectorFillerType;
	private short arenaSelectorFillerData;
	private String settingsFillerType;
	private short settingsFillerData;
	private int queuesRows;
	private String queuesFillerType;
	private short queuesFillerData;
	private boolean inheritKitItemType;
	private double soupHeartsToRegen;
	private boolean soupRemoveEmptyBowl;
	private boolean soupCancelIfAlreadyFull;

	private final Map<String, MessageSound> sounds = new HashMap<>();

	public Config(final DuelsPlugin plugin) {
		super(plugin, "config");
	}

	@Override
	protected void loadValues(FileConfiguration configuration) throws Exception {

		version = configuration.getString("version");

		ctpPreventDuel = configuration.getBoolean("supported-plugins.CombatTagPlus.prevent-duel-if-tagged", true);
		ctpPreventTag = configuration.getBoolean("supported-plugins.CombatTagPlus.prevent-tag-in-duel", true);
		pmPreventDuel = configuration.getBoolean("supported-plugins.PvPManager.prevent-duel-if-tagged", true);
		pmPreventTag = configuration.getBoolean("supported-plugins.PvPManager.prevent-tag-in-duel", true);
		clxPreventDuel = configuration.getBoolean("supported-plugins.CombatLogX.prevent-duel-if-tagged", true);
		clxPreventTag = configuration.getBoolean("supported-plugins.CombatLogX.prevent-tag-in-duel", true);
		autoUnvanish = configuration.getBoolean("supported-plugins.Essentials.auto-unvanish", true);
		setBackLocation = configuration.getBoolean("supported-plugins.Essentials.set-back-location", true);
		disableSkills = configuration.getBoolean("supported-plugins.mcMMO.disable-skills-in-duel", true);
		fuNoPowerLoss = configuration.getBoolean("supported-plugins.FactionsUUID.no-power-loss-in-duel", true);
		fNoPowerLoss = configuration.getBoolean("supported-plugins.Factions.no-power-loss-in-duel", true);
		duelzoneEnabled = configuration.getBoolean("supported-plugins.WorldGuard.duelzone.enabled", false);
		duelzones = configuration.getStringList("supported-plugins.WorldGuard.duelzone.regions");
		myPetDespawn = configuration.getBoolean("supported-plugins.MyPet.despawn-pet-in-duel", false);
		preventBountyLoss = configuration.getBoolean("supported-plugins.BountyHunters.prevent-bounty-loss-in-duel", true);
		preventKDRChange = configuration.getBoolean("supported-plugins.SimpleClans.prevent-kdr-change", true);
		lhWinsCmd = configuration.getString("supported-plugins.LeaderHeads.wins.menu.command", "openwins");
		lhWinsTitle = configuration.getString("supported-plugins.LeaderHeads.wins.menu.title", "Duel Wins");
		lhLossesCmd = configuration.getString("supported-plugins.LeaderHeads.losses.menu.command", "openlosses");
		lhLossesTitle = configuration.getString("supported-plugins.LeaderHeads.losses.menu.title", "Duel Losses");

		requiresClearedInventory = configuration.getBoolean("request.requires-cleared-inventory", true);
		preventCreativeMode = configuration.getBoolean("request.prevent-creative-mode", false);
		ownInventoryEnabled = configuration.getBoolean("request.use-own-inventory.enabled", true);
		ownInventoryDropInventoryItems = configuration.getBoolean("request.use-own-inventory.drop-inventory-items", false);
		ownInventoryUsePermission = configuration.getBoolean("request.use-own-inventory.use-permission", false);
		kitSelectingEnabled = configuration.getBoolean("request.kit-selecting.enabled", true);
		kitSelectingUsePermission = configuration.getBoolean("request.kit-selecting.use-permission", false);
		arenaSelectingEnabled = configuration.getBoolean("request.arena-selecting.enabled", true);
		arenaSelectingUsePermission = configuration.getBoolean("request.arena-selecting.use-permission", false);
		itemBettingEnabled = configuration.getBoolean("request.item-betting.enabled", true);
		itemBettingUsePermission = configuration.getBoolean("request.item-betting.use-permission", false);
		moneyBettingEnabled = configuration.getBoolean("request.money-betting.enabled", true);
		moneyBettingUsePermission = configuration.getBoolean("request.money-betting.use-permission", false);
		expiration = Math.max(configuration.getInt("request.expiration", 30), 0);

		maxDuration = configuration.getInt("duel.match.max-duration", -1);
		startCommandsEnabled = configuration.getBoolean("duel.match.start-commands.enabled", false);
		startCommandsQueueOnly = configuration.getBoolean("duel.match.start-commands.queue-matches-only", false);
		startCommands = configuration.getStringList("duel.match.start-commands.commands");
		endCommandsEnabled = configuration.getBoolean("duel.match.end-commands.enabled", false);
		endCommandsQueueOnly = configuration.getBoolean("duel.match.end-commands.queue-matches-only", false);
		endCommands = configuration.getStringList("duel.match.end-commands.commands");
		projectileHitMessageEnabled = configuration.getBoolean("duel.projectile-hit-message.enabled", true);
		projectileHitMessageTypes = configuration.getStringList("duel.projectile-hit-message.types");
		preventInventoryOpen = configuration.getBoolean("duel.prevent-inventory-open", true);
		protectKitItems = configuration.getBoolean("duel.protect-kit-items", true);
		removeEmptyBottle = configuration.getBoolean("duel.remove-empty-bottle", true);
		preventTpToMatchPlayers = configuration.getBoolean("duel.prevent-teleport-to-match-players", true);
		forceAllowCombat = configuration.getBoolean("duel.force-allow-combat", true);
		cancelIfMoved = configuration.getBoolean("duel.cancel-if-moved", false);
		blacklistedWorlds = configuration.getStringList("duel.blacklisted-worlds");
		teleportToLastLocation = configuration.getBoolean("duel.teleport-to-last-location", false);
		teleportDelay = configuration.getInt("duel.teleport-delay", 5);
		spawnFirework = configuration.getBoolean("duel.spawn-firework", true);
		arenaOnlyEndMessage = configuration.getBoolean("duel.arena-only-end-message", false);
		displayInventories = configuration.getBoolean("duel.display-inventories", true);
		preventItemDrop = configuration.getBoolean("duel.prevent-item-drop", false);
		preventItemPickup = configuration.getBoolean("duel.prevent-item-pickup", true);
		limitTeleportEnabled = configuration.getBoolean("duel.limit-teleportation.enabled", true);
		distanceAllowed = configuration.getDouble("duel.limit-teleportation.distance-allowed", 5.0);
		blockAllCommands = configuration.getBoolean("duel.block-all-commands", false);
		whitelistedCommands = configuration.getStringList("duel.whitelisted-commands");
		blacklistedCommands = configuration.getStringList("duel.blacklisted-commands");
		isVictoryTitleEnabled = configuration.getBoolean("duel.enable-victory-title");

		queueBlacklistedCommands = configuration.getStringList("queue.blacklisted-commands");

		ratingEnabled = configuration.getBoolean("rating.enabled", true);
		kFactor = Math.max(configuration.getInt("rating.k-factor", 32), 1);
		defaultRating = Math.max(configuration.getInt("rating.default-rating", 1400), 0);
		ratingQueueOnly = configuration.getBoolean("rating.queue-matches-only", true);

		specRequiresClearedInventory = configuration.getBoolean("spectate.requires-cleared-inventory", false);
		specUseSpectatorGamemode = configuration.getBoolean("spectate.use-spectator-gamemode", false);
		specAddInvisibilityEffect = configuration.getBoolean("spectate.add-invisibility-effect", true);
		specWhitelistedCommands = configuration.getStringList("spectate.whitelisted-commands");

		cdEnabled = configuration.getBoolean("countdown.enabled", true);
		cdMessages = configuration.getStringList("countdown.messages");
		titles = configuration.getStringList("countdown.titles");
		preventMovement = configuration.getBoolean("countdown.prevent.movement", true);
		preventLaunchProjectile = configuration.getBoolean("countdown.prevent.launch-projectile", true);
		preventPvp = configuration.getBoolean("countdown.prevent.pvp", true);
		preventInteract = configuration.getBoolean("countdown.prevent.interact", true);

		displayKitRatings = configuration.getBoolean("stats.display-kit-ratings", true);
		displayNoKitRating = configuration.getBoolean("stats.display-nokit-rating", false);
		displayPastMatches = configuration.getBoolean("stats.display-past-matches", true);
		matchesToDisplay = Math.max(configuration.getInt("stats.matches-to-display", 10), 0);

		topUpdateInterval = Math.max(configuration.getInt("top.update-interval", 5), 1) * 60L * 1000L;
		topWinsType = configuration.getString("top.displayed-replacers.wins.type", "Wins");
		topWinsIdentifier = configuration.getString("top.displayed-replacers.wins.identifier", "wins");
		topLossesType = configuration.getString("top.displayed-replacers.losses.type", "Losses");
		topLossesIdentifier = configuration.getString("top.displayed-replacers.losses.identifier", "losses");
		topKitType = configuration.getString("top.displayed-replacers.kit.type", "%kit%");
		topKitIdentifier = configuration.getString("top.displayed-replacers.kit.identifier", "rating");
		topNoKitType = configuration.getString("top.displayed-replacers.no-kit.type", "No Kit");
		topNoKitIdentifier = configuration.getString("top.displayed-replacers.no-kit.identifier", "rating");

		kitSelectorRows = Math.min(Math.max(configuration.getInt("guis.kit-selector.rows", 2), 1), 5);
		kitSelectorFillerType = configuration.getString("guis.kit-selector.space-filler-item.type", "STAINED_GLASS_PANE");
		kitSelectorFillerData = (short) configuration.getInt("guis.kit-selector.space-filler-item.data", 0);
		arenaSelectorRows = Math.min(Math.max(configuration.getInt("guis.arena-selector.rows", 3), 1), 5);
		arenaSelectorFillerType = configuration.getString("guis.arena-selector.space-filler-item.type", "STAINED_GLASS_PANE");
		arenaSelectorFillerData = (short) configuration.getInt("guis.arena-selector.space-filler-item.data", 0);
		settingsFillerType = configuration.getString("guis.settings.space-filler-item.type", "STAINED_GLASS_PANE");
		settingsFillerData = (short) configuration.getInt("guis.settings.space-filler-item.data", 0);
		queuesRows = Math.min(Math.max(configuration.getInt("guis.queues.rows", 3), 1), 5);
		queuesFillerType = configuration.getString("guis.queues.space-filler-item.type", "STAINED_GLASS_PANE");
		queuesFillerData = (short) configuration.getInt("guis.queues.space-filler-item.data", 0);
		inheritKitItemType = configuration.getBoolean("guis.queues.inherit-kit-item-type", true);

		soupHeartsToRegen = Math.max(configuration.getDouble("soup.hearts-to-regen", 3.5), 0);
		soupRemoveEmptyBowl = configuration.getBoolean("soup.remove-empty-bowl", true);
		soupCancelIfAlreadyFull = configuration.getBoolean("soup.cancel-if-already-full", true);

		final ConfigurationSection sounds = configuration.getConfigurationSection("sounds");

		if (sounds != null) {
			for (final String name : sounds.getKeys(false)) {
				final ConfigurationSection sound = sounds.getConfigurationSection(name);
				final Sound type = EnumUtil.getByName(sound.getString("type"), Sound.class);

				if (type == null) {
					continue;
				}

				this.sounds.put(name, new MessageSound(type, sound.getDouble("pitch"), sound.getDouble("volume"), sound.getStringList("trigger-messages")));
			}
		}
	}

	public void playSound(final Player player, final String message) {
		sounds.values().stream()
		.filter(sound -> sound.getMessages().contains(message))
		.forEach(sound -> player.playSound(player.getLocation(), sound.getType(), sound.getVolume(), sound.getPitch()));
	}

	public MessageSound getSound(final String name) {
		return sounds.get(name);
	}

	public Set<String> getSounds() {
		return sounds.keySet();
	}

	public class MessageSound {

		private final Sound type;
		private final float pitch;
		private final float volume;
		private final List<String> messages;

		MessageSound(final Sound type, final double pitch, final double volume, final List<String> messages) {
			this.type = type;
			this.pitch = (float) pitch;
			this.volume = (float) volume;
			this.messages = messages;
		}

		public Sound getType() {
			return type;
		}

		public float getPitch() {
			return pitch;
		}

		public float getVolume() {
			return volume;
		}

		public List<String> getMessages() {
			return messages;
		}

	}

	public String getVersion() {
		return version;
	}

	public boolean isCtpPreventDuel() {
		return ctpPreventDuel;
	}

	public boolean isCtpPreventTag() {
		return ctpPreventTag;
	}

	public boolean isPmPreventDuel() {
		return pmPreventDuel;
	}

	public boolean isPmPreventTag() {
		return pmPreventTag;
	}

	public boolean isClxPreventDuel() {
		return clxPreventDuel;
	}

	public boolean isClxPreventTag() {
		return clxPreventTag;
	}

	public boolean isAutoUnvanish() {
		return autoUnvanish;
	}

	public boolean isSetBackLocation() {
		return setBackLocation;
	}

	public boolean isDisableSkills() {
		return disableSkills;
	}

	public boolean isFuNoPowerLoss() {
		return fuNoPowerLoss;
	}

	public boolean isfNoPowerLoss() {
		return fNoPowerLoss;
	}

	public boolean isDuelzoneEnabled() {
		return duelzoneEnabled;
	}

	public List<String> getDuelzones() {
		return duelzones;
	}

	public boolean isMyPetDespawn() {
		return myPetDespawn;
	}

	public boolean isPreventBountyLoss() {
		return preventBountyLoss;
	}

	public boolean isPreventKDRChange() {
		return preventKDRChange;
	}

	public String getLhWinsCmd() {
		return lhWinsCmd;
	}

	public String getLhWinsTitle() {
		return lhWinsTitle;
	}

	public String getLhLossesCmd() {
		return lhLossesCmd;
	}

	public String getLhLossesTitle() {
		return lhLossesTitle;
	}

	public boolean isRequiresClearedInventory() {
		return requiresClearedInventory;
	}

	public boolean isPreventCreativeMode() {
		return preventCreativeMode;
	}

	public boolean isOwnInventoryEnabled() {
		return ownInventoryEnabled;
	}

	public boolean isOwnInventoryDropInventoryItems() {
		return ownInventoryDropInventoryItems;
	}

	public boolean isOwnInventoryUsePermission() {
		return ownInventoryUsePermission;
	}

	public boolean isKitSelectingEnabled() {
		return kitSelectingEnabled;
	}

	public boolean isKitSelectingUsePermission() {
		return kitSelectingUsePermission;
	}

	public boolean isArenaSelectingEnabled() {
		return arenaSelectingEnabled;
	}

	public boolean isArenaSelectingUsePermission() {
		return arenaSelectingUsePermission;
	}

	public boolean isItemBettingEnabled() {
		return itemBettingEnabled;
	}

	public boolean isItemBettingUsePermission() {
		return itemBettingUsePermission;
	}

	public boolean isMoneyBettingEnabled() {
		return moneyBettingEnabled;
	}

	public boolean isMoneyBettingUsePermission() {
		return moneyBettingUsePermission;
	}

	public int getExpiration() {
		return expiration;
	}

	public int getMaxDuration() {
		return maxDuration;
	}

	public boolean isStartCommandsEnabled() {
		return startCommandsEnabled;
	}

	public boolean isStartCommandsQueueOnly() {
		return startCommandsQueueOnly;
	}

	public List<String> getStartCommands() {
		return startCommands;
	}

	public boolean isEndCommandsEnabled() {
		return endCommandsEnabled;
	}

	public boolean isEndCommandsQueueOnly() {
		return endCommandsQueueOnly;
	}

	public List<String> getEndCommands() {
		return endCommands;
	}

	public boolean isProjectileHitMessageEnabled() {
		return projectileHitMessageEnabled;
	}

	public List<String> getProjectileHitMessageTypes() {
		return projectileHitMessageTypes;
	}

	public boolean isPreventInventoryOpen() {
		return preventInventoryOpen;
	}

	public boolean isProtectKitItems() {
		return protectKitItems;
	}

	public boolean isRemoveEmptyBottle() {
		return removeEmptyBottle;
	}

	public boolean isPreventTpToMatchPlayers() {
		return preventTpToMatchPlayers;
	}

	public boolean isForceAllowCombat() {
		return forceAllowCombat;
	}

	public boolean isCancelIfMoved() {
		return cancelIfMoved;
	}

	public List<String> getBlacklistedWorlds() {
		return blacklistedWorlds;
	}

	public boolean isTeleportToLastLocation() {
		return teleportToLastLocation;
	}

	public int getTeleportDelay() {
		return teleportDelay;
	}

	public boolean isSpawnFirework() {
		return spawnFirework;
	}

	public boolean isArenaOnlyEndMessage() {
		return arenaOnlyEndMessage;
	}

	public boolean isDisplayInventories() {
		return displayInventories;
	}

	public boolean isPreventItemDrop() {
		return preventItemDrop;
	}

	public boolean isPreventItemPickup() {
		return preventItemPickup;
	}

	public boolean isLimitTeleportEnabled() {
		return limitTeleportEnabled;
	}

	public double getDistanceAllowed() {
		return distanceAllowed;
	}

	public boolean isBlockAllCommands() {
		return blockAllCommands;
	}

	public List<String> getWhitelistedCommands() {
		return whitelistedCommands;
	}

	public List<String> getBlacklistedCommands() {
		return blacklistedCommands;
	}

	public boolean isVictoryTitleEnabled() {
		return isVictoryTitleEnabled;
	}

	public List<String> getQueueBlacklistedCommands() {
		return queueBlacklistedCommands;
	}

	public boolean isRatingEnabled() {
		return ratingEnabled;
	}

	public int getKFactor() {
		return kFactor;
	}

	public int getDefaultRating() {
		return defaultRating;
	}

	public boolean isRatingQueueOnly() {
		return ratingQueueOnly;
	}

	public boolean isSpecRequiresClearedInventory() {
		return specRequiresClearedInventory;
	}

	public boolean isSpecUseSpectatorGamemode() {
		return specUseSpectatorGamemode;
	}

	public boolean isSpecAddInvisibilityEffect() {
		return specAddInvisibilityEffect;
	}

	public List<String> getSpecWhitelistedCommands() {
		return specWhitelistedCommands;
	}

	public boolean isCdEnabled() {
		return cdEnabled;
	}

	public List<String> getCdMessages() {
		return cdMessages;
	}

	public List<String> getTitles() {
		return titles;
	}

	public boolean isPreventMovement() {
		return preventMovement;
	}

	public boolean isPreventLaunchProjectile() {
		return preventLaunchProjectile;
	}

	public boolean isPreventPvp() {
		return preventPvp;
	}

	public boolean isPreventInteract() {
		return preventInteract;
	}

	public boolean isDisplayKitRatings() {
		return displayKitRatings;
	}

	public boolean isDisplayNoKitRating() {
		return displayNoKitRating;
	}

	public boolean isDisplayPastMatches() {
		return displayPastMatches;
	}

	public boolean isFNoPowerLoss() {
		return fNoPowerLoss;
	}

	public int getMatchesToDisplay() {
		return matchesToDisplay;
	}

	public long getTopUpdateInterval() {
		return topUpdateInterval;
	}

	public String getTopWinsType() {
		return topWinsType;
	}

	public String getTopWinsIdentifier() {
		return topWinsIdentifier;
	}

	public String getTopLossesType() {
		return topLossesType;
	}

	public String getTopLossesIdentifier() {
		return topLossesIdentifier;
	}

	public String getTopKitType() {
		return topKitType;
	}

	public String getTopKitIdentifier() {
		return topKitIdentifier;
	}

	public String getTopNoKitType() {
		return topNoKitType;
	}

	public String getTopNoKitIdentifier() {
		return topNoKitIdentifier;
	}

	public int getKitSelectorRows() {
		return kitSelectorRows;
	}

	public String getKitSelectorFillerType() {
		return kitSelectorFillerType;
	}

	public short getKitSelectorFillerData() {
		return kitSelectorFillerData;
	}

	public int getArenaSelectorRows() {
		return arenaSelectorRows;
	}

	public String getArenaSelectorFillerType() {
		return arenaSelectorFillerType;
	}

	public short getArenaSelectorFillerData() {
		return arenaSelectorFillerData;
	}

	public String getSettingsFillerType() {
		return settingsFillerType;
	}

	public short getSettingsFillerData() {
		return settingsFillerData;
	}

	public int getQueuesRows() {
		return queuesRows;
	}

	public String getQueuesFillerType() {
		return queuesFillerType;
	}

	public short getQueuesFillerData() {
		return queuesFillerData;
	}

	public boolean isInheritKitItemType() {
		return inheritKitItemType;
	}

	public double getSoupHeartsToRegen() {
		return soupHeartsToRegen;
	}

	public boolean isSoupRemoveEmptyBowl() {
		return soupRemoveEmptyBowl;
	}

	public boolean isSoupCancelIfAlreadyFull() {
		return soupCancelIfAlreadyFull;
	}

}