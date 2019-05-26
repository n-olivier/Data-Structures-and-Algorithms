/* Huffman Tester implementation
    By Olivier Nshimiyimana
 */

import java.util.ArrayList;
import java.util.Map;

public class Tester {
    public static void main(String[] args) {
//        Files to read
        String path = "/home/olivier/IdeaProjects/pa3-trees-huffman-coding-Olivier-rw/src/test_input.txt";
        String encodedTestPath = "/home/olivier/IdeaProjects/pa3-trees-huffman-coding-Olivier-rw/src/this_is_me_encoded.txt";
        String freqTableGiven = "/home/olivier/IdeaProjects/pa3-trees-huffman-coding-Olivier-rw/src/frequency_table_input.txt";
        String secretMsg = "/home/olivier/IdeaProjects/pa3-trees-huffman-coding-Olivier-rw/src/secret_message.txt";

//        Files to write
        String inputPath = "/home/olivier/IdeaProjects/pa3-trees-huffman-coding-Olivier-rw/src/OlivierNshimiyimana_TestInput.txt";
        String freqPath = "/home/olivier/IdeaProjects/pa3-trees-huffman-coding-Olivier-rw/src/OlivierNshimiyimana_FrequencyTable.txt";
        String codePath = "/home/olivier/IdeaProjects/pa3-trees-huffman-coding-Olivier-rw/src/OlivierNshimiyimana_CodeTable.txt";
        String encodedPath = "/home/olivier/IdeaProjects/pa3-trees-huffman-coding-Olivier-rw/src/OlivierNshimiyimana_EncodedMessage.txt";
        String decodedPath = "/home/olivier/IdeaProjects/pa3-trees-huffman-coding-Olivier-rw/src/OlivierNshimiyimana_DecodedMessage.txt";

//        Output separator
        String separator = "\n ##################################################################################### \n";

//        Reading text_input.txt file, and writing it to a new file
        String fileContent = ReadWriteFiles.fileToString(path);
        ReadWriteFiles.stringToFile(fileContent, inputPath);
        System.out.println(fileContent);

        System.out.println(separator);

//        Creating a frequency table from it, and writing it to a new file
        ArrayList<HuffmanCoding.CharNode> frequencyMap = HuffmanCoding.getFrequency(fileContent);
        ReadWriteFiles.stringToFile(frequencyMap, freqPath);
        for (int i = 0; i < frequencyMap.size(); i++) {
            System.out.println(frequencyMap.get(i).character + " "+ frequencyMap.get(i).count);
        }

        System.out.println(separator);

//        Creating a tree from frequency table, creating code table from a tree, and writing code table to a new file
        HuffmanCoding.CharNode root = HuffmanCoding.getTree(frequencyMap);
        Map<Character, String> codeTable = HuffmanCoding.getCodeTable(root);
        ReadWriteFiles.stringToFile(codeTable, codePath);
        for (Character i: codeTable.keySet()) {
            System.out.println(i + " " + codeTable.get(i));
        }

        System.out.println(separator);

//        Encoding text using code table created, and writing new encoded text to a new file
        String encodedText = HuffmanCoding.encoding(codeTable, fileContent);
        ReadWriteFiles.stringToFile(encodedText, encodedPath);
        System.out.println(encodedText);

        System.out.println(separator);

//        Reading frequency table given
        String fTableGiven = ReadWriteFiles.fileToString(freqTableGiven);
        System.out.println(fTableGiven);
        String[] arrOfFreq = fTableGiven.split("\n");
        ArrayList<HuffmanCoding.CharNode> orderedFTable = HuffmanCoding.getFrequency(arrOfFreq);
        System.out.println(orderedFTable.toString());

        System.out.println(separator);

//        Creating a Huffman tree from the ordered frequency table created
        HuffmanCoding.CharNode newTree = HuffmanCoding.getTree(orderedFTable);
        System.out.println(newTree);

        System.out.println(separator);

        String decodedMsg = HuffmanCoding.decoding(newTree, ReadWriteFiles.fileToString(secretMsg));
        ReadWriteFiles.stringToFile(decodedMsg, decodedPath);
        System.out.println(decodedMsg);

//        Decoding message
        String newDecodedMsg = HuffmanCoding.decoding(root, encodedText);
        System.out.println(decodedMsg);


//        Reading encoded text to test the decode method
        String encodedTest = ReadWriteFiles.fileToString(encodedTestPath);
        System.out.println(encodedTest);

        System.out.println(separator);

//        Decoding message using the test encoded text given
        String decodedTestMsg = HuffmanCoding.decoding(root, encodedTest);
        System.out.println(decodedTestMsg);

    }
}
