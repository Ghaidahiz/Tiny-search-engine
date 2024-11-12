package src;

import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {

        LinkedList2<Integer, String> index = createIndexList();

        // index.getList().findfirst();
        // System.out.print(index.getList().retrieve()=="" );

        // the folowing code is just to print our index list to enjoy watching it 
        if (!index.empty()) {
            index.findfirst();
            while (!index.last()) {
                System.out.print("\n\n" + index.retrieve() + ": ");
                index.getList().findfirst();
                if (!index.getList().empty()) {
                    while (!index.getList().last()) {
                        System.out.print(index.getList().retrieve() + "|");
                        index.getList().findnext();
                    }
                    System.out.print(index.getList().retrieve() + "|");
                }
                index.findnext();
            }
            System.out.print("\n\n" + index.retrieve() + ": ");
            index.getList().findfirst();
            while (!index.getList().last()) {
                System.out.print(index.getList().retrieve() + "|");
                index.getList().findnext();
            }
            System.out.print(index.getList().retrieve() + "|");
        }
        // for (String value : line)
        // System.out.println(value+"\n");

    }

    public static LinkedList2<Integer, String> createIndexList() throws Exception {

        LinkedList2<Integer, String> indexList = new LinkedList2<Integer, String>();
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

            line = br.readLine();
            line = line.toLowerCase();
            // line= line.replace("'","");
            words = line.split("[,;\\- .\"" + i + "]+");
            indexList.insert(i);

            for (String word : words) {
                flag = true;
                if (!word.isEmpty()) {
                    index = word.charAt(0) - 97;
                    stop[index].findfirst();
                    while (!stop[index].last()) {
                        if (word.equals(stop[index].retrieve())) {
                            flag = false;
                            break;
                        }
                        stop[index].findnext();
                    }
                    if (word.equals(stop[index].retrieve()))
                        flag = false;
                    if (flag) {
                        word = word.replace("'", "");
                        indexList.getList().insert(word);
                    }

                }
            }
        }
        br.close();
        return indexList;
    }

    public static void removeStopWords(LinkedList2<Integer, String> indexList) {
    }

    public static LinkedList2<String, Integer> createInvertedIndexList() {
        return new LinkedList2<>();
    }

    public static BST<LinkedList<Integer>> createInvertedIndexBST(LinkedList2<String, Integer> invList) {
        BST<LinkedList<Integer>> invBST = new BST<LinkedList<Integer>>(); // creating a blank binary search tree that
                                                                          // uses strings as keys and has a list of
                                                                          // integers (document IDs) as values, this
                                                                          // method will use the keys and the values in
                                                                          // the previously made inverted index list
                                                                          // to implement it using a BST.
        invList.findfirst();   //to start the insertion from the first and not missing any node                                                                  
        while (!invList.last()) {
            String currentkey = invList.retrieve();
            LinkedList<Integer> affiliatedDocs = invList.getList();
            invBST.insert(currentkey, affiliatedDocs);
            invList.findnext();
        }
        invBST.insert(invList.retrieve(), invList.getList());// for the last word

        return invBST;
    }
}
