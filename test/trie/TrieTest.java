package trie;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * Test program to verifu the validity of the trie implementation.
 * 
 * @author Alfonso Alhambra Morón
 */
public class TrieTest {

    /**
     * Retrieval tree test program.
     *
     * @param args The first argument is the commands file, separated by commas
     * with the format
     * "insert("word"),remove("word"),search("word"),startsWith("prefix")", the
     * second argument is the expected output, also separated by commas with the
     * format "true,false,true" in the same order as the search/startsWith
     * commands are executed. The third and last argument is the output file in
     * which the program will write the result of the tests.
     * @throws FileNotFoundException In case one of the input files cannot be
     * found.
     * @throws IOException In case there is a problem reading or writing files.
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        hardcodedTest();
        if (args.length >= 3) {
            customTest(args[0], args[1], args[2]);
        }
    }

    /**
     * Super basic test testing the trie implementation with a small set of
     * commands.
     */
    public static void hardcodedTest() {
        System.out.println("-------------------------------------------------------");
        System.out.println("Hardcoded test:");
        System.out.println("-------------------------------------------------------");
        Trie trie = new Trie();
        trie.insert("");
        System.out.println("Inserted \"\"");
        trie.insert("hola");
        System.out.println("Inserted \"hola\"");
        trie.insert("hola");
        System.out.println("Inserted \"hola\" again");
        trie.insert("hi");
        System.out.println("Inserted \"hi\"");
        trie.insert("bu");
        System.out.println("Inserted \"bu\"");
        trie.insert("bubu");
        System.out.println("Inserted \"bubu\"");
        trie.insert("ho");
        System.out.println("Inserted \"ho\"");
        trie.insert("");
        System.out.println("Inserted \"\" again");
        System.out.println("Search \"\":..........." + trie.search(""));
        System.out.println("Search \"h\":.........." + trie.search("h"));
        System.out.println("Search \"ho\":........." + trie.search("ho"));
        System.out.println("Search \"hol\":........" + trie.search("hol"));
        System.out.println("Search \"hola\":......." + trie.search("hola"));
        System.out.println("Search \"he\":........." + trie.search("he"));
        System.out.println("Search \"hel\":........" + trie.search("hel"));
        System.out.println("Search \"hela\":......." + trie.search("hela"));
        System.out.println("Search \"hi\":........." + trie.search("hi"));
        System.out.println("Search \"b\":.........." + trie.search("b"));
        System.out.println("Search \"bu\":........." + trie.search("bu"));
        trie.remove("bu");
        System.out.println("Removed \"bu\"");
        System.out.println("Search \"bu\":........." + trie.search("bu"));
        System.out.println("Search \"bub\":........" + trie.search("bub"));
        System.out.println("Search \"bubu\":......." + trie.search("bubu"));
        System.out.println("Search \"ho\":........." + trie.search("ho"));
        System.out.println("Search \"hu\":........." + trie.search("hu"));
        System.out.println("StartsWith \"\":......." + trie.search(""));
        System.out.println("StartsWith \"h\":......" + trie.startsWith("h"));
        System.out.println("StartsWith \"ho\":....." + trie.startsWith("ho"));
        System.out.println("StartsWith \"hol\":...." + trie.startsWith("hol"));
        System.out.println("StartsWith \"hola\":..." + trie.startsWith("hola"));
        System.out.println("StartsWith \"he\":....." + trie.startsWith("he"));
        System.out.println("StartsWith \"hel\":...." + trie.startsWith("hel"));
        System.out.println("StartsWith \"hela\":..." + trie.startsWith("hela"));
        System.out.println("StartsWith \"hi\":....." + trie.startsWith("hi"));
        System.out.println("StartsWith \"b\":......" + trie.startsWith("b"));
        System.out.println("StartsWith \"bu\":....." + trie.startsWith("bu"));
        System.out.println("StartsWith \"bub\":...." + trie.startsWith("bub"));
        System.out.println("StartsWith \"bubu\":..." + trie.startsWith("bubu"));
        System.out.println("StartsWith \"ho\":....." + trie.startsWith("ho"));
        System.out.println("StartsWith \"hu\":....." + trie.startsWith("hu"));
    }

