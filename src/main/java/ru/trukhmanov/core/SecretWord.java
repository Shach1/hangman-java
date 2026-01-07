package ru.trukhmanov.core;

import java.util.ArrayList;
import java.util.List;

public final class SecretWord {
    private final List<Character> secretWord = new ArrayList<>(15);
    private final List<Character> maskSecretWord = new ArrayList<>(15);
    private final String secretWordString;

    public String getMaskSecretWordString(){
        StringBuilder maskSecretWordString = new StringBuilder();
        for (var letter : maskSecretWord) {
            maskSecretWordString.append(letter.toString());
        }
        return maskSecretWordString.toString();
    }

    public String getSecretWordString() {
        return secretWordString;
    }

    public SecretWord(String inputWord){
        if (inputWord == null) throw new NullPointerException("inputWord for SecretWord constructor can't be null");

        this.secretWordString = inputWord.toUpperCase();
        for (var letter : secretWordString.toCharArray()) {
            secretWord.add(letter);
            maskSecretWord.add('_');
        }
    }

    public boolean checkLetterInSecretWord(char letter){
        return secretWord.contains(Character.toUpperCase(letter));
    }

    public void replaceVoidInMaskSecretWord(char letter){
        for (int i = 0; i < secretWord.size(); i++){
            if (secretWord.get(i).equals(letter)){
                maskSecretWord.set(i, letter);
            }
        }
    }

    public boolean isSecretWordEqualsMaskSecretWord(){
        return secretWord.equals(maskSecretWord);
    }
}