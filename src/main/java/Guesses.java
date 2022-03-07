import java.util.EnumMap;
import java.util.Set;

public class Guesses {

    private final int index;
    private final SetMultimap<Guess, Character> guesses = new SetMultimap<>(new EnumMap<>(Guess.class));

    public Guesses(final int index) {
        this.index = index;
    }

    public Set<Character> getCharsOfGuess(final Guess guess) {
        return this.guesses.get(guess);
    }

    public void addCharGuess(final Guess guess, final Character character) {
        if (guess == Guess.NO && this.guesses.get(Guess.EXACT).contains(character)) return;
        this.guesses.put(guess, character);
    }

    public SetMultimap<Guess, Character> getGuesses() {
        return guesses;
    }
}
