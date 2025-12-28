package ru.trukhmanov;


import ru.trukhmanov.core.CLIGameProvider;
import ru.trukhmanov.core.IGameProvider;

public class Main {
    public static void main(String[] args) {
        new CLIGameProvider().startGame();
    }
}