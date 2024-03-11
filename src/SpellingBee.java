import java.io.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Spelling Bee
 *
 * This program accepts an input of letters. It prints to an output file
 * all English words that can be generated from those letters.
 *
 * For example: if the user inputs the letters "doggo" the program will generate:
 * do
 * dog
 * doggo
 * go
 * god
 * gog
 * gogo
 * goo
 * good
 *
 * It utilizes recursion to generate the strings, mergesort to sort them, and
 * binary search to find them in a dictionary.
 *
 * @author Zach Blick, [ADD YOUR NAME HERE]
 *
 * Written on March 5, 2023 for CS2 @ Menlo School
 *
 * DO NOT MODIFY MAIN OR ANY OF THE METHOD HEADERS.
 */
public class SpellingBee {

    private String letters;
    private ArrayList<String> words;
    public static final int DICTIONARY_SIZE = 143091;
    public static final String[] DICTIONARY = new String[DICTIONARY_SIZE];

    public SpellingBee(String letters) {
        this.letters = letters;
        words = new ArrayList<String>();
    }

    // TODO: generate all possible substrings and permutations of the letters.
    //  Store them all in the ArrayList words. Do this by calling ANOTHER method
    //  that will find the substrings recursively.
    public void generate() {
        // YOUR CODE HERE — Call your recursive method!
        //Using recursion to generate all possible combinations
        permuteLetters("", letters);
    }
    //permute Letters use the parameters str1 and str2
    // this generates all possible combinations recursively by writing it in str1
    public void permuteLetters(String str1, String str2) {
        if(str2.length() == 0)
        {
            return;
        }
        String tstr1 = "", tstr2 = "";
        //adds all possible combinations to array words
        for (int i = 0; i < str2.length(); i++) {
            if(tstr1.length() > 0)
            {
                words.add(tstr1);
            }
            tstr1 = push(str1+tstr1, str2.charAt(i));
            tstr2 = pull(str2, i);
            if(tstr2.length() > 0)
            {
                words.add(tstr2);
            }
            permuteLetters(tstr1, tstr2);
            if(tstr1.length() > 0)
            {
                words.add(tstr1);
            }
            tstr1 = "";
        }
    }
    //adds char c to String str1
    public String push(String str1, char c)
    {
        return str1 + c;
    }

    //takes out a char at an index from str2 and returns it
    public String pull(String str2, int index)
    {
        String retStr = str2.substring(0, index) + str2.substring(index + 1);
        return retStr;
    }

    // TODO: Apply mergesort to sort all words. Do this by calling ANOTHER method
    //  that will find the substrings recursively.
    public void sort() {
        // YOUR CODE HERE
        //System.out.println(words);
        mergeSort(words);
        //System.out.println("Sorted Array: ");
        //System.out.println(words);
    }

    //recursive algorithm mergesort using an arraylist of words
    public void mergeSort(ArrayList<String> arr)
    {
        int subArraySize = arr.size() / 2;
        ArrayList<String> arr1 = new ArrayList<String>();
        ArrayList<String> arr2 = new ArrayList<String>();
        if(arr.size() < 2)
        {
            return;
        }
        for(int i = 0; i < arr.size() / 2; i++)
        {
            arr1.add(arr.get(i));
        }
        for(int i = arr.size()/2; i < arr.size(); i++)
        {
            arr2.add(arr.get(i));
        }
        mergeSort(arr1);
        mergeSort(arr2);
        merge(arr, arr1,arr2);
    }

    //merges the arrays by comparing them to each other
    public void merge(ArrayList<String> arr, ArrayList<String> arr1, ArrayList<String> arr2)
    {
        arr.removeAll(arr);
        int j = 0;
        int i = 0;
        /*System.out.println("First Array");
        System.out.println(arr1);
        System.out.println("Second Array");
        System.out.println(arr2); */
        for(;i < arr1.size() && j < arr2.size();)
        {
            if(arr1.get(i).compareTo(arr2.get(j)) <= 0)
            {
                //System.out.println("Adding " + arr1.get(i));
                arr.add(arr1.get(i));
                i++;
            }
            else
            {
                //System.out.println("Adding " + arr2.get(j));
                arr.add(arr2.get(j));
                j++;
            }
        }
        //System.out.println(i + " " + arr1.size() + " " + j + " " + arr2.size());
        //System.out.println("Merged Array1");
        //System.out.println(arr);
        while(i < arr1.size())
        {
            arr.add(arr1.get(i));
            i++;
        }
        //System.out.println("Merged Array2");
        //System.out.println(arr);
        while(j < arr2.size())
        {
            arr.add(arr2.get(j));
            j++;
        }
        //System.out.println("Merged Array3");
        //System.out.println(arr);
    }

