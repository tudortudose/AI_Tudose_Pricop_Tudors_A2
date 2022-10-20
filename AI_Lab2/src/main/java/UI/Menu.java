package UI;

import Algorithms.BucketAStar;
import Algorithms.BucketBFS;
import Algorithms.BucketBacktracking;
import Algorithms.BucketHillClimbing;
import BucketUtil.BucketState;

import java.util.Scanner;

public class Menu {
    private enum Actions {
        QUIT,
        RUN,
        READ,
    }

    private int firstBucketCapacity, secondBucketCapacity, desiredQuantity;
    private int algorithm, action = Actions.READ.ordinal();

    private void readData() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter first bucket capacity: ");
        firstBucketCapacity = scanner.nextInt();
        System.out.println("Enter second bucket capacity: ");
        secondBucketCapacity = scanner.nextInt();
        System.out.println("Enter the desired quantity: ");
        desiredQuantity = scanner.nextInt();
    }

    private void selectAlgorithm() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("""
                Select the algorithm you want to perform:
                1.Backtracking
                2.BFS
                3.HillClimbing
                4.AStar
                """);
        algorithm = scanner.nextInt();
    }

    private void execAlgorithm() {
        BucketState bucketState = new BucketState();
        bucketState.initializeState(firstBucketCapacity, secondBucketCapacity, desiredQuantity);

        switch (algorithm) {
            case 1 -> {
                System.out.println("Backtracking selected");
                BucketBacktracking bucketBacktracking = new BucketBacktracking();
                bucketBacktracking.initBKT(bucketState);
            }
            case 2 -> {
                System.out.println("BFS selected");
                BucketBFS bucketBFS = new BucketBFS();
                bucketBFS.initBFS(bucketState);
            }
            case 3 -> {
                System.out.println("HillClimbing selected");
                BucketHillClimbing bucketHillClimbing = new BucketHillClimbing();
                bucketHillClimbing.initHillClimbing(bucketState);
            }
            case 4 -> {
                System.out.println("AStar selected");
                BucketAStar bucketAStar = new BucketAStar();
                bucketAStar.initAStar(bucketState);
            }
        }
    }

    private void continueExec() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("""
                Enter 0 if you want to quit.
                Enter 1 if you want to continue with the same instance.
                Enter 2 if you want to introduce a new instance.
                """);
        action = scanner.nextInt();
    }

    public void run() {
        do {
            if (action == Actions.READ.ordinal()) {
                readData();
            }
            selectAlgorithm();
            execAlgorithm();
            continueExec();
        } while (action != Actions.QUIT.ordinal()
        );
    }

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.run();
    }
}
