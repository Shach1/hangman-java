package ru.trukhmanov.core;

import java.util.regex.Pattern;

public final class GameInputValidator {
    private final Pattern latterPattern = Pattern.compile("[ёЁа-яА-Я]");
    private final Pattern wordPattern = Pattern.compile("[ёЁа-яА-Я]{3,15}");

    public GameInputValidator(){}

    public boolean isValidLatter(String input){
        if (input == null) return false;
        return latterPattern.matcher(input).matches();
    }

    public boolean isValidWord(String input){
        if (input == null) return false;
        return wordPattern.matcher(input).matches();
    }
}
