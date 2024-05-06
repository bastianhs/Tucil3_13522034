import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

class WordList {
    private final List<String> words;
    private Map<String, List<String>> wordToWordsDifferBy1Char;

    WordList(String path) throws IOException {
        words = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line;
        while ((line = reader.readLine()) != null) {
            words.add(line.toLowerCase());
        }
        reader.close();
    }

    boolean isWordNotExist(String word) {
        return !words.contains(word);
    }

    private static boolean isWordsDifferBy1Char(String word1, String word2) {
        if (word1.length() != word2.length()) {
            throw new IllegalArgumentException("Both words must have the same length");
        }
        int count = 0;
        int i = 0;
        while (i < word1.length() && count <= 1) {
            if (word1.charAt(i) != word2.charAt(i)) {
                ++count;
            }

            ++i;
        }
        return count == 1;
    }

    private List<String> generateWordsWithSameLength(int wordLength) {
        return words.stream().filter(eachWord -> eachWord.length() == wordLength).toList();
    }

    void generateWordToWordsDifferBy1Char(int wordLength) {
        List<String> wordsWithSameLength = generateWordsWithSameLength(wordLength);
        wordToWordsDifferBy1Char = new HashMap<>();
        for (String key: wordsWithSameLength) {
            List<String> value = new ArrayList<>();
            for (String anotherWord: wordsWithSameLength) {
                if (isWordsDifferBy1Char(key, anotherWord)) {
                    value.add(anotherWord);
                }
            }
            wordToWordsDifferBy1Char.put(key, value);
        }
    }

    List<String> getWordsDifferBy1Char(String word) {
        if (wordToWordsDifferBy1Char == null) {
            throw new IllegalStateException("Words differ by 1 character must be generated first");
        }
        return wordToWordsDifferBy1Char.get(word);
    }
}
