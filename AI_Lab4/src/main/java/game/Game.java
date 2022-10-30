package game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Game {
    private List<String> firstPlayerMoves;
    private List<String> secondPlayerMoves;

    private String firstPlayerName;
    private String secondPlayerName;

    private Pair[][] movesCost;

    public Game() {
        firstPlayerMoves = new ArrayList<>();
        secondPlayerMoves = new ArrayList<>();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public class Pair {
        private int firstCost;
        private int secondCost;

        @Override
        public String toString() {
            return firstCost + " / " + secondCost;
        }
    }

    public void initCostMatrix(Scanner scanner) {
        movesCost = new Pair[firstPlayerMoves.size()][secondPlayerMoves.size()];

        for (int i = 0; i < firstPlayerMoves.size(); ++i) {
            String costsLine = scanner.nextLine();

            StringTokenizer stringTokenizer = new StringTokenizer(costsLine, " ");
            for (int j = 0; j < secondPlayerMoves.size(); ++j) {
                String currentCost = stringTokenizer.nextToken();

                StringTokenizer costStringTokenizer = new StringTokenizer(currentCost, "/");
                movesCost[i][j] = new Pair(Integer.parseInt(costStringTokenizer.nextToken()),
                        Integer.parseInt(costStringTokenizer.nextToken()));
            }
        }
    }

    public void readInstance(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            String firstPlayerInput = scanner.nextLine();
            StringTokenizer stringTokenizer = new StringTokenizer(firstPlayerInput, " ");
            firstPlayerName = stringTokenizer.nextToken();
            while (stringTokenizer.hasMoreTokens()) {
                firstPlayerMoves.add(stringTokenizer.nextToken());
            }

            String secondPlayerInput = scanner.nextLine();
            stringTokenizer = new StringTokenizer(secondPlayerInput, " ");
            secondPlayerName = stringTokenizer.nextToken();
            while (stringTokenizer.hasMoreTokens()) {
                secondPlayerMoves.add(stringTokenizer.nextToken());
            }

            initCostMatrix(scanner);
        } catch (FileNotFoundException e) {
            System.out.println("File cannot be opened!");
        }
    }

    public void printCostMatrix() {
        System.out.println("First player moves " + firstPlayerName + " :");
        System.out.println(firstPlayerMoves);

        System.out.println("Second player moves " + secondPlayerName + " :");
        System.out.println(secondPlayerMoves);

        System.out.println("\n/////////// COST MATRIX ///////////");

        for (int i = 0; i < firstPlayerMoves.size(); ++i) {
            for (int j = 0; j < secondPlayerMoves.size(); ++j) {
                System.out.print(movesCost[i][j] + "    ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.readInstance("C:\\Users\\prico\\OneDrive\\Desktop\\Facultate\\AI\\" +
                "AI_Tudose_Pricop_Tudors_A2\\AI_Lab4\\src\\main\\resources\\file.input");
        game.printCostMatrix();
    }
}
