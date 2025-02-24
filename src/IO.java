import java.io.*;
import java.util.*;

public class IO {

    public Board readBoard(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {
            String[] firstLine = br.readLine().split(" ");
            int rows = Integer.parseInt(firstLine[0]);
            int cols = Integer.parseInt(firstLine[1]);
            return new Board(rows, cols);
        }
    }

    public List<Block> readBlocks(String filePath) throws IOException {
        List<Block> blocks = new ArrayList<>();
        Set<Character> usedSymbols = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {
            br.readLine();
            br.readLine();

            String currentSymbol = null;
            List<int[]> currentShape = new ArrayList<>();
            int row = 0;

            String line;
            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) continue;

                char firstChar = line.charAt(0);

                if (currentSymbol == null || firstChar != currentSymbol.charAt(0)) {
                    if (!currentShape.isEmpty()) {
                        saveBlock(currentSymbol.charAt(0), currentShape, blocks, usedSymbols);
                        currentShape.clear();
                        row = 0;
                    }
                    currentSymbol = String.valueOf(firstChar);
                    validateSymbol(currentSymbol.charAt(0), usedSymbols);
                }

                for (int col = 0; col < line.length(); col++) {
                    if (line.charAt(col) == currentSymbol.charAt(0)) {
                        currentShape.add(new int[]{row, col});
                    }
                }
                row++;
            }

            if (!currentShape.isEmpty()) {
                saveBlock(currentSymbol.charAt(0), currentShape, blocks, usedSymbols);
            }
        }

        return blocks;
    }

    public void writeSolution(String filePath, Board board, int casesReviewed, long timeSearching) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath)))) {
            bw.write("Solusi papan:\n");
            bw.write(board.boardToString());
            bw.write("\nJumlah kasus yang ditinjau: " + casesReviewed);
            bw.write("\nDurasi pencarian: " + timeSearching + " ms\n");
        }
    }

    private void saveBlock(char id, List<int[]> shapeCoordinates, List<Block> blocks, Set<Character> usedSymbols) {
        if (shapeCoordinates.isEmpty()) {
            throw new IllegalArgumentException("Blok " + id + " tidak memiliki bentuk valid!");
        }

        int[][] shape = shapeCoordinates.toArray(new int[0][]);

        Block block = new Block(id);
        block.addShape(shape);
        blocks.add(block);
    }

    private void validateSymbol(char symbol, Set<Character> usedSymbols) {
        if (!Character.isUpperCase(symbol) || symbol < 'A' || symbol > 'Z') {
            throw new IllegalArgumentException("Simbol blok harus berupa huruf kapital A-Z! Ditemukan: " + symbol);
        }

        if (usedSymbols.contains(symbol)) {
            throw new IllegalArgumentException("Simbol blok " + symbol + " sudah digunakan!");
        }

        usedSymbols.add(symbol);
    }

    private String formatShape(int[][] shape) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < shape.length; i++) {
            sb.append("(").append(shape[i][0]).append(", ").append(shape[i][1]).append(")");
            if (i < shape.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
