package src;

public class LinkedList<T> {
    private Node<T> head;
    private Node<T> current;
    int size;

    public LinkedList() {
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

    public void findFirst() {
        current = head;
    }

    public void findNext() {
        current = current.next;
    }

    public void findPrevious() {
        current = current.previous;
    }

    public T retrieve() {
        return current.data;
    }

    public String getWord(){
        return current.word;
    }

    public void update(T val) {
        current.data = val;
    }

    public void insert(T val) {
        Node<T> tmp = new Node<T>(val);
        if (empty()) {
            current = head = tmp;
        } else {
            size++;
            tmp.next = current.next;
            tmp.previous = current;
            if (current.next != null)
                current.next.previous = tmp;
            current.next = tmp;
            current = tmp;
        }
    }

    public void insert(T val,String word) {
        Node<T> tmp = new Node<T>(val);
        tmp.word=word;
        if (empty()) {
            current = head = tmp;
        } else {
            size++;
            tmp.next = current.next;
            tmp.previous = current;
            if (current.next != null)
                current.next.previous = tmp;
            current.next = tmp;
            current = tmp;
        }
    }

    public void remove() {
        size--;
        if (current == head) {
            head = head.next;
            if (head != null)
                head.previous = null;
        } else {
            current.previous.next = current.next;
            if (current.next != null)
                current.next.previous = current.previous;
        }
        if (current.next == null)
            current = head;
        else
            current = current.next;
    }

    public void marge( LinkedList<T> l2){
        while (!last()) {
            findNext();
        }
        current.next=l2.head;
        l2.head.previous=current;
    }

    public void removeDuplicate() {
        if (head == null) { // Handle empty list
            return;
        }
        
        Node<T> tmp = head; // Outer loop traverses the list
        
        while (tmp != null) {
            Node<T> q = tmp.next; // Inner loop checks for duplicates of `tmp`
            
            while (q != null) {
                if (q.data.equals(tmp.data)) {
                    // Remove duplicate node `q`
                    if (q.next != null) {
                        q.next.previous = q.previous;
                    }
                    if (q.previous != null) {
                        q.previous.next = q.next;
                    }
                    if (q == head) {
                        head = q.next; // Update head if necessary
                    }
                    size--; // Decrement size
                }
                q = q.next; // Move to the next node
            }
            
            tmp = tmp.next; // Move to the next node in the outer loop
        }
    }

    public T retrieveDocumentIDs ( String word){
        Node<T> tmp =head;
        while (tmp!=null) {
            if(tmp.word.equals(word))
               return tmp.data;
        }
        return null;
    }
    
}
