package Files;

import UI.DFS;
import UI.Node;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WriteToFile {
    static String outputPath = "/home/olivier/IdeaProjects/pp-ii-the-road-runner-olivier-erastus/src/Files/paths_outputs/";
    public static void stringToFile(ArrayList<DFS.Node> path, String filePath){
        String output = "";
        output += "Start " + path.get(0).row + " " + path.get(0).col + '\n';
        output += "Goal " + path.get(path.size()-1).row + " " + path.get(path.size()-1).col + '\n';
        output += generateStringPath(path);

        filePath = outputPath + "Output" + filePath;
        try{
            FileWriter fw = new FileWriter(filePath);
            fw.write(output);
//            fw.append(fileContent);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String generateStringPath(ArrayList<UI.DFS.Node> path){
        String output = "";

        for (int i = path.size()-1; i > 0 ; i--) {
            output += compare(path.get(i), path.get(i-1));
        }

        return output;
    }

    private static String compare(DFS.Node from, DFS.Node to) {
        String move="";
        if(from.row > to.row){
            move += "North";
        }
        else if(from.row < to.row){
            move += "South";
        }

        if (from.col > to.col){
            move += "West";
        }
        else if (from.col < to.col){
            move += "East";
        }
        return move+'\n';
    }

    public static void stringToFile(List<Node> path, String filePath){
        String output = "";
        output += "Start " + path.get(0).getRow() + " " + path.get(0).getCol() + '\n';
        output += "Goal " + path.get(path.size()-1).getRow() + " " + path.get(path.size()-1).getCol() + '\n';
        output += generateStringPath(path);
        filePath = outputPath + "Output" + filePath;
        try{

            FileWriter fw = new FileWriter(filePath);
            fw.write(output);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String generateStringPath(List<UI.Node> path){
        String output ="";

        for (int i = 0; i < path.size()-1 ; i++) {
            output += compare(path.get(i), path.get(i+1));
        }

        return output;
    }

    private static String compare(UI.Node from, UI.Node to) {
        String move="";
        if(from.getRow() > to.getRow()){
            move += "North";
        }
        else if(from.getRow() < to.getRow()){
            move += "South";
        }

        if (from.getCol() > to.getCol()){
            move += "West";
        }
        else if (from.getCol() < to.getCol()){
            move += "East";
        }
        return move + '\n';
    }

    public static void stringToFile(int[][] path, String filePath, int startRow, int startColumn, int endRow, int endColumn){

        String output = "";
        output += "Start " + startRow + " " + startColumn + '\n';
        output += "Goal " + endRow + " " + endColumn + '\n';

        for (int i = path.length-1; i >0 ; i--) {
            if (!(path[i][0] == 0 && path[i][1] == 0)){
                output += compare(path[i][0], path[i][1], path[i-1][0], path[i-1][1]);
            }
        }

//        output += generateStringPath(path);

        filePath = outputPath + "Output" + filePath;
        try{
            FileWriter fw = new FileWriter(filePath);
            fw.write(output);
//            fw.append(fileContent);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String compare(int fromi, int fromj, int toi, int toj) {
        String move="";
        if(fromi > toi){
            move += "North";
        }
        else if(fromi < toi){
            move += "South";
        }

        if (fromj > toj){
            move += "West";
        }
        else if (fromj < toj){
            move += "East";
        }
        return move + '\n';
    }
}
