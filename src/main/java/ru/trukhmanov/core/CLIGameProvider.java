package ru.trukhmanov.core;

import java.util.Scanner;

public class CLIGameProvider implements IGameProvider {

    Game game;

    @Override
    public void startGame(){
        Scanner scanner = new Scanner(System.in);

        //TODO: ввод слова с клавиатуры
        //TODO: считывание файла со словами(100 шутк) и пик рандомного

        this.game = new Game("test");

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
}