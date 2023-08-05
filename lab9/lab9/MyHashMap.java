package lab9;

import java.security.Key;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  @author Caterpillar
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private static final int DEFAULT_SIZE = 16;
    private static final double MAX_LF = 0.75;

    private ArrayMap<K, V>[] buckets;
    private int size;

    private int loadFactor() {
        return size / buckets.length;
    }

    public MyHashMap() {
        buckets = new ArrayMap[DEFAULT_SIZE];
        this.clear();
    }

    public MyHashMap(int initialSize) {
        buckets = new ArrayMap[initialSize];
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        for (int i = 0; i < this.buckets.length; i += 1) {
            this.buckets[i] = new ArrayMap<>();
        }
    }

    /** Computes the hash function of the given key. Consists of
     *  computing the hashcode, followed by modding by the number of buckets.
     *  To handle negative numbers properly, uses floorMod instead of %.
     */
    private int hash(K key) {
        if (key == null) {
            return 0;
        }

        int numBuckets = buckets.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return buckets[hash(key)].get(key);
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        int index = hash(key);
        if (!buckets[index].containsKey(key)) ++size;
        buckets[index].put(key, value);
        if (loadFactor() > MAX_LF) {
            resize(2 * buckets.length);
        }
    }

    private void resize(int size) {
        MyHashMap<K, V> temp = new MyHashMap<>(size);
        for (int i = 0; i < buckets.length; ++i) {
            for (K key : buckets[i]) {
                temp.put(key, buckets[i].get(key));
            }
        }
        this.buckets = temp.buckets;
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        TreeSet<K> ret = new TreeSet<>();
        for (int i = 0; i < buckets.length; ++i) {
            for (K key: buckets[i]) {
                ret.add(key);
            }
        }
        return ret;
    }

    /* Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        V ret = buckets[hash(key)].remove(key);
        if (ret != null) --size;
        return ret;
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        V ret = buckets[hash(key)].remove(key, value);
        if (ret != null) --size;
        return ret;
    }

    @Override
    public Iterator<K> iterator() {
        return new MyHashMapIterator();
    }

    private class MyHashMapIterator implements Iterator<K> {
        private int pos;
        private Iterator<K> it;

        public MyHashMapIterator() {
            pos = 0;
            it = buckets[0].iterator();
        }

        @Override
        public boolean hasNext() {
            if (pos == buckets.length) {
                return false;
            }
            return true;
        }

        @Override
        public K next() {
            K ret = it.next();
            if (!it.hasNext()) {
                pos++;
                if (pos < buckets.length) {
                    it = buckets[pos].iterator();
                }
            }
            return ret;
        }
    }
}
