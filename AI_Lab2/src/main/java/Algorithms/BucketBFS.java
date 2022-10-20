package Algorithms;

import BucketUtil.BucketState;
import BucketUtil.Transitions;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class BucketBFS {
    private final Set<BucketState> visitedStates = new HashSet<>();
    private final Queue<BucketState> visitedQueue = new ArrayDeque<>();

    public void initBFS(BucketState bucketState) {
        if (bucketState.isViable()) {
            runBFS(bucketState);
        } else {
            System.out.println("There is no solution available!\n");
        }
    }

    public void runBFS(BucketState bucketState) {
        visitedStates.add(bucketState);
        visitedQueue.add(bucketState);

        while (!visitedQueue.isEmpty()) {
            BucketState currentState = visitedQueue.poll();
            if (currentState.isFinal()) {
                currentState.printSolution();
            }

            for (Transitions transition : Transitions.values()) {
                BucketState newState = currentState.getNextBucketState(transition);
                if (!visitedStates.contains(newState)) {
                    visitedQueue.add(newState);
                    visitedStates.add(newState);
                }
            }
        }
    }

    public static void main(String[] args) {
        BucketState bucketState = new BucketState();
        bucketState.initializeState(7, 10, 5);

        BucketBFS bucketBFS = new BucketBFS();
        bucketBFS.initBFS(bucketState);
    }
}
