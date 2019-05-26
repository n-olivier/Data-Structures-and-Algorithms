/* Huffman Coding implementation
    By Olivier Nshimiyimana
 */

import java.util.*;

public class HuffmanCoding {
    private static String filePath = "/home/olivier/IdeaProjects/pa3-trees-huffman-coding-Olivier-rw/src/test.txt";

//    public static void main(String[] args) {
//        String content = ReadWriteFiles.fileToString(filePath);
//        ArrayList<CharNode> frequencyMap = getFrequency(content);
//        System.out.println(frequencyMap.toString());
//
//        ArrayList<CharNode> orderedFrequencyMap = mergeSort(frequencyMap, frequencyMap.size());
//        System.out.println(orderedFrequencyMap.toString());
////
//        CharNode root = getTree(orderedFrequencyMap);
//        System.out.println(root);
////        getCodeTable(root);
//        System.out.println(getCodeTable(root).toString());
//
//        String encodedText = encoding(code, content);
//        System.out.println(encodedText);
//
//        System.out.println(decoding(root, encodedText));
//
//    }

//    Frequency table creation. It is stored as an array of character nodes.
/*    Each node is supposed to be a character with its count,
        but because we may want to use this in our huffman tree, we will also give it left and right variables
 */

static class CharNode{
        int count;
        char character;
        CharNode left;
        CharNode right;

        CharNode(char character, int count){
            this.character = character;
            this.count = count;
        }

        CharNode(CharNode left, CharNode right, int count){
            this.left = left;
            this.right = right;
            this.count = count;
        }

        CharNode(CharNode copy){
            this.character = copy.character;
            this.left = copy.left;
            this.right = copy.right;
        }

    @Override
        public String toString() {
            return character+":"+count;
        }
    }

//    This function uses two structures: ArrayList for storing character objects,
//    and HashMap to keep track of characters we have met before

    static ArrayList<CharNode> getFrequency(String fileContent){
        Map<Character, Integer> charFrequency = new HashMap<>();
        ArrayList<CharNode> charSequence = new ArrayList<>();
        int counter = 0;

        for (char c: fileContent.toCharArray()){
            if (charFrequency.containsKey(c)){
                int index = charFrequency.get(c);
                ++charSequence.get(index).count;

            } else {
                charFrequency.put(c, counter);
                counter++;
                charSequence.add(new CharNode(c, 1));
            }
        }
        return mergeSort(charSequence, charSequence.size());

//        This method uses O(n) runtime, O(n) space and O(n) auxiliary space
    }

    static ArrayList<CharNode> getFrequency(String[] arr){
        ArrayList<CharNode> charSequence = new ArrayList<>();

        for (int i = 0; i < arr.length; i++) {
            String[] charFr = arr[i].split(" ");
            char key=' ';
            int freq=0;
            if (charFr.length == 3){
                key= ' ';
                freq= Integer.parseInt(charFr[2]);
            }
            else if(charFr.length==1){
                i++;
                String[] c = arr[i].split(" ");
                key= '\n';
                freq= Integer.parseInt(c[1]);
            }
            else if (charFr.length == 2){
                key= charFr[0].toCharArray()[0];
                freq= Integer.parseInt(charFr[1]);
            }

            CharNode newNode = new CharNode(key, freq);

            charSequence.add(newNode);
        }

        return mergeSort(charSequence, charSequence.size());

//        This method uses O(n) runtime, O(n) space and O(n) auxiliary space
    }

//    To easily create Huffman tree, we need to sort our characters on their frequency and sequence.
//    Merge sort is a good sort that can keeps the frequency and sequence in order.

    static ArrayList<CharNode> mergeSort(ArrayList<CharNode> a, int n) {
        if (n < 2) {
            return null;
        }
        int mid = n / 2;
        ArrayList<CharNode> l = new ArrayList<>();
        ArrayList<CharNode> r = new ArrayList<>();

        for (int i = 0; i < mid; i++) {
            l.add(a.get(i));
        }
        for (int i = mid; i < n; i++) {
            r.add(a.get(i));
        }
        mergeSort(l, mid);
        mergeSort(r, n - mid);

        merge(a, l, r, mid, n - mid);

        return a;
    }

//    The sorting compare on values, but swap on nodes themselves.

    static void merge(ArrayList<CharNode> a, ArrayList<CharNode> l, ArrayList<CharNode> r, int left, int right) {

        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            if (l.get(i).count <= r.get(j).count) {
                a.set(k++, l.get(i++));
            }
            else {
                a.set(k++, r.get(j++));
            }
        }
        while (i < left) {
            a.set(k++, l.get(i++));
        }
        while (j < right) {
            a.set(k++, r.get(j++));
        }
    }


//    Creation of Huffman tree.
//    This function removes first two elements from the array, create one node from them and add it back to array

    static CharNode getTree(ArrayList<CharNode> arr){
//        ArrayList<CharNode> arr = getFrequency(fileContent);

        while (arr.size()>1){
            CharNode left = arr.remove(0);
            CharNode right = arr.remove(0);

            CharNode newNode = new CharNode(left, right, left.count+right.count);

            int i=0;

            while (i < arr.size() && arr.get(i).count <= newNode.count){
                i++;
            }
            arr.add(i, newNode);
//            System.out.println(arr.toString());
        }
        return arr.get(0);
//        This method uses O(n^2) runtime, O(n) space and O(1) auxiliary space
    }

    static Map<Character, String> code = new HashMap<>();

    static Map<Character, String> getCodeTable(CharNode root){
        String path = "";

        getCodeTable(root, path);
        return code;
//        This method uses O(n) runtime, O(n) space and O(n) auxiliary space
    }

//    Helper function for getCode
    static void getCodeTable(CharNode root, String s){
        if (root.left == null || root.right == null){
            code.put(root.character, s);
            return;
        }
        else{
            getCodeTable(root.left, s.concat("0"));
            getCodeTable(root.right, s.concat("1"));
        }
    }

    static String encoding(Map<Character, String> codeTable, String inputString){
        String encodedText ="";
        for (int i = 0; i < inputString.length(); i++) {
            String currentCode = codeTable.get(inputString.charAt(i));
            encodedText = encodedText.concat(currentCode);
        }
        return encodedText;
//        This method uses O(n) runtime, O(n) space and O(1) auxiliary space
    }

    static String decoding(CharNode root, String code){
        CharNode pointer = root;
        String originalMsg = "";

        for (int i = 0; i < code.length(); i++) {
            if (code.charAt(i) == '0'){
                pointer = pointer.left;
            }
            else {
                pointer = pointer.right;
            }

            if (pointer.left == null || pointer.right == null) {
                originalMsg = originalMsg.concat(String.valueOf(pointer.character));
                pointer = root;
            }
        }
        return originalMsg;

//        This method uses O(n) runtime, O(n) space and O(1) auxiliary space
    }
}
