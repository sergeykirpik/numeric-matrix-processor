package processor;

public class HorizontalLineTransposeStrategy implements TransposeStrategy {

    private final int rows;
    private final int cols;

    public HorizontalLineTransposeStrategy(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    @Override
    public int transposedRow(int row, int col) {
        return rows - row - 1;
    }

    @Override
    public int transposedCol(int row, int col) {
        return col;
    }

    @Override
    public boolean isDiagonal() {
        return false;
    }
}
