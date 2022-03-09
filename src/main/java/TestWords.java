import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class TestWords {

    private final int doubleIndex;
    private final Map<Word, Float> words = new HashMap<>();
    private final Map<Word, Map<Integer, Integer>> wordGuessAmounts = new HashMap<>();

    public TestWords(final int doubleIndex) {
        this.doubleIndex = doubleIndex;
    }

    public void testAll(final Dictionary dictionary) {
        System.out.println("Calculating scores for double index: " + this.doubleIndex);
        final String AVERAGE_FILE_NAME = "src/main/resources/tests/averages-" + this.doubleIndex + ".txt";
        final String SCORES_FILE_NAME = "src/main/resources/tests/scores-" + this.doubleIndex + ".txt";
        final File averagesFile = new File(AVERAGE_FILE_NAME);
        final Path averagesPath = averagesFile.toPath();

        final File scoresFile = new File(SCORES_FILE_NAME);
        final Path scoresPath = scoresFile.toPath();

        final AtomicInteger x = new AtomicInteger();
            dictionary.getAllWords().parallelStream().forEach(word -> {
                final List<Integer> guesses = new ArrayList<>();
                for (final Word word2 : dictionary.getWordleWords()) {
                    final TestWord testWord = new TestWord(word2, word);
                    final int total = testWord.guess(dictionary, this.doubleIndex);
                    guesses.add(total);
                    this.addGuessAmount(word, total);
                }
                final float average = this.averageInts(guesses);
                this.words.put(word, average);
                final int getX = x.get();
                if (getX % 100 == 0 && getX != 0) System.out.println("Overall average is: " + this.averageFloats(new ArrayList<>(this.words.values())) + " after " + getX + " words.");

                System.out.println("Average for word # " + getX + ": " + word + " was " + average + " | double index is " + this.doubleIndex);
                x.addAndGet(1);
            });
        try {
            final File parentFile = averagesFile.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            if (!averagesFile.exists()) averagesFile.createNewFile();
            if (!scoresFile.exists()) scoresFile.createNewFile();
            for (final var entry : this.words.entrySet()) {
                Files.writeString(averagesPath, entry.getKey() + ":" + entry.getValue() + "\n", StandardCharsets.UTF_8, StandardOpenOption.APPEND);
            }
            for (final var entry : this.wordGuessAmounts.entrySet()) {
                final Word word = entry.getKey();
                final var map = entry.getValue();
                final List<Map.Entry<Integer, Integer>> list = map.entrySet().stream().sorted(Comparator.comparingInt(Map.Entry::getKey)).collect(Collectors.toList());
                final StringBuilder builder = new StringBuilder(word.toString()).append("=");
                int i = 0;
                final int size = list.size();
                for (final var l : list) {
                    builder.append(l.getKey()).append(":").append(l.getValue());
                    if (i < size - 1) {
                        builder.append("|");
                    }
                    i++;
                }
                builder.append("\n");
                Files.writeString(scoresPath, builder, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
            }
        } catch (final IOException exception) {
            exception.printStackTrace();
        }
    }

    private void addGuessAmount(final Word word, final int index) {
        Map<Integer, Integer> map = this.wordGuessAmounts.computeIfAbsent(word, k -> new HashMap<>());
        final int current = map.getOrDefault(index, 0);
        map.put(index, current + 1);
    }

    private float averageInts(final List<Integer> list) {
        int total = list.size();
        int sum = 0;
        for (final int i : list) {
            sum += i;
        }
        return (float) sum / total;
    }

    private float averageFloats(final List<Float> list) {
        int total = list.size();
        float sum = 0;
        for (final float i : list) {
            sum += i;
        }
        return sum / total;
    }
}
