package BeforePattern;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

	public static void main(String[] args) throws SQLException {

            String url = "jdbc:mysql://localhost:3306/mydatabase";
            String username = "username";
            String password = "password";

            // Establish database connection
            Connection connection = DriverManager.getConnection(url, username, password);

            User newUser = new User(0, "maram", "maram@gmail.com");
            newUser.save(connection);
            System.out.println("New user created with id: " + newUser.getId());

            User user = User.findById(connection, newUser.getId());
            if (user != null) {
                System.out.println("Found user: " + user.getUsername());

                user.setUsername("newUsername");
                user.setEmail("newemail@gmail.com");
                user.save(connection);
                System.out.println("User updated: " + user.getUsername());

                user.delete(connection);
                System.out.println("User deleted.");
            } else {
                System.out.println("User not found.");
            }

            for (User u : User.findAll(connection)) {
                System.out.println(u.getUsername());
            }

            // Close connection
            connection.close();
    }

}
