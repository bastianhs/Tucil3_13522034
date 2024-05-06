import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class GreedyBestFirstSearch extends Searching {
    @Override
    List<String> getPath(String startWord, String endWord, WordList wordList) {
        currentExpandNode = new NodeGBFS(startWord, new ArrayList<>(), endWord);
        previousExpandNodes = new ArrayList<>();
        lifeNodes = new PriorityQueue<>();
        numOfNodesVisited = 0;

        while (currentExpandNode != null) {
            ++numOfNodesVisited;
            if (currentExpandNode.getCurrentWord().equals(endWord)) {
                break;
            }

            // print the currentExpandNode
            // System.out.println("Expand Node");
            // System.out.print("Current word: ");
            // System.out.println(currentExpandNode.getCurrentWord());
            // System.out.print("Previous words: ");
            // System.out.println(currentExpandNode.getPreviousWords());

            List<String> wordsDifferBy1Char = wordList.getWordsDifferBy1Char(currentExpandNode.getCurrentWord());
            wordsDifferBy1Char = wordsDifferBy1Char.stream().filter(this::isWordNotVisited).toList();

            // print the wordsDifferBy1Char
            // System.out.print("Words differ by 1 char: ");
            // System.out.println(wordsDifferBy1Char);

            for (String eachWord: wordsDifferBy1Char) {
                List<String> newPreviousWords = new ArrayList<>(currentExpandNode.getPreviousWords());
                newPreviousWords.add(currentExpandNode.getCurrentWord());
                NodeGBFS newNode = new NodeGBFS(eachWord, newPreviousWords, endWord);
                lifeNodes.add(newNode);
            }

            // print the lifeNodes
            // System.out.print("Life nodes: ");
            // for (Node eachNode: lifeNodes) {
            //     System.out.print(eachNode.getCurrentWord() + " ");
            // }
            // System.out.println();

            // System.out.println();

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
