import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuessWord {

//    private static final int EXACT_SCORE = 200;
//    private static final int MATCH_SCORE = 100;
    private static final int DUPLICATE_SCORE = -1000;

    private final Map<Word, Integer> wordScores = new HashMap<>();
    private final int size;
    private final char[] chars;
    private final WordGuesses wordGuesses;

    public GuessWord(final int size) {
        this.size = size;
        this.chars = new char[size];
        this.wordGuesses = new WordGuesses(new HashMap<>(), new ArrayList<>(), this.size);
    }

    public GuessWord(final char[] chars, final WordGuesses wordGuesses) {
        this.chars = chars;
        this.size = this.chars.length;
        this.wordGuesses = wordGuesses;
    }

    public char charAt(final int index) {
        return this.chars[index];
    }

    public boolean isPossible(final Word word) {
        final char[] wordChars = word.getChars();
        final List<Character> checkContained = new ArrayList<>();
        for (int i = 0; i < wordChars.length; i++) {
            final char wordChar = wordChars[i];
            if (this.wordGuesses.requiresExact(i)) {
                if (!this.wordGuesses.isExact(wordChar, i)) return false;
            }
            if (this.wordGuesses.isNo(wordChar, i)) return false;
            checkContained.add(wordChar);
        }
        return this.wordGuesses.containsAll(checkContained);
    }

    public void addGuess(final GuessWord guessWord) {
        final WordGuesses wordGuesses = guessWord.wordGuesses;
        this.wordGuesses.add(wordGuesses);

    }

    public List<Integer> findExactMatching(final Word word) {
        final List<Integer> matching = new ArrayList<>();
        final char[] wordChars = word.getChars();
        for (int i = 0; i < wordChars.length; i++) {
            final char c = wordChars[i];
            if (this.charAt(i) == c) matching.add(i);
        }
        return matching;
    }

    public int score(final Word word, final List<Character> scores, final int doubleIndex) {
        final Integer cached = this.wordScores.get(word);
        if (cached != null) return cached;
        final List<Integer> exact = this.findExactMatching(word);
        final List<Integer> matching = this.findExactMatching(word);

        matching.removeAll(exact);
        int score = 0;
        final List<Character> used = new ArrayList<>();
        final char[] chars = word.getChars();
        for (int i = 0; i < chars.length; i++) {
            final char c = chars[i];
            if (used.contains(c)) score += DUPLICATE_SCORE;
            if (exact.contains(i) || matching.contains(i)) {
                used.add(c);
                continue;
            }
            if (used.contains(c)) continue;
            used.add(c);
            final int index = scores.indexOf(c);
            final int size = scores.size();
            int add = size - scores.indexOf(c);
            if (doubleIndex >= index) add *= 2;
            score += add;
        }
        final int found = score;
        this.wordScores.put(word, found);
        return found;
    }

    public char[] getChars() {
        return chars;
    }

    public WordGuesses getWordGuesses() {
        return wordGuesses;
    }

    @Override
    public String toString() {
        return "GuessWord{" +
                "size=" + size +
                ", chars=" + Arrays.toString(chars) +
                ", wordGuesses=" + wordGuesses +
                '}';
    }

}