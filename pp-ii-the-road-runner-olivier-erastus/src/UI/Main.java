package UI;

import Files.ReadMap;
import Files.WriteToFile;
import Map.Elements;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.ButtonType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class Main extends Application {
    int W = 650;
    int H = 650;
    int startColumn;
    int startRow;
    int endColumn;
    int endRow;
    boolean use8Directions = false;
    int gameScore = 0;
    boolean started = false;
    ArrayList<int[]> last3 = new ArrayList<>();
    ArrayList<int[]> redo3 = new ArrayList<>();
    KeyEvent key;
    boolean checkUndo;
    ArrayList<int[]> boulders;
    ArrayList<ArrayList<Elements>> gridMap;
    GridPane grid;
    Text score;
    boolean solve = true;
    int rows;
    int columns;
    //    int clickedX;
//    int clickedY;
    boolean clickedNewStart = false;
    static boolean weightsChanged=false;


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Road Runner");
        primaryStage.show();

        grid = new GridPane();
        grid.setMaxHeight(H);
        grid.setAlignment(Pos.CENTER);


        HBox window = new HBox();
        UIDesign.style(window);

        VBox controlBar = new VBox();
        UIDesign.style(controlBar);
        controlBar.setMinWidth(100);
        controlBar.setMaxWidth(100);
        controlBar.setMaxHeight(H);

        Button start = new Button("Start");
        UIDesign.style(start);
        start.setStyle("-fx-font-size: 1.5em; -fx-background-color: #00ff00; -fx-text-fill: #ffffff;");
//        start.setAlignment(Pos.);
        controlBar.getChildren().add(start);

        Button aStar = new Button("A*");
        UIDesign.style(aStar);
        aStar.setStyle("-fx-font-size: 1.5em; -fx-background-color: #00ff00; -fx-text-fill: #ffffff;");
//        start.setAlignment(Pos.);
        controlBar.getChildren().add(aStar);


        Text scoreDisplay = new Text("Your Score:");
        UIDesign.style(scoreDisplay);
        controlBar.getChildren().add(scoreDisplay);

        score = new Text(String.valueOf(gameScore));
        score.wrappingWidthProperty().bind(controlBar.widthProperty());
        UIDesign.style(score);
        controlBar.getChildren().add(score);

        Button reset = new Button("Reset");
//        reset.setAlignment(Pos.BOTTOM);
        UIDesign.style(reset);
        reset.setStyle("-fx-font-size: 1.5em; -fx-background-color: #444; -fx-text-fill: #ffffff;");
        controlBar.getChildren().add(reset);

        Text show = new Text("Use 8 Directions?");
        show.wrappingWidthProperty().bind(controlBar.widthProperty());
        show.setStyle("-fx-font-size:1em;");
        UIDesign.style(show);
        controlBar.getChildren().add(show);

        Button directions = new Button("Enable");
        UIDesign.style(directions);
        directions.setStyle("-fx-font-size: 1em; -fx-background-color: #00ff00; -fx-text-fill: #ffffff;");
        controlBar.getChildren().add(directions);

        Button undo = new Button("Undo");
        UIDesign.style(undo);
        undo.setStyle("-fx-font-size: 1.5em; -fx-background-color: #444; -fx-text-fill: #ffffff;");
        controlBar.getChildren().add(undo);

        Button redo = new Button("Redo");
        UIDesign.style(redo);
        redo.setStyle("-fx-font-size: 1em; -fx-background-color: #00ff00; -fx-text-fill: #ffffff;");
        controlBar.getChildren().add(redo);


        Button newMap = new Button("Load New");
        UIDesign.style(newMap);
        newMap.setStyle("-fx-font-size: 1em; -fx-background-color: #00ff00; -fx-text-fill: #ffffff;");
        controlBar.getChildren().add(newMap);


        window.setSpacing(10);
        window.getChildren().add(controlBar);
        window.getChildren().add(grid);

        ReadMap mapObj = new ReadMap();
        gridMap = mapObj.readMapWithoutPath();

        rows = mapObj.rows;
        columns = mapObj.columns;

        displayMap(mapObj, grid, gridMap);


        Scene scene = new Scene(window, W + 350, H + 50);
        primaryStage.setScene(scene);

        FileChooser fil_chooser = new FileChooser();
        newMap.setOnAction(event -> {
            File file = fil_chooser.showOpenDialog(primaryStage);
            if (file != null) {
                try {
                    gridMap = mapObj.readMapWithPath(file.getAbsolutePath());
                    grid.getChildren().clear();
//                    GridPane newGrid = new GridPane();

                    displayMap(mapObj, grid, gridMap);
                    started = false;
                    start.setStyle("-fx-font-size: 1.5em; -fx-background-color: #00ff00; -fx-text-fill: #ffffff;");
                    last3.clear();
                    redo3.clear();
                    gameScore = 0;
                    use8Directions = false;
                    directions.setText("Enable");
                    score.setText(String.valueOf(gameScore));
//                    gridMap.removeAll(mapObj.makeMap());
                    rows = mapObj.rows;
                    columns = mapObj.columns;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


//        ########################################################################################


//        Olivier's new changes

        Button dfsButton = new Button("DFS");
        UIDesign.style(dfsButton);
        controlBar.getChildren().add(dfsButton);

        Button dj = new Button("Dijkstra");
        UIDesign.style(dj);
        controlBar.getChildren().add(dj);

        dj.setOnAction(event -> {
            try {
                dj();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        dfsButton.setOnAction(event -> {
            try {
                runDfs(grid, rows, columns, gridMap);
//                score.setText(String.valueOf(gameScore));
                if (!solve) {
                    score.setText("Not Solvable");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Button newStart = new Button("A new start");
        UIDesign.style(newStart);
        newStart.setStyle("-fx-font-size: 1em; -fx-background-color: #00ff00; -fx-text-fill: #ffffff;");
        controlBar.getChildren().add(newStart);

        newStart.setOnAction(event -> {
            clickedNewStart = true;

        });
        Button newWeights = new Button("New Weights");
        UIDesign.style(newWeights);
        newWeights.setStyle("-fx-font-size: 0.8em; -fx-background-color: #00ff00; -fx-text-fill: #ffffff;");
        controlBar.getChildren().add(newWeights);

        newWeights.setOnAction(event -> {
            newWeightUI(mapObj);
        });


//        ########################################################################################

        aStar.setOnAction(event -> {
            try {
                aStar(grid, rows, columns, gridMap);
//                score.setText(String.valueOf(gameScore));
                if (!solve) {
                    score.setText("Not Solvable");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
//            findNeighbours(gridMap, startRow, startColumn);
        });


        directions.setOnAction(event -> {
            if (use8Directions) {
                use8Directions = false;
                directions.setText("Enable");
                directions.setStyle("-fx-font-size: 1em; -fx-background-color: #00ff00; -fx-text-fill: #ffffff;");

            } else {
                use8Directions = true;
                directions.setText("Disable");
                directions.setStyle("-fx-font-size: 1em; -fx-background-color: #00ff00; -fx-text-fill: #ffffff;");
            }
        });

        start.setOnAction(event -> {
            if (!started) {
                ImageView roadRunner = null;
                try {
                    roadRunner = ReadMap.getRoadRunner();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                roadRunner.setFitHeight(H / columns);
                roadRunner.setFitWidth(W / rows);
                grid.add(roadRunner, startColumn, startRow);
                started = true;
                start.setStyle("-fx-font-size: 1.5em; -fx-background-color: #ff350c; -fx-text-fill: #ffffff;");
            }
        });

        reset.setOnAction(event -> {

            try {
                gameScore = 0;
                use8Directions = false;
                directions.setText("Enable");
                started = false;
                displayMap(mapObj, grid, gridMap);
                start.setStyle("-fx-font-size: 1.5em; -fx-background-color: #00ff00; -fx-text-fill: #ffffff;");
                last3.clear();
                redo3.clear();
                solve = true;
                gridMap = mapObj.readMapWithoutPath();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        undo.setOnAction(event -> {
            try {
                if (last3.size() == 0) {
//                    System.out.println("Cannot undo anymore");
                    undo.setStyle("-fx-font-size: 1.5em; -fx-background-color: #444; -fx-text-fill: #ffffff;");
                } else {
//                    System.out.println("Undoing");
                    undo(grid, gridMap, rows, columns);
                    undo.setStyle("-fx-font-size: 1.5em; -fx-background-color: #00ff00; -fx-text-fill: #ffffff;");
                    score.setText(String.valueOf(gameScore));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        });

        redo.setOnAction(event -> {
            if (checkUndo) {
                try {
                    redo(grid, gridMap, rows, columns);
                    score.setText(String.valueOf(gameScore));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    moveRoadRunner(grid, rows, columns, key, gridMap);
                    score.setText(String.valueOf(gameScore));
                    undo.setStyle("-fx-font-size: 1.5em; -fx-background-color: #00ff00; -fx-text-fill: #ffffff;");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        scene.setOnKeyPressed(event -> {
            try {
                if (started) {
                    moveRoadRunner(grid, rows, columns, event, gridMap);
                    score.setText(String.valueOf(gameScore));
                    undo.setStyle("-fx-font-size: 1.5em; -fx-background-color: #00ff00; -fx-text-fill: #ffffff;");
                } else {
                    score.setText("Click start to start the game");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    void newWeightUI(ReadMap mapObj) {
        Dialog dialog = new Dialog();
        DialogPane  dialogPane=dialog.getDialogPane();
//        dialog.show();


        GridPane ctrlGrid = new GridPane();
        ctrlGrid.setAlignment(Pos.CENTER);

        dialogPane.setContent(ctrlGrid);
//        //dialog.show();
//        dialog.setHeight(400);
//        dialog.setWidth(400);
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);


        TextField ordinaryRoad = new TextField();
        ordinaryRoad.setPromptText("Ordinary Road weight");

        TextField pothole = new TextField();
        pothole.setPromptText("Pothole weight");

        TextField explosive = new TextField();
        explosive.setPromptText("Explosive weight");

        TextField coyote = new TextField();
        coyote.setPromptText("Coyote weight");

        TextField tarredRoad = new TextField();
        tarredRoad.setPromptText("Tarred Road weight");

        TextField goldRoad = new TextField();
        goldRoad.setPromptText("Gold Road weight");

        ctrlGrid.setVgap(10);


        ctrlGrid.add(new Label("Ordinary Road:"), 0, 0);
        ctrlGrid.add(ordinaryRoad, 1, 0);

        ctrlGrid.add(new Label("Pothole:"), 0, 1);
        ctrlGrid.add(pothole, 1, 1);

        ctrlGrid.add(new Label("Explosive:"), 0, 2);
        ctrlGrid.add(explosive, 1, 2);

        ctrlGrid.add(new Label("Coyote:"), 0, 5);
        ctrlGrid.add(coyote, 1, 5);

        ctrlGrid.add(new Label("Tarred Road:"), 0, 3);
        ctrlGrid.add(tarredRoad, 1, 3);

        ctrlGrid.add(new Label("Gold Road:"), 0, 4);
        ctrlGrid.add(goldRoad, 1, 4);

        dialog.setResultConverter(btn ->{
            if (btn == ButtonType.OK){
                try {
                    return getValues(mapObj,ordinaryRoad, pothole, explosive, coyote, tarredRoad, goldRoad);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            return null;

        });

        Optional<Map<String, Integer>> optionalResult = dialog.showAndWait();

    }

    public Map<String, Integer> getValues(ReadMap mapObj, TextField ordinaryRoad, TextField pothole, TextField explosive, TextField coyote, TextField tarredRoad, TextField goldRoad) throws Exception {
//        newWeightUI();
        Map<String, Integer> newValues = new HashMap<>();

        if (!ordinaryRoad.getText().equals("")){
            int val = Integer.parseInt(ordinaryRoad.getText());
            newValues.put("0", val);
        }
        if (!pothole.getText().equals("")){
            int val = Integer.parseInt(pothole.getText());
            newValues.put("2", val);
        }
        if (!explosive.getText().equals("")){
            int val = Integer.parseInt(explosive.getText());
            newValues.put("3", val);
        }
        if (!coyote.getText().equals("")){
            int val = Integer.parseInt(coyote.getText());
            newValues.put("4", val);
        }
        if (!tarredRoad.getText().equals("")){
            int val = Integer.parseInt(tarredRoad.getText());
            newValues.put("5", val);
        }

        if (!goldRoad.getText().equals("")){
            int val = Integer.parseInt(goldRoad.getText());
            newValues.put("6", val);
        }
//        System.out.println(newValues);
        updateGridMap(mapObj,newValues);
        weightsChanged = true;

        return newValues;
//        System.out.println(ordinaryRoad.getText() + " " + pothole.getText() + " ");

    }


    void updateGridMap(ReadMap mapObj, Map<String, Integer> newWeights) throws Exception {
        for (int i = 0; i <gridMap.size() ; i++) {
            for (int j = 0; j <gridMap.get(i).size(); j++) {
                if (newWeights.containsKey(gridMap.get(i).get(j).index)){
                    gridMap.get(i).get(j).energyCost = newWeights.get(gridMap.get(i).get(j).index);
                }
            }
        }
        displayMap(mapObj, grid, gridMap);


    }

    void displayMap(ReadMap mapObj, GridPane grid, ArrayList<ArrayList<Elements>> gridMap) throws Exception {
        int rows = mapObj.rows;
        int columns = mapObj.columns;
        boulders = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                FileInputStream IMG = new FileInputStream(gridMap.get(i).get(j).imgPath);

                Image img = new Image(IMG);

                ImageView imgView = new ImageView(img);
                imgView.setFitHeight(H / columns);
                imgView.setFitWidth(W / rows);

                HBox hBox = new HBox();
                hBox.setStyle("-fx-border-width: 1; -fx-border-color: black;");
                hBox.getChildren().add(imgView);
                grid.add(hBox, j, i);
                gridMap.get(i).get(j).visited = false;
                if (gridMap.get(i).get(j).index.equals("8")) {
                    startColumn = j;
                    startRow = i;
                }
                if (gridMap.get(i).get(j).index.equals("9")) {
                    endColumn = j;
                    endRow = i;
                }

                if (gridMap.get(i).get(j).index.equals("1")) {
                    int[] iii = {i, j};
                    boulders.add(iii);
                }
                int finalJ = j;
                int finalI = i;

                imgView.setOnMouseClicked(event -> {
                    if (clickedNewStart) {
                        try {
                            setNewStart(finalI, finalJ, mapObj);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }

    }

    void setNewStart(int i, int j, ReadMap mapObj) throws Exception {
        gameScore = 0;
        use8Directions = false;
        started = false;
        displayMap(mapObj, grid, gridMap);
        last3.clear();
        redo3.clear();
        solve = true;
        System.out.println("Hell0" + i + " " + j);

        Elements temp = gridMap.get(i).get(j);
        gridMap.get(i).set(j, gridMap.get(startRow).get(startColumn));
        gridMap.get(startRow).set(startColumn, temp);
        displayMap(mapObj, grid, gridMap);
        clickedNewStart = false;
    }

    // Function to move the road runner around the map
    public void moveRoadRunner(GridPane grid, int rows, int columns, KeyEvent event, ArrayList<ArrayList<Elements>> gridMap) throws Exception {
        ImageView imgView = ReadMap.getRoadRunner();
        imgView.setFitHeight(H / columns);
        imgView.setFitWidth(W / rows);

        // If the road runner is already at the finish position, then the game has ended
        if (startColumn == endColumn && startRow == endRow) {
            System.out.println("Game over!!");
            System.out.println("Your score is: " + gameScore);
            // Else the game is not over
        } else {
            // When the right arrow key is pressed
            if (event.getCode() == KeyCode.RIGHT && startColumn < columns - 1 && !gridMap.get(startRow).get(startColumn + 1).visited && !gridMap.get(startRow).get(startColumn + 1).index.equals("1")) {
                moveEast(grid, imgView, gridMap, rows, columns);
                key = event;
            }
            // When the left arrow key is pressed
            if (event.getCode() == KeyCode.LEFT && startColumn > 0 && !gridMap.get(startRow).get(startColumn - 1).visited && !gridMap.get(startRow).get(startColumn - 1).index.equals("1")) {
                moveWest(grid, imgView, gridMap, rows, columns);
                key = event;
            }
            // When the down arrow key is pressed
            if (event.getCode() == KeyCode.DOWN && startRow < rows - 1 && !gridMap.get(startRow + 1).get(startColumn).visited && !gridMap.get(startRow + 1).get(startColumn).index.equals("1")) {
                moveSouth(grid, imgView, gridMap, rows, columns);
                key = event;
            }
            // When the up arrow key is pressed
            if (event.getCode() == KeyCode.UP && startRow > 0 && !gridMap.get(startRow - 1).get(startColumn).visited && !gridMap.get(startRow - 1).get(startColumn).index.equals("1")) {
                moveNorth(grid, imgView, gridMap, rows, columns);
                key = event;
            }
            if (use8Directions) {
                // When the up arrow key is pressed
                if (event.getCode() == KeyCode.W && startRow > 0 && startColumn < columns - 1 && !gridMap.get(startRow - 1).get(startColumn + 1).visited && !gridMap.get(startRow - 1).get(startColumn + 1).index.equals("1")) {
                    moveNorthEast(grid, imgView, gridMap, rows, columns);
                    key = event;
                }
                if (event.getCode() == KeyCode.Q && startRow > 0 && startColumn > 0 && !gridMap.get(startRow - 1).get(startColumn - 1).visited && !gridMap.get(startRow - 1).get(startColumn - 1).index.equals("1")) {
                    moveNorthWest(grid, imgView, gridMap, rows, columns);
                    key = event;
                }
                if (event.getCode() == KeyCode.S && startRow < rows - 1 && startColumn < columns - 1 && !gridMap.get(startRow + 1).get(startColumn + 1).visited && !gridMap.get(startRow + 1).get(startColumn + 1).index.equals("1")) {
                    moveSouthEast(grid, imgView, gridMap, rows, columns);
                    key = event;
                }
                if (event.getCode() == KeyCode.A && startRow > 0 && startColumn > 0 && startRow < rows - 1 && !gridMap.get(startRow + 1).get(startColumn - 1).visited && !gridMap.get(startRow + 1).get(startColumn - 1).index.equals("1")) {
                    moveSouthWest(grid, imgView, gridMap, rows, columns);
                    key = event;
                }
            }

        }
    }

    // Function to update an obstacle's picture after the road runner has visited it
    public void updateMap(GridPane grid, int rows, int columns, int i, int j, ArrayList<ArrayList<Elements>> gridMap) throws Exception {
        Map<String, String> alternativePaths = ReadMap.alternativeImgPaths;
        Map<String, String> realPaths = ReadMap.imgPaths;
        String path = alternativePaths.get(gridMap.get(i).get(j).index);
        String startImagePath = realPaths.get(gridMap.get(i).get(j).index);
        gridMap.get(i).get(j).visited = true;

        // Make the array to store the previous visited location
        int[] store = new int[3];
        store[0] = i;
        store[1] = j;
        store[2] = gameScore;
        last3.add(store);
        if (last3.size() > 3) {
            last3.remove(0);
        }

        if (gridMap.get(i).get(j).index.equals("8")) {
            FileInputStream STARTIMG = new FileInputStream(startImagePath);
            Image StartImg = new Image(STARTIMG);
            ImageView StartImgView = new ImageView(StartImg);
            StartImgView.setFitHeight(H / columns);
            StartImgView.setFitWidth(W / rows);
            grid.add(StartImgView, j, i);
        } else {
            // Replace the other images
            FileInputStream IMG = new FileInputStream(path);
            Image img = new Image(IMG);
            ImageView imgView = new ImageView(img);
            imgView.setFitHeight(H / columns);
            imgView.setFitWidth(W / rows);
            grid.add(imgView, j, i);
            if (!weightsChanged){
                Score(gridMap.get(i).get(j).index);
            }
            else{
                Score(String.valueOf(gridMap.get(i).get(j).energyCost));
            }


        }
    }

    public void undo(GridPane grid, ArrayList<ArrayList<Elements>> gridMap, int rows, int columns) throws Exception {
        int size = last3.size();
//        System.out.println(size);
        if (size > 0) {
            int sC = startColumn;
            int sR = startRow;
//            System.out.println(sC + " " + sR);
            startColumn = last3.get(size - 1)[1];
            startRow = last3.get(size - 1)[0];
            gameScore = last3.get(size - 1)[2];

            ImageView imgView = ReadMap.getRoadRunner();
            imgView.setFitHeight(H / columns);
            imgView.setFitWidth(W / rows);
            grid.add(imgView, startColumn, startRow);

            Map<String, String> realPaths = ReadMap.imgPaths;
            String previousImagePath = realPaths.get(gridMap.get(sR).get(sC).index);
            gridMap.get(sR).get(sC).visited = false;

            FileInputStream IMGP = new FileInputStream(previousImagePath);
            Image imgP = new Image(IMGP);
            ImageView imgViewP = new ImageView(imgP);
            imgViewP.setFitHeight(H / columns);
            imgViewP.setFitWidth(W / rows);
            grid.add(imgViewP, sC, sR);
            redo3.add(last3.get(size - 1));
            last3.remove(size - 1);
            checkUndo = true;
        }


    }

    public void redo(GridPane grid, ArrayList<ArrayList<Elements>> gridMap, int rows, int columns) throws Exception {
        int size = redo3.size();
//        System.out.println(size);

//        for (int k = 0; k < redo3.size(); k++) {
//            System.out.println(Arrays.toString(redo3.get(k)));
//        }
//        System.out.println("###");

        if (size > 1) {
            int sC = startColumn;
            int sR = startRow;
//            System.out.println(sC + " " + sR);
            startColumn = redo3.get(size - 2)[1];
            startRow = redo3.get(size - 2)[0];
            gameScore = redo3.get(size - 2)[2];


            ImageView imgView = ReadMap.getRoadRunner();
            imgView.setFitHeight(H / columns);
            imgView.setFitWidth(W / rows);
            grid.add(imgView, startColumn, startRow);

            Map<String, String> realPaths = ReadMap.alternativeImgPaths;
            String previousImagePath = realPaths.get(gridMap.get(sR).get(sC).index);
            gridMap.get(sR).get(sC).visited = true;

            FileInputStream IMGP = new FileInputStream(previousImagePath);
            Image imgP = new Image(IMGP);
            ImageView imgViewP = new ImageView(imgP);
            imgViewP.setFitHeight(H / columns);
            imgViewP.setFitWidth(W / rows);
            grid.add(imgViewP, sC, sR);
//            System.out.println(startColumn + " " + startRow);
            last3.add(redo3.get(size - 1));

            redo3.remove(size - 1);
        }

    }

    // Start functions to move the road runner in all the 8 directions
    public void moveEast(GridPane grid, ImageView imgView, ArrayList<ArrayList<Elements>> gridMap, int rows, int columns) throws Exception {
        startColumn++;
        grid.add(imgView, startColumn, startRow);
        updateMap(grid, rows, columns, startRow, startColumn - 1, gridMap);
        setRedo3();
//        System.out.println(startColumn + " " + startRow);
    }

    public void moveWest(GridPane grid, ImageView imgView, ArrayList<ArrayList<Elements>> gridMap, int rows, int columns) throws Exception {
        startColumn--;
        grid.add(imgView, startColumn, startRow);
        updateMap(grid, rows, columns, startRow, startColumn + 1, gridMap);
        setRedo3();
//        System.out.println(startColumn + " " + startRow);
    }

    public void moveSouth(GridPane grid, ImageView imgView, ArrayList<ArrayList<Elements>> gridMap, int rows, int columns) throws Exception {
        startRow++;
        grid.add(imgView, startColumn, startRow);
        updateMap(grid, rows, columns, startRow - 1, startColumn, gridMap);
        setRedo3();
//        System.out.println(startColumn + " " + startRow);
    }

    public void moveNorth(GridPane grid, ImageView imgView, ArrayList<ArrayList<Elements>> gridMap, int rows, int columns) throws Exception {
        startRow--;
        grid.add(imgView, startColumn, startRow);
        updateMap(grid, rows, columns, startRow + 1, startColumn, gridMap);
        setRedo3();
//        System.out.println(startColumn + " " + startRow);
    }

    public void moveNorthEast(GridPane grid, ImageView imgView, ArrayList<ArrayList<Elements>> gridMap, int rows, int columns) throws Exception {
        startRow--;
        startColumn++;
        grid.add(imgView, startColumn, startRow);
        updateMap(grid, rows, columns, startRow + 1, startColumn - 1, gridMap);
        setRedo3();
//        System.out.println(startColumn + " " + startRow);
    }

    public void moveNorthWest(GridPane grid, ImageView imgView, ArrayList<ArrayList<Elements>> gridMap, int rows, int columns) throws Exception {
        startRow--;
        startColumn--;
        grid.add(imgView, startColumn, startRow);
        updateMap(grid, rows, columns, startRow + 1, startColumn + 1, gridMap);
        setRedo3();
//        System.out.println(startColumn + " " + startRow);
    }

    public void moveSouthEast(GridPane grid, ImageView imgView, ArrayList<ArrayList<Elements>> gridMap, int rows, int columns) throws Exception {
        startRow++;
        startColumn++;
        grid.add(imgView, startColumn, startRow);
        updateMap(grid, rows, columns, startRow - 1, startColumn - 1, gridMap);
        setRedo3();
//        System.out.println(startColumn + " " + startRow);
    }

    public void moveSouthWest(GridPane grid, ImageView imgView, ArrayList<ArrayList<Elements>> gridMap, int rows, int columns) throws Exception {
        startRow++;
        startColumn--;
        grid.add(imgView, startColumn, startRow);
        updateMap(grid, rows, columns, startRow - 1, startColumn + 1, gridMap);
        setRedo3();
//        System.out.println(startColumn + " " + startRow);
    }

    // End of the functions that move the road runner around the map in all the 8 directions

    // Function to compute the score as one plays
    public void Score(String score) {
        if (!weightsChanged){
            if (score.equals("0")) {
                gameScore -= 1;
            }
            if (score.equals("2")) {
                gameScore -= 2;
            }
            if (score.equals("3")) {
                gameScore -= 4;
            }
            if (score.equals("4")) {
                gameScore -= 8;
            }
            if (score.equals("5")) {
                gameScore += 1;
            }
            if (score.equals("6")) {
                gameScore += 5;
            }}
        else {
            gameScore += Integer.parseInt(score);
        }
    }

    void setRedo3() {
        int[] tmp = {startRow, startColumn, gameScore};
        redo3.add(tmp);
        if (redo3.size() > 1) {
            redo3.remove(0);
        }

    }

    public ArrayList findNeighbours(ArrayList<ArrayList<Elements>> gridMap, int i, int j) {
// Array of Neighbours
        ArrayList<int[]> neighbours = new ArrayList<>();
        int rowLimit = gridMap.size();
        int colLimit = gridMap.size();


// Get valid indices
        for (int x = Math.max(0, i - 1); x < Math.min(i + 1, rowLimit); x++) {
            for (int y = Math.max(0, j - 1); y < Math.min(j + 1, colLimit); y++) {
// Add new indices to array list

                neighbours.add(new int[]{x, y});

                System.out.println(x + " " + y);
            }
        }
// Return array list of valid indices.
        return neighbours;
    }

    void runDfs(GridPane grid, int rows, int columns, ArrayList<ArrayList<Elements>> gridMap) throws Exception {
        ArrayList<DFS.Node> p = DFS.run(gridMap, use8Directions);
        if (p == null) {
            solve = false;
            return;
        }

        int s = p.size();
        WriteToFile.stringToFile(p, "DFS");
        while (s > 0) {
            s--;
            DFS.Node next = p.get(s);
            updateMap(grid, rows, columns, next.row, next.col, gridMap);
        }
        System.out.println(p.toString());
    }

    void aStar(GridPane grid, int rows, int cols, ArrayList<ArrayList<Elements>> gridMap) throws Exception {
        Node initialNode = new Node(startRow, startColumn);
        Node finalNode = new Node(endRow, endColumn);

        AStar aStar = new AStar(rows, cols, initialNode, finalNode);
        if (boulders.size() != 0) {
            int[][] blocksArray = new int[boulders.size()][boulders.get(0).length];
//                {{1, 3}, {2, 3}, {3, 3}};
            for (int i = 0; i < boulders.size(); i++) {
                blocksArray[i][0] = boulders.get(i)[0];
                blocksArray[i][1] = boulders.get(i)[1];
            }
            aStar.setBlocks(blocksArray);
        }



        List<Node> path = aStar.findPath(use8Directions);
        if (path.size() == 0) {
            solve = false;
        } else {
            System.out.println(path.toString());
            WriteToFile.stringToFile(path, "AStar");

            for (Node node : path) {
                updateMap(grid, rows, cols, node.getRow(), node.getCol(), gridMap);
//                System.out.println(rows + " " + cols + " " + node);
            }
        }

    }

    void dj() throws Exception {


        int [][] map_ = nodesMap(gridMap);
        int[][] blocksArray;
        if (boulders.size() != 0) {
            blocksArray = new int[boulders.size()][boulders.get(0).length];
//                {{1, 3}, {2, 3}, {3, 3}};
            for (int i = 0; i < boulders.size(); i++) {
                blocksArray[i][0] = boulders.get(i)[0];
                blocksArray[i][1] = boulders.get(i)[1];
            }
        }
        else{
            blocksArray = new int[0][0];

        }

        DijkstraAlgo test = new DijkstraAlgo(map_, use8Directions, rows, columns,startRow, startColumn, endRow, endColumn,
                blocksArray);



        test.display();
        test.process(use8Directions);
        int[][] path = test.displaySolution(50);
        WriteToFile.stringToFile(path, "Dijkstra", startRow, startColumn, endRow, endColumn);

        for (int i = path.length-1; i >=0 ; i--) {
            if (!(path[i][0] == 0 && path[i][1] == 0)){
                updateMap(grid, rows, columns, path[i][0], path[i][1], gridMap);
                System.out.println(Arrays.toString(path[i]));
            }
        }


    }
    int[][] nodesMap(ArrayList<ArrayList<Elements>> gridMap) {
        int[][] nodes = new int[gridMap.size()][gridMap.get(0).size()];

        for (int i = 0; i < gridMap.size(); i++) {
            for (int j = 0; j < gridMap.get(0).size(); j++) {
                nodes[i][j] = Integer.parseInt(gridMap.get(i).get(j).index);
            }
        }
        for (int i = 0; i <nodes.length ; i++) {
        }
        return nodes;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
