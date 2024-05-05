// import java.util.List;
// import java.util.Scanner;
//
// public class Main {
//     public static void main(String[] args) {
//         try {
//             final String dictionaryFolder = "test";
//
//             Scanner scanner = new Scanner(System.in);
//
//             System.out.print("Enter dictionary path (from test folder): ");
//             String pathFromTest = scanner.nextLine();
//
//             WordList wordList = new WordList(dictionaryFolder + "/" + pathFromTest);
//
//             System.out.print("Enter start word: ");
//             String startWord = scanner.nextLine().toLowerCase();
//
//             if (wordList.isWordNotExist(startWord)) {
//                 throw new Exception("Start word does not exist in the dictionary");
//             }
//
//             System.out.print("Enter end word: ");
//             String endWord = scanner.nextLine().toLowerCase();
//
//             if (wordList.isWordNotExist(endWord)) {
//                 throw new Exception("End word does not exist in the dictionary");
//             }
//
//             if (startWord.length() != endWord.length()) {
//                 throw new Exception("Start word and end word length must be the same");
//             }
//
//             System.out.println("Choose the algorithm: ");
//             System.out.println("1. Uniform Cost Search");
//             System.out.println("2. Greedy Best First Search");
//             System.out.println("3. A*");
//             System.out.print("Enter your choice (1-3): ");
//             String choice = scanner.nextLine();
//
//             List<String> result;
//             int numOfNodesVisited;
//
//             long startTime = System.nanoTime();
//
//             switch (choice) {
//                 case "1":
//                     UniformCostSearch uniformCostSearch = new UniformCostSearch();
//                     result = uniformCostSearch.getPath(startWord, endWord, wordList);
//                     numOfNodesVisited = uniformCostSearch.getNumOfNodesVisited();
//                     break;
//                 case "2":
//                     GreedyBestFirstSearch greedyBestFirstSearch = new GreedyBestFirstSearch();
//                     result = greedyBestFirstSearch.getPath(startWord, endWord, wordList);
//                     numOfNodesVisited = greedyBestFirstSearch.getNumOfNodesVisited();
//                     break;
//                 case "3":
//                     AStar aStar = new AStar();
//                     result = aStar.getPath(startWord, endWord, wordList);
//                     numOfNodesVisited = aStar.getNumOfNodesVisited();
//                     break;
//                 default:
//                     throw new Exception("Invalid choice");
//             }
//
//             long endTime = System.nanoTime();
//
//             System.out.print("Result: ");
//             System.out.println(result);
//             System.out.print("Number of nodes visited: ");
//             System.out.println(numOfNodesVisited);
//             System.out.println("Time taken: " + (endTime - startTime) / 1000000 + " ms");
//
//         } catch (Exception e) {
//             // e.printStackTrace();
//             System.err.println(e.getMessage());
//         }
//     }
// }

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }
}

class GUI extends JFrame {
    private JTextField dictionaryPathField;
    private JTextField startWordField;
    private JTextField endWordField;
    private JLabel resultLabel;
    private JLabel nodesVisitedLabel;
    private JLabel executionTimeLabel;
    private JButton executeButton;
    private JComboBox<String> algorithmComboBox;

    public GUI() {
        createView();

        setTitle("Word Search GUI");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void createView() {
        JPanel panel = new JPanel();
        getContentPane().add(panel);

        panel.setLayout(new GridLayout(8, 2));

        panel.add(new JLabel("Dictionary Path: "));
        dictionaryPathField = new JTextField();
        panel.add(dictionaryPathField);

        panel.add(new JLabel("Start Word: "));
        startWordField = new JTextField();
        panel.add(startWordField);

        panel.add(new JLabel("End Word: "));
        endWordField = new JTextField();
        panel.add(endWordField);

        panel.add(new JLabel("Algorithm: "));
        String[] algorithms = {"Uniform Cost Search", "Greedy Best-First Search", "A*"};
        algorithmComboBox = new JComboBox<>(algorithms);
        panel.add(algorithmComboBox);

        executeButton = new JButton("Execute");
        panel.add(executeButton);
        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeSearch();
            }
        });

        panel.add(new JLabel("Result: "));
        resultLabel = new JLabel();
        panel.add(resultLabel);

        panel.add(new JLabel("Number of Nodes Visited: "));
        nodesVisitedLabel = new JLabel();
        panel.add(nodesVisitedLabel);

        panel.add(new JLabel("Execution Time: "));
        executionTimeLabel = new JLabel();
        panel.add(executionTimeLabel);
    }

    private void executeSearch() {
        try {
            final String dictionaryFolder = "test";
            String pathFromTest = dictionaryPathField.getText();
            WordList wordList = new WordList(dictionaryFolder + "/" + pathFromTest);

            String startWord = startWordField.getText().toLowerCase();
            if (wordList.isWordNotExist(startWord)) {
                throw new Exception("Start word does not exist in the dictionary");
            }

            String endWord = endWordField.getText().toLowerCase();
            if (wordList.isWordNotExist(endWord)) {
                throw new Exception("End word does not exist in the dictionary");
            }

            if (startWord.length() != endWord.length()) {
                throw new Exception("Start word and end word length must be the same");
            }

            String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();
            List<String> result;
            int numOfNodesVisited;
            long startTime = System.nanoTime();

            switch (selectedAlgorithm) {
                case "Uniform Cost Search":
                    UniformCostSearch uniformCostSearch = new UniformCostSearch();
                    result = uniformCostSearch.getPath(startWord, endWord, wordList);
                    numOfNodesVisited = uniformCostSearch.getNumOfNodesVisited();
                    break;
                case "Greedy Best-First Search":
                    GreedyBestFirstSearch greedyBestFirstSearch = new GreedyBestFirstSearch();
                    result = greedyBestFirstSearch.getPath(startWord, endWord, wordList);
                    numOfNodesVisited = greedyBestFirstSearch.getNumOfNodesVisited();
                    break;
                case "A*":
                    AStar aStar = new AStar();
                    result = aStar.getPath(startWord, endWord, wordList);
                    numOfNodesVisited = aStar.getNumOfNodesVisited();
                    break;
                default:
                    throw new Exception("Invalid algorithm selection");
            }

            long endTime = System.nanoTime();

            resultLabel.setText(result.toString());
            nodesVisitedLabel.setText(String.valueOf(numOfNodesVisited));
            executionTimeLabel.setText((endTime - startTime) / 1000000 + " ms");
        } catch (Exception e) {
            // e.printStackTrace();
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
