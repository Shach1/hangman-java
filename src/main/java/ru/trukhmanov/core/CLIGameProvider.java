package ru.trukhmanov.core;

import java.util.Scanner;

public class CLIGameProvider implements IGameProvider {

    private Game game;
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void startGame(){
        while (true){
            System.out.println("Меню игры:\n1. Ввести слово в консоли\n2. Рандомное слово\n0. Выход \n ->");
            String menuInput = scanner.nextLine();
            switch (menuInput){
                case "1": {
                    _playWithYourWord();
                    break;
                }
                case "2":{
                    //TODO: считывание файла со словами(100 шутк) и пик рандомного
                    System.out.println("Эмитация рандома слова");
                    _playWithRandomWord();
                    break;
                }
                default: return;
            }
        }
    }

    private boolean _inputIsValid(String str){
        if (str.length() != 1) return false;
        if (!str.matches("[a-zA-Zа-яА-Я]")) return false;
        return true;
    }

    private void printCurrentState(){
        System.out.println(HangmanAsciiPrinter.hangmanAscii(game.getErrorCounter()));
        System.out.println("Слово: " + String.copyValueOf(game.getMaskHiddenWord()));
        System.out.println("Ошибки: " + game.getMisspelledLetters());
    }

    private void play(){
        System.out.println("Игра начинается 🎬");
        printCurrentState();
        while (!game.isEnd()){
            System.out.print("Введите букву 👉 ");
            String input = scanner.nextLine().toUpperCase();
            if (_inputIsValid(input)){
                switch(game.play(input.charAt(0))){
                    case -1:{
                        System.out.println("❌❌❌Игра окончена вы проиграли");
                        printCurrentState();
                        break;
                    }
                    case 1:{
                        System.out.println("✅✅✅Вы выйграли!");
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

    private void _playWithYourWord() {
        System.out.println("Введите слово, которое будут отгадывать:");
        if (scanner.hasNextLine()){
            String word = scanner.nextLine();
            this.game = new Game(word);
            play();
        }

    }

    private void _playWithRandomWord(){
        this.game = new Game("test");
        play();
    }
}