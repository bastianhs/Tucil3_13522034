import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

class GUI extends JFrame implements ActionListener {

    private final JLabel chooseFileLabel;
    private final JButton chooseFileButton;
    private File selectedFile;
    private final JTextField startWordField;
    private final JTextField endWordField;
    private final JComboBox<String> algorithmComboBox;
    private final JButton searchButton;
    private final JLabel numberOfNodesVisited;
    private final JLabel executionTime;
    private final JPanel resultPanel;
    private final String baseTextNodesVisited;
    private final String baseTextExecutionTime;

    public GUI() {
        setVisible(true);
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Word Ladder Solver");
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new GridLayout(1, 2));

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 1));
        add(inputPanel);

        JPanel filePanel = new JPanel();
        inputPanel.add(filePanel);

        chooseFileLabel = new JLabel("No Dictionary File Selected");
        chooseFileLabel.setPreferredSize(new Dimension(250, 30));
        chooseFileLabel.setHorizontalAlignment(JLabel.CENTER);
        chooseFileLabel.setVerticalAlignment(JLabel.BOTTOM);
        chooseFileLabel.setOpaque(true);
        filePanel.add(chooseFileLabel);

        chooseFileButton = new JButton("Choose File (.txt)");
        chooseFileButton.setPreferredSize(new Dimension(150, 30));
        chooseFileButton.addActionListener(this);
        filePanel.add(chooseFileButton);

        JPanel startWordPanel = new JPanel();
        inputPanel.add(startWordPanel);

        JLabel startWordLabel = new JLabel("Start Word:");
        startWordLabel.setPreferredSize(new Dimension(250, 30));
        startWordLabel.setHorizontalAlignment(JLabel.CENTER);
        startWordLabel.setVerticalAlignment(JLabel.BOTTOM);
        startWordPanel.add(startWordLabel);

        startWordField = new JTextField();
        startWordField.setPreferredSize(new Dimension(200, 30));
        startWordPanel.add(startWordField);

        JPanel endWordPanel = new JPanel();
        inputPanel.add(endWordPanel);

        JLabel endWordLabel = new JLabel("End Word:");
        endWordLabel.setPreferredSize(new Dimension(250, 30));
        endWordLabel.setHorizontalAlignment(JLabel.CENTER);
        endWordLabel.setVerticalAlignment(JLabel.BOTTOM);
        endWordPanel.add(endWordLabel);

        endWordField = new JTextField();
        endWordField.setPreferredSize(new Dimension(200, 30));
        endWordPanel.add(endWordField);

        JPanel algorithmPanel = new JPanel();
        inputPanel.add(algorithmPanel);

        JLabel algorithmLabel = new JLabel("Algorithm:");
        algorithmLabel.setPreferredSize(new Dimension(250, 30));
        algorithmLabel.setHorizontalAlignment(JLabel.CENTER);
        algorithmLabel.setVerticalAlignment(JLabel.BOTTOM);
        algorithmPanel.add(algorithmLabel);

        algorithmComboBox = new JComboBox<>(new String[]{"Uniform Cost Search", "Greedy Best-First Search", "A*"});
        algorithmComboBox.setPreferredSize(new Dimension(200, 30));
        algorithmPanel.add(algorithmComboBox);

        JPanel searchPanel = new JPanel();
        inputPanel.add(searchPanel);

        searchButton = new JButton("Search");
        searchButton.setPreferredSize(new Dimension(100, 30));
        searchButton.addActionListener(this);
        searchPanel.add(searchButton);

        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new BoxLayout(outputPanel, BoxLayout.Y_AXIS));
        add(outputPanel);

        baseTextNodesVisited = "Number of Nodes Visited: ";
        numberOfNodesVisited = new JLabel(baseTextNodesVisited);
        numberOfNodesVisited.setAlignmentX(Component.CENTER_ALIGNMENT);
        numberOfNodesVisited.setPreferredSize(new Dimension(0, 50));
        numberOfNodesVisited.setFont(new Font("Arial", Font.PLAIN, 16));
        outputPanel.add(numberOfNodesVisited);

        baseTextExecutionTime = "Execution Time: ";
        executionTime = new JLabel(baseTextExecutionTime);
        executionTime.setAlignmentX(Component.CENTER_ALIGNMENT);
        executionTime.setPreferredSize(new Dimension(0, 50));
        executionTime.setFont(new Font("Arial", Font.PLAIN, 16));
        outputPanel.add(executionTime);

        JLabel pathLabel = new JLabel("Path: ");
        pathLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        pathLabel.setPreferredSize(new Dimension(0, 30));
        pathLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        outputPanel.add(pathLabel);

        resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));

        JScrollPane resultScroll = new JScrollPane(resultPanel);
        resultScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        resultScroll.setAlignmentX(Component.CENTER_ALIGNMENT);
        outputPanel.add(resultScroll);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        try {
            if (event.getSource() == chooseFileButton) {
                getDictionaryFile();
            } else if (event.getSource() == searchButton) {
                search();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            JOptionPane.showMessageDialog(this, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void getDictionaryFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("test"));
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.getName().toLowerCase().endsWith(".txt") || f.isDirectory();
            }

            @Override
            public String getDescription() {
                return "Text Files (*.txt)";
            }
        });
        int response = fileChooser.showOpenDialog(this);
        if (response == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            chooseFileLabel.setText(selectedFile.getName());
        }
    }

    private void search() throws IOException {
        resultPanel.removeAll();
        resultPanel.revalidate();
        resultPanel.repaint();
        resultPanel.paintImmediately(resultPanel.getBounds());

        if (selectedFile == null) {
            throw new FileNotFoundException("No dictionary file selected");
        }
        WordList wordList = new WordList(selectedFile);

        String startWord = startWordField.getText().toLowerCase();
        if (wordList.isWordNotExist(startWord)) {
            throw new NoSuchElementException("Start word does not exist in the dictionary");
        }

        String endWord = endWordField.getText().toLowerCase();
        if (wordList.isWordNotExist(endWord)) {
            throw new NoSuchElementException("End word does not exist in the dictionary");
        }

        if (startWord.length() != endWord.length()) {
            throw new IllegalArgumentException("Start word and end word length must be the same");
        }

        String loadAndGenerateWordListMessage = "Loading and generating word list";
        System.out.println(loadAndGenerateWordListMessage);

        wordList.generateWordToWordsDifferBy1Char(startWord.length());
        String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();

        List<String> result;
        int numOfNodesVisited;

        String searchingStartedMessage = "Searching has started";
        System.out.println(searchingStartedMessage);

        long startTime = System.nanoTime();

        switch (Objects.requireNonNull(selectedAlgorithm)) {
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
                throw new IllegalArgumentException("Invalid algorithm selection");
        }

        long endTime = System.nanoTime();
        long timeDifference = endTime - startTime;

        String searchingEndedMessage = "Searching has ended";
        System.out.println(searchingEndedMessage);

        System.out.println(baseTextNodesVisited + numOfNodesVisited);
        System.out.println(baseTextExecutionTime + timeDifference / 1000000 + " ms");
        System.out.println("Path: " + result);

        System.out.println("--------------------------------------------------");

        numberOfNodesVisited.setText(baseTextNodesVisited + numOfNodesVisited);
        executionTime.setText(baseTextExecutionTime + timeDifference / 1000000 + " ms");

        for (String word : result) {
            JLabel wordLabel = new JLabel(word);
            wordLabel.setText(word);
            wordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            resultPanel.add(wordLabel);
        }
    }
}
