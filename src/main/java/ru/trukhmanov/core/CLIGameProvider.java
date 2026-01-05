package ru.trukhmanov.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class CLIGameProvider implements IGameProvider {

    private Game game;
    private final Scanner scanner = new Scanner(System.in);
    private final Random random = new Random();

    @Override
    public void startGame(){
        while (true){
            System.out.println("Меню игры:\n1. Ввести слово в консоли\n2. Рандомное слово\n0. Выход \n ->");
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

    private boolean inputIsValid(String str){
        if (str.length() != 1) return false;
        if (!str.matches("[ёЁа-яА-Я]")) return false;
        return true;
    }

    private void printCurrentState(){
        System.out.println(HangmanAsciiStages.getStage(game.getErrorCounter()));
        System.out.println("Слово: " + game.getMaskHiddenWordString());
        System.out.println("Ошибки: " + game.getErrorCounter() + " - " + game.getMisspelledLetters());
    }

    private void play(){
        System.out.println("Игра начинается 🎬");
        printCurrentState();
        while (!game.isEnd()){
            System.out.print("Введите букву 👉 ");
            String input = scanner.nextLine().toUpperCase();
            if (inputIsValid(input)){
                switch(game.play(input.charAt(0))){
                    case -3:{
                        System.out.println("Вы уже угадали эту букву!");
                        break;
                    }
                    case -2:{
                        System.out.println("Вы повторно ввели неправильную букву!");
                        break;
                    }
                    case -1:{
                        System.out.println("\n\n\n❌❌❌Игра окончена. Вы проиграли");
                        System.out.printf("Правильное слово: %s\n", game.getHiddenWordString());
                        printCurrentState();
                        break;
                    }
                    case 1:{
                        System.out.println("\n\n\n✅✅✅Вы выйграли!");
                        printCurrentState();
                        break;
                    }
                    default:{
                        printCurrentState();
                    }
                }
            }
            else System.out.println("❗️Некорректный ввод");
        }
    }

    private void playWithPlayerWord() {
        System.out.println("Введите слово, которое будут отгадывать:");
        if (scanner.hasNextLine()){
            String word = scanner.nextLine();
            if(!word.matches("[ёЁа-яА-Я]{3,15}")){
                System.out.println("Неправильный формат слова");
                playWithPlayerWord();
                return;
            }
            this.game = new Game(word);
            play();
        }

    }

    private void playWithRandomWord(){
        try {
            ArrayList<String> words = new ArrayList<>();
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream inputStream = classloader.getResourceAsStream("words.txt");
            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);
            for (String word; (word = reader.readLine()) != null;) {
                words.add(word);
            }
            this.game = new Game(words.get(random.nextInt(words.size())));
            play();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}