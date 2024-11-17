package src;

import java.io.*;

public class Main<T> {
    public static void main(String[] args) throws Exception {

        ArrayList<String> index = createIndexList();
        LinkedList<Integer> list = booleanRetrivalindext("market OR sports AND warming", index);
        if(!list.empty()){ 
            list.findFirst();
        while (!list.last()) {
            System.out.println(list.retrieve());
            list.findNext();
        }
        System.out.println(list.retrieve());}
        // index.getList().findfirst();
        // System.out.print(index.getList().retrieve()=="" );

        // the folowing code is just to print our index list to enjoy watching it
    /*    if (!index.empty()) {
            index.findFirst();
            int i = 0;
            while (!index.last()) {
                System.out.print("\n\n" + i + ": ");
                i++;
                index.retrieve().findFirst();
                if (!index.retrieve().empty()) {
                    while (!index.retrieve().last()) {
                        System.out.print(index.retrieve().retrieve() + "|");
                        index.retrieve().findNext();
                    }
                    System.out.print(index.retrieve().retrieve() + "|");
                }
                index.findNext();
            }
            System.out.print("\n\n" + i + ": ");
            index.retrieve().findFirst();
            while (!index.retrieve().last()) {
                System.out.print(index.retrieve().retrieve() + "|");
                index.retrieve().findNext();
            }
            System.out.print(index.retrieve().retrieve() + "|");
        }*/
        

    }
                                //***********************/Creating Indices/****************** */

    public static ArrayList<String> createIndexList() throws Exception {

        ArrayList<String> indexList = new ArrayList<String>(50);
        @SuppressWarnings("unchecked")
        LinkedList<String>[] stop = new LinkedList[26];
        BufferedReader br = new BufferedReader(new FileReader("resources\\dataset.csv")); // relative path for CVS file
        BufferedReader br2 = new BufferedReader(new FileReader("resources\\stop.txt")); // file that contains stop words
        try {
            String stopword;
            String first = br2.readLine();
            for (int i = 0; i < 26; i++) {
                stop[i] = new LinkedList<String>();
                stop[i].insert(first);
                while (true) {
                    stopword = br2.readLine();
                    if (stopword.length() == 1) {
                        first = stopword;
                        break;
                    }
                    stop[i].insert(stopword);
                }
            }
        } catch (Exception e) {
            br2.close(); // TODO: handle exception
        }

        String line = br.readLine(); // to Get rid of the first line in CSV fle
        String[] words;
        int index;
        boolean flag;
        for (int i = 0; i < 50; i++) {
            indexList.insert(new LinkedList<String>());
            line = br.readLine();
            line = line.toLowerCase();
            words = line.split("[,;\\- .\"" + i + "]+");
            for (String word : words) {
                flag = true;
                if (!word.isEmpty()) {
                    index = word.charAt(0) - 97;
                    stop[index].findFirst();
                    while (!stop[index].last()) {
                        if (word.equals(stop[index].retrieve())) {
                            flag = false;
                            break;
                        }
                        stop[index].findNext();
                    }
                    if (word.equals(stop[index].retrieve()))
                        flag = false;
                    if (flag) {
                        word = word.replace("'", "");
                        indexList.retrieve().insert(word);
                    }
                }
            }
        }
        br.close();
        return indexList;
    }

    public static LinkedList<LinkedList<Integer>> createInvertedIndexList() {
        return new LinkedList<LinkedList<Integer>>();
    }

