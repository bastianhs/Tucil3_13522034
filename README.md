# Word Ladder Solver

## What is This?
This program is a solver for the [Word Ladder](https://en.wikipedia.org/wiki/Word_ladder) game.  
This solver uses the Uniform Cost Search, Greedy Best-First Search, or A* algorithm.  
You can choose one of the three algorithms.

The default dictionary used in this program is located in **test/dictionary.txt**.  
This is the dictionary.txt [source](https://docs.oracle.com/javase/tutorial/collections/interfaces/examples/dictionary.txt).  
You can add your own dictionary file, but it must be in .txt format.

## Requirements and Installation
This program is written in Java, so you need to have Java Development Kit (JDK) installed on your computer.  

This program is compiled using OpenJDK JDK 22.0.1, so it is recommended to use the same version or newer.

You can download OpenJDK JDK 22.0.1 [here](https://jdk.java.net/22/).  
Tutorial on how to install OpenJDK JDK can be found [here](https://www.freecodecamp.org/news/install-openjdk-free-java-multi-os-guide/).

## How to Compile and Run
1. Clone the repository
   ```
   git clone https://github.com/bastianhs/Tucil3_13522034.git
   ```
2. Change directory to the repository's main directory (Tucil3_13522034)
   ```
   cd Tucil3_13522034
   ```
3. (Optional) Compile all .java files in **src/directory** to **bin/directory**  
   The .class files is already included in this repository, so you can skip this step.  
   If you want to recompile all .java files, follow these steps:  
   1. (Optional) Delete all .class files in **bin/directory**
      ```
      rm -r bin/*
      ```
   2. Compile all .java files
      ```
      javac -d bin src/*.java
      ```
4. Run the **Main.class** file in **bin/directory**
   ```
   java -cp bin Main
   ```

## How to Use
1. Choose the dictionary file by clicking the **Choose File (.txt)** button.
2. Input the start word.
3. Input the end word.
4. Choose the algorithm.
5. Click the **Search** button.
6. The result will be displayed on the right side.

## Author
Bastian H. Suryapratama  
13522034  
K02
