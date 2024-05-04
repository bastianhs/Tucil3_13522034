import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class WordList {
    private final List<String> words;
    private List<String> wordsWithLengthN;

    WordList(String path) throws IOException {
        words = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line;
        while ((line = reader.readLine()) != null) {
            words.add(line.toLowerCase());
        }

        reader.close();
    }

    static int numOfCharDifference(String word1, String word2) throws IllegalArgumentException {
        if (word1.length() != word2.length()) {
            throw new IllegalArgumentException("Both words must have the same length");
        }

        int count = 0;
        for (int i = 0; i < word1.length(); ++i) {
            if (word1.charAt(i) != word2.charAt(i)) {
                ++count;
            }
        }

        return count;
    }

    static boolean isWordsDifferBy1Char(String word1, String word2) throws IllegalArgumentException {
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

    private void generateWordsWithSameLength(String word) {
        if (wordsWithLengthN == null) {
            wordsWithLengthN = new ArrayList<>();
            for (String eachWord: words) {
                if (eachWord.length() == word.length()) {
                    wordsWithLengthN.add(eachWord);
                }
            }
        }
    }

    List<String> getWordsDifferBy1Char(String word) {
        generateWordsWithSameLength(word);
        List<String> result = new ArrayList<>();
        for (String eachWord: wordsWithLengthN) {
            if (isWordsDifferBy1Char(word, eachWord)) {
                result.add(eachWord);
            }
        }

        return result;
    }

    boolean isWordNotExist(String word) {
        return !words.contains(word);
    }
}
