import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        int maxLength = 0;
        for (String s: asciis) {
            maxLength = Math.max(s.length(), maxLength);
        }

        for (int i = 0; i < maxLength; i++) {
            sortHelperLSD(asciis, i);
        }
        return asciis;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        ArrayList<String>[] buckets = new ArrayList[257];
        for (int i = 0; i < 257; i++) {
            buckets[i] = new ArrayList<String>();
        }
        for (String ascii : asciis) {
            if (ascii.length() <= index) {
                buckets[0].add(ascii);
                continue;
            }
            buckets[ascii.charAt(index) + 1].add(ascii);
        }
        int count = 0;
        for (int i = 0; i < 257; i++) {
            for (String s: buckets[i]) {
                asciis[count] = s;
                count++;
            }
        }
        assert (count == asciis.length);
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }
}
