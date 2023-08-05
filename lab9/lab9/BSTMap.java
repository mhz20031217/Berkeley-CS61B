package lab9;

import java.util.*;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>, Iterable<K> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the value mapped to by KEY in the subtree rooted in P.
     * or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) return null;
        int cmp = key.compareTo(p.key);
        if (cmp < 0) {
            return getHelper(key, p.left);
        } else if (cmp > 0) {
            return getHelper(key, p.right);
        } else {
            return p.value;
        }
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        return getHelper(key, root);
    }

    /**
     * Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
     * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            ++size;
            return new Node(key, value);
        }
        int cmp = key.compareTo(p.key);
        if (cmp < 0) {
            p.left = putHelper(key, value, p.left);
        } else if (cmp > 0) {
            p.right = putHelper(key, value, p.right);
        } else {
            p.value = value;
        }
        return p;
    }

    /**
     * Inserts the key KEY
     * If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        root = putHelper(key, value, root);
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
        TreeSet<K> set = new TreeSet<>();
        for (K key: this) {
            set.add(key);
        }
        return set;
    }

    private Node getMin(Node p) {
        if (p == null) return null;
        if (p.left != null) return getMin(p.left);
        return p;
    }

    private Node getMax(Node p) {
        if (p == null) return null;
        if (p.right != null) return getMax(p.right);
        return p;
    }

    private Node removeMin(Node p) {
        if (p == null) return null;
        if (p.left == null) return p.right;
        p.left = removeMin(p.left);
        return p;
    }

    private Node removeMax(Node p) {
        if (p == null) return null;
        if (p.right == null) return p.left;
        p.right = removeMax(p.right);
        return p;
    }

    private Node removeRegister;

    private Node removeHelper(K key, Node p) {
        if (p == null) {
            return null;
        }

        int cmp = key.compareTo(p.key);
        if (cmp < 0) {
            p.left = removeHelper(key, p.left);
        } else if (cmp > 0) {
            p.right = removeHelper(key, p.right);
        } else {
            if (p.left == null) return p.right;
            if (p.right == null) return p.left;
            removeRegister = p;
            Node rightMin = getMin(p.right);
            Node rightWithoutMin = removeMin(p.right);
            p = rightMin;
            p.left = removeRegister.left;
            p.right = rightWithoutMin;
        }
        return p;
    }

    private Node removeHelper(K key, V value, Node p) {
        if (p == null) {
            return null;
        }

        int cmp = key.compareTo(p.key);
        if (cmp < 0) {
            p.left = removeHelper(key, value, p.left);
        } else if (cmp > 0) {
            p.right = removeHelper(key, value, p.right);
        } else {
            if (!p.value.equals(value)) {
                return p;
            }
            if (p.left == null) return p.right;
            if (p.right == null) return p.left;
            removeRegister = p;
            Node rightMin = getMin(p.right);
            Node rightWithoutMin = removeMin(p.right);
            p = rightMin;
            p.left = removeRegister.left;
            p.right = rightWithoutMin;
        }
        return p;
    }

    /**
     * Removes KEY from the tree if present
     * returns VALUE removed,
     * null on failed removal.
     */
    @Override
    public V remove(K key) {
        removeRegister = null;
        root = removeHelper(key, root);
        if (removeRegister == null) return null;
        return removeRegister.value;
    }

    /**
     * Removes the key-value entry for the specified key only if it is
     * currently mapped to the specified value.  Returns the VALUE removed,
     * null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        removeRegister = null;
        root = removeHelper(key, value, root);
        if (removeRegister == null) return null;
        return removeRegister.value;
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTMapIterator();
    }

    public class BSTMapIterator implements Iterator<K> {
        private final Stack<Node> path;

        public BSTMapIterator() {
            path = new Stack<>();
            Node cur = root;
            while (cur != null) {
                path.push(cur);
                cur = cur.left;
            }
        }

        public BSTMapIterator(BSTMapIterator it) {
            path = (Stack<Node>) it.path.clone();
        }

        @Override
        public boolean hasNext() {
            return !path.empty();
        }

        @Override
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Node ret = path.peek(), cur = ret.right;
            path.pop();
            while (cur != null) {
                path.push(cur);
                cur = cur.left;
            }
            return ret.key;
        }
    }
}
