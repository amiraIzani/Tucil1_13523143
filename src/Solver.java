import java.util.List;

public class Solver {
    private Board board;
    private List<Block> blocks;
    private long timeSearching;
    private int caseReviewed = 0;

    public Solver(Board board, List<Block> blocks) {
        this.board = board;
        this.blocks = blocks;
    }

    public boolean solve(int idx) {
        if (idx == blocks.size()) {
            return board.isFull();
        }

        Block block = blocks.get(idx);

        for (int[][] shape : block.getShapes()) {
            if (shape == null) {
                continue;
            }

            for (int rotation = 0; rotation < 4; rotation++) {
                for (int mirror = 0; mirror < 2; mirror++) {
                    int[][] currentShape;
                    if (mirror == 0) {
                        currentShape = shape;
                    } else {
                        currentShape = block.mirror(shape);
                    }

                    currentShape = block.rotate(currentShape, rotation);

                    for (int row = 0; row < board.getRows(); row++) {
                        for (int col = 0; col < board.getCols(); col++) {
                            if (board.canPlaceBlock(block, row, col, currentShape)) {
                                caseReviewed++;
                                board.placeBlock(block, row, col, currentShape);

                                if (solve(idx + 1)) {
                                    return true;
                                }

                                board.removeBlock(block);
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    public int getCaseReviewed() {
        return caseReviewed;
    }

    public void setTimeSearching(long duration) {
        this.timeSearching = duration;
    }

    public long getTimeSearching() {
        return timeSearching;
    }
}

