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

        List<String> words = readDictionary("C:\\Users\\User\\Downloads\\hangman-player-1.0\\hangman-player-1.0\\src\\main\\resources\\dictionary.txt");

        Map<Integer,List<String>> lengthPerWord = words.stream().collect(Collectors.groupingBy(word -> word.length()));

        // init game
        ServerResponse serverResponse1 = server.startNewGame();
        token = serverResponse1.getToken();
        int sizeWord = serverResponse1.getHangman().length();

        // first filter, by word length
        List<String> filterCurrWordsList = lengthPerWord.get(sizeWord);

        // get updated map
        Hashtable<Character, Integer> mostCommonChar = getUpdatedMap(filterCurrWordsList,null);
        while(!serverResponse1.isGameEnded()){
            Character chosenCharacterToGuess = mostCommonChar.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();
            ServerResponse serverResponse = server.guess(token,chosenCharacterToGuess.toString());
            token = serverResponse.getToken(); // update token

            if (serverResponse.isCorrect() == null) // for some reason correct = null in some cases
                continue;
            if(!serverResponse.isCorrect())
            {
                mostCommonChar.remove(chosenCharacterToGuess);
                filterCurrWordsList =  filterCurrWordsList.stream().filter(w -> !(w.contains(chosenCharacterToGuess.toString()))).collect(Collectors.toList());
            }else{
                filterCurrWordsList =  filterCurrWordsList.stream().filter(w -> w.contains(chosenCharacterToGuess.toString())).collect(Collectors.toList());
                mostCommonChar = getUpdatedMap(filterCurrWordsList,chosenCharacterToGuess);
            }
        }
        System.out.println("END");
    }

    public static List<String> readDictionary(String path) throws IOException {
        BufferedReader br = new BufferedReader( new FileReader (path));
        String line = "";
        List<String> words = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            words.add(line);
        }
        return words;
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

}
