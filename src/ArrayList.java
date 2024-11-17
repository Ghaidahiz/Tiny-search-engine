package src;

public class ArrayList<T>  {

    private int maxsize;
    private int size;
    private int current;
    private LinkedList<T>[] nodes;

    @SuppressWarnings("unchecked")
    public ArrayList(int n) {
        maxsize = n;
        size = 0;
        current = -1;
        nodes = (LinkedList<T>[]) new LinkedList<?>[n];
    }

    public boolean full() {
        return size == maxsize;
    }

    public boolean empty() {
        return size == 0;
    }

    public boolean last() {
        return current == size - 1;
    }

    public void findFirst() {
        current = 0;
    }

    public void findNext() {
        current++;
    }

    public LinkedList<T> retrieve() {
        return nodes[current];
    }

    public void update(LinkedList<T> val) {
        nodes[current] = val;
    }

    public void insert(LinkedList<T> val) {
        for (int i = size - 1; i > current; --i) {
            nodes[i + 1] = nodes[i];
        }
        current++;
        nodes[current] = val;
        size++;
    }

    public void remove() {
        for (int i = current + 1; i < size; i++) {
            nodes[i - 1] = nodes[i];
        }
        size--;
        if (size == 0)
            current = -1;
        else if (current == size)
            current = 0;
    }

    public LinkedList<Integer> retrieveDocumentIDs(String word) {
        LinkedList<Integer> IDs = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            nodes[i].findFirst();
            while (!nodes[i].last()) {
                if (nodes[i].retrieve().equals(word)) {
                    IDs.insert(i);
                    break;
                }
                nodes[i].findNext();
            }
            if (nodes[i].retrieve().equals(word) && nodes[i].last() )
                IDs.insert(i);
        }
        return IDs;
    }

}
