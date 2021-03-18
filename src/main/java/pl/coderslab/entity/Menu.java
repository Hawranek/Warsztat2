package pl.coderslab.entity;

import java.util.Scanner;

public class Menu {
    private static UserDAO DAO = new UserDAO();
    public static final String MENU = "Podaj co chcesz zrobić:" +
            "\n1 - dodawanie użytkownika" +
            "\n2 - zmiana danych" +
            "\n3 - pobieranie użytkownika" +
            "\n4 - usuwanie użytkownika" +
            "\n5 - pobieranie wszystkich użytkowników" +
            "\n6 - wyjście";

    public static void createUser() {           //działa

        User user_create = new User();
        user_create.setUserName(KeyboardReader.readString("Podaj imię użytkownika:"));
        user_create.setEmail(KeyboardReader.readString("Podaj adres email użytkownika:"));
        user_create.setPassword(KeyboardReader.readString("Podaj hasło użytkownika:"));

        DAO.create(user_create);
    }

    public static void findAllUsers() {         //działa
        User[] users = new User[0];
        users = DAO.findAll();
        printUsers(users);
    }

    private static void printUsers(User[] users) {          //działa
        for (User u : users) {
            System.out.println(u.toString());
        }
    }

    public static void printMenu() {                        //działa
        System.out.println(MENU);
    }

    public static void delete() {                           //działa
        int id = KeyboardReader.readInt("Podaj ID użytkownika do usunięcia:");
        User user_delete = DAO.read(id);
        if (user_delete != null) {
            System.out.println("Czy na pewno chceszy usunąć poniższego użytkownika? ");
            System.out.println(user_delete.toString());
            String del = KeyboardReader.readString("[napisz T jeśli tak, napisz cokolwiek jeśli nie]");
            if (del.equals("T")) {
                DAO.delete(id);
            }
        } else {
            System.out.println("Podanego użytkownika nie ma w bazie danych");
        }
    }

    public static void read() {                     //działa
        int id = KeyboardReader.readInt("Podaj nr id użytkownika do pobrania:");

        User user_read = DAO.read(id);
        if (user_read != null) {
            System.out.println(user_read.toString());
        } else {
            System.out.println("Podany użytkownik nie istnieje.");
        }
    }

    public static void modify() {           //działa

        User user_modify = DAO.read(KeyboardReader.readInt("Podaj nr id użytkownika do edycji:"));

        if (user_modify != null) {
            user_modify.setUserName(KeyboardReader.readString("Podaj nowe imię użytkownika:"));
            user_modify.setEmail(KeyboardReader.readString("Podaj nowy adres email użytkownika:"));
            user_modify.setPassword(KeyboardReader.readString("Podaj nowe hasło użytkownika:"));

            DAO.update(user_modify);
        } else {
            System.out.println("Podany użytkownik nie istnieje.");
        }
    }
}
