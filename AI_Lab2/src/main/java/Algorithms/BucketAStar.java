package Algorithms;

import BucketUtil.BucketState;
import BucketUtil.Transitions;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class BucketAStar {
    @Getter
    private static class StateFunctionPair implements Comparable<StateFunctionPair> {
        private final BucketState bucketState;
        private final int stateFunction;

        public StateFunctionPair(BucketState bucketState, Integer stateFunction) {
            this.bucketState = bucketState;
            this.stateFunction = stateFunction;
        }

        @Override
        public int compareTo(StateFunctionPair o) {
            return Integer.compare(this.stateFunction, o.stateFunction);
        }
    }

    private final Queue<StateFunctionPair> bucketStateQueue = new PriorityQueue<>();
    private final Map<BucketState, Integer> statesDistance = new HashMap<>();
    private final Map<BucketState, Integer> statesFunction = new HashMap<>();

    public void initAStar(BucketState bucketState) {
        if (bucketState.isViable()) {
            statesDistance.put(bucketState, 0);
            statesFunction.put(bucketState, computeHeuristic(bucketState));
            bucketStateQueue.add(new StateFunctionPair(bucketState, computeHeuristic(bucketState)));

            runAStar();
        } else {
            System.out.println("There is no solution available!\n");
        }
    }

    public void runAStar() {
        while (!bucketStateQueue.isEmpty()) {
            BucketState currentState = bucketStateQueue.poll().getBucketState();

            if (currentState.isFinal()) {
                currentState.printSolution();
            }

            for (Transitions transition : Transitions.values()) {
                BucketState neighbourState = currentState.getNextBucketState(transition);

                if (!statesDistance.containsKey(neighbourState)) {
                    statesDistance.put(neighbourState, Integer.MAX_VALUE);
                }

                int distanceToNeighbour = statesDistance.get(currentState) +
                        computeDistanceStates(currentState, neighbourState);

                if (distanceToNeighbour < statesDistance.get(neighbourState)) {
                    statesDistance.put(neighbourState, distanceToNeighbour);

                    int heuristicToNeighbour = computeHeuristic(neighbourState);
                    statesFunction.put(neighbourState,
                            statesDistance.get(neighbourState) + heuristicToNeighbour);

                    StateFunctionPair nextStateFunctionPair =
                            new StateFunctionPair(neighbourState, statesFunction.get(neighbourState));

                    if (!bucketStateQueue.contains(nextStateFunctionPair)) {
                        bucketStateQueue.add(nextStateFunctionPair);
                    }
                }
            }
        }
        System.out.println("Done cyka");
    }

    private int computeHeuristic(BucketState bucketState) {
        return Math.min(Math.abs(bucketState.getFirstBucketCurrent() - bucketState.getDesiredQuantity()),
                Math.abs(bucketState.getSecondBucketCurrent() - bucketState.getDesiredQuantity()));
    }

    private int computeDistanceStates(BucketState currentState, BucketState nextState) {
        return 1;
    }

    public static void main(String[] args) {
        BucketState bucketState = new BucketState();
        bucketState.initializeState(7, 10, 5);

        BucketAStar bucketAStar = new BucketAStar();
        bucketAStar.initAStar(bucketState);
    }
}
