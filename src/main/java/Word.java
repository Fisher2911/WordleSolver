import java.util.ArrayList;
import java.util.Arrays;
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

    public boolean containsAtLeast(List<Character> list, final int amount) {
        int totalMatching = 0;
        final List<Character> toList = this.toList();
        for (char c : list) {
            if (toList.contains(c)) totalMatching++;
        }
        return totalMatching >= amount;
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Word word = (Word) o;
        return Arrays.equals(chars, word.chars);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(chars);
    }
}
