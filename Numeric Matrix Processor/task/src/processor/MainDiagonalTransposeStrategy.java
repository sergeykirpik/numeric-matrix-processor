package processor;

public class MainDiagonalTransposeStrategy implements TransposeStrategy {

    @Override
    public int transposedRow(int row, int col) {
        return col;
    }

    @Override
    public int transposedCol(int row, int col) {
        return row;
    }

    @Override
    public boolean isDiagonal() {
        return true;
    }
}
