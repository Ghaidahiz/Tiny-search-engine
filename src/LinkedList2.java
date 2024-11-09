package src;

class LinkedList2<T, V>  {

    private Node2<T, V> head;
    private Node2<T, V> current;

    public LinkedList2() {
        head = current = null;
    }

    public boolean empty() {
        return head == null;
    }

    public boolean last() {
        return current.next == null;
    }

    public boolean first() {
        return current.previous == null;
    }

    public void findfirst() {
        current = head;
    }

    public void findnext() {
        current = current.next;
    }

    public void findPrevious() {
        current = current.previous;
    }

    public LinkedList<V> getList() {
        return current.list;
    }

    public T retrieve() {
        return current.data;
    }

    public void insert(T val) {
        Node2<T, V> tmp = new Node2<T, V>(val);
        if (empty()) {
            current = head = tmp;
        } else {
            tmp.next = current.next;
            tmp.previous = current;
            if (current.next != null)
                current.next.previous = tmp;
            current.next = tmp;
            current = tmp;
        }
    }

}