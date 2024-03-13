package AfterPattern;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {

	public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String username = "username";
        String password = "password";

        // Establish database connection
        Connection connection = DriverManager.getConnection(url, username, password);

        // Create a user mapper
        DataMapper userMapper = new DataMapperImpl(connection);

        User newUser = new User(0, "maram", "maram@gmail.com");
        userMapper.save(newUser);
        System.out.println("New user created with id: " + newUser.getId());

        User user = userMapper.findById(newUser.getId());
        if (user != null) {
            System.out.println("Found user: " + user.getUsername());

            // Update user properties
            user.setUsername("newUsername");
            user.setEmail("newemail@gmail.com");
            userMapper.update(user);
            System.out.println("User updated: " + user.getUsername());

            // Delete user
            userMapper.delete(user.getId());
            System.out.println("User deleted.");
        } else {
            System.out.println("User not found.");
        }

        List<User> userList = userMapper.findAll();
        System.out.println("All users:");
        for (User u : userList) {
            System.out.println(u.getUsername());
        }
        
        connection.close();

	}

}
