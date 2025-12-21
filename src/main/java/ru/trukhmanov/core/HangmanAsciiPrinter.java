package ru.trukhmanov.core;

public class HangmanAsciiPrinter {
    public static String hangmanAscii(int errorCount){
        return switch (errorCount) {
            case 1 -> """
                      +---+
                      |   |
                      O   |
                          |
                          |
                          |
                    =========""";
            case 2 -> """
                      +---+
                      |   |
                      O   |
                      |   |
                          |
                          |
                    =========""";
            case 3 -> """
                      +---+
                      |   |
                      O   |
                     /|   |
                          |
                          |
                    =========""";
            case 4 -> """
                      +---+
                      |   |
                      O   |
                     /|\\  |
                          |
                          |
                    =========""";
            case 5 -> """
                      +---+
                      |   |
                      O   |
                     /|\\  |
                     /    |
                          |
                    =========""";
            case 6 -> """
                      +---+
                      |   |
                      O   |
                     /|\\  |
                     / \\  |
                          |
                    =========""";
            default -> """
                      +---+
                      |   |
                          |
                          |
                          |
                          |
                    =========""";
        };
    }
}