    /**
     * Custom test to test the trie against a series of commands and expected
     * outputs read from the specified input files. This test stores the results
     * in the specified output file.
     *
     * @param commandsFilePath Path to the test commands file: A file separated
     * by commas with the format
     * "insert("word"),remove("word"),search("word"),startsWith("prefix")"
     * @param expectedOutputsFilePath Path to the expected output file: Also a
     * file separated by commas with the format "true,false,true" in the same
     * order as the search/startsWith commands are executed.
     * @param outputFilePath Path to the output file in which the program will
     * write the result of the tests.
     * @throws FileNotFoundException In case one of the input files cannot be
     * found.
     * @throws IOException In case there is a problem reading or writing files.
     */
    public static void customTest(String commandsFilePath, String expectedOutputsFilePath, String outputFilePath) throws FileNotFoundException, IOException {

        System.out.println("-------------------------------------------------------");
        System.out.println("Custom test");
        System.out.println("-------------------------------------------------------");
        Trie trie = new Trie();
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(new FileInputStream(commandsFilePath)));
        BufferedReader expectedOutputReader = new BufferedReader(new InputStreamReader(new FileInputStream(expectedOutputsFilePath)));
        PrintStream outputWriter = new PrintStream(new FileOutputStream(outputFilePath));
        String[] input = inputReader.readLine().split(",");
        inputReader.close();
        String[] expectedOutput = expectedOutputReader.readLine().split(",");
        expectedOutputReader.close();
        int i = 0;
        int insertionsCounter = 0;
        int removalsCounter = 0;
        int searchSuccessCounter = 0;
        int searchExperimentsCounter = 0;
        int prefixSuccessCounter = 0;
        int prefixExperimentsCounter = 0;
        int misunderstandingCounter = 0;
        for (String command : input) {
            if (command.startsWith("insert(\"")) {
                String wordToInsert = command.substring("insert(\"".length(), command.length() - "\")".length());
                trie.insert(wordToInsert);
                outputWriter.println("inserted word:....\"" + wordToInsert + "\"");
                //System.out.println("inserted word:....\"" + wordToInsert + "\"");
                insertionsCounter++;
            } else if (command.startsWith("remove(\"")) {
                String wordToRemove = command.substring("remove(\"".length(), command.length() - "\")".length());
                trie.remove(wordToRemove);
                outputWriter.println("removed word:.....\"" + wordToRemove + "\"");
                //System.out.println("removed word:.....\"" + wordToRemove + "\"");
                removalsCounter++;
            } else if (command.startsWith("search(\"")) {
                String wordToSearch = command.substring("search(\"".length(), command.length() - "\")".length());
                boolean result = trie.search(wordToSearch);
                boolean expectedResult = Boolean.parseBoolean(expectedOutput[i++]);
                outputWriter.println("searched word:....\"" + wordToSearch + "\"\tresult: " + result + " expectedResult: " + expectedResult + " Test passed: " + (result == expectedResult));
                //System.out.println("searched word:....\"" + wordToSearch + "\"\tresult: " + result + " expectedResult: " + expectedResult + " Test passed: " + (result == expectedResult));
                searchSuccessCounter += (result == expectedResult ? 1 : 0);
                searchExperimentsCounter++;
            } else if (command.startsWith("startsWith(\"")) {
                String prefixToCheck = command.substring("startsWith(\"".length(), command.length() - "\")".length());
                boolean result = trie.startsWith(prefixToCheck);
                boolean expectedResult = Boolean.parseBoolean(expectedOutput[i++]);
                outputWriter.println("checked prefix:...\"" + prefixToCheck + "\"\tresult: " + result + " expectedResult: " + expectedResult + " Test passed: " + (result == expectedResult));
                //System.out.println("checked prefix:...\"" + prefixToCheck + "\"\tresult: " + result + " expectedResult: " + expectedResult + " Test passed: " + (result == expectedResult));
                prefixSuccessCounter += (result == expectedResult ? 1 : 0);
                prefixExperimentsCounter++;
            } else {
                outputWriter.println("Unable to understand command: " + command);
                misunderstandingCounter++;
            }
        }
        outputWriter.println("-------------------------------------------------------");
        outputWriter.println("Summary of the experiment:");
        outputWriter.println("  Number of commands attempted to execute:...." + input.length);
        outputWriter.println("  Number of misunderstood commands:..........." + misunderstandingCounter + " (" + (misunderstandingCounter * 100. / input.length) + "% of all commands)");
        outputWriter.println("  Number of insertions:......................." + insertionsCounter + " (" + (insertionsCounter * 100. / input.length) + "% of all commands)");
        outputWriter.println("  Number of removals:........................." + removalsCounter + " (" + (removalsCounter * 100. / input.length) + "% of all commands)");
        outputWriter.println("  Number of searches:........................." + searchExperimentsCounter + " (" + (searchExperimentsCounter * 100. / input.length) + "% of all commands)");
        outputWriter.println("    Number of successful searches:............" + searchSuccessCounter + " (" + (searchSuccessCounter * 100. / searchExperimentsCounter) + "% of all searches)");
        outputWriter.println("  Number of prefix checks:...................." + prefixExperimentsCounter + " (" + (prefixExperimentsCounter * 100. / input.length) + "% of all commands)");
        outputWriter.println("    Number of successful prefix checks:......." + prefixSuccessCounter + " (" + (prefixSuccessCounter * 100. / prefixExperimentsCounter) + "% of all prefix checks)");
        outputWriter.close();
        System.out.println("Summary of the experiment:");
        System.out.println("  Number of commands attempted to execute:...." + input.length);
        System.out.println("  Number of misunderstood commands:..........." + misunderstandingCounter + " (" + (misunderstandingCounter * 100. / input.length) + "% of all commands)");
        System.out.println("  Number of insertions:......................." + insertionsCounter + " (" + (insertionsCounter * 100. / input.length) + "% of all commands)");
        System.out.println("  Number of removals:........................." + removalsCounter + " (" + (removalsCounter * 100. / input.length) + "% of all commands)");
        System.out.println("  Number of searches:........................." + searchExperimentsCounter + " (" + (searchExperimentsCounter * 100. / input.length) + "% of all commands)");
        System.out.println("    Number of successful searches:............" + searchSuccessCounter + " (" + (searchSuccessCounter * 100. / searchExperimentsCounter) + "% of all searches)");
        System.out.println("  Number of prefix checks:...................." + prefixExperimentsCounter + " (" + (prefixExperimentsCounter * 100. / input.length) + "% of all commands)");
        System.out.println("    Number of successful prefix checks:......." + prefixSuccessCounter + " (" + (prefixSuccessCounter * 100. / prefixExperimentsCounter) + "% of all prefix checks)");
    }
}
