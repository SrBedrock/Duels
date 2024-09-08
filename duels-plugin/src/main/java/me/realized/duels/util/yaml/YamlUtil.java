package me.realized.duels.util.yaml;

import java.util.Map;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public final class YamlUtil {

	private YamlUtil() {}

	public static String yamlDump(final Object object) {
		if (! (object instanceof Map)) {
			throw new IllegalArgumentException("Object must be a Map");
		}
		YamlConfiguration yamlConfiguration = new YamlConfiguration();
		yamlConfiguration.createSection("data", (Map<?, ?>) object);
		return yamlConfiguration.saveToString();
	}

	public static <T> T yamlLoad(final String yaml, Class<T> type) {
		YamlConfiguration yamlConfiguration = new YamlConfiguration();
		try {
			yamlConfiguration.loadFromString(yaml);
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
			return null;
		}
		return type.cast(yamlConfiguration.get("data"));
	}

	public static <T> T yamlLoadAs(final String yaml, final Class<T> type) {
		return yamlLoad(yaml, type);
	}

}