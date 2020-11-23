package processor;

public interface TransposeStrategy {
    int transposedRow(int row, int col);
    int transposedCol(int row, int col);
    boolean isDiagonal();
}
