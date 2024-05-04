import java.util.List;
import java.util.PriorityQueue;

abstract class Searching {
    Node currentExpandNode;
    List<Node> previousExpandNodes;
    PriorityQueue<Node> lifeNodes;
    int numOfNodesVisited;

    boolean isWordNotVisited(String word) {
        for (Node eachNode: previousExpandNodes) {
            if (eachNode.getCurrentWord().equals(word)) {
                return false;
            }
        }

        return true;
    }

    abstract List<String> getPath(String startWord, String endWord, WordList wordList);

    int getNumOfNodesVisited() {
        return numOfNodesVisited;
    }
}
