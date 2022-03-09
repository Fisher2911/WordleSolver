import java.util.EnumMap;
import java.util.Objects;
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

    @Override
    public String toString() {
        return "Guesses{" +
                "index=" + index +
                ", guesses=" + guesses +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Guesses guesses1 = (Guesses) o;
        return index == guesses1.index && Objects.equals(guesses, guesses1.guesses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, guesses);
    }
}
