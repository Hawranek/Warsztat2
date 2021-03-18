package pl.coderslab.entity;


public class Main_v2 {
    public static void main(String[] args) {
        int wybor = 0;
        UserDAO DAO = new UserDAO();
        while (wybor != 6) {
            Menu.printMenu();
            wybor = KeyboardReader.readInt("");

            switch (wybor) {
                case 1:     //dodawanie użytkownika
                    Menu.createUser();
                    break;
                case 2:     //zmiana danych
                    Menu.modify();
                    break;
                case 3:     //pobieranie użytkownika po id
                    Menu.read();
                    break;
                case 4:     //usuwanie użytkownika
                    Menu.delete();
                    break;
                case 5:     //pobieranie wszystkich użytkowników
                    Menu.findAllUsers();
                    break;
                case 6:     //wyjście
                    System.out.println("Pa pa.");
                    break;
                default:
                    System.out.println("Niepoprawny wybór");
                    break;
            }
        }

    }
}
