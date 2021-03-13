package pl.coderslab.entity;

import java.util.Scanner;

public class Main {

    public static final String MENU = "Podaj co chcesz zrobić:" +
            "\n1 - dodawanie użytkownika" +
            "\n2 - zmiana danych" +
            "\n3 - pobieranie użytkownika" +
            "\n4 - usuwanie użytkownika" +
            "\n5 - pobieranie wszystkich użytkowników" +
            "\n6 - wyjście";

    public static void main(String[] args) {
        try (Scanner scan = new Scanner(System.in)) {
            int wybor = 0;
            UserDAO DAO = new UserDAO();
            while (wybor != 6) {
                System.out.println(MENU);
                wybor = getInt(scan);

                switch (wybor) {
                    case 1:     //dodawanie użytkownika
                        getUserCreateData(scan, DAO);
                        break;
                    case 2:     //zmiana danych
                        getUserModifyData(scan, DAO);
                        break;
                    case 3:     //pobieranie użytkownika po id
                        getUserReadData(scan, DAO);
                        break;
                    case 4:     //usuwanie użytkownika
                        getUserDeleteData(scan, DAO);
                        break;
                    case 5:     //pobieranie wszystkich użytkowników
                        getAllUsers(DAO);
                        break;
                    case 6:     //wyjście
                        System.out.println("Pa pa.");
                        break;
                    default:
                        System.out.println("Niepoprawny wybór");
                        System.out.println(MENU);
                        break;
                }
            }
        } catch (NullPointerException e1) {
            e1.printStackTrace();
        }
    }

    private static void getAllUsers(UserDAO DAO) {
        User[] users = new User[0];
        users = DAO.findAll();
        printUsers(users);
    }

    private static void printUsers(User[] users) {
        for (User u : users) {
            System.out.println("ID: "+u.getId()+
                    "\tImię: "+u.getUserName()+
                    "\tEmail: "+u.getEmail()+
                    "\tHasło: "+u.getPassword());
        }
    }

    private static void getUserDeleteData(Scanner scan, UserDAO DAO) {
        System.out.println("Podaj ID użytkownika do usunięcia:");
        int id = getInt(scan);
        User user_delete = DAO.read(id);
        if (user_delete != null) {
            System.out.println("Czy na pewno chceszy usunąć poniższego użytkownika? [napisz T jeśli tak, napisz cokolwiek jeśli nie]");
            System.out.println("ID:" + user_delete.getId());
            System.out.println("Imię: " + user_delete.getUserName());
            System.out.println("Email: " + user_delete.getEmail());
            System.out.println("Hasło: " + user_delete.getPassword());
            String del = scan.next();
            if (del.equals("T")) {
                DAO.delete(id);
            }
        } else {
            System.out.println("Podanego użytkownika nie ma w bazie danych");
        }
    }

    private static void getUserReadData(Scanner scan, UserDAO DAO) {
        System.out.println("Podaj nr id użytkownika do pobrania:");
        User user_read = new User();
        int id = getInt(scan);

        user_read = DAO.read(id);
        if (user_read != null) {
            System.out.println("ID:" + user_read.getId());
            System.out.println("Imię: " + user_read.getUserName());
            System.out.println("Email: " + user_read.getEmail());
            System.out.println("Hasło: " + user_read.getPassword());
        } else {
            System.out.println("Podany użytkownik nie istnieje.");
        }
    }

    private static void getUserCreateData(Scanner scan, UserDAO DAO) {
        User user_create = new User();
        System.out.println("Podaj imię użytkownika:");
        user_create.setUserName(scan.next());
        System.out.println("Podaj adres email użytkownika:");
        user_create.setEmail(scan.next());
        System.out.println("Podaj hasło użytkownika:");
        user_create.setPassword(scan.next());
        DAO.create(user_create);
    }

    private static void getUserModifyData(Scanner scan, UserDAO DAO) {
        System.out.println("Podaj nr id użytkownika do edycji:");
        User user_modify = new User();
        user_modify = DAO.read(getInt(scan));
        if (user_modify != null) {
            System.out.println("Podaj nowe imię użytkownika:");
            user_modify.setUserName(scan.next());
            System.out.println("Podaj nowy adres email użytkownika:");
            user_modify.setEmail(scan.next());
            System.out.println("Podaj nowe hasło użytkownika:");
            user_modify.setPassword(scan.next());
            DAO.update(user_modify);
        } else {
            System.out.println("Podany użytkownik nie istnieje.");
        }
    }

    private static int getInt(Scanner scan) {
        while (!scan.hasNextInt()) {
            scan.next();
            System.out.print("Nieprawidłowe dane. Podaj jeszcze raz:");
        }
        int a = scan.nextInt();
        return a;
    }

}
