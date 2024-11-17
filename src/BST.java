package src;

public class BST<T>  { // normal BST that has the string as a key to create an inverted index
    BSTNode<T> root, current;

    public BST() {
        root = current = null;
    }

    public boolean empty() {
        return root == null;
    }

    public boolean full() {
        return false; // linkedlist not array
    }

    public T retrieve() {
        return current.data;
    }

    public boolean findkey(String tkey) {
        BSTNode<T> p = root, q = root;
        if (empty())
            return false;
        while (p != null) {
            q = p;
            if (p.key.equalsIgnoreCase(tkey)) { // assuming this inverted index is not case sensitive
                current = p;
                return true;
            } else if (tkey.compareToIgnoreCase(p.key) < 0)// p.key larger
                p = p.left;
            else
                p = p.right;

        }
        current = q;
        return false;
    }

    public boolean insert(String k, T val) {
        BSTNode<T> p, q = current;
        if (findkey(k)) {
            current = q; // findkey() modified current and we need to reset it
            return false; // key already in the BST
        }
        p = new BSTNode<T>(k, val);
        if (empty()) {

            root = current = p;
            return true;
        }

        else {
            // current is pointing to parent of the new key
            if (k.compareToIgnoreCase(current.key) < 0) // current key is larger
                current.left = p;
            else
                current.right = p;
            current = p;
            return true;
        }
    }

    public boolean remove_key(String tkey) {
        BooleanWrapper removed = new BooleanWrapper(false);
        BSTNode<T> p;
        p = remove_aux(tkey, root, removed);
        current = root = p;
        return removed.get();
    }

    private BSTNode<T> remove_aux(String key, BSTNode<T> p, BooleanWrapper flag) {
        BSTNode<T> q, child = null;
        if (p == null)
            return null;
        if (key.compareToIgnoreCase(p.key) < 0) // p.key larger
            p.left = remove_aux(key, p.left, flag); // go left
        else if (key.compareToIgnoreCase(p.key) > 0) // key larger
            p.right = remove_aux(key, p.right, flag); // go right
        else { // key is found
            flag.set(true);
            if (p.left != null && p.right != null) { // two children
                q = find_min(p.right);
                p.key = q.key;
                p.data = q.data;
                p.right = remove_aux(q.key, p.right, flag);
            } else {
                if (p.right == null) // one child
                    child = p.left;
                else if (p.left == null) // one child
                    child = p.right;
                return child;
            }
        }
        return p;
    }

    private BSTNode<T> find_min(BSTNode<T> p) {
        if (p == null)
            return null;
        while (p.left != null) {
            p = p.left;
        }
        return p;
    }

    public boolean update(String key, T data) {
        remove_key(current.key);
        return insert(key, data);
    }

    public T retrieveDocumentIDs(String word) {
        if (findkey(word))
            return current.data;
        return null;
    }

}
