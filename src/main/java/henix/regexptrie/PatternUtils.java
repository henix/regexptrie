package henix.regexptrie;

import java.util.BitSet;

public final class PatternUtils {

	public static final String metaChars = "\\^$.()[]{}<>?*+|=:";

	private static final int METACHAR_LEN = 128;
	private static final BitSet isMetaChar = new BitSet(METACHAR_LEN);

	static {
		final int len = metaChars.length();
		for (int i = 0; i < len; i++) {
			isMetaChar.set(metaChars.charAt(i));
		}
	}

	/**
	 * For performance, append result to out
	 */
	public static void escape(char ch, StringBuilder out) {
		if (ch < METACHAR_LEN && isMetaChar.get(ch)) {
			out.append('\\');
		}
		out.append(ch);
	}

	public static void escape(String s, StringBuilder out) {
		final int len = s.length();
		for (int i = 0; i < len; i++) {
			escape(s.charAt(i), out);
		}
	}
}
