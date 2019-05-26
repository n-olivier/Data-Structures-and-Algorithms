package Map;

public class Elements {
    public String index;
    public int energyCost;
    public int row;
    public int col;
    public String imgPath;
    public String altImgPath;
    public boolean visited;
    public boolean explored;
    public Elements parent;

    public Elements(String index,int energyCost, String path, String altImgPath, int row, int col){
        this.index = index;
        this.energyCost = energyCost;
        this.imgPath = path;
        this.altImgPath = altImgPath;
        this.row = row;
        this.col = col;
    }

    public void setEnergyCost(int energyCost) {
        this.energyCost = energyCost;
    }

    public void setParent(Elements parent) {
        this.parent = parent;
    }

    public Elements getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return this.index;
    }
}
