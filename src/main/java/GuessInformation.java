import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuessInformation {

    private final char[] chars;
    private final Guess[] guesses;

    public GuessInformation(final char[] chars, final Guess[] guesses) {
        this.chars = chars;
        this.guesses = guesses;
    }

    public GuessInformation(final String chars, final Guess... guesses) {
        this.chars = chars.toCharArray();
        this.guesses = guesses;
    }

    public GuessWord toGuessWord() {
        final Map<Integer, Guesses> guessesMap = new HashMap<>();
        for (int i = 0; i < this.chars.length; i++) {
            final Guesses guesses = new Guesses(i);
            Guess guess = this.guesses[i];
            if (guess == Guess.CONTAINS) guess = Guess.NO;
            guesses.addCharGuess(guess, this.chars[i]);
            guessesMap.put(i, guesses);
        }
        final List<Character> contains = new ArrayList<>();
        final List<Character> no = new ArrayList<>();
        for (int i = 0; i < this.chars.length; i++) {
            final Guesses guesses = guessesMap.get(i);
            final Guess guess = this.guesses[i];
            final char c = this.chars[i];
            if (guess == Guess.NO) {
                no.add(c);
                guesses.addCharGuess(guess, c);
            }
            if (guess == Guess.CONTAINS) contains.add(c);
        }
        for (int i = 0; i < this.chars.length; i++) {
            final Guesses guesses = guessesMap.get(i);
            for (final char c : no) {
                guesses.addCharGuess(Guess.NO, c);
            }
        }
        final WordGuesses wordGuesses = new WordGuesses(guessesMap, contains, this.chars.length);
        return new GuessWord(chars, wordGuesses);
    }


    //    private final Map<Integer, Character> correctPositions;
//    private final List<Character> contains;
//    private final SetMultimap<Integer, Character> incorrectSpots;
//    private final Set<Character> wrongCharacters;
//
//    public GuessInformation(final Map<Integer, Character> correctPositions, final List<Character> contains, final SetMultimap<Integer, Character> incorrectSpots) {
//        this.correctPositions = correctPositions;
//        this.contains = contains;
//        this.incorrectSpots = incorrectSpots;
//        this.wrongCharacters = new HashSet<>();
//        for (final var entry : incorrectSpots.entries()) {
//            for (final char c : entry.getValue()) {
//                if (contains.contains(c) || this.correctPositions.get(entry.getKey()) == c) continue;
//                this.wrongCharacters.add(c);
//            }
//        }
//    }
}
