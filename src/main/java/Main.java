import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final String FILE_NAME = "words.txt";
    private static final Dictionary dictionary;
    private static final List<String> ALL_WORDS;

    private static WordHistory wordHistory;

    static {
        List<String> temp;
        try {
            // word list from wordle
            temp = Files.readAllLines(new File(Main.class.getResource(FILE_NAME).getPath()).toPath());
        } catch (IOException e) {
            temp = new ArrayList<>();
        }
        ALL_WORDS = temp;
        dictionary = new Dictionary(ALL_WORDS.stream().map(Word::new).collect(Collectors.toSet()));
    }

    public static void main(String[] args) {
        dictionary.getBestStartWords(1);
        one();
    }

    // xylyl test
    // brine
    private static void one() {
        System.out.println(dictionary.getBestStartWords(5));
        final char[] chars = new char[]{'x', 'y', 'l', 'y', 'l'};
        final Guesses zero = new Guesses(0);
        final Guesses one = new Guesses(1);
        final Guesses two = new Guesses(2);
        final Guesses three = new Guesses(3);
        final Guesses four = new Guesses(4);

        zero.addCharGuess(Guess.NO, 'x');
        one.addCharGuess(Guess.NO, 'y');
        two.addCharGuess(Guess.NO, 'l');
        three.addCharGuess(Guess.NO, 'y');
        four.addCharGuess(Guess.NO, 'l');
        final Map<Integer, Guesses> guessesMap = new HashMap<>();
        guessesMap.put(0, zero);
        guessesMap.put(1, one);
        guessesMap.put(2, two);
        guessesMap.put(3, three);
        guessesMap.put(4, four);
        for (int i = 0; i < 5; i++) {
            guessesMap.get(i).addCharGuess(Guess.NO, 'x');
            guessesMap.get(i).addCharGuess(Guess.NO, 'y');
            guessesMap.get(i).addCharGuess(Guess.NO, 'l');
        }
        final List<Character> contains = new ArrayList<>();
        final WordGuesses wordGuesses = new WordGuesses(guessesMap, contains, 5);
        final GuessWord guessWord = new GuessWord(chars, wordGuesses);
        final List<Word> guessWords = dictionary.getTopLikely(guessWord, -1);
        System.out.println(guessWords);
        two(guessWord);
    }

    private static void two(final GuessWord other) {
        System.out.println(dictionary.getBestStartWords(5));
        final char[] chars = new char[]{'a', 'r', 'o', 's', 'e'};
        final Guesses zero = new Guesses(0);
        final Guesses one = new Guesses(1);
        final Guesses two = new Guesses(2);
        final Guesses three = new Guesses(3);
        final Guesses four = new Guesses(4);

        zero.addCharGuess(Guess.NO, 'a');
        one.addCharGuess(Guess.EXACT, 'r');
        two.addCharGuess(Guess.NO, 'o');
        three.addCharGuess(Guess.NO, 's');
        four.addCharGuess(Guess.EXACT, 'e');
        final Map<Integer, Guesses> guessesMap = new HashMap<>();
        guessesMap.put(0, zero);
        guessesMap.put(1, one);
        guessesMap.put(2, two);
        guessesMap.put(3, three);
        guessesMap.put(4, four);
        for (int i = 0; i < 5; i++) {
            guessesMap.get(i).addCharGuess(Guess.NO, 'a');
            guessesMap.get(i).addCharGuess(Guess.NO, 'o');
            guessesMap.get(i).addCharGuess(Guess.NO, 's');
        }
        final List<Character> contains = new ArrayList<>();
        final WordGuesses wordGuesses = new WordGuesses(guessesMap, contains, 5);
        final GuessWord guessWord = new GuessWord(chars, wordGuesses);
        guessWord.addGuess(other);
        final List<Word> guessWords = dictionary.getTopLikely(guessWord, -1);
        System.out.println(guessWords);
        three(guessWord);
    }

    private static void three(final GuessWord other) {
        System.out.println(dictionary.getBestStartWords(5));
        final char[] chars = new char[]{'t', 'r', 'i', 'p', 'e'};
        final Guesses zero = new Guesses(0);
        final Guesses one = new Guesses(1);
        final Guesses two = new Guesses(2);
        final Guesses three = new Guesses(3);
        final Guesses four = new Guesses(4);

        zero.addCharGuess(Guess.NO, 't');
        one.addCharGuess(Guess.EXACT, 'r');
        two.addCharGuess(Guess.EXACT, 'i');
        three.addCharGuess(Guess.NO, 'p');
        four.addCharGuess(Guess.EXACT, 'e');
        final Map<Integer, Guesses> guessesMap = new HashMap<>();
        guessesMap.put(0, zero);
        guessesMap.put(1, one);
        guessesMap.put(2, two);
        guessesMap.put(3, three);
        guessesMap.put(4, four);
        for (int i = 0; i < 5; i++) {
            guessesMap.get(i).addCharGuess(Guess.NO, 't');
            guessesMap.get(i).addCharGuess(Guess.NO, 'p');
        }
        final List<Character> contains = new ArrayList<>();
        final WordGuesses wordGuesses = new WordGuesses(guessesMap, contains, 5);
        final GuessWord guessWord = new GuessWord(chars, wordGuesses);
        guessWord.addGuess(other);
        final List<Word> guessWords = dictionary.getTopLikely(guessWord, -1);
        System.out.println(guessWords);
//        three(guessWord);
    }
}
