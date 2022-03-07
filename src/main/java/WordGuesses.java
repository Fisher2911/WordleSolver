import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WordGuesses {

    private final int length;
    private final Map<Integer, Guesses> guessesMap;
    private final List<Character> contains;

    public WordGuesses(final Map<Integer, Guesses> guessesMap, final List<Character> contains, final int length) {
        this.length = length;
        this.guessesMap = guessesMap;
        this.contains = contains;
        for (int i = 0; i < this.length; i++) {
            this.guessesMap.putIfAbsent(i, new Guesses(i));
        }
    }

    public boolean isExact(final char c, final int index) {
        return this.guessesMap.get(index).getCharsOfGuess(Guess.EXACT).contains(c);
    }

    public boolean requiresExact(final int index) {
        return !this.guessesMap.get(index).getCharsOfGuess(Guess.EXACT).isEmpty();
    }

    public boolean contains(final char c) {
        return this.contains.contains(c);
    }

    public boolean containsAll(final List<Character> chars) {
        return chars.containsAll(this.contains);
    }

    public boolean isNo(final char c, final int index) {
        return this.guessesMap.get(index).getCharsOfGuess(Guess.NO).contains(c);
    }

    public List<Character> getCharsOfGuess(final Guess guess) {
        final List<Character> chars = new ArrayList<>();
        for (int i = 0; i < this.length; i++) {
            chars.addAll(this.guessesMap.get(i).getCharsOfGuess(guess));
        }
        return chars;
    }

    public Guesses get(final int index) {
        return this.guessesMap.get(index);
    }

    public void add(final WordGuesses wordGuesses) {
        for (int i = 0; i < wordGuesses.length; i++) {
            final Guesses guesses = wordGuesses.get(i);
            final Guesses thisGuesses = this.get(i);
            for (final Guess guess : Guess.values()) {
                for (final char c : guesses.getCharsOfGuess(guess)) {
                    switch (guess) {
                        case EXACT -> {
                            this.contains.remove((Character) c);
                            thisGuesses.addCharGuess(guess, c);
                        }
                        case NO -> thisGuesses.addCharGuess(guess, c);
                    }
                }
            }
        }
    }

    public List<Character> getContains() {
        return this.contains;
    }
}