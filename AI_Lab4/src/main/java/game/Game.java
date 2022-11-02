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

@Getter
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
            System.out.println("/////////////////////////////////");
        }

        System.out.println();
    }

    /**
     * Returns whether a player has a dominant strategy or not.
     *
     * @param player 0 -> first player; 1 -> second player;
     */
    public boolean hasDominantStrategy(int player) {
        int[][] markDominantMoves = new int[firstPlayerMoves.size()][secondPlayerMoves.size()];
        if (player == 0) {
            // mark dominant moves for each of other player's fixed moves:
            for (int j = 0; j < secondPlayerMoves.size(); ++j) {
                int maxPayoff = movesCost[0][j].getFirstCost();
                for (int i = 1; i < firstPlayerMoves.size(); ++i) {
                    if (movesCost[i][j].getFirstCost() > maxPayoff) {
                        maxPayoff = movesCost[i][j].getFirstCost();
                    }
                }
                for (int i = 0; i < firstPlayerMoves.size(); ++i) {
                    if (movesCost[i][j].getFirstCost() == maxPayoff) {
                        markDominantMoves[i][j] = 1;
                    }
                }
            }

            // check for marked lines (a dominant strategy) in dominant moves' matrix:
            for (int i = 0; i < firstPlayerMoves.size(); ++i) {
                boolean lineIsStrategy = true;
                for (int j = 0; j < secondPlayerMoves.size(); ++j) {
                    if (markDominantMoves[i][j] == 0) {
                        lineIsStrategy = false;
                    }
                }
                if (lineIsStrategy) {
                    System.out.println("Dominant in line: " + i);
                    System.out.println("Dominant in move: " + firstPlayerMoves.get(i));
                    return true;
                }
            }
        } else if (player == 1) {
            // mark dominant moves for each of other player's fixed moves:
            for (int i = 0; i < firstPlayerMoves.size(); ++i) {
                int maxPayoff = movesCost[i][0].getSecondCost();
                for (int j = 1; j < secondPlayerMoves.size(); ++j) {
                    if (movesCost[i][j].getSecondCost() > maxPayoff) {
                        maxPayoff = movesCost[i][j].getSecondCost();
                    }
                }
                for (int j = 0; j < secondPlayerMoves.size(); ++j) {
                    if (movesCost[i][j].getSecondCost() == maxPayoff) {
                        markDominantMoves[i][j] = 1;
                    }
                }
            }

            // check for marked columns (a dominant strategy) in dominant moves' matrix:
            for (int j = 0; j < secondPlayerMoves.size(); ++j) {
                boolean colIsStrategy = true;
                for (int i = 0; i < firstPlayerMoves.size(); ++i) {
                    if (markDominantMoves[i][j] == 0) {
                        colIsStrategy = false;
                    }
                }
                if (colIsStrategy) {
                    System.out.println("Dominant in column: " + j);
                    System.out.println("Dominant in move: " + secondPlayerMoves.get(j));
                    return true;
                }
            }
        }
        return false;
    }

    public void findNashEquilibria() {
        int[][] markDominantMoves = new int[firstPlayerMoves.size()][secondPlayerMoves.size()];
        // mark dominant moves for first player:
        for (int j = 0; j < secondPlayerMoves.size(); ++j) {
            int maxPayoff = movesCost[0][j].getFirstCost();
            for (int i = 1; i < firstPlayerMoves.size(); ++i) {
                if (movesCost[i][j].getFirstCost() > maxPayoff) {
                    maxPayoff = movesCost[i][j].getFirstCost();
                }
            }
            for (int i = 0; i < firstPlayerMoves.size(); ++i) {
                if (movesCost[i][j].getFirstCost() == maxPayoff) {
                    markDominantMoves[i][j]++;
                }
            }
        }

        // mark dominant moves for second player:
        for (int i = 0; i < firstPlayerMoves.size(); ++i) {
            int maxPayoff = movesCost[i][0].getSecondCost();
            for (int j = 1; j < secondPlayerMoves.size(); ++j) {
                if (movesCost[i][j].getSecondCost() > maxPayoff) {
                    maxPayoff = movesCost[i][j].getSecondCost();
                }
            }
            for (int j = 0; j < secondPlayerMoves.size(); ++j) {
                if (movesCost[i][j].getSecondCost() == maxPayoff) {
                    markDominantMoves[i][j]++;
                }
            }
        }

        System.out.println("Searching Nash equilibria");
        boolean foundNashEq = false;
        // find nash equilibria:
        for (int i = 0; i < firstPlayerMoves.size(); ++i) {
            for (int j = 0; j < secondPlayerMoves.size(); ++j) {
                if (markDominantMoves[i][j] == 2) {
                    System.out.println("Found equilibria at (" + i + ";" + j + ")");
                    foundNashEq = true;
                }
            }
        }

        if (!foundNashEq) {
            System.out.println("No Nash equilibria found!");
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.readInstance("C:\\Users\\prico\\OneDrive\\Desktop\\Facultate\\AI\\" +
                "AI_Tudose_Pricop_Tudors_A2\\AI_Lab4\\src\\main\\resources\\file.input");
        game.printCostMatrix();

        System.out.println("Player " + game.getFirstPlayerName() + " has dominant strategy? " +
                game.hasDominantStrategy(0) + "\n");
        System.out.println("Player " + game.getSecondPlayerName() + " has dominant strategy? " +
                game.hasDominantStrategy(1) + "\n");

        game.findNashEquilibria();
    }
}
