public class LinkedListDeque<T> {
    private static class ListNode<T> {
        public T item;
        public ListNode<T> prev, next;

        public ListNode(T item, ListNode<T> prev, ListNode<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }

        public ListNode(T item) {
            this.item = item;
        }

        public ListNode() {
        }
    }

    ListNode<T> head;
    private long size;

    public LinkedListDeque() {
    }

    public int size() {
        return (int) size;
    }

    public boolean empty() {
        return head == null;
    }

    public T front() {
        if (head == null) return null;
        return head.item;
    }

    public T back() {
        if (head == null) return null;
        return head.prev.item;
    }

    public T get(long pos) {
        if (empty()) return null;
        ListNode<T> p = head;
        while (pos > 0) {
            p = p.next;
            --pos;
        }
        while (pos < 0) {
            p = p.prev;
            ++pos;
        }
        return p.item;
    }

    private T getRecursive_helper(int pos, ListNode<T> cur) {
        if (pos == 0) return cur.item;
        return getRecursive_helper(pos - 1, cur.next);
    }

    public T getRecursive(int pos) {
        return getRecursive_helper(pos, head);
    }

    public void push_back(T item) {
        if (empty()) {
            head = new ListNode<>(item);
            head.next = head;
            head.prev = head;
        } else {
            ListNode<T> C = new ListNode<>(item);
            ListNode<T> A = head.prev, B = head;

            A.next = C;
            C.prev = A;
            B.prev = C;
            C.next = B;
        }

        ++size;
        return;
    }

    public void push_front(T item) {
        if (empty()) {
            head = new ListNode<>(item);
            head.next = head;
            head.prev = head;
        } else {
            ListNode<T> C = new ListNode<>(item);
            ListNode<T> A = head, B = head.next;

            A.next = C;
            C.prev = A;
            B.prev = C;
            C.next = B;
        }

        ++size;
    }

    public T pop_back() {
        ListNode<T> A, B, C;
        if (empty()) {
            return null;
        } else if (size == 1) {
            B = head;
            head = null;
        } else {
            A = head.prev.prev;
            B = head.prev;
            C = head;
            A.next = C;
            C.prev = A;
        }

        --size;
        return B.item;
    }

    public T pop_front() {
        ListNode<T> A, B, C;
        if (empty()) {
            return null;
        } else if (size == 1) {
            B = head;
            head = null;
        } else {
            A = head.prev;
            B = head;
            C = head.next;
            A.next = C;
            C.prev = A;
        }

        --size;
        return B.item;
    }

    public void addFirst(T item) {
        push_front(item);
    }

    public void addLast(T item) {
        push_back(item);
    }

    public T removeLast() {
        return pop_back();
    }

    public T removeFirst() {
        return pop_front();
    }

    public boolean isEmpty() {
        return empty();
    }

    public void printDeque() {
        ListNode<T> cur = head;
        do {
            System.out.print(cur.item);
            System.out.print(' ');
            cur = cur.next;
        } while (cur != head);
    }
}