package Algorithms;

import BucketUtil.BucketState;
import BucketUtil.Transitions;

import java.util.HashSet;
import java.util.Set;

public class BucketBacktracking {
    private final Set<BucketState> visitedStates = new HashSet<>();

    public void initBKT(BucketState bucketState) {
        if (bucketState.isViable()) {
            runBKT(bucketState);
        } else {
            System.out.println("There is no solution available!");
        }
    }

    private void runBKT(BucketState bucketState) {
        if (bucketState.isFinal()) {
            bucketState.printSolution();
        }
        if (visitedStates.contains(bucketState)) {
            return;
        }
        visitedStates.add(bucketState);
        for (Transitions transition : Transitions.values()) {
            runBKT(bucketState.getNextBucketState(transition));
        }
    }
}
