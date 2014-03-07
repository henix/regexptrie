package henix.regexptrie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Regexp::Trie in Java
 *
 * inspired by:
 * - Regexp::Trie in Perl
 * - [Regexp::Trie in Python](https://gist.github.com/fcicq/3394647)
 *
 * Instead of building an entire trie, a different approach is used here:
 *
 * sort all strings by 1st char to obtain the 1st char of the trie, then by the next char, ...
 *
 * I think this approach can archive less memory usage with no or very little performance penalty.
 *
 * n = number of strings
 *
 * space: O(n), time: O(nlogn * maxlen)
 */
public final class RegexpTrie {

	private static class CharAtComparator implements Comparator<String> {
		private final int n;
		public CharAtComparator(int n) {
			this.n = n;
		}
		@Override
		public int compare(String s1, String s2) {
			final int len1 = s1.length();
			final int len2 = s2.length();
			if (len1 > n) {
				if (len2 > n) {
					return s1.charAt(n) - s2.charAt(n);
				} else {
					return 1;
				}
			} else {
				if (len2 > n) {
					return -1;
				} else {
					return 0;
				}
			}
		}
	}

	private static void buildRegexInDepth(List<String> strs, int depth, StringBuilder out) {
		final int size = strs.size();
		if (size == 0) {
			return;
		} else if (size == 1) {
			final String t = strs.get(0);
			if (depth < t.length()) {
				PatternUtils.escape(t.substring(depth), out);
			}
			return;
		}

		Collections.sort(strs, new CharAtComparator(depth));
		int i = 0;
		// find the first string with length > depth
		while (i < size && strs.get(i).length() <= depth) {
			i++;
		}
		// not fount, exit
		if (i >= size) {
			return;
		}
		final boolean hasEmpty = i > 0;
		final boolean allSame = strs.get(i).charAt(depth) == strs.get(size-1).charAt(depth);
		if (!allSame || hasEmpty) {
			out.append("(?:");
		}
		if (allSame) {
			PatternUtils.escape(strs.get(i).charAt(depth), out);
			if (i > 0) {
				buildRegexInDepth(strs.subList(i, size), depth + 1, out);
			} else {
				buildRegexInDepth(strs, depth + 1, out);
			}
		} else {
			// using `charAt(depth)` to split strs into sections
			boolean first = true;
			while (i < size) {
				final int start = i;
				final char ch = strs.get(i).charAt(depth);
				while (i < size && strs.get(i).charAt(depth) == ch) {
					i++;
				}
				final int end = i;
				if (first) {
					first = false;
				} else {
					out.append('|');
				}
				PatternUtils.escape(ch, out);
				buildRegexInDepth(strs.subList(start, end), depth + 1, out);
			}
		}
		if (!allSame | hasEmpty) {
			out.append(')');
		}
		if (hasEmpty) {
			out.append('?');
		}
	}

	public static String buildRegex(Iterable<String> strs) {
		final ArrayList<String> ar = new ArrayList<String>();
		for (final String s : strs) {
			ar.add(s);
		}
		final StringBuilder sb = new StringBuilder();
		buildRegexInDepth(ar, 0, sb);
		return sb.toString();
	}

	public static String buildRegex(String... strs) {
		final ArrayList<String> ar = new ArrayList<String>();
		for (final String s : strs) {
			ar.add(s);
		}
		final StringBuilder sb = new StringBuilder();
		buildRegexInDepth(ar, 0, sb);
		return sb.toString();
	}
}
