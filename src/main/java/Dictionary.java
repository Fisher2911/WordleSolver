import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Dictionary {

    private final Set<Word> wordleWords;
    private final Set<Word> allWords;
    private final Map<Character, Integer> charOccurrences;

    public Dictionary(final Set<Word> wordleWords, final Set<Word> allWords) {
        this.wordleWords = wordleWords;
        this.allWords = allWords;
        this.charOccurrences = this.findMostCommon();
    }

    private Map<Character, Integer> findMostCommon() {
        final Map<Character, Integer> chars = new HashMap<>();
        for (final Word word : this.wordleWords) {
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

    public Set<Word> getWordleWords() {
        return wordleWords;
    }

    public Set<Word> getAllWords() {
        return allWords;
    }

    public List<Word> sortByScore(final GuessWord guessWord, final int doubleIndex) {
        final List<Word> words = new ArrayList<>(this.wordleWords);
        final List<Character> common = this.getSortedChars(this.getCommonCharsWithGuess(guessWord));
        words.removeIf(word -> !guessWord.isPossible(word));
        words.sort((o1, o2) -> Integer.compare(guessWord.score(o2, common, doubleIndex), guessWord.score(o1, common, doubleIndex)));
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
        for (final Word word : this.wordleWords) {
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

    public List<Word> getTopLikely(final GuessWord guessWord, final int doubleIndex) {
        return this.getTopLikely(guessWord, -1, doubleIndex);
    }

    public List<Word> getTopLikely(final GuessWord guessWord, final int top, final int doubleIndex) {
        final List<Word> words = this.sortByScore(guessWord, doubleIndex);
        for (final Word word : words) {
            if (Arrays.equals(word.getChars(), guessWord.getChars())) throw new IllegalStateException(words + " : " + guessWord + " cannot be equal!");
        }
        if (top == -1) return words;
        final int size = words.size();
        final List<Word> list = words.subList(0, size - top);
        return list;
    }

    public List<Word> getBestStartWords(final int length) {
        final List<Character> topCharacters = new ArrayList<>();
        final List<Character> characters = this.getSortedChars();
        for (int i = 0; i < length; i++) {
            if (characters.size() <= i) break;
            topCharacters.add(characters.get(i));
        }
        final List<Word> topWords = new ArrayList<>();
        for (final Word word : this.wordleWords) {
            if (!word.containsAll(topCharacters)) continue;
            topWords.add(word);
        }
        if (topWords.isEmpty()) return this.getBestStartWords(length -1);
        topWords.sort((o1, o2) -> Integer.compare(this.scoreWord(o2, this.charOccurrences), this.scoreWord(o1, this.charOccurrences)));
        return topWords;
    }

    private int scoreWord(final Word word, final Map<Character, Integer> charOccurrences) {
        int sum = 0;
        for (char c : word.getChars()) {
            final Integer num = charOccurrences.get(c);
            if (num == null) continue;
            sum += num;
        }
        return sum;
    }

    public List<Character> getSortedChars() {
       return this.getSortedChars(this.charOccurrences);
    }

    private List<Character> getSortedChars(final Map<Character, Integer> map) {
        final List<Map.Entry<Character, Integer>> entries = new ArrayList<>(map.entrySet());
        entries.sort((o1, o2) -> Integer.compare(o2.getValue(), o1.getValue()));
        return entries.stream().map(Map.Entry::getKey).collect(Collectors.toList());
    }
}