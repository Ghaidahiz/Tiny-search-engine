package src;
public class Node<T> {

    T data;
    Node<T> next;
    Node<T> previous;

    public Node(T val) {
        data = val;
        next = null;
        previous=null;
    }

}