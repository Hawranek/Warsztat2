package pl.coderslab.entity;

import java.sql.*;
import java.util.Arrays;

public class UserDAO {
    private static final String CREATE_USER =
            "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";
    private static final String GET_USER_BY_ID = "SELECT * FROM users WHERE id=?";
    public static final String USERS = "SELECT * FROM users";
    public static final String UPDATE = "UPDATE users\n" +
            "SET username=?, email=?, password=? WHERE id=?;";
    public static final String DELETE = "DELETE\n" +
            "FROM users\n" +
            "WHERE id = ?;";

    public User create(User user) {
        try (Connection conn = DBUtil.connect();
             PreparedStatement statement =
                     conn.prepareStatement(CREATE_USER, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.executeUpdate();
            //   /\ dodawanie parametrów do zapytania

            //   \/ pobieranie nadanego nru id do id obiektu user (synchronizacja)
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getInt(1));
            }

            return user;
        } catch (SQLIntegrityConstraintViolationException e1) {
            System.out.println("Podany użytkownik istnieje już w bazie danych.");

            return null;
        } catch (SQLException throwables) {
            throwables.printStackTrace();

            return null;
        }
    }

    public User read(int id) {
        try (Connection conn = DBUtil.connect();            //połączenie do baz danych
             PreparedStatement statement = conn.prepareStatement(GET_USER_BY_ID)) {     //stworzenie zapytania z parametrami
            statement.setInt(1, id);                                      //wstawienie parametru id do zapytania
            ResultSet resultSet = statement.executeQuery();                             // przesłanie zapytania
            if (resultSet.next()) {                          //sprawdzanie, czy istnieje użytkownik o podanym id
                User user = new User();
                user.setId(resultSet.getInt("id"));                     //pobieranie danych z tabeli do obiektu user
                user.setUserName(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }
        return null;
    }

    public String hashPassword(String password) {  //hashowanie hasła podaną metodą
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public User[] findAll() {
        try (Connection conn = DBUtil.connect();
             PreparedStatement statement = conn.prepareStatement(USERS);
             ResultSet rs = statement.executeQuery()) {
            User[] users = new User[0];                                         //inicjacia tabeli to zwrócenia
            while (rs.next()) {
                User nowyUser = new User();                                     //inicjacja nowego usera
                nowyUser.setId(rs.getInt("id"));                     //dodawanie danych o usera
                nowyUser.setUserName(rs.getString("userName"));
                nowyUser.setEmail(rs.getString("email"));
                nowyUser.setPassword(rs.getString("password"));
                users = addToArray(nowyUser, users);
            }
            return users;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public User[] addToArray(User user, User[] users) {
        User[] nowiUsersi = Arrays.copyOf(users, users.length + 1);
        nowiUsersi[nowiUsersi.length - 1] = user;
        return nowiUsersi;
    }

    public void update(User user) {
        try (Connection conn = DBUtil.connect();
             PreparedStatement statement = conn.prepareStatement(UPDATE)) {
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setInt(4, user.getId());
            statement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e1) {
            System.out.println("Podany użytkownik już istnieje w bazie.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void delete(int id) {
        try (Connection conn = DBUtil.connect();
             PreparedStatement statement = conn.prepareStatement(DELETE)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
