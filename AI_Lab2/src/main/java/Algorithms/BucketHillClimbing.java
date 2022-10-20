package Algorithms;

import BucketUtil.BucketState;
import BucketUtil.Transitions;

import java.util.*;

public class BucketHillClimbing {
    private Map<BucketState, Integer> visitedStates;
    private final Random rand = new Random();
    private final static int visitedLimit = 1;

    public void initHillClimbing(BucketState bucketState) {
        if (bucketState.isViable()) {
            for (int i = 0; i < 100; i++) {
                runHillClimbing(bucketState);
            }
        } else {
            System.out.println("There is no solution available!\n");
        }
    }

    private void runHillClimbing(BucketState bucketState) {
        visitedStates = new HashMap<>();
        visitedStates.put(bucketState, 1);
        List<BucketState> neighborStates;
        BucketState neighborState;
        while (true) {
            if (bucketState.isFinal()) {
                bucketState.printSolution();
                return;
            }
            neighborStates = selectAllNeighbors(bucketState);
            neighborState = selectOneBetterNeighbor(bucketState, neighborStates);
            if (neighborState == null) {
                System.out.println("Failure");
                System.out.println("\n\\\\\\\\\\\\\\\\\\\\\\\\\\\\\n");
                return;
            }
            bucketState = neighborState;
        }
    }

    private BucketState selectOneBetterNeighbor(BucketState currentState, List<BucketState> bucketStates) {
        if (bucketStates.size() == 0) return null;
        int currentStateH = computeHeuristic(currentState);
        int neighborStateH;
        int randomIndex;
        do {
            randomIndex = rand.nextInt(bucketStates.size());
            neighborStateH = computeHeuristic(bucketStates.get(randomIndex));
            if (neighborStateH <= currentStateH) {
                return bucketStates.get(randomIndex);
            }
            bucketStates.remove(randomIndex);
        } while (bucketStates.size() > 0);
        return null;
    }

    private List<BucketState> selectAllNeighbors(BucketState bucketState) {
        List<BucketState> bucketStates = new ArrayList<>();
        for (Transitions transition : Transitions.values()) {
            BucketState newState = bucketState.getNextBucketState(transition);
            if (!visitedStates.containsKey(newState)) {
                visitedStates.put(newState, 1);
                bucketStates.add(newState);
            } else if (visitedStates.get(newState) < visitedLimit) {
                visitedStates.put(newState, visitedStates.get(newState) + 1);
                bucketStates.add(newState);
            }
        }
        return bucketStates;
    }

    private int computeHeuristic(BucketState bucketState) {
        return Math.min(Math.abs(bucketState.getFirstBucketCurrent() - bucketState.getDesiredQuantity()),
                Math.abs(bucketState.getSecondBucketCurrent() - bucketState.getDesiredQuantity()));
    }

    public static void main(String[] args) {
        BucketState bucketState = new BucketState();
        bucketState.initializeState(7, 10, 4);

        BucketHillClimbing bucketHillClimbing = new BucketHillClimbing();
        bucketHillClimbing.initHillClimbing(bucketState);
    }
}
