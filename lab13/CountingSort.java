import javax.management.relation.InvalidRelationTypeException;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class with 2 ways of doing Counting sort, one naive way and one "better" way
 *
 * @author Akhil Batra, Alexander Hwang
 **/
public class
CountingSort {
    /**
     * Counting sort on the given int array. Returns a sorted version of the array.
     * Does not touch original array (non-destructive method).
     * DISCLAIMER: this method does not always work, find a case where it fails
     *
     * @param arr int array that will be sorted
     * @return the sorted array
     */
    public static int[] naiveCountingSort(int[] arr) {
        // find max
        int max = Integer.MIN_VALUE;
        for (int i : arr) {
            max = max > i ? max : i;
        }

        // gather all the counts for each value
        int[] counts = new int[max + 1];
        for (int i : arr) {
            counts[i]++;
        }

        // when we're dealing with ints, we can just put each value
        // count number of times into the new array
        int[] sorted = new int[arr.length];
        int k = 0;
        for (int i = 0; i < counts.length; i += 1) {
            for (int j = 0; j < counts[i]; j += 1, k += 1) {
                sorted[k] = i;
            }
        }

        // however, below is a more proper, generalized implementation of
        // counting sort that uses start position calculation
        int[] starts = new int[max + 1];
        int pos = 0;
        for (int i = 0; i < starts.length; i += 1) {
            starts[i] = pos;
            pos += counts[i];
        }

        int[] sorted2 = new int[arr.length];
        for (int i = 0; i < arr.length; i += 1) {
            int item = arr[i];
            int place = starts[item];
            sorted2[place] = item;
            starts[item] += 1;
        }

        // return the sorted array
        return sorted;
    }

    /**
     * Counting sort on the given int array, must work even with negative numbers.
     * Note, this code does not need to work for ranges of numbers greater
     * than 2 billion.
     * Does not touch original array (non-destructive method).
     *
     * @param arr int array that will be sorted
     */
    public static int[] betterCountingSort(int[] arr) {
        int[] v = arr, ret = new int[v.length];
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        int N = v.length;
        for (int i = 0; i < N; i++) {
            max = Math.max(max, v[i]);
            min = Math.min(min, v[i]);
        }
        int range = max - min + 1;
        int[] buckets = new int[range];
        for (int i = 0; i < N; i++) {
            buckets[v[i] - min]++;
        }

//        Map<Integer, Integer> start = new TreeMap<>();
//        int pt = 0;
//        for (int i = 0; i < range; i++) {
//            if (buckets[i] > 0) {
//                start.put(i + min, pt);
//                pt += buckets[i];
//            }
//        }
//
//        for (int i = 0; i < N; i++) {
//            ret[start.get(v[i]) + (--buckets[v[i] - min])] = v[i];
//        }
        int count = 0;
        for (int i = 0; i < range; i++) {
            for (int j = 0; j < buckets[i]; j++) {
                ret[count++] = i + min;
            }
        }

        return ret;
    }
}
