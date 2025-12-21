package ru.trukhmanov;


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //TODO: ввод слова с клавиатуры
        //TODO: считывание файла со словами(100 шутк) и пик рандомного

        Game game = new Game("game");
        System.out.println("Игрф начинается");
        while (!game.isEnd()){
            char symbol = scanner.nextLine().toUpperCase().charAt(0);
            game.play(symbol);
        }
    }
}