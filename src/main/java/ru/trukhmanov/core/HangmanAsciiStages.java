package ru.trukhmanov.core;

public final class HangmanAsciiStages {
    private HangmanAsciiStages(){}

    public static String getStage(int errorCount){
        return switch (errorCount) {
            case 0 -> """
                      +---+
                      |   |
                          |
                          |
                          |
                          |
                    =========""";
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
            default -> throw new IllegalArgumentException("Unknown stage: " + errorCount);
        };
    }
}