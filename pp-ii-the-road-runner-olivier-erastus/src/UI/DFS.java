package UI;

import Map.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DFS {
    public DFS() {
    }

    static Node start;
    static Node goal;
    static boolean use8directions;

    public static class Node{
        public int row;
        public int col;
        public String weight;
        public Node parent;
        boolean visited = false;
        boolean explored = false;

        Node(int i, int j, String index) {
            this.row = i;
            this.col = j;
            this.weight = index;
        }

        @Override
        public String toString() {
            return weight + " at " + row + " and " + col;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }
    }

    static Node[][] nodesMap(ArrayList<ArrayList<Elements>> gridMap) {
        Node[][] nodes = new Node[gridMap.size()][gridMap.get(0).size()];
        for (int i = 0; i < gridMap.size(); i++) {
            for (int j = 0; j < gridMap.get(0).size(); j++) {
                nodes[i][j] = new Node(i, j, gridMap.get(i).get(j).index);

                if (nodes[i][j].weight.equals("8")) {
                    start = nodes[i][j];
                }
                if (nodes[i][j].weight.equals("9")) {
                    goal = nodes[i][j];
                }
            }
        }
        return nodes;

    }

    ArrayList<Node> dfs(Node v, Node[][] nodes) {
        ArrayList<Node> exploredNodes = new ArrayList<>();

        exploredNodes.add(v);

        while (!exploredNodes.isEmpty()) {
            Node current = exploredNodes.remove(exploredNodes.size()-1);

            System.out.println(current);

            if (current.equals(goal)) {
                System.out.println("Found!!");
                return getPath(current);
            }

            current.visited = true;

            for (Node neighbour : getNeighbours(current, nodes)) {

                if (!neighbour.visited) {
                    neighbour.setParent(current);
                }
                if (!neighbour.explored) {
                    exploredNodes.add(neighbour);
                }
                if (neighbour.equals(goal)){
                    return getPath(neighbour);
                }
            }
        }
        return null;
    }

    private ArrayList<Node> getNeighbours(Node v, Node[][] nodes) {
        int rowLimit = nodes.length;
        int colLimit = nodes[0].length;
        ArrayList<Node> neighbours = new ArrayList<>();
        int i = v.row;
        int j = v.col;
        if (use8directions){
            for (int x = Math.max(0, i - 1); x <= Math.min(i + 1, rowLimit - 1); x++) {
                for (int y = Math.max(0, j - 1); y <= Math.min(j + 1, colLimit - 1); y++) {
                    if (!nodes[x][y].weight.equals("1") && !(x == i && y == j) && !nodes[x][y].visited) {
                        neighbours.add(nodes[x][y]);
                    }
                }
            }
        }
        else{
            for (int y = Math.max(0, j - 1); y <= Math.min(j + 1, colLimit - 1); y++) {
            if (!nodes[i][y].weight.equals("1") && !(y == j) && !nodes[i][y].visited) {
                neighbours.add(nodes[i][y]);
            }
        }
            for (int x = Math.max(0, i - 1); x <= Math.min(i + 1, rowLimit - 1); x++) {
                if (!nodes[x][j].weight.equals("1") && !(x == i) && !nodes[x][j].visited) {
                    neighbours.add(nodes[x][j]);
                }
            }
        }

        return neighbours;
    }



    ArrayList<Node> getPath(Node p) {
        ArrayList<Node> path = new ArrayList<>();

        while (p.parent != null) {
            p = p.parent;
            path.add(p);
        }

        return path;

    }

    static ArrayList<Node> run(ArrayList<ArrayList<Elements>> gridMap, boolean use8Directions) throws IOException {
        use8directions = use8Directions;
        Node[][] nodes = nodesMap(gridMap);
        DFS dfs = new DFS();

        return dfs.dfs(start, nodes);
    }




    public static void main(String[] args) throws IOException { }

}
