package henix.regexptrie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws IOException {
		// Java 1.7 try-with-resources
		try (
			final BufferedReader in = new BufferedReader(new InputStreamReader(System.in))
		) {
			final ArrayList<String> lines = new ArrayList<>();
			while (true) {
				final String line = in.readLine();
				if (line == null) {
					break;
				}
				lines.add(line);
			}
			System.out.println(RegexpTrie.buildRegex(lines));
		}
	}
}
