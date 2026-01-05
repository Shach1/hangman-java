package ru.trukhmanov.core;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private boolean isEnd = false;
    private final List<Character> hiddenWord = new ArrayList<>(15);
    private final String hiddenWordString;
    private final List<Character> maskHiddenWord = new ArrayList<>(15);
    private short errorCounter = 0;
    private String misspelledLetters = "";

    protected short getErrorCounter() {
        return errorCounter;
    }

    protected String getMisspelledLetters() {return misspelledLetters;}

    protected boolean isEnd() {
        return isEnd;
    }

    protected String getHiddenWordString() {
        return hiddenWordString;
    }

    protected String getMaskHiddenWordString() {
        String maskHiddenWordString = "";
        for (var letter : maskHiddenWord) {
            maskHiddenWordString += letter.toString();
        }
        return maskHiddenWordString;
    }

    public Game(String word) {
        this.hiddenWordString = word.toUpperCase();
        for (var letter : hiddenWordString.toCharArray()) {
            hiddenWord.add(letter);
            maskHiddenWord.add('_');
        }
    }

    /*
        Возвращает -1, когда допущена ошибка
        Возвращает -2, когда повторно введена ошибочная буква
        Возвращает -3, когда повторно введена правильная буква
        Возвращает 0, когда введена верная буква
     */
    private int letterCheckAndReplace(char letter){
        if (misspelledLetters.contains(String.valueOf(letter))) return -2; //проверка повторной ошибки
        if (maskHiddenWord.contains(letter)) return -3;
        boolean isMistake = true;
        for (int i = 0; i < hiddenWord.size(); i++){
            if (hiddenWord.get(i).equals(letter)){
                maskHiddenWord.set(i, letter);
                isMistake = false;
            }
        }
        if (isMistake) return -1;
        return 0;
    }

    /*
        Возвращает -1, когда игра проиграна,
        Возвращает -2, когда повторно введена ошибочная буква
        Возвращает -3, когда повторно введена правильная буква
        Возвращает 0, когда игра продолжается,
        Возвращает 1, когда игра выиграна
     */
    public int play(char letter){
        int resultLetterCheck = letterCheckAndReplace(letter);
        if (resultLetterCheck == -2) return -2;
        if (resultLetterCheck == -3) return -3;
        if (resultLetterCheck == -1){
            errorCounter++;
            misspelledLetters += letter + " ";
            short maxErrorCount = 6;
            if (errorCounter == maxErrorCount){     //проверка поражения
                isEnd = true;
                return -1;
            }
        }
        else {      //проверка выйгрыша
            if (maskHiddenWord.equals(hiddenWord)){
                isEnd = true;
                return 1;
            }
        }
        return 0;   //играем дальше
    }
}