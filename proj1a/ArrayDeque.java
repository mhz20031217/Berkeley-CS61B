public class ArrayDeque<T> {
    private final int INITIAL_SIZE = 8;

    private T[] array;

    private final int[] pow2 = {1, 2, 4, 8, 16, 32, 64, 128, 256, 512,
       1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288,
       1048576, 2097152, 4194304, 8388608, 16777216, 33554432, 67108864, 134217728, 268435456,
       536870912, 1073741824
    };

    private int begin, end, size, level;

    public ArrayDeque() {
        array = (T[]) new Object[INITIAL_SIZE];
    }

    public ArrayDeque(int n) {
        while (pow2[level] < n) {
            ++level;
        }
        int length = pow2[level];
        array = (T[]) new Object[length];
        begin = 0;
        end = n;
        size = n;
    }

    public T get(int pos) {
        return array[(pos % size + begin) % array.length];
    }

    public void reallocate(int n) {
        while (level < 32 && pow2[level] < n) {
            ++level;
        }
        while (level > 0 && pow2[level] > 3 * n) {
            --level;
        }
        int length = pow2[level];

        if (length < array.length) {
            T[] narray = (T[]) new Object[length];
            if (begin + size <= array.length) {
                System.arraycopy(array, begin, narray, 0, Math.min(n, size));
            } else {
                int part1 = array.length - begin;
                System.arraycopy(array, begin, narray, 0, Math.min(n, part1));
                if (part1 < n) {
                    System.arraycopy(array, 0, narray, part1, n - part1);
                }
            }
            begin = 0;
            end = n;
            array = narray;
        } else if (length == array.length) {
            if (n >= size) {
                end = (begin + n) % length;
            } else {
                int nend = (begin + n) % length;
                if (begin + size > array.length && begin + n < array.length) {
                    for (int i = begin + n; i < array.length; ++i) {
                        array[i] = null;
                    }
                    for (int i = 0; i < end; ++i) {
                        array[i] = null;
                    }
                } else {
                    for (int i = begin + n; i < end; ++i) {
                        array[i] = null;
                    }
                }
                end = nend;
            }
        } else {
            T[] narray = (T[]) new Object[length];
            if (begin + size <= array.length) {
                System.arraycopy(array, begin, narray, 0, size);
            } else {
                int part1 = array.length - begin;
                System.arraycopy(array, begin, narray, 0, part1);
                System.arraycopy(array, 0, narray, part1, end);
            }
            begin = 0;
            end = n;
            array = narray;
        }
    }

    public int size() {
        return size;
    }

    public boolean empty() {
        return size == 0;
    }

    public void pushBack(T item) {
        if (size == array.length) {
            reallocate(array.length + 1);
        }
        array[end] = item;
        end = (end + 1) % array.length;
        ++size;
    }

    public void pushFront(T item) {
        if (size == array.length) {
            reallocate(size + 1);
        }
        begin = Math.floorMod(begin - 1, array.length);
        array[begin] = item;
        ++size;
    }

    public T popBack() {
        if (size == 0) {
            return null;
        }
        if (size > INITIAL_SIZE && array.length > 3 * size) {
            reallocate(size * 2);
        }
        end = Math.floorMod(end - 1, array.length);
        T ret = array[end];
        array[end] = null;
        --size;
        return ret;
    }

    public T popFront() {
        if (size == 0) {
            return null;
        }
        if (size > INITIAL_SIZE && array.length > 3 * size) {
            reallocate(size * 2);
        }
        T ret = array[begin];
        array[begin] = null;
        begin = Math.floorMod(begin + 1, array.length);
        --size;
        return ret;
    }

    public boolean isEmpty() {
        return empty();
    }

    public void addFirst(T item) {
        pushFront(item);
    }

    public void addLast(T item) {
        pushBack(item);
    }

    public T removeLast() {
        return popBack();
    }

    public T removeFirst() {
        return popFront();
    }

    public void printDeque() {
        for (int i = 0; i < size; ++i) {
            System.out.print(array[(begin + i) % array.length]);
            System.out.print(' ');
        }
    }
}
