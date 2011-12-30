package be.rottenrei.android.lib.util;

/**
 * Utility functions for working with Strings and CharSequences.
 */
public class StringUtils {

	public static String concatWithSeparator(CharSequence separator, CharSequence... charSequences) {
		StringBuilder builder = new StringBuilder();
		boolean first = true;
		for (CharSequence sequence : charSequences) {
			if (sequence == null) {
				continue;
			}
			String trimmed = sequence.toString().trim();
			if (trimmed.isEmpty()) {
				continue;
			}
			if (!first) {
				builder.append(separator);
			}
			builder.append(trimmed);
			first = false;
		}
		return builder.toString();
	}

}
