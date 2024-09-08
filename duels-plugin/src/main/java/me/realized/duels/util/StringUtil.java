package me.realized.duels.util;

import java.util.Collection;
import java.util.List;
import java.util.TreeMap;
import java.util.regex.Pattern;

import org.bukkit.Location;

import me.realized.duels.util.reflect.ReflectionUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public final class StringUtil {

	private static final Pattern ALPHANUMERIC = Pattern.compile("^[a-zA-Z0-9_]+$");
	private static final TreeMap<Integer, String> ROMAN_NUMERALS = new TreeMap<>();
	private static final boolean COMMONS_LANG3;

	static {
		ROMAN_NUMERALS.put(1000, "M");
		ROMAN_NUMERALS.put(900, "CM");
		ROMAN_NUMERALS.put(500, "D");
		ROMAN_NUMERALS.put(400, "CD");
		ROMAN_NUMERALS.put(100, "C");
		ROMAN_NUMERALS.put(90, "XC");
		ROMAN_NUMERALS.put(50, "L");
		ROMAN_NUMERALS.put(40, "XL");
		ROMAN_NUMERALS.put(10, "X");
		ROMAN_NUMERALS.put(9, "IX");
		ROMAN_NUMERALS.put(5, "V");
		ROMAN_NUMERALS.put(4, "IV");
		ROMAN_NUMERALS.put(1, "I");
		COMMONS_LANG3 = ReflectionUtil.getClassUnsafe("org.apache.commons.lang3.StringUtils") != null;
	}

	private StringUtil() {}

	public static String toRoman(final int number) {
		if (number <= 0) {
			return String.valueOf(number);
		}

		int key = ROMAN_NUMERALS.floorKey(number);

		if (number == key) {
			return ROMAN_NUMERALS.get(number);
		}

		return ROMAN_NUMERALS.get(key) + toRoman(number - key);
	}

	public static String fromList(final List<?> list) {
		StringBuilder builder = new StringBuilder();

		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				builder.append(list.get(i).toString()).append(i + 1 != list.size() ? "\n" : "");
			}
		}

		return builder.toString();
	}

	public static String parse(final Location location) {
		return "(" + location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ() + ")";
	}

	// Converts a string with legacy color codes into a Component
	public static Component toComponent(final String input) {
		return LegacyComponentSerializer.legacyAmpersand().deserialize(input);
	}

	// Converts a list of strings with legacy color codes into a list of Components
	public static List<Component> toComponents(final List<String> input) {
		return input.stream()
				.map(StringUtil::toComponent)
				.toList();
	}

	// Converts a Component back to a string with legacy color codes
	public static String fromComponent(final Component component) {
		return LegacyComponentSerializer.legacyAmpersand().serialize(component);
	}

	// Converts a list of Components back to a list of strings with legacy color codes
	public static List<String> fromComponents(final List<Component> components) {
		return components.stream()
				.map(StringUtil::fromComponent)
				.toList();
	}

	public static String color(final String input) {
		return fromComponent(toComponent(input));
	}

	public static List<String> color(final List<String> input) {
		return input.stream().map(StringUtil::color).toList();
	}

	public static boolean isAlphanumeric(final String input) {
		return ALPHANUMERIC.matcher(input.replace(" ", "")).matches();
	}

	// In some versions of Spigot, commons-lang3 is not available.
	public static String join(final Object[] array, final String separator, final int startIndex, final int endIndex) {
		if (COMMONS_LANG3) {
			return org.apache.commons.lang3.StringUtils.join(array, separator, startIndex, endIndex);
		} else {
			return org.apache.commons.lang.StringUtils.join(array, separator, startIndex, endIndex);
		}
	}

	public static String join(final Collection<?> collection, final String separator) {
		if (COMMONS_LANG3) {
			return org.apache.commons.lang3.StringUtils.join(collection, separator);
		} else {
			return org.apache.commons.lang.StringUtils.join(collection, separator);
		}
	}

	public static String capitalize(final String s) {
		if (COMMONS_LANG3) {
			return org.apache.commons.lang3.StringUtils.capitalize(s);
		} else {
			return org.apache.commons.lang.StringUtils.capitalize(s);
		}
	}

	public static boolean containsIgnoreCase(final String str, final String searchStr) {
		if (COMMONS_LANG3) {
			return org.apache.commons.lang3.StringUtils.containsIgnoreCase(str, searchStr);
		} else {
			return org.apache.commons.lang.StringUtils.containsIgnoreCase(str, searchStr);
		}
	}
}
