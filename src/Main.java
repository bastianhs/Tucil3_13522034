import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            final String dictionaryFolder = "test";

            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter dictionary path (from test folder): ");
            String pathFromTest = scanner.nextLine();

            WordList wordList = new WordList(dictionaryFolder + "/" + pathFromTest);

            System.out.print("Enter start word: ");
            String startWord = scanner.nextLine().toLowerCase();

            if (wordList.isWordNotExist(startWord)) {
                throw new Exception("Start word does not exist in the dictionary");
            }

            System.out.print("Enter end word: ");
            String endWord = scanner.nextLine().toLowerCase();

            if (wordList.isWordNotExist(endWord)) {
                throw new Exception("End word does not exist in the dictionary");
            }

            if (startWord.length() != endWord.length()) {
                throw new Exception("Start word and end word length must be the same");
            }

            System.out.println("Choose the algorithm: ");
            System.out.println("1. Uniform Cost Search");
            System.out.println("2. Greedy Best First Search");
            System.out.println("3. A*");
            System.out.print("Enter your choice (1-3): ");
            String choice = scanner.nextLine();

            scanner.close();

            List<String> result;
            int numOfNodesVisited;

            long startTime = System.nanoTime();

            switch (choice) {
                case "1":
                    UniformCostSearch uniformCostSearch = new UniformCostSearch();
                    result = uniformCostSearch.getPath(startWord, endWord, wordList);
                    numOfNodesVisited = uniformCostSearch.getNumOfNodesVisited();
                    break;
                case "2":
                    GreedyBestFirstSearch greedyBestFirstSearch = new GreedyBestFirstSearch();
                    result = greedyBestFirstSearch.getPath(startWord, endWord, wordList);
                    numOfNodesVisited = greedyBestFirstSearch.getNumOfNodesVisited();
                    break;
                case "3":
                    AStar aStar = new AStar();
                    result = aStar.getPath(startWord, endWord, wordList);
                    numOfNodesVisited = aStar.getNumOfNodesVisited();
                    break;
                default:
                    throw new Exception("Invalid choice");
            }

            long endTime = System.nanoTime();

            System.out.print("Result: ");
            System.out.println(result);
            System.out.print("Number of nodes visited: ");
            System.out.println(numOfNodesVisited);
            System.out.println("Time taken: " + (endTime - startTime) / 1000000 + " ms");

        } catch (Exception e) {
            // e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }
}
