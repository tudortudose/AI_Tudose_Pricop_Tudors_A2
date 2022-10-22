import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Getter
@Setter
public class Queens {
    private int n;
    private List<List<Integer>> matrix;

    private List<List<QueenPosition>> domains;

    @Getter
    @Setter
    @AllArgsConstructor
    private class QueenPosition {
        private int line;
        private int column;

        @Override
        public String toString() {
            return "[" + line + " : " + column + "]";
        }
    }

    public List<List<Integer>> readInstance(String filename) {
        List<List<Integer>> matrix = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(filename))) {

            n = scanner.nextInt();

            for (int i = 0; i < n; ++i) {
                matrix.add(new ArrayList<>());
                for (int j = 0; j < n; ++j) {
                    matrix.get(i).add(0);
                }
            }

            while (scanner.hasNext()) {
                int firstPos = scanner.nextInt();
                int secondPos = scanner.nextInt();

                matrix.get(firstPos).set(secondPos, -1);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return matrix;
    }

    public void printMatrix() {
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                System.out.printf("%6d", matrix.get(i).get(j));
            }
            System.out.println("");
        }
    }

    public void FC(int index) {
//        List<List<Integer>> matrixCopy = matrix.

        for (int j = 0; j < n; ++j) {
            if (matrix.get(index).get(j) == 0) {
                matrix.get(index).set(j, 1); //queen put


            }
        }
    }

    private void addQueensDomains() {
        domains = new ArrayList<>();

        for (int i = 0; i < n; ++i) {
            domains.add(new ArrayList<>());
            for (int j = 0; j < n; ++j) {
                if (matrix.get(i).get(j) == 0) {
                    domains.get(i).add(new QueenPosition(i, j));
                }
            }
        }
    }

    private void markAttackedPositions() {

    }

    private void printDomains() {
        for (int i = 0; i < n; ++i) {
            System.out.println(domains.get(i));
        }
    }

    public static void main(String[] args) {
        Queens queens = new Queens();
        queens.setMatrix(queens.readInstance("C:\\Users\\prico\\OneDrive\\Desktop\\Facultate\\AI" +
                "\\AI_Tudose_Pricop_Tudors_A2\\AI_Lab3\\src\\main\\resources\\file.in"));
        queens.addQueensDomains();
        queens.printMatrix();
        queens.printDomains();
    }
}
