package UI;

public class Cell {

    //UtilityFiles.Cell Coordinates.
    public int i, j;

    //Parent cell definition
    public Cell parent;

    //Heuristic Value variable
    public int heuristicValue;

    //Final value variable
    public int finalValue;

    public boolean solution;

    public Cell (int i, int j){
        this.i = i;
        this.j = j;
    }

    public int returnRow(){
        return i;
    }

    public int returnCol(){
        return j;
    }


    @Override
    public String toString(){
        return ( "[" + i + ", " + j + "]");
    }

}
