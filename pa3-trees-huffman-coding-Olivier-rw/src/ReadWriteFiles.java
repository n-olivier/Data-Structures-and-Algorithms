/* Read and write functions implementation
    By Olivier Nshimiyimana
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class ReadWriteFiles {

//    This functions takes a filename as a parameter, and read it into a string
    static String fileToString(String filePath){
        // This will reference one line at a time
        String line = null;
        String text = "";

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(filePath);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                text = text.concat(line+"\n");
            }
            // Always close files.
            bufferedReader.close();
            return text;
        } catch(IOException e) {
            e.printStackTrace();
        }

        return text;
    }

//    Multiple write to files function are created according to what is being written to a file
    static void stringToFile(String fileContent, String filePath){
        try{
            FileWriter fw = new FileWriter(filePath);
            fw.write(fileContent);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void stringToFile(ArrayList<HuffmanCoding.CharNode> frequencyMap, String filePath){
        try {
            FileWriter fw = new FileWriter(filePath);
            for (int i = 0; i < frequencyMap.size(); i++) {
                fw.write(frequencyMap.get(i).character + " "+ frequencyMap.get(i).count + "\n");
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    static void stringToFile(Map<Character, String> codeTable, String filePath){
        try {
            FileWriter fw = new FileWriter(filePath);
            for (Character i: codeTable.keySet()) {
                fw.write(i + " " + codeTable.get(i) + "\n");
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
