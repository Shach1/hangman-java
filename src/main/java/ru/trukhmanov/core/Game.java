package ru.trukhmanov.core;

import java.util.Arrays;

public class Game {
    private boolean isEnd = false;
    private char[] hiddenWord;
    private char[] maskHiddenWord;
    private short errorCounter = 0;
    private final short maxErrorCount = 6;
    private String misspelledLetters = "";

    public char[] getMaskHiddenWord() {
        return maskHiddenWord;
    }

    public short getErrorCounter() {
        return errorCounter;
    }

    public String getMisspelledLetters() {
        return misspelledLetters;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public Game(String word) {
        this.hiddenWord = word.toUpperCase().toCharArray();
        maskHiddenWord = new char[hiddenWord.length];
        Arrays.fill(maskHiddenWord, '_');
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

    /*
        Возвращает -1, когда игра проиграна
        Возвращает 0, когда игра продолжается
        Возвращает 1, когда игра выиграна
     */
    public int play(char letter){
        int resultLetterCheck = _letterCheckAndReplace(letter);
        if (resultLetterCheck == -1){
            errorCounter++;
            misspelledLetters += letter + " ";
            if (errorCounter == maxErrorCount){     //проверка поражения
                isEnd = true;
                return -1;
            }
        }
        else {      //проверка выйгрыша
            if (Arrays.equals(maskHiddenWord, hiddenWord)){
                isEnd = true;
                return 1;
            }
        }
        return 0;   //играем дальше
    }
}