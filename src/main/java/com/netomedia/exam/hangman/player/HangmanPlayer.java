package com.netomedia.exam.hangman.player;

import com.netomedia.exam.hangman.model.ServerResponse;
import com.netomedia.exam.hangman.server.HangmanServer;
import org.apache.commons.lang3.ArrayUtils;
//import com.sun.tools.javac.util.ArrayUtils;

import java.io.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class HangmanPlayer {

    private static HangmanServer server = new HangmanServer();
    private static String token = "";

    /**
     * This is the entry point of your Hangman Player
     * To start a new game call server.startNewGame()
     */
    public static void main(String[] args) throws Exception {
        Hashtable< Integer, String > map = new Hashtable< Integer, String >();
        BufferedReader br = new BufferedReader( new FileReader ("C:\\Users\\User\\Downloads\\hangman-player-1.0\\hangman-player-1.0\\src\\main\\resources\\dictionary.txt"));
        String line = "";
        List<String> words = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            words.add(line);
        }
        Map<Integer,List<String>> lengthPerWord = words.stream().collect(Collectors.groupingBy(word -> word.length()));

        ServerResponse serverResponse1 = server.startNewGame();
        token = serverResponse1.getToken();
        int sizeWord = serverResponse1.getHangman().length();

        List<String> filterCurrWordsList = lengthPerWord.get(sizeWord);
        Hashtable<Character, Integer> mostCommonChar = getUpdatedMap(filterCurrWordsList,null);

        while(!serverResponse1.isGameEnded()){
            Character chosenCharacterToGuess = mostCommonChar.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();
            ServerResponse serverResponse = server.guess(token,chosenCharacterToGuess.toString());
            if (serverResponse.isCorrect() == null)
                continue;
            if(!serverResponse.isCorrect())
            {
                mostCommonChar.remove(chosenCharacterToGuess);
                token = serverResponse.getToken(); // update token
            }else{
                token = serverResponse.getToken();
                filterCurrWordsList =  filterCurrWordsList.stream().filter(w -> w.contains(chosenCharacterToGuess.toString())).collect(Collectors.toList());
                mostCommonChar = getUpdatedMap(filterCurrWordsList,chosenCharacterToGuess);
            }
        }
        System.out.println("END");
        // 1 .filter all the word according to size of the word
        // 2. guess the most common char in the eng lang
        // 3. say that we found one char  (h)
        // 3.1 search the char that appear in most words with 'h'
        //


        // load the file as hash table
        //
    }

    public static Hashtable<Character, Integer> getUpdatedMap(List<String> filterWordsByLength,Character excludeLetter)
    {
        Hashtable<Character, Integer> mostCommonChar = new Hashtable<>();
        for(String word:filterWordsByLength){

            char[] charArray = word.toCharArray();
            Character[] charObjectArray = ArrayUtils.toObject(charArray);

            Set<Character> charSet = new HashSet<Character>(Arrays.asList(charObjectArray));
            for (Character c: charSet){
                if(excludeLetter == null || !c.equals(excludeLetter))
                {
                    mostCommonChar.put(c, mostCommonChar.getOrDefault(c,0)+1);
                }
            }
        }
        return mostCommonChar;
    }

    public static Hashtable<Character, Integer> getUpdatedMap2(List<String> filterWordsByLength,String currFound)
    {
        Hashtable<Character, Integer> mostCommonChar = new Hashtable<>();
        for(String word:filterWordsByLength){

            char[] charArray = word.toCharArray();
            Character[] charObjectArray = ArrayUtils.toObject(charArray);

            Set<Character> charSet = new HashSet<Character>(Arrays.asList(charObjectArray));
            for (Character c: charSet){
                mostCommonChar.put(c, mostCommonChar.getOrDefault(c,0)+1);
            }
        }
        return mostCommonChar;
    }
}
