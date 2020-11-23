package processor;

public class VerticalLineTransposeStrategy implements TransposeStrategy {

    private final int rows;
    private final int cols;

    public VerticalLineTransposeStrategy(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    @Override
    public int transposedRow(int row, int col) {
        return row;
    }

    @Override
    public int transposedCol(int row, int col) {
        return cols - col - 1;
    }

    @Override
    public boolean isDiagonal() {
        return false;
    }
}
