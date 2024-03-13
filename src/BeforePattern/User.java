package BeforePattern;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String username;
    private String email;
    
	public User(int id, String username, String email) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
    
    // CRUD operations 
	
    public void save(Connection connection) throws SQLException {
        if (id == 0) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users (username, email) VALUES (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, username);
            statement.setString(2, email);

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
            }
        } else {
            PreparedStatement statement = connection.prepareStatement("UPDATE users SET username = ?, email = ? WHERE id = ?");
            statement.setString(1, username);
            statement.setString(2, email);
            statement.setInt(3, id);

            statement.executeUpdate();
        }
    }

    public void delete(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id = ?");
        statement.setInt(1, id);

        statement.executeUpdate();
    }

    public static User findById(Connection connection, int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            int userId = resultSet.getInt("id");
            String username = resultSet.getString("username");
            String email = resultSet.getString("email");

            return new User(userId, username, email);
        }

        return null;
    }

    public static List<User> findAll(Connection connection) throws SQLException {
        List<User> userList = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int userId = resultSet.getInt("id");
            String username = resultSet.getString("username");
            String email = resultSet.getString("email");

            userList.add(new User(userId, username, email));
        }

        return userList;
    }
}
