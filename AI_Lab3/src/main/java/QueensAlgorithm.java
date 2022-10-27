import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Each of n queens represents one line in the matrix.
 * The domain of the queen is the column index that is available.
 */
@Getter
@Setter
public class QueensAlgorithm {
    private int queensNumber;

    private int[][] queensMatrix;
    private List<List<Integer>> queensDomains;
    private int[] queensPositions;

    private Set<String> queensSolutions = new HashSet<>();

    //    public void findQueensBKT(int[] queensPositions, int currentQueenIndex,
//                              List<List<Integer>> queensDomains) {
//        if (currentQueenIndex == queensNumber) {
//            System.out.println("I have found a solution: ");
//            System.out.println(Arrays.toString(queensPositions));
//            return;
//        }
//
//        List<Integer> queenDomainMRV=
//                    getQueenDomainMRV(queensDomains.get(queensPositions[currentQueenIndex]), queensDomains);
//
//        for (Integer nextQueenPosition : queenDomainMRV) {
//            int[] newQueensPositions = Arrays.copyOf(queensPositions, queensNumber);
//            List<List<Integer>> newQueensDomains =
//                    forwardChecking(currentQueenIndex, queensDomains, nextQueenPosition, newQueensPositions);
//            System.out.println("///\n" + Arrays.toString(queensPositions));
//            System.out.println(queenDomainMRV);
//            System.out.println("ITERARE:");
//            System.out.println(nextQueenPosition);
//            System.out.println(newQueensDomains);
//            System.out.println(Arrays.toString(newQueensPositions));
//            if (newQueensDomains != null && queensDomains.get(currentQueenIndex).contains(nextQueenPosition)) {
//                newQueensPositions[currentQueenIndex] = nextQueenPosition;
//
//                findQueensBKT(newQueensPositions, currentQueenIndex + 1, newQueensDomains);
//            }
//        }
//    }
    private boolean isSolution(int[] queensPositions) {
        for (int i = 0; i < queensNumber; ++i) {
            if (queensPositions[i] == -1) {
                return false;
            }
        }
        return true;
    }

    public void findQueensBKT(int[] queensPositions, int currentQueenIndex,
                              List<List<Integer>> currentQueenDomains) {
        for (Integer queenPosition : currentQueenDomains.get(currentQueenIndex)) {
            int[] newQueensPositions = Arrays.copyOf(queensPositions, queensNumber);
            newQueensPositions[currentQueenIndex] = queenPosition;

            if (isSolution(newQueensPositions)) {
//                System.out.println("Found a solution: ");
//                System.out.println(Arrays.toString(newQueensPositions));
                queensSolutions.add(Arrays.toString(newQueensPositions));
            } else {
//                System.out.println("DOMAINS");
//                System.out.println(currentQueenDomains);
                List<List<Integer>> newQueensDomains =
                        forwardChecking(currentQueenIndex, currentQueenDomains, currentQueenIndex, newQueensPositions);
//                System.out.println("DOMAINS AFTER");
//                System.out.println(currentQueenDomains);

                if (newQueensDomains != null) {
                    List<Integer> notVisited = new ArrayList<>();
                    for (int i = 0; i < queensNumber; ++i) {
                        notVisited.add(i);
                    }
                    for (int i = 0; i < queensNumber; ++i) {
                        if (newQueensPositions[i] != -1) {
                            notVisited.remove(Integer.valueOf(i));
                        }
                    }

                    List<Integer> queenDomainMRV =
                            getQueenDomainMRV(notVisited, newQueensDomains);

//                    System.out.println("///\n" + Arrays.toString(queensPositions));
//                    System.out.println(Arrays.toString(newQueensPositions));
//                    System.out.println(notVisited);
//                    System.out.println(queenDomainMRV);
//                    System.out.println("ITERARE:");
//                    System.out.println(currentQueenIndex);
//                    System.out.println(newQueensDomains);

                    for (Integer nextQueenPosition : queenDomainMRV) {
                        findQueensBKT(newQueensPositions, nextQueenPosition, newQueensDomains);
                    }
                }

//                    List<Integer> notVisited = new ArrayList<>();
//                for(int i=0;i<queensNumber;++i){
//                    notVisited.add(i);
//                }
//                for(int i=0;i<queensNumber;++i){
//                    notVisited.remove(Integer.valueOf(newQueensPositions[i]));
//                }
//
//                List<Integer> queenDomainMRV=
//                    getQueenDomainMRV(notVisited, queensDomains);
//
//                for (Integer nextQueenPosition : queenDomainMRV) {
//                    List<List<Integer>> newQueensDomains =
//                            forwardChecking(currentQueenIndex, queensDomains, nextQueenPosition, newQueensPositions);
//                    System.out.println("///\n" + Arrays.toString(queensPositions));
//                    System.out.println(queenDomainMRV);
//                    System.out.println("ITERARE:");
//                    System.out.println(nextQueenPosition);
//                    System.out.println(newQueensDomains);
//                    System.out.println(Arrays.toString(newQueensPositions));
//                    if (newQueensDomains != null && queensDomains.get(currentQueenIndex).contains(nextQueenPosition)) {
//                        findQueensBKT(newQueensPositions, nextQueenPosition, newQueensDomains);
//                    }
//                }
            }
        }
    }

