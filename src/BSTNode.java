package src;

public class BSTNode<T> {
    public String key;
    public T data;
    public BSTNode<T> left, right;

    public BSTNode(String str, T val) {
        key = str;
        data = val;
        left = right = null;
    }

    public BSTNode(String str, T val, BSTNode<T> l, BSTNode<T> r) {
        key = str;
        data = val;
        left = l;
        right = r;
    }
}
