package src;
public class Node2<T,V>  {
    T data;
    Node2<T,V> next;
    Node2<T,V> previous;
    LinkedList<V> list;


    public Node2 (T val){
        data= val;
        next=null;
        previous=null;
        list = new LinkedList<V>();

    }

}
