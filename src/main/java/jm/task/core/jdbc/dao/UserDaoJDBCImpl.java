package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
        //можно создать блок трай с ресурсами для коннекш. Тогда вводим поле класса и используем его в каждом методе
        //не выходит. Не получается назначить полю коннекшн значение из блока трай.
    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE if not exists `users` ( `id` bigint NOT NULL AUTO_INCREMENT," +
                    "`name` varchar(255) NOT NULL," +
                    "`lastName` varchar(255) NOT NULL," +
                    "`age` smallint NOT NULL, PRIMARY KEY (`id`))";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP table IF EXISTS users");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            String format = "INSERT INTO users (name, lastname, age) values ('%s', '%s', (%d))";
            String sql = String.format(format, name, lastName, age);
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public void removeUserById(long id) { //OK
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            String sql = "DELETE FROM users WHERE id = " + id + " LIMIT 1";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }

    public List<User> getAllUsers() {
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select name, lastName, age from users");
            List<User> resultList = new ArrayList<>();
            while (resultSet.next()) {
                resultList.add(new User(resultSet.getString("name"), resultSet.getString("lastName"), resultSet.getByte("age")));
            }
            return resultList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            String sql = "TRUNCATE table users";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
