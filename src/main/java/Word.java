import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Word {

    private final char[] chars;
    private final Map<Character, Integer> charOccurrences;

    public Word(final String word) {
        this(word.toCharArray());
    }

    public Word(final char[] chars) {
        this.chars = chars;
        this.charOccurrences = this.occurrences(chars);
    }

    public char[] getChars() {
        return this.chars;
    }

    public Map<Character, Integer> getCharOccurrences() {
        return charOccurrences;
    }

    private Map<Character, Integer> occurrences(final char[] chars) {
        final Map<Character, Integer> charMap = new HashMap<>();
        for (final char c : chars) {
            Integer current = charMap.get(c);
            if (current == null) current = 0;
            charMap.put(c, current + 1);
        }

        return charMap;
    }

    public int count(final char count) {
        int sum = 0;
        for (char c : this.chars) {
            if (c == count) sum++;
        }
        return sum;
    }

    public boolean contains(final char check) {
        return this.count(check) >= 1;
    }

    public boolean containsAll(List<Character> list) {
        return this.toList().containsAll(list);
    }

    public List<Character> toList() {
        final List<Character> list = new ArrayList<>();
        for (final char c : this.chars) {
            list.add(c);
        }
        return list;
    }

    @Override
    public String toString() {
        return new String(this.chars);
    }
}
