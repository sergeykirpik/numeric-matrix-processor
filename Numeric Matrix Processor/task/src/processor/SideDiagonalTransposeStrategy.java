package processor;

public class SideDiagonalTransposeStrategy implements TransposeStrategy {

    private final int rows;
    private final int cols;

    public SideDiagonalTransposeStrategy(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    @Override
    public int transposedRow(int row, int col) {
        return cols - col - 1;
    }

    @Override
    public int transposedCol(int row, int col) {
        return rows - row - 1;
    }

    @Override
    public boolean isDiagonal() {
        return true;
    }
}
