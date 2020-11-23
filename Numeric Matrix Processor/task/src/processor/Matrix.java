package processor;

public class Matrix {
    private final double[][] matrix;

    public Matrix(int rows, int cols) {
        matrix = new double[rows][cols];
    }

    public double get(int row, int col) {
        return matrix[row][col];
    }

    public void set(int row, int col, double val) {
        matrix[row][col] = val;
    }

    public int rows() {
        return matrix.length;
    }

    public int cols() {
        return matrix.length == 0 ? 0 : matrix[0].length;
    }

    public static boolean canAdd(Matrix m1, Matrix m2) {
        return m1.rows() == m2.rows() || m1.cols() == m2.cols();
    }

    public static Matrix add(Matrix m1, Matrix m2) {
        if (!canAdd(m1, m2)) {
            throw new InvalidMatrixDimensionsException();
        }
        Matrix r = new Matrix(m1.rows(), m1.cols());
        for (int row = 0; row < r.rows(); row++) {
            for (int col = 0; col < r.cols(); col++) {
                double sum = m1.get(row, col) + m2.get(row, col);
                r.set(row, col, sum);
            }
        }
        return r;
    }

    public static boolean canMultiply(Matrix m1, Matrix m2) {
        return m1.cols() == m2.rows();
    }

    public static Matrix multiply(Matrix m1, Matrix m2) {
        if (!canMultiply(m1, m2)) {
            throw new InvalidMatrixDimensionsException();
        }
        Matrix r = new Matrix(m1.rows(), m2.cols());
        for (int row1 = 0; row1 < m1.rows(); row1++) {
            for (int col2 = 0; col2 < m2.cols(); col2++) {
                double sum = 0;
                for (int col1row2 = 0; col1row2 < m2.rows(); col1row2++) {
                    sum += m1.get(row1, col1row2) * m2.get(col1row2, col2);
                }
                r.set(row1, col2, sum);
            }
        }
        return r;
    }

    public Matrix multiply(Matrix m) {
        return Matrix.multiply(this, m);
    }

    public Matrix multiply(double scalar) {
        Matrix r = new Matrix(rows(), cols());
        for (int row = 0; row < r.rows(); row++) {
            for (int col = 0; col < r.cols(); col++) {
                double val = scalar * get(row, col);
                r.set(row, col, val);
            }
        }
        return r;
    }

    public Matrix transpose(TransposeStrategy strategy) {
        Matrix r;
        if (strategy.isDiagonal()) {
            r = new Matrix(cols(), rows());
        } else {
            r = new Matrix(rows(), cols());
        }
        for (int row = 0; row < rows(); row++) {
            for (int col = 0; col < cols(); col++) {
                int tRow = strategy.transposedRow(row, col);
                int tCol = strategy.transposedCol(row, col);
                r.set(tRow, tCol, get(row, col));
            }
        }
        return r;
    }

    public Matrix transpose() {
        return transpose(new MainDiagonalTransposeStrategy());
    }

    public boolean isSquared() {
        return rows() == cols();
    }

    public double determinant() {
        if (!isSquared()) {
            throw new InvalidMatrixDimensionsException();
        }
        if (rows() == 2) {
            double a11 = get(0,0);
            double a22 = get(1,1);
            double a12 = get(0,1);
            double a21 = get(1,0);
            return a11 * a22 - a12 * a21;
        }
        double determinant = 0;
        for (int col = 0; col < cols(); col++) {
            determinant += get(0, col) * cofactor(0, col);
        }
        return determinant;
    }

    public double cofactor(int row, int col) {
        int sign = (col + row) % 2 == 0 ? 1 : -1;
        return sign * minor(row, col);
    }

    public double minor(int row, int col) {
        Matrix m = new Matrix(rows() - 1, cols() - 1);
        for (int r1 = 0; r1 < rows(); r1++) {
            for (int c1 = 0; c1 < cols(); c1++) {
                int r2 = r1;
                int c2 = c1;
                if (r2 > row) {
                    r2--;
                }
                if (c2 > col) {
                    c2--;
                }
                if (r2 < m.rows() && c2 < m.cols()) {
                    m.set(r2, c2, get(r1, c1));
                }
            }
        }
        return m.determinant();
    }

    public Matrix cofactorMatrix() {
        Matrix m = new Matrix(rows(), cols());
        for (int row = 0; row < rows(); row++) {
            for (int col = 0; col < cols(); col++) {
                double cofactor = cofactor(row, col);
                m.set(row, col, cofactor);
            }
         }
        return m;
    }

    public boolean canInverse() {
        return isSquared() && determinant() != 0;
    }

    public Matrix inverse() {
        if (!canInverse()) {
            throw new InvalidMatrixOperationException();
        }
        return cofactorMatrix().transpose().multiply(1.0 / determinant());
    }

}
