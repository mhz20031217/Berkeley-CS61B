package hw3.hash;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        int N = oomages.size();
        int[] buckets = new int[M];
        for (int i = 0; i < N; i++) {
            buckets[(oomages.get(i).hashCode() & 0x7FFFFFFF) % M] += 1;
        }
        for (int i = 0; i < M; i++) {
            if (buckets[i] <= N / 50 || buckets[i] >= N / 2.5) {
                return false;
            }
        }
        return true;
    }
}
