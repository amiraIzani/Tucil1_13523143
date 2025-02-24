import java.util.ArrayList;
import java.util.List;

public class Block {
    private char id;
    private List<int[][]> shapes;
    private List<int[]> placedCoordinates;

    public Block(char id) {
        this.id = id;
        this.shapes = new ArrayList<>();
    }

    public char getId() {
        return id;
    }

    public List<int[][]> getShapes() {
        return shapes;
    }

    public void addShape(int[][] shape) {
        if (shapes.size() >= 26) {
            throw new IllegalStateException("Tidak dapat menambahkan lebih dari 26 bentuk untuk blok " + id);
        }
        shapes.add(shape);
    }

    public int[][] rotate(int[][] shape, int rotation) {
        int[][] rotatedShape = shape;
        for (int i = 0; i < rotation; i++) {
            rotatedShape = rotate(rotatedShape);
        }
        return rotatedShape;
    }

    public int[][] rotate(int[][] shape) {
        int[][] rotated = new int[shape.length][2];
        for (int i = 0; i < shape.length; i++) {
            int x = shape[i][0];
            int y = shape[i][1];
            rotated[i][0] = y;
            rotated[i][1] = -x;
        }
        return rotated;
    }

    public int[][] mirror(int[][] shape) {
        int[][] mirrored = new int[shape.length][2];
        for (int i = 0; i < shape.length; i++) {
            int x = shape[i][0];
            int y = shape[i][1];
            mirrored[i][0] = x;
            mirrored[i][1] = -y;
        }
        return mirrored;
    }

    public void setPlacedCoordinates(List<int[]> coordinates) {
        this.placedCoordinates = coordinates;
    }

    public List<int[]> getPlacedCoordinates() {
        return placedCoordinates;
    }
}