    public static BST<LinkedList<Integer>> createInvertedIndexBST(LinkedList<LinkedList<Integer>> invList) {
        BST<LinkedList<Integer>> invBST = new BST<LinkedList<Integer>>(); // creating a blank binary search tree that
                                                                          // uses strings as keys and has a list of
                                                                          // integers (document IDs) as values, this
                                                                          // method will use the keys and the values in
                                                                          // the previously made inverted index list
                                                                          // to implement it using a BST.
        invList.findFirst(); // to start the insertion from the first and not missing any node
        while (!invList.last()) {
            String currentkey = invList.getWord();
            LinkedList<Integer> affiliatedDocs = invList.retrieve();
            invBST.insert(currentkey, affiliatedDocs);
            invList.findNext();
        }
        invBST.insert(invList.getWord(), invList.retrieve());// for the last word

        return invBST;
    }

  
                           // ****************** Boolean Retrieval*****************//

    static LinkedList<Integer> booleanRetrivalinvertedIndex(String query, LinkedList<LinkedList<Integer>> invertedIndex) {
        query = query.toLowerCase();
        String[] qList = query.split(" ");
        LinkedStack<LinkedList<Integer>> stack = new LinkedStack<LinkedList<Integer>>();
        for (int i = 0; i < qList.length - 1; i++) {
            if (i == 0){
                if(invertedIndex.retrieveDocumentIDs(qList[i])==null)
                stack.push(new LinkedList<Integer>());
               else stack.push(invertedIndex.retrieveDocumentIDs(qList[i]));
             } else {

                if (qList[i].equalsIgnoreCase("or")){
                    if(invertedIndex.retrieveDocumentIDs(qList[i+1])==null)
                      stack.push(new LinkedList<Integer>());
                    else stack.push(invertedIndex.retrieveDocumentIDs(qList[i+1]));
                }
                else if (qList[i].equalsIgnoreCase("and")) {
                    if(invertedIndex.retrieveDocumentIDs(qList[i + 1])==null)
                    stack.push(new LinkedList<Integer>());
                    else
                    stack.push(LinkedList.intersection(stack.pop(), invertedIndex.retrieveDocumentIDs(qList[i + 1])));
                } else
                    continue;
            }
        }
        LinkedList<Integer> list = stack.pop();
        while (!stack.empty()) {
            list = LinkedList.union(list, stack.pop());
        }
        return list;
    }

    static LinkedList<Integer> booleanRetrivalindext(String query, ArrayList<String> index) {
        query = query.toLowerCase();
        String[] qList = query.split(" ");
        LinkedStack<LinkedList<Integer>> stack = new LinkedStack<LinkedList<Integer>>();
        for (int i = 0; i < qList.length ; i++) {
            if (i == 0)
                stack.push(index.retrieveDocumentIDs(qList[i]));
            else {

                if (qList[i].equalsIgnoreCase("or"))
                    stack.push(index.retrieveDocumentIDs(qList[i + 1]));
                else if (qList[i].equalsIgnoreCase("and")) {
                    stack.push(LinkedList.intersection(stack.pop(), index.retrieveDocumentIDs(qList[i + 1])));
                } else
                    continue;
            }
        }
        LinkedList<Integer> list = stack.pop();
        while (!stack.empty()) {
            list = LinkedList.union(list, stack.pop());
        }
        return list;
    }

    static LinkedList<Integer> booleanRetrivalBST(String query, BST<LinkedList<Integer>> tree) {
        query = query.toLowerCase();
        String[] qList = query.split(" ");
        LinkedStack<LinkedList<Integer>> stack = new LinkedStack<LinkedList<Integer>>();
        for (int i = 0; i < qList.length - 1; i++) {
            if (i == 0)
                stack.push(tree.retrieveDocumentIDs(qList[i]));
            else {

                if (qList[i].equalsIgnoreCase("or"))
                    stack.push(tree.retrieveDocumentIDs(qList[i + 1]));
                else if (qList[i].equalsIgnoreCase("and")) {
                    stack.push(LinkedList.intersection(stack.pop(), tree.retrieveDocumentIDs(qList[i + 1])));
                } else
                    continue;
            }
        }
        LinkedList<Integer> list = stack.pop();
        while (!stack.empty()) {
            list = LinkedList.union(list, stack.pop());
        }
        return list;
    }

   

}
