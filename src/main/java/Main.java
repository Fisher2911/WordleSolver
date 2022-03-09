import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final String WORDLE_FILE_NAME = "wordle-words.txt";
    private static final String ALL_FILE_NAME = "all-words.txt";
    private static final Dictionary dictionary;
    private static final List<String> WORDLE_WORDS;
    private static final List<String> ALL_WORDS;

    private static TestWords testWords;

    static {
        List<String> wordleTemp;
        List<String> allTemp;
        try {
            // word list from wordle
            wordleTemp = Files.readAllLines(new File(Main.class.getResource(WORDLE_FILE_NAME).getPath()).toPath());
            allTemp = Files.readAllLines(new File(Main.class.getResource(ALL_FILE_NAME).getPath()).toPath());
        } catch (IOException e) {
            wordleTemp = new ArrayList<>();
            allTemp = new ArrayList<>();
        }
        WORDLE_WORDS = wordleTemp;
        ALL_WORDS = allTemp;
        dictionary = new Dictionary(
                WORDLE_WORDS.stream().map(Word::new).collect(Collectors.toSet()),
                ALL_WORDS.stream().map(Word::new).collect(Collectors.toSet())
        );
    }

    private static final ExecutorService SERVICE = Executors.newFixedThreadPool(3);

    public static void main(String[] args) {
//        for (int i = 0; i < 26; i++) {
//            final int finalI = i;
//            /*SERVICE.submit(() -> */new TestWords(-1).testAll(dictionary)/*)*/;
//        }
//        final Word start = new Word("trape");
//        final Word correct = new Word("sweet");
//        System.out.println("Testing word: " + start);
//        System.out.println(new TestWord(correct, start).guess(dictionary, -1));
//        one();

    }

    // xylyl test
    // brine
    private static void one() {
        final char[] chars = new char[]{'a', 'r', 'o', 's', 'e'};
        final Guesses zero = new Guesses(0);
        final Guesses one = new Guesses(1);
        final Guesses two = new Guesses(2);
        final Guesses three = new Guesses(3);
        final Guesses four = new Guesses(4);

        zero.addCharGuess(Guess.NO, 'a');
        one.addCharGuess(Guess.NO, 'r');
        two.addCharGuess(Guess.EXACT, 'o');
        three.addCharGuess(Guess.NO, 's');
        four.addCharGuess(Guess.NO, 'e');
        final Map<Integer, Guesses> guessesMap = new HashMap<>();
        guessesMap.put(0, zero);
        guessesMap.put(1, one);
        guessesMap.put(2, two);
        guessesMap.put(3, three);
        guessesMap.put(4, four);
        for (int i = 0; i < 5; i++) {
            guessesMap.get(i).addCharGuess(Guess.NO, 'a');
            guessesMap.get(i).addCharGuess(Guess.NO, 'r');
            guessesMap.get(i).addCharGuess(Guess.NO, 's');
            guessesMap.get(i).addCharGuess(Guess.NO, 'e');
        }
        final List<Character> contains = new ArrayList<>();
        final WordGuesses wordGuesses = new WordGuesses(guessesMap, contains, 5);
        final GuessWord guessWord = new GuessWord(chars, wordGuesses);
        final List<Word> guessWords = dictionary.getTopLikely(guessWord, -1);
//        two(guessWord);
    }

    private static void two(final GuessWord other) {
        final char[] chars = new char[]{'g', 'o', 'r', 'g', 'e'};
        final Guesses zero = new Guesses(0);
        final Guesses one = new Guesses(1);
        final Guesses two = new Guesses(2);
        final Guesses three = new Guesses(3);
        final Guesses four = new Guesses(4);

        zero.addCharGuess(Guess.NO, 'g');
        one.addCharGuess(Guess.EXACT, 'o');
        two.addCharGuess(Guess.NO, 'r');
        three.addCharGuess(Guess.NO, 'g');
        four.addCharGuess(Guess.NO, 'e');
        final Map<Integer, Guesses> guessesMap = new HashMap<>();
        guessesMap.put(0, zero);
        guessesMap.put(1, one);
        guessesMap.put(2, two);
        guessesMap.put(3, three);
        guessesMap.put(4, four);
        for (int i = 0; i < 5; i++) {
            guessesMap.get(i).addCharGuess(Guess.NO, 'g');
            guessesMap.get(i).addCharGuess(Guess.NO, 'e');
        }
        final List<Character> contains = new ArrayList<>();
        contains.add('r');
        final WordGuesses wordGuesses = new WordGuesses(guessesMap, contains, 5);
        final GuessWord guessWord = new GuessWord(chars, wordGuesses);
        guessWord.addGuess(other);
        final List<Word> guessWords = dictionary.getTopLikely(guessWord, -1);
        System.out.println(guessWords);
        three(guessWord);
    }

    private static void three(final GuessWord other) {
        final char[] chars = new char[]{'r', 'o', 'a', 'c', 'h'};
        final Guesses zero = new Guesses(0);
        final Guesses one = new Guesses(1);
        final Guesses two = new Guesses(2);
        final Guesses three = new Guesses(3);
        final Guesses four = new Guesses(4);

        zero.addCharGuess(Guess.NO, 'r');
        one.addCharGuess(Guess.EXACT, 'o');
        two.addCharGuess(Guess.EXACT, 'a');
        three.addCharGuess(Guess.NO, 'c');
        four.addCharGuess(Guess.NO, 'h');
        final Map<Integer, Guesses> guessesMap = new HashMap<>();
        guessesMap.put(0, zero);
        guessesMap.put(1, one);
        guessesMap.put(2, two);
        guessesMap.put(3, three);
        guessesMap.put(4, four);
        for (int i = 0; i < 5; i++) {
            guessesMap.get(i).addCharGuess(Guess.NO, 'c');
        }
        final List<Character> contains = new ArrayList<>();
        contains.add('r');
        contains.add('h');
        final WordGuesses wordGuesses = new WordGuesses(guessesMap, contains, 5);
        final GuessWord guessWord = new GuessWord(chars, wordGuesses);
        guessWord.addGuess(other);
        final List<Word> guessWords = dictionary.getTopLikely(guessWord, -1);
        System.out.println(guessWords);
//        three(guessWord);
    }
}
