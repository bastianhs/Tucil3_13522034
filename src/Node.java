import java.util.List;

abstract class Node implements Comparable<Node> {
    final String currentWord;
    final List<String> previousWords;

    Node(String currentWord, List<String> previousWords) {
        this.currentWord = currentWord;
        this.previousWords = previousWords;
    }

    String getCurrentWord() {
        return currentWord;
    }

    List<String> getPreviousWords() {
        return previousWords;
    }

    static int numOfCharDifference(String word1, String word2) {
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
}

class NodeUCS extends Node {
    NodeUCS(String currentWord, List<String> previousWords) {
        super(currentWord, previousWords);
    }

    @Override
    public int compareTo(Node otherNode) {
        int comparisonResult = Integer.compare(previousWords.size(), otherNode.previousWords.size());
        if (comparisonResult != 0) {
            return comparisonResult;
        }
        return currentWord.compareTo(otherNode.currentWord);
    }
}

class NodeGBFS extends Node {
    private final int diffFromCurrentToEndWord;

    NodeGBFS(String currentWord, List<String> previousWords, String endWord) {
        super(currentWord, previousWords);
        diffFromCurrentToEndWord = numOfCharDifference(currentWord, endWord);
    }

    @Override
    public int compareTo(Node otherNode) {
        int comparisonResult = Integer.compare(diffFromCurrentToEndWord, ((NodeGBFS) otherNode).diffFromCurrentToEndWord);
        if (comparisonResult != 0) {
            return comparisonResult;
        }
        return currentWord.compareTo(otherNode.currentWord);
    }
}

class NodeAStar extends Node {
    private final int diffFromCurrentToEndWord;

    NodeAStar(String currentWord, List<String> previousWords, String endWord) {
        super(currentWord, previousWords);
        diffFromCurrentToEndWord = numOfCharDifference(currentWord, endWord);
    }

    @Override
    public int compareTo(Node otherNode) {
        int thisNodeCost = previousWords.size() + diffFromCurrentToEndWord;
        int otherNodeCost = otherNode.previousWords.size() + ((NodeAStar) otherNode).diffFromCurrentToEndWord;
        int comparisonResult = Integer.compare(thisNodeCost, otherNodeCost);
        if (comparisonResult != 0) {
            return comparisonResult;
        }
        return currentWord.compareTo(otherNode.currentWord);
    }
}
