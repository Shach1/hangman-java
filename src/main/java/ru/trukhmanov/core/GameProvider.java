package ru.trukhmanov.core;

import java.util.Scanner;

public class GameProvider {
    public static void startGame(){
        Scanner scanner = new Scanner(System.in);

        //TODO: ввод слова с клавиатуры
        //TODO: считывание файла со словами(100 шутк) и пик рандомного

        Game game = new Game("test");
        System.out.println("Игра начинается");
        game.printCurrentState();
        while (!game.isEnd()){
            System.out.print("Введите букву: ");
            char symbol = scanner.nextLine().toUpperCase().charAt(0);
            game.play(symbol);
        }
    }
}
