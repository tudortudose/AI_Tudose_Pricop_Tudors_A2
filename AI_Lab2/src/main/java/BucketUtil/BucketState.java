package BucketUtil;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class BucketState {
    private static final int EMPTY_BUCKET_VALUE = 0;

    private int firstBucketQuantity;
    private int secondBucketQuantity;
    private int desiredQuantity;
    private int firstBucketCurrent;
    private int secondBucketCurrent;

    @EqualsAndHashCode.Exclude
    private BucketState previousState;

    public BucketState(BucketState bucketState) {
        this.firstBucketQuantity = bucketState.firstBucketQuantity;
        this.secondBucketQuantity = bucketState.secondBucketQuantity;
        this.firstBucketCurrent = bucketState.firstBucketCurrent;
        this.secondBucketCurrent = bucketState.secondBucketCurrent;
        this.desiredQuantity = bucketState.desiredQuantity;
        this.previousState = bucketState.previousState;
    }

    public void initializeState(int firstBucketQuantity, int secondBucketQuantity, int desiredQuantity) {
        this.firstBucketQuantity = firstBucketQuantity;
        this.secondBucketQuantity = secondBucketQuantity;
        this.desiredQuantity = desiredQuantity;
        this.firstBucketCurrent = EMPTY_BUCKET_VALUE;
        this.secondBucketCurrent = EMPTY_BUCKET_VALUE;
        this.previousState = null;
    }

    public boolean isFinal() {
        return firstBucketCurrent == desiredQuantity || secondBucketCurrent == desiredQuantity;
    }

    public BucketState getNextBucketState(Transitions transition) {
        BucketState newBucketState = new BucketState(this);
        switch (transition) {
            case FILL1 -> newBucketState.firstBucketCurrent = firstBucketQuantity;
            case FILL2 -> newBucketState.secondBucketCurrent = secondBucketQuantity;
            case EMPTY1 -> newBucketState.firstBucketCurrent = EMPTY_BUCKET_VALUE;
            case EMPTY2 -> newBucketState.secondBucketCurrent = EMPTY_BUCKET_VALUE;
            case TRANSITION1 -> {
                newBucketState.firstBucketCurrent = firstBucketCurrent +
                        Math.min(firstBucketQuantity - firstBucketCurrent, secondBucketCurrent);
                newBucketState.secondBucketCurrent = secondBucketCurrent -
                        Math.min(firstBucketQuantity - firstBucketCurrent, secondBucketCurrent);
            }
            case TRANSITION2 -> {
                newBucketState.secondBucketCurrent = secondBucketCurrent +
                        Math.min(secondBucketQuantity - secondBucketCurrent, firstBucketCurrent);
                newBucketState.firstBucketCurrent = firstBucketCurrent -
                        Math.min(secondBucketQuantity - secondBucketCurrent, firstBucketCurrent);
            }
        }
        newBucketState.previousState = this;
        return newBucketState;
    }

    /**
     * Moving the water between buckets means that we do repetitive subtractions,
     * equivalent to the greatest common divisor (obtained through the same method).
     */
    public boolean isViable() {
        if (desiredQuantity > firstBucketQuantity || desiredQuantity > secondBucketQuantity) {
            return false;
        }
        return desiredQuantity % gcd(firstBucketQuantity, secondBucketQuantity) == 0;
    }

    private int gcd(int first, int second) {
        while (first != second) {
            if (first > second) {
                first = first - second;
            } else {
                second = second - first;
            }
        }
        return first;
    }

    @Override
    public String toString() {
        return "State: " + firstBucketCurrent + " : " + secondBucketCurrent + " ;";
    }

    public void printSolution() {
        BucketState finalBucketState = this;
        System.out.println("All possible solutions: ");

        List<BucketState> bucketStates = new ArrayList<>();
        while (finalBucketState != null) {
            bucketStates.add(finalBucketState);
            finalBucketState = finalBucketState.getPreviousState();
        }

        for (int i = bucketStates.size() - 1; i >= 0; --i) {
            System.out.println(bucketStates.get(i));
        }

        System.out.println("\n\\\\\\\\\\\\\\\\\\\\\\\\\\\\\n");
    }
}
