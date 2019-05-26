package Files;

import Map.Elements;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;
import java.util.*;

public class ReadMap {
    public int rows;
    public int columns;

    //    Project directory path
    static String projectPath = "/home/olivier/IdeaProjects/pp-ii-the-road-runner-olivier-erastus/src/Files/";

    //     Other paths to different folders in the project;
    static String imagesPath = projectPath + "img/";
    static String roadRunner = projectPath + "img/road_runner.jpg";


    //    Mapping an all indices with their corresponding image paths, and alternative paths
    public static Map<String, String> alternativeImgPaths = new HashMap<String, String>() {{
        put("0", imagesPath + "road_alt.jpg");
        put("1", imagesPath + "boulder.jpg");
        put("2", imagesPath + "pothole_alt.jpg");
        put("3", imagesPath + "explosive_alt.jpg");
        put("4", imagesPath + "coyote_alt.jpg");
        put("5", imagesPath + "tarred_alt.jpg");
        put("6", imagesPath + "gold_alt.jpg");
        put("8", imagesPath + "start.jpg");
        put("9", imagesPath + "goal.jpg");

    }};

    public static Map<String, String> imgPaths = new HashMap<String, String>() {{
        put("0", imagesPath + "road.jpg");
        put("1", imagesPath + "boulder.jpg");
        put("2", imagesPath + "pothole.jpg");
        put("3", imagesPath + "explosive.jpg");
        put("4", imagesPath + "coyote.jpg");
        put("5", imagesPath + "tarred.jpg");
        put("6", imagesPath + "gold.jpg");
        put("8", imagesPath + "start.jpg");
        put("9", imagesPath + "goal.jpg");

    }};

//    Setting up cost for every item of the environment
    public static Map<String, Integer> costEnergy = new HashMap<String, Integer>() {{
        put("0", -1);
        put("1", 0);
        put("2", -2);
        put("3", -4);
        put("4", -8);
        put("5", 1);
        put("6", 5);
        put("8", 0);
        put("9", 0);

    }};

    //    If there is no file path given we give a default file path manually
    public ArrayList<ArrayList<Elements>> readMapWithoutPath() throws Exception {
        String mapPath = projectPath + "test_inputs/" + "test_input_10_15_unsolvable (1).txt";
        return generateMap(mapPath);
    }

    //    If we already have a filepath(i.e: when load new map is used) then we load it
    public ArrayList<ArrayList<Elements>> readMapWithPath(String filePath) throws Exception {
        return generateMap(filePath);

    }

    //    This function takes in a string path and return a ArrayList of ArrayList as a map
    public ArrayList<ArrayList<Elements>> generateMap(String mapPath) throws Exception {
        Scanner scanner = new Scanner(new File(mapPath));

        ArrayList<ArrayList<Elements>> map = new ArrayList<>();
        String[] firstLine = scanner.nextLine().split("\\s");

//        We update global values for rows and columns
        rows = Integer.parseInt(firstLine[0]);
        columns = Integer.parseInt(firstLine[1]);

        for (int i = 0; i < rows; i++) {
            String[] line = scanner.nextLine().split("");

            ArrayList<Elements> newLine = new ArrayList<>();
            for (int j = 0; j < line.length; j++) {
                Elements element = new Elements(line[j], costEnergy.get(line[j]), imgPaths.get(line[j]), alternativeImgPaths.get(line[j]), i, j);
                newLine.add(element);
            }
            map.add(newLine);
        }
        return map;
//        The function takes r*c runtime. r being the height of a map and c being the width of it.
    }

    //    This function returns an imageView of a road runner;
    public static ImageView getRoadRunner() throws FileNotFoundException {
        FileInputStream IMG = new FileInputStream(roadRunner);
        Image img = new Image(IMG);
        ImageView imgView = new ImageView(img);
        return imgView;
    }
}
