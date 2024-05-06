import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class UniformCostSearch extends Searching {
    @Override
    List<String> getPath(String startWord, String endWord, WordList wordList) {
        currentExpandNode = new NodeUCS(startWord, new ArrayList<>());
        previousExpandNodes = new ArrayList<>();
        lifeNodes = new PriorityQueue<>();
        numOfNodesVisited = 0;

        while (currentExpandNode != null) {
            ++numOfNodesVisited;
            if (currentExpandNode.getCurrentWord().equals(endWord)) {
                break;
            }

            List<String> wordsDifferBy1Char = wordList.getWordsDifferBy1Char(currentExpandNode.getCurrentWord());
            wordsDifferBy1Char = wordsDifferBy1Char.stream().filter(this::isWordNotVisited).toList();

            for (String eachWord: wordsDifferBy1Char) {
                List<String> newPreviousWords = new ArrayList<>(currentExpandNode.getPreviousWords());
                newPreviousWords.add(currentExpandNode.getCurrentWord());
                NodeUCS newNode = new NodeUCS(eachWord, newPreviousWords);
                lifeNodes.add(newNode);
            }

            previousExpandNodes.add(currentExpandNode);
            currentExpandNode = lifeNodes.poll();
        }

        if (currentExpandNode == null) {
            return new ArrayList<>();
        }
        List<String> result = new ArrayList<>(currentExpandNode.getPreviousWords());
        result.add(currentExpandNode.getCurrentWord());
        return result;
    }
}