    private List<Integer> getQueenDomainMRV(List<Integer> queenDomain, List<List<Integer>> queensDomains) {
//        System.out.println("SORTING:");
        List<Integer> newQueenDomain = new ArrayList<>(queenDomain);
//        System.out.println(newQueenDomain);
        newQueenDomain.sort(Comparator.comparingInt(pos -> queensDomains.get(pos).size()));
//        System.out.println(newQueenDomain);
        return newQueenDomain;
    }

    private void removeCollidingQueenPositions(List<Integer> queenDomain, int position, int offset) {
        queenDomain.remove(Integer.valueOf(position - offset));
        queenDomain.remove(Integer.valueOf(position));
        queenDomain.remove(Integer.valueOf(position + offset));
    }

    private List<List<Integer>> forwardChecking(int currentQueenIndex, List<List<Integer>> queensDomains,
                                                int currentQueenPosition, int[] queensPositions) {
        List<List<Integer>> newQueensDomains = new ArrayList<>();
        for (int i = 0; i < queensNumber; ++i) {
            newQueensDomains.add(new ArrayList<>(queensDomains.get(i)));
        }

        for (int i = currentQueenIndex - 1, j = 1; i >= 0; --i, ++j) {
            removeCollidingQueenPositions(newQueensDomains.get(i), queensPositions[currentQueenPosition], j);

            int finalI = i;
            if (Arrays.stream(queensPositions).anyMatch(pos -> pos == finalI) && newQueensDomains.get(i).size() == 0) {
                return null;
            }
        }

//        System.out.println("HERE");
//        System.out.println(newQueensDomains);

        for (int i = currentQueenIndex + 1, j = 1; i < queensNumber; ++i, ++j) {
            removeCollidingQueenPositions(newQueensDomains.get(i), queensPositions[currentQueenPosition], j);

            int finalI = i;
            if (Arrays.stream(queensPositions).anyMatch(pos -> pos == finalI) && newQueensDomains.get(i).size() == 0) {
                return null;
            }
        }

        return newQueensDomains;
    }

    public void initQueensDomains() {
        queensDomains = new ArrayList<>();
        for (int i = 0; i < queensNumber; ++i) {
            queensDomains.add(new ArrayList<>());
            for (int j = 0; j < queensNumber; ++j) {
                if (queensMatrix[i][j] == 0) {
                    queensDomains.get(i).add(j);
                }
            }
        }
    }

    private void initQueensPositions() {
        queensPositions = new int[queensNumber];

        for (int i = 0; i < queensNumber; ++i) {
            queensPositions[i] = -1;
        }
    }

    public void readInstance(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            queensNumber = scanner.nextInt();
            queensMatrix = new int[queensNumber][queensNumber];
            initQueensPositions();

            while (scanner.hasNext()) {
                int firstPos = scanner.nextInt();
                int secondPos = scanner.nextInt();

                queensMatrix[firstPos][secondPos] = -1;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File cannot be opened!");
        }
    }

    public void printMatrix() {
        for (int i = 0; i < queensNumber; ++i) {
            for (int j = 0; j < queensNumber; ++j) {
                System.out.printf("%6d", queensMatrix[i][j]);
            }
            System.out.println();
        }
    }

    public void printDomains() {
        for (int i = 0; i < queensNumber; ++i) {
            System.out.println(queensDomains.get(i));
        }
    }

    public void printSolution() {
        System.out.println("The solutions:");
        for (String solution : queensSolutions) {
            System.out.println(solution);
        }
    }

    public static void main(String[] args) {
        QueensAlgorithm queensAlgorithm = new QueensAlgorithm();
        queensAlgorithm.readInstance("C:\\Users\\prico\\OneDrive\\Desktop\\Facultate\\AI" +
                "\\AI_Tudose_Pricop_Tudors_A2\\AI_Lab3\\src\\main\\resources\\file.in");
        queensAlgorithm.initQueensDomains();
        queensAlgorithm.printMatrix();
        queensAlgorithm.printDomains();
        List<Integer> allPositions = new ArrayList<>();
        for (int i = 0; i < queensAlgorithm.getQueensNumber(); ++i) {
            allPositions.add(i);
        }
//        System.out.println("HERE " + queensAlgorithm.getQueenDomainMRV(allPositions, queensAlgorithm.getQueensDomains()));
        for (Integer startPosition : queensAlgorithm.getQueenDomainMRV(allPositions, queensAlgorithm.getQueensDomains())) {
//            System.out.println("FFS " + startPosition);
//            System.out.println("HEREHERE " + queensAlgorithm.getQueensDomains());
            queensAlgorithm.findQueensBKT(
                    queensAlgorithm.getQueensPositions(), startPosition, queensAlgorithm.getQueensDomains());
        }
//        queensAlgorithm.findQueensBKT(
//                queensAlgorithm.getQueensPositions(), 0, queensAlgorithm.getQueensDomains());

        queensAlgorithm.printSolution();
    }
}
