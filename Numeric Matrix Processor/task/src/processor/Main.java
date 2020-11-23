package processor;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
    static NumberFormat formatter = NumberFormat.getInstance(Locale.US);
    static {
        formatter.setGroupingUsed(false);
    }

    public static void main(String[] args) {
        while (true) {
            switch (mainMenu()) {
                case 1:
                    addMatrices();
                    break;
                case 2:
                    multiplyMatrixByConstant();
                    break;
                case 3:
                    multiplyMatrices();
                    break;
                case 4:
                    transposeMatrix();
                    break;
                case 5:
                    calculateDeterminant();
                    break;
                case 6:
                    inverseMatrix();
                    break;
                case 0:
                    return;
            }
        }
    }

    static int mainMenu() {
        while(true) {
            System.out.println();
            System.out.println("1. Add matrices");
            System.out.println("2. Multiply matrix by a constant");
            System.out.println("3. Multiply matrices");
            System.out.println("4. Transpose matrix");
            System.out.println("5. Calculate a determinant");
            System.out.println("6. Inverse matrix");
            System.out.println("0. Exit");
            System.out.print("Your choice: ");
            String s  = scanner.next();
            if (s.matches("[0-6]")) {
                return Integer.parseInt(s);
            }
        }
    }

    static int readTransposeType() {
        while(true) {
            System.out.println();
            System.out.println("1. Main diagonal");
            System.out.println("2. Side diagonal");
            System.out.println("3. Vertical line");
            System.out.println("4. Horizontal line");
            System.out.print("Your choice: ");
            String s  = scanner.next();
            if (s.matches("[1-4]")) {
                return Integer.parseInt(s);
            }
        }
    }

    public static void addMatrices() {
        Matrix m1 = readMatrix("first matrix");
        Matrix m2 = readMatrix("second matrix");

        if (Matrix.canAdd(m1, m2)) {
            Matrix res = Matrix.add(m1, m2);
            printResult(res);
        } else {
            System.out.println("ERROR: Can not add matrices");
        }
    }

    public static void multiplyMatrixByConstant() {
        Matrix m = readMatrix("matrix");
        System.out.print("Enter constant: ");
        int scalar = scanner.nextInt();
        Matrix res = m.multiply(scalar);
        printResult(res);
    }

    public static void multiplyMatrices() {
        Matrix m1 = readMatrix("first matrix");
        Matrix m2 = readMatrix("second matrix");

        if (Matrix.canMultiply(m1, m2)) {
            Matrix res = m1.multiply(m2);
            printResult(res);
        } else {
            System.out.println("ERROR: Can not multiply matrices");
        }
    }

    public static void transposeMatrix() {
        int transposeType = readTransposeType();
        Matrix m = readMatrix("matrix");
        TransposeStrategy strategy;
        switch (transposeType) {
            case 1:
                strategy = new MainDiagonalTransposeStrategy();
                break;
            case 2:
                strategy = new SideDiagonalTransposeStrategy(m.rows(), m.cols());
                break;
            case 3:
                strategy = new VerticalLineTransposeStrategy(m.rows(), m.cols());
                break;
            case 4:
                strategy = new HorizontalLineTransposeStrategy(m.rows(), m.cols());
                break;
            default:
                throw new UnsupportedOperationException("Invalid transpose type");
        }
        Matrix res = m.transpose(strategy);
        printResult(res);
    }

   public static void calculateDeterminant() {
        Matrix m = readMatrix("matrix");
        if (m.isSquared()) {
            printResult(m.determinant());
        } else {
            System.out.println("ERROR: Can not calculate determinant");
        }
   }

   public static void inverseMatrix() {
        Matrix m = readMatrix("matrix");
        if (m.canInverse()) {
            Matrix res = m.inverse();
            printResult(res);
        } else {
            System.out.println("This matrix doesn't have an inverse.");
        }
   }

    public static Matrix readMatrix(String matrixName) {
        System.out.printf("Enter size of %s: ", matrixName);
        int rows = scanner.nextInt();
        int cols = scanner.nextInt();
        System.out.printf("Enter %s:\n", matrixName);

        Matrix matrix = new Matrix(rows, cols);
        for (int row = 0; row < matrix.rows(); row++) {
            for (int col = 0; col < matrix.cols(); col++) {
                double val = scanner.nextDouble();
                matrix.set(row, col, val);
            }
        }
        return matrix;
    }

    public static void printResult(Matrix matrix) {
        System.out.println("The result is:");
        for (int row = 0; row < matrix.rows(); row++) {
            for (int col = 0; col < matrix.cols(); col++) {
                double val = matrix.get(row, col);
                System.out.printf("%s ", formatter.format(val));
            }
            System.out.println();
        }
    }

    public static void printResult(double res) {
        System.out.println("The result is:");
        System.out.println(res);
    }
}


