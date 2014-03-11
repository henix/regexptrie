# henix.regexptrie

Builds trie-ized regexp

## Example

```java
RegexpTrie.buildRegex("foobar", "foobah", "foozap", "fooza") // => "foo(?:ba(?:h|r)|za(?:p)?)"
```

## Build and Run

```bash
mvn compile
mvn exec:java -Dexec.mainClass=henix.regexptrie.Main
```

## Inspired by

* Regexp::Trie in Perl
* [Regexp::Trie in Python](https://gist.github.com/fcicq/3394647)
