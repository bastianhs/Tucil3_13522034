import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class AStar extends Searching {
    @Override
    List<String> getPath(String startWord, String endWord, WordList wordList) {
        currentExpandNode = new NodeAStar(startWord, new ArrayList<>(), endWord);
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
            // System.out.println("Current word:");
            // System.out.println(currentExpandNode.getCurrentWord());
            // System.out.println("Previous words:");
            // System.out.println(currentExpandNode.getPreviousWord());

            List<String> wordsDifferBy1Char = wordList.getWordsDifferBy1Char(currentExpandNode.getCurrentWord());
            wordsDifferBy1Char = wordsDifferBy1Char.stream().filter(this::isWordNotVisited).toList();

            // print the wordsDifferBy1Char
            // System.out.println("Words differ by 1 char:");
            // System.out.println(wordsDifferBy1Char);

            for (String eachWord: wordsDifferBy1Char) {
                List<String> newPreviousWords = new ArrayList<>(currentExpandNode.getPreviousWord());
                newPreviousWords.add(currentExpandNode.getCurrentWord());
                NodeAStar newNode = new NodeAStar(eachWord, newPreviousWords, endWord);
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
        List<String> result = new ArrayList<>(currentExpandNode.getPreviousWord());
        result.add(currentExpandNode.getCurrentWord());
        return result;
    }
}
