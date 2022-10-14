package Algorithms;

import BucketUtil.BucketState;
import BucketUtil.Transitions;
import lombok.Getter;

import javax.swing.plaf.nimbus.State;
import java.util.*;

public class AStar {
    @Getter
    private class StateFunctionPair implements Comparable<StateFunctionPair> {
        private BucketState bucketState;
        private int stateFunction;

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

            runAStar(bucketState);
        } else {
            System.out.println("There is no solution available!");
        }
    }

    public void runAStar(BucketState bucketState) {
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
                            new StateFunctionPair(neighbourState, heuristicToNeighbour);

                    if (!bucketStateQueue.contains(nextStateFunctionPair)) {
                        bucketStateQueue.add(nextStateFunctionPair);
                    }
                }
            }
        }
    }

    private int computeHeuristic(BucketState bucketState) {
        return 1;
    }

    private int computeDistanceStates(BucketState currentState, BucketState nextState) {
        return 1;
    }

}
