package ru.trukhmanov;

import java.util.Arrays;

public class Game {
    private boolean isEnd = false;
    private char[] hiddenWord;
    private char[] maskHiddenWord;
    private short errorCounter = 0;
    private final short maxErrorCount = 8;
    private String misspelledLetters = "";

    public Game(String word) {
        this.hiddenWord = word.toUpperCase().toCharArray();
        maskHiddenWord = new char[hiddenWord.length];
        Arrays.fill(maskHiddenWord, '_');
    }

    public boolean isEnd() {
        return isEnd;
    }

    private int _letterCheckAndReplace(char letter){
        boolean isMistake = true;
        int i = 0;
        while (i < hiddenWord.length) {
            if(hiddenWord[i] == letter){
                maskHiddenWord[i] = letter;
                isMistake = false;
            }
            i++;
        }
        if (isMistake) return -1;
        return 0;
    }

    public void play(char letter){
        int resultLetterCheck = _letterCheckAndReplace(letter);
        if (resultLetterCheck == -1){
            errorCounter++;
            misspelledLetters += letter + " ";
            _printCurrentState();
            if (errorCounter == maxErrorCount){
                isEnd = true;
                System.out.println("Игра окончена вы проиграли");
                return;
            }
        }
        else {
            if (Arrays.equals(maskHiddenWord, hiddenWord)){
                isEnd = true;
                System.out.println("Вы выйграли!!!");
            }
            _printCurrentState();
        }
    }

    private void _printCurrentState(){
        System.out.println("Тут будет нарисована висилица");
        System.out.println("Слово: " + Arrays.toString(maskHiddenWord));
        System.out.println("Ошибки: " + misspelledLetters);
    }
}
