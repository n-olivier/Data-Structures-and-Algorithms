package UI;
import java.util.Arrays;
import java.util.PriorityQueue;

public class DijkstraAlgo {

    public static final int Diagonal_Value = 14;
    public static final int Vert_Hor_Value = 10;

    private Cell[][] grid_;

    private PriorityQueue<Cell> openCells;

    private boolean[][] closedCells;

    private int i_start, j_start;

    private int i_end, j_end;

    public DijkstraAlgo(int [][] map_, boolean enabled8, int width, int height, int i_start, int j_start, int i_end, int j_end, int[][] cellBlocks) {
        grid_ = new Cell[width][height];
        closedCells = new boolean[width][height];
        openCells = new PriorityQueue<Cell>((Cell c1, Cell c2) -> {
            return c1.finalValue < c2.finalValue ? -1 : c1.finalValue > c2.finalValue ? 1 : 0;
//            return Integer.compare(c1.finalValue, c2.finalValue);
        });

        startCell(i_start, j_start);
        endCell(i_end, j_end);

        //Initialize heuristic cells.

        for (int i = 0; i < grid_.length; i++) {
            for (int j = 0; j < grid_[i].length; j++) {
                grid_[i][j] = new Cell(i, j);
                grid_[i][j].heuristicValue = map_[i][j];
                grid_[i][j].solution = false;
            }
        }

        grid_[i_start][j_start].finalValue = 0;

        //Put the cells on the grid.
        for (int i = 0; i < cellBlocks.length; i++) {
            addBlocktoCell(cellBlocks[i][0], cellBlocks[i][1]);
        }
    }

    public void addBlocktoCell(int i, int j){
        grid_[i][j] = null;
    }

    public void startCell(int i, int j){
        i_start = i;
        j_start = j;
    }

    public void endCell(int i, int j){
        i_end = i;
        j_end = j;
    }

    public void updateValue(Cell current, Cell x, int value){
        if (x == null || closedCells[x.i][x.j])
            return;

        int xFinalValue = x.heuristicValue + value;
        boolean isOpen = openCells.contains(x);

        if (!isOpen || xFinalValue < x.finalValue){
            x.finalValue = xFinalValue;
            x.parent = current;

            if (!isOpen)
                openCells.add(x);
        }
    }

    public void process (boolean enabled8){

        openCells.add(grid_[i_start][j_start]);
        Cell current;

        while (true){
            current = openCells.poll();

            if (current == null)
                break;

            closedCells[current.i][current.j] = true;

            if(current.equals(grid_[i_end][j_end]))
                return;

            Cell x;

            if(enabled8){
                if(current.i - 1 >= 0){
                    x = grid_[current.i - 1][current.j];
                    updateValue(current, x, current.finalValue );

                    if(current.j - 1 >= 0){
                        x = grid_[current.i - 1][current.j - 1];
                        updateValue(current, x, current.finalValue );
                    }

                    if(current.j + 1 < grid_[0].length){
                        x = grid_[current.i - 1][current.j + 1];
                        updateValue(current, x, current.finalValue);
                    }
                }

                if(current.j - 1 >= 0){
                    x = grid_[current.i] [current.j - 1];
                    updateValue(current, x, current.finalValue );
                }

                if(current.j + 1 < grid_[0].length){
                    x = grid_[current.i] [current.j + 1];
                    updateValue(current, x, current.finalValue);
                }

                if(current.i + 1 < grid_.length){
                    x = grid_[current.i + 1] [current.j];
                    updateValue(current, x, current.finalValue);

                    if(current.j - 1 >= 0){
                        x = grid_[current.i + 1] [current.j - 1];
                        updateValue(current, x, current.finalValue);
                    }

                    if(current.j + 1 < grid_[0].length){
                        x = grid_[current.i + 1] [current.j + 1];
                        updateValue(current, x, current.finalValue);
                    }
                }

            }

            else{

                if(current.i - 1 >= 0){
                    x = grid_[current.i - 1][current.j];
                    updateValue(current, x, current.finalValue );

                }

                if(current.j - 1 >= 0){
                    x = grid_[current.i] [current.j - 1];
                    updateValue(current, x, current.finalValue );
                }

                if(current.j + 1 < grid_[0].length){
                    x = grid_[current.i] [current.j + 1];
                    updateValue(current, x, current.finalValue);
                }

                if(current.i + 1 < grid_.length){
                    x = grid_[current.i + 1] [current.j];
                    updateValue(current, x, current.finalValue);

                }


            }


        }
    }

    public void display(){
        System.out.println("Grid : ");

        for(int i = 0; i < grid_.length; i++){
            for(int j = 0; j < grid_[i].length; j++){
                if (i == i_start && j == j_start)
                    System.out.print("S ");
                else if(i == i_end && j == j_end)
                    System.out.print("D ");
                else if (grid_[i][j] != null)
                    System.out.printf("%-3d", 0);
                else
                    System.out.print("B ");
            }
            System.out.println();
        }

        System.out.println();
    }

    public  void displayValues(){
        System.out.println("\nValues for cells :");

        for(int i = 0; i < grid_.length; i++){
            for (int j = 0; j < grid_[i].length; j++){
                if (grid_[i][j] != null)
                    System.out.printf("%-3d", grid_[i][j].finalValue);
                else
                    System.out.print("B ");
            }

            System.out.println();
        }
        System.out.println();
    }


    public int counter = 0;

    public int[][] displaySolution (int arrayrow){
        int[][] solArray = new int [arrayrow][2];
        if(closedCells[i_end][j_end]){
            System.out.println("Path:");
            Cell current = grid_[i_end][j_end];
            System.out.print(current);
            int [] temp = {current.returnRow(), current.returnCol()};
            solArray[counter] = temp;
            counter++;
            grid_[current.i][current.j].solution = true;

            while (current.parent != null){
                System.out.print(" -> " + current.parent);
                int [] temp_ = {current.parent.returnRow(), current.parent.returnCol()};
                solArray[counter] = temp_;
                counter++;
                grid_[current.parent.i][current.parent.j].solution = true;
                current = current.parent;
            }

            System.out.println("\n");

            for(int i = 0; i < grid_.length; i++){
                for(int j = 0; j < grid_[i].length; j++){
                    if (i == i_start && j == j_start)
                        System.out.print("S ");
                    else if(i == i_end && j == j_end)
                        System.out.print("D ");
                    else if (grid_[i][j] != null)
                        System.out.printf("%-3s", grid_[i][j].solution ? "X" : "0");
                    else
                        System.out.print("B ");
                }
                System.out.println();
            }
            System.out.println();
        } else
            System.out.println("No Possible Path");

        return solArray;
    }

    public void setGrid_(Cell[][] grid_) {
        this.grid_ = grid_;
    }

//    public static void main(String[] args) {
//
//    }
}