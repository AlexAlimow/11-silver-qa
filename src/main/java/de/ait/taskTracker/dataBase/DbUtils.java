package de.ait.taskTracker.dataBase;

import java.sql.*;

public class DbUtils {

   static Connection connection;

    public static String getDataFromTable(String query, String label) {
        try {
            dataBaseConnection();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {

                return resultSet.getString(label);
            } else {
                return null;
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteRowFromTable(String query) {
        try {
            dataBaseConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.executeUpdate();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void dataBaseConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(DbData.dbUrl,DbData.username,DbData.password);
    }

}
