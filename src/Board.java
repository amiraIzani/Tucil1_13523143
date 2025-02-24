import java.util.ArrayList;
import java.util.List;

public class Board {
    private int rows;
    private int cols;
    private char[][] grid;
    private List<Block> blocks;

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new char[rows][cols];
        this.blocks = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = '_';
            }
        }
    }

    public boolean isFull() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == '_') {
                    return false;
                }
            }
        }
        return true;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public boolean canPlaceBlock(Block block, int x, int y, int[][] currentShape) {
        for (int[] square : currentShape) {
            int newX = x + square[0];
            int newY = y + square[1];
            if (newX < 0 || newX >= rows || newY < 0 || newY >= cols) {
                return false;
            }
            if (grid[newX][newY] != '_') {
                return false;
            }
        }
        return true;
    }

    public void placeBlock(Block block, int x, int y, int[][] currentShape) {
        if (!canPlaceBlock(block, x, y, currentShape)) {
            throw new IllegalArgumentException("Tidak dapat menempatkan blok di posisi ini");
        }

        List<int[]> placedCoordinates = new ArrayList<>();
        for (int[] square : currentShape) {
            int newX = x + square[0];
            int newY = y + square[1];
            grid[newX][newY] = block.getId();
            placedCoordinates.add(new int[]{newX, newY});
        }

        block.setPlacedCoordinates(placedCoordinates);
    }

    public void removeBlock(Block block) {
        List<int[]> placedCoordinates = block.getPlacedCoordinates();
        if (placedCoordinates == null || placedCoordinates.isEmpty()) {
            throw new IllegalArgumentException("Blok tidak ditemukan di papan");
        }

        for (int[] coord : placedCoordinates) {
            grid[coord[0]][coord[1]] = '_';
        }

        block.setPlacedCoordinates(null);
    }

    public void display() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    public String boardToString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                sb.append(grid[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
