package ru.trukhmanov.core;

import java.util.Scanner;

public class GameEngine {
    private final Scanner scanner = new Scanner(System.in);
    private final GameInputValidator gameInputValidator = new GameInputValidator();
    private final Dictionary dictionary = new Dictionary("words.txt");

    // Game states
    private boolean isGameLose = false;
    private boolean isGameWon = false;
    private SecretWord secretWord;
    private short errorCounter = 0;
    private String misspelledLetters = "";


    public void startMainMenu(){
        while (true){
            System.out.println("""
            Меню игры:
                1. Ввести слово в консоли
                2. Рандомное слово
                0. Выход
            ->
            """);
            String menuInput = scanner.nextLine();
            switch (menuInput){
                case "0": {
                    return;
                }
                case "1": {
                    playWithPlayerWord();
                    break;
                }
                case "2":{
                    playWithRandomWord();
                    break;
                }
            }
        }
    }

    private void playWithPlayerWord() {
        System.out.println("Введите слово от 3 до 15 букв, без пробелов и спецсимволов:");
        if (scanner.hasNextLine()){
            String wordForGame = scanner.nextLine();
            if(!gameInputValidator.isValidWord(wordForGame)){
                System.out.println("Неправильный формат слова");
                playWithPlayerWord();
                return;
            }
            startGame(wordForGame);
        }
    }

    private void printCurrentState(){
        System.out.println(HangmanAsciiStages.getStage(errorCounter));
        System.out.println("Слово: " + secretWord.getMaskSecretWordString());
        System.out.println("Ошибки: " + errorCounter + " - " + misspelledLetters);
    }

    private void playWithRandomWord(){
        String wordForGame = dictionary.getRandomWord();
        startGame(wordForGame);
    }

    private void handlingGuessIncorrect(char letter){
        errorCounter++;
        misspelledLetters += letter + " ";
        final short maxErrorCount = 6;
        if (errorCounter == maxErrorCount){                 //проверка поражения
            isGameLose = true;
            return;
        }
        printCurrentState();
    }

    private void handlingGuessCorrect(char letter){
        secretWord.replaceVoidInMaskSecretWord(letter);
        if(secretWord.isSecretWordEqualsMaskSecretWord()){  //проверка победы
            isGameWon = true;
            return;
        }
        printCurrentState();
    }

    private boolean isGameOver(){
        return isGameLose || isGameWon;
    }

    private void restartGameStates(){
        isGameWon = false;
        isGameLose = false;
        secretWord = null;
        errorCounter = 0;
        misspelledLetters = "";
    }

    private void startGame(String wordForGame){
        System.out.println("\n\n\nИгра начинается 🎬");
        secretWord = new SecretWord(wordForGame);
        printCurrentState();
        while (!isGameOver()){
            System.out.print("Введите букву 👉 ");
            String cliInput = scanner.nextLine().toUpperCase();
            if (!gameInputValidator.isValidLatter(cliInput)){
                System.out.println("❗️Некорректный ввод");
                continue;
            }
            if (secretWord.getMaskSecretWordString().contains(cliInput)){
                System.out.println("Вы уже угадали эту букву!");
                continue;
            }
            if (misspelledLetters.contains(cliInput)){
                System.out.println("Вы повторно ввели неправильную букву!");
                continue;
            }

            char supposedLetter = cliInput.charAt(0);
            if(secretWord.checkLetterInSecretWord(supposedLetter)){
                handlingGuessCorrect(supposedLetter);
                continue;
            }
            handlingGuessIncorrect(supposedLetter);
        }

        if (isGameWon){
            System.out.println("\n✅✅✅Вы выйграли!");
            printCurrentState();
            restartGameStates();
        }
        if (isGameLose){
            System.out.println("\n❌❌❌Игра окончена. Вы проиграли");
            System.out.printf("Правильное слово: %s\n", secretWord.getSecretWordString());
            printCurrentState();
            restartGameStates();
        }
    }
}