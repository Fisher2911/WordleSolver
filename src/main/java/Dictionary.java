import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Dictionary {

    private final Set<Word> words;
    private final Map<Character, Integer> charOccurrences;

    public Dictionary(final Set<Word> words) {
        this.words = words;
        this.charOccurrences = this.findMostCommon();
    }

    private Map<Character, Integer> findMostCommon() {
        final Map<Character, Integer> chars = new HashMap<>();
        for (final Word word : this.words) {
            final var map = word.getCharOccurrences();
            for (final var entry : map.entrySet()) {
                final char c = entry.getKey();
                final int i = entry.getValue();
                int current = chars.getOrDefault(c, 0);
                chars.put(c, i + current);
            }
        }
        return chars;
    }

    private Map<Character, Integer> findCharScores() {
        final Map<Character, Integer> scores = new HashMap<>();
        final List<Character> sorted = this.getSortedChars();
        int i = 26;
        for (char c : sorted) {
            scores.put(c, i);
            i--;
        }
        return scores;
    }

    public Map<Character, Integer> getCharOccurrences() {
        return charOccurrences;
    }

    public Set<Word> getWords() {
        return words;
    }

    public List<Word> sortByScore(final GuessWord guessWord) {
        final List<Word> words = new ArrayList<>(this.words);
        final List<Character> common = this.getSortedChars(this.getCommonCharsWithGuess(guessWord));
        words.removeIf(word -> !guessWord.isPossible(word));
        words.sort((o1, o2) -> Integer.compare(guessWord.score(o2, common), guessWord.score(o1, common)));
        return words;
    }

    public Map<Character, Integer> getCommonCharsWithGuess(final GuessWord guessWord) {
        final WordGuesses wordGuesses = guessWord.getWordGuesses();
        final List<Character> chars = wordGuesses.getCharsOfGuess(Guess.EXACT);
        chars.addAll(wordGuesses.getContains());

        return this.getCommonCharactersWithOthers(chars);
    }

    private Map<Character, Integer> getCommonCharactersWithOthers(final List<Character> characters) {
        final Map<Character, Integer> map = new HashMap<>();
        for (final Word word : this.words) {
            if (!word.containsAll(characters)) continue;
            final List<Character> chars = word.toList();
            for (final char c : chars) {
                Integer i = map.get(c);
                if (i == null) i = 0;
                map.put(c, i + 1);
            }
        }
        return map;
    }

    public List<Word> getTopLikely(final GuessWord guessWord) {
        return this.getTopLikely(guessWord, -1);
    }

    public List<Word> getTopLikely(final GuessWord guessWord, final int top) {
        final List<Word> words = this.sortByScore(guessWord);
        if (top == -1) return words;
        final int size = words.size();
        return words.subList(0, size - top);
    }

    public List<Word> getBestStartWords(final int length) {
        final List<Character> topCharacters = new ArrayList<>();
        final List<Character> characters = this.getSortedChars();
        for (int i = 0; i < length; i++) {
            if (characters.size() <= i) break;
            topCharacters.add(characters.get(i));
        }
        final List<Word> topWords = new ArrayList<>();
        for (final Word word : this.words) {
            if (!word.containsAll(topCharacters)) continue;
            topWords.add(word);
        }
        return topWords;
    }

    private List<Character> getSortedChars() {
       return this.getSortedChars(this.charOccurrences);
    }

    private List<Character> getSortedChars(final Map<Character, Integer> map) {
        final List<Map.Entry<Character, Integer>> entries = new ArrayList<>(map.entrySet());
        entries.sort((o1, o2) -> Integer.compare(o2.getValue(), o1.getValue()));
        return entries.stream().map(Map.Entry::getKey).collect(Collectors.toList());
    }
}