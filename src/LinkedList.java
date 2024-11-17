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
        if (head == null) { 
            return;
        }
        Node<T> tmp = head; 
        while (tmp != null) {
            Node<T> q = tmp.next; 
            while (q != null) {
                if (q.data.equals(tmp.data)) {
                    if (q.next != null) {
                        q.next.previous = q.previous;
                    }
                    if (q.previous != null) {
                        q.previous.next = q.next;
                    }
                    if (q == head) {
                        head = q.next; 
                    }
                    size--; 
                }
                q = q.next;
            }
            tmp = tmp.next; 
        }
    }

    static LinkedList<Integer> intersection(LinkedList<Integer> l1, LinkedList<Integer> l2) {
        LinkedList<Integer> intersect = new LinkedList<>();
       
        if (!l1.empty() && !l2.empty()) {
            l1.findFirst();
            while (!l1.last()) {
                l2.findFirst();
                while (!l2.last()) {
                    if (l1.retrieve() == l2.retrieve()) {
                        intersect.insert(l1.retrieve());
                        break;
                    }
                    l2.findNext();
                }
                if (l1.retrieve() == l2.retrieve())
                    intersect.insert(l1.retrieve());
                l1.findNext();
            }
            l2.findFirst(); // for the last elemant in l1
            while (!l2.last()) {
                if (l1.retrieve() == l2.retrieve()) {
                    intersect.insert(l1.retrieve());
                    break;
                }
                l2.findNext();
            }
            if (l1.retrieve() == l2.retrieve())
                intersect.insert(l1.retrieve());
           
        }
        return intersect;
    }
    
    static LinkedList<Integer> union(LinkedList<Integer> l1, LinkedList<Integer> l2) {
        if(!l1.empty() && !l2.empty()){
            l1.marge(l2);
            l1.removeDuplicate();
            return  l1;
        }
        return l1.empty()?l2:l1;
        
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
