import java.util.Arrays;
import java.util.List;

public class TestWord {

    private final Word word;
    private Word start;
    private GuessWord current;

    public TestWord(final Word word) {
        this.word = word;
    }

    public TestWord(final Word word, final Word start) {
        this.word = word;
        this.start = start;
    }

    public int guess(final Dictionary dictionary, final int doubleIndex) {
        return this.guess(dictionary, 1, doubleIndex);
    }

    public int guess(final Dictionary dictionary, final int totalGuesses, final int doubleIndex) {
        final List<Word> words;
        if (this.current == null) {
            words = dictionary.getBestStartWords(this.word.getChars().length);
        } else {
            words = dictionary.getTopLikely(this.current, doubleIndex);
        }
        if (words.isEmpty()) return -1;
        final Word first;
        if (start == null || totalGuesses > 1) {
             first = words.get(0);
        } else {
            first = this.start;
        }
        if (first.equals(word)) return totalGuesses;
        final char[] chars = new char[5];
        final Guess[] guesses = new Guess[5];

        final char[] wordChars = first.getChars();
        for (int i = 0; i < wordChars.length; i++) {
            final char c = wordChars[i];
            chars[i] = c;
            if (this.word.getChars()[i] == c) {
                guesses[i] = Guess.EXACT;
                continue;
            }
            if (this.word.contains(c)) {
                guesses[i] = Guess.CONTAINS;
                continue;
            }
            guesses[i] = Guess.NO;
        }
        final GuessWord guessWord = new GuessInformation(chars, guesses).toGuessWord();
        if (this.current != null) guessWord.addGuess(this.current);
        this.current = guessWord;
        return this.guess(dictionary, totalGuesses + 1, doubleIndex);
    }

//    public void test() {
//
//        final GuessInformation info = new GuessInformation(new char[]{'h', 'e', 'l', 'l', 'o'}, new Guess[]{Guess.EXACT, Guess.EXACT, Guess.EXACT, Guess.EXACT, Guess.EXACT});
//        System.out.println(info.toGuessWord());
//    }
}
