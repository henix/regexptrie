package henix.regexptrie;

import org.junit.Test;
import static org.junit.Assert.*;

public class RegexpTrieTest {

	@Test
	public void a_abc() {
		assertEquals("a(?:bc)?", RegexpTrie.buildRegex("a", "abc"));
	}
}
