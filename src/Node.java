package src;
public class Node<T> {

    T data;
    Node<T> next;
    Node<T> previous;
    String word;
    int frequency=0;

    public Node(T val) {
        data = val;
        next = null;
        previous=null;
        word=null;
    }

}