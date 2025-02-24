import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String testFolder = "test/"; // Path relatif dari lokasi Main.class

        System.out.println("Masukkan nama file input (contoh: input.txt):");
        String inputFilename = scanner.next();
        File inputFile = new File(testFolder + inputFilename);

        try {
            IO io = new IO();

            Board board = io.readBoard(inputFile.getAbsolutePath());
            List<Block> blocks = io.readBlocks(inputFile.getAbsolutePath());

            Solver solver = new Solver(board, blocks);
            long startTime = System.currentTimeMillis();
            boolean solved = solver.solve(0);
            long endTime = System.currentTimeMillis();

            solver.setTimeSearching(endTime - startTime);

            if (solved) {
                System.out.println("\nSolusi ditemukan!");
                board.display();
            } else {
                System.out.println("\nTidak ada solusi yang ditemukan.");
            }

            System.out.println("Jumlah kasus yang ditinjau: " + solver.getCaseReviewed());
            System.out.println("Durasi pencarian: " + solver.getTimeSearching() + " ms");

            System.out.println("Apakah anda ingin menyimpan solusi? (ya/tidak):");
            if (scanner.next().equalsIgnoreCase("ya")) {
                System.out.println("Masukkan nama file output (contoh: output.txt):");
                String outputFilename = scanner.next();
                File outputFile = new File(testFolder + outputFilename);
                io.writeSolution(outputFile.getAbsolutePath(), board, solver.getCaseReviewed(), solver.getTimeSearching());
                System.out.println("Solusi disimpan ke " + outputFile.getAbsolutePath());
            }

        } catch (IOException e) {
            System.err.println("Terjadi kesalahan saat membaca atau menulis file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Kesalahan: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
