package ru.trukhmanov.core;

import java.util.regex.Pattern;

public class GameInputValidator {
    private final Pattern LETTER_PATTERN = Pattern.compile("[ёЁа-яА-Я]");
    private final Pattern WORD_PATTERN = Pattern.compile("[ёЁа-яА-Я]{3,15}");

    public GameInputValidator(){}

    public boolean isValidLatter(String input){
        if (input == null) return false;
        return LETTER_PATTERN.matcher(input).matches();
    }

    public boolean isValidWord(String input){
        if (input == null) return false;
        return WORD_PATTERN.matcher(input).matches();
    }
}
