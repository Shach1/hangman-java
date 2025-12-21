package ru.trukhmanov.core;

import java.util.Scanner;

public class GameProvider {
    public static void startGame(){
        Scanner scanner = new Scanner(System.in);

        //TODO: ввод слова с клавиатуры
        //TODO: считывание файла со словами(100 шутк) и пик рандомного

        Game game = new Game("test");
        System.out.println("Игра начинается 🎬");
        game.printCurrentState();
        while (!game.isEnd()){
            System.out.print("Введите букву 👉 ");
            String input = scanner.nextLine().toUpperCase();
            if (_inputIsValid(input)) game.play(input.charAt(0));
            else System.out.println("❗️Некорректный ввод");
        }
    }
    private static boolean _inputIsValid(String str){
        if (str.length() != 1) return false;
        if (!str.matches("[a-zA-Zа-яА-Я]")) return false;
        return true;
    }
}