    // Removes duplicates from the sorted list.
    public void removeDuplicates() {
        int i = 0;
        while (i < words.size() - 1) {
            String word = words.get(i);
            if (word.equals(words.get(i + 1)))
                words.remove(i + 1);
            else
                i++;
        }
    }

    // TODO: For each word in words, use binary search to see if it is in the dictionary.
    //  If it is not in the dictionary, remove it from words.
    //Uses recursive binary algorithm to search if it exists in the dictionary by compareTo()
    public void checkWords() {
        // YOUR CODE HERE
        ArrayList<String> goodWords = new ArrayList<String>();
        for(int i = 0; i < words.size(); i++)
        {
            if(binarySearchDict(words.get(i), DICTIONARY) == true)
            {
                goodWords.add(words.get(i));
                //System.out.println("Good word "+words.get(i));
            }
        }
        words.removeAll(words);
        for(int i = 0; i< goodWords.size(); i++)
        {
            words.add(goodWords.get(i));
        }
        //System.out.println("Dictionary Words");
        //System.out.println(words);
    }

    public boolean binarySearchDict(String word, String[] Dict){
        //base cases
        if(Dict == null)
        {
            return false;
        }
        if(Dict.length <= 0)
        {
            return false;
        }
        if(Dict.length == 1 && Dict[0] != null)
        {
            if(word.compareTo(Dict[0]) == 0)
            {
                return true;
            }
            return false;
        }
        if(Dict.length == 2)
        {
            if((Dict[0] != null && word.compareTo(Dict[0]) == 0) || (Dict[1] != null && word.compareTo(Dict[1]) == 0))
            {
                return true;
            }
            return false;
        }
        int middle;
        if(Dict.length % 2 ==0)
        {
            middle = (Dict.length)/2;
        }
        else
        {
            middle = (Dict.length + 1)/2;
        }
        int middleindex = Dict.length/2;
        String[] subDictionary = new String[middle];
        if(word.compareTo(Dict[middleindex]) == 0) {
            return true;
        }
        else if(word.compareTo(Dict[middleindex]) < 0)
        {
            for(int i = 0; i < Dict.length / 2; i++)
            {
                subDictionary[i] = Dict[i];
            }
            return binarySearchDict(word, subDictionary);
        }
        else
        {
            int j = 0;
            for(int i = Dict.length / 2; i < Dict.length; i++)
            {
                subDictionary[j] = Dict[i];
                j++;
            }
            return binarySearchDict(word, subDictionary);
        }
    }
    // Prints all valid words to wordList.txt
    public void printWords() throws IOException {
        File wordFile = new File("Resources/wordList.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(wordFile, false));
        for (String word : words) {
            writer.append(word);
            writer.newLine();
        }
        writer.close();
    }

    public ArrayList<String> getWords() {
        return words;
    }

    public void setWords(ArrayList<String> words) {
        this.words = words;
    }

    public SpellingBee getBee() {
        return this;
    }

    public static void loadDictionary() {
        Scanner s;
        File dictionaryFile = new File("Resources/dictionary.txt");
        try {
            s = new Scanner(dictionaryFile);
        } catch (FileNotFoundException e) {
            System.out.println("Could not open dictionary file.");
            return;
        }
        int i = 0;
        while(s.hasNextLine()) {
            DICTIONARY[i++] = s.nextLine();
        }
    }

    public static void main(String[] args) {

        // Prompt for letters until given only letters.
        Scanner s = new Scanner(System.in);
        String letters;
        do {
            System.out.print("Enter your letters: ");
            letters = s.nextLine();
        }
        while (!letters.matches("[a-zA-Z]+"));

        // Load the dictionary
        SpellingBee.loadDictionary();

        // Generate and print all valid words from those letters.
        SpellingBee sb = new SpellingBee(letters);
        sb.generate();
        sb.sort();
        sb.removeDuplicates();
        sb.checkWords();
        try {
            sb.printWords();
        } catch (IOException e) {
            System.out.println("Could not write to output file.");
        }
        s.close();
    }
}
