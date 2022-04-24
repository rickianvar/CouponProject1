package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCUtils {

    public static final String URL = "jdbc:mysql://localhost:3306?createDatabaseIfNotExist=FALSE&useTimezone=TRUE&serverTimezone=UTC";
    public static final String USER = "root";
    public static final String PASSWORD = "1234";

    // Step 1 - Loads the Driver
    // Since we're using MySQL Connector J 8.0.17 - Type 4 - this is not relevant no more
    // You can comment it out or just leave it here - it won't affect you
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void closeStatement(Statement stmt) throws SQLException {
        if (stmt != null) {
            stmt.close();
        }
    }

    public static void closeConnection(Connection conn) throws SQLException {
        ConnectionPool.getInstance().restoreConnection(conn);
    }

    public static void closeResultSet(ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
        }
    }

    public static void closeResources(Connection connection, PreparedStatement preparedStatement) throws SQLException {
        if (connection != null) {
            closeConnection(connection);
        }

        if (preparedStatement != null) {
            closeStatement(preparedStatement);
        }
    }

    public static void closeResources(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) throws SQLException {
        closeResources(connection, preparedStatement);
        if (resultSet != null) {
            closeResultSet(resultSet);
        }
    }

    public static Connection getConnection() throws SQLException, InterruptedException {
        return ConnectionPool.getInstance().getConnection();
    }

    public static void execute(String sql) throws SQLException, InterruptedException {
        // Step 2 - Open Connection to db;
        Connection conn = getConnection();

        // Step 3 - Prepared Statement and execute it
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.execute();
        // Step 5 - Close Resources
        closeResources(conn, statement);
    }

    public static void execute(String sql, Map<Integer, Object> map) throws SQLException, InterruptedException {
        // Step 2 - Open Connection to db;
        Connection conn = getConnection();

        // Step 3 - Prepared Statement and execute it
        PreparedStatement statement = conn.prepareStatement(sql);
        addParams(statement, map);
        statement.execute();
        // Step 5 - Close Resources
        closeResources(conn, statement);
    }


    public static List<?> executeResults(String sql) throws SQLException, InterruptedException {

        // Step 2 - Open Connection to DB
        Connection conn = getConnection();
        ResultSet resultSet = null;

        // Step 3 - Prepared Statement
        PreparedStatement statement = conn.prepareStatement(sql);

        // Step 4 - ResultSet
        resultSet = statement.executeQuery();
        List<?> res = resultSetToArrayList(resultSet);

        // Step 5 - Close resources
        closeResources(conn, statement, resultSet);

        return res;
    }

    public static List<?> executeResults(String sql, Map<Integer, Object> map) throws SQLException, InterruptedException {

        // Step 2 - Open Connection to DB
        Connection conn = getConnection();
        ResultSet resultSet = null;

        // Step 3 - Prepared Statement
        PreparedStatement statement = conn.prepareStatement(sql);
        addParams(statement, map);

        // Step 4 - ResultSet
        resultSet = statement.executeQuery();
        List<?> res = resultSetToArrayList(resultSet);

        // Step 5 - Close resources
        closeResources(conn, statement, resultSet);

        return res;
    }

    public static List<?> resultSetToArrayList(ResultSet rs) throws SQLException {

        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();

        List<HashMap<String, Object>> list = new ArrayList<>();

        while (rs.next()) {
            HashMap<String, Object> row = new HashMap<>(columns);
            for (int i = 1; i <= columns; ++i) {
                row.put(md.getColumnName(i), rs.getObject(i));
            }
            list.add(row);
        }

        return list;
    }

    public static void addParams(PreparedStatement statement, Map<Integer, Object> map) throws SQLException {
        for (Map.Entry<Integer, Object> entry : map.entrySet()) {
            Integer key = entry.getKey();
            Object obj = entry.getValue();

            if (obj instanceof Integer) {
                statement.setInt(key, (int) obj);
            } else if (obj instanceof String) {
                statement.setString(key, (String) obj);
            } else if (obj instanceof Double) {
                statement.setDouble(key, (double) obj);
            } else if (obj instanceof Float) {
                statement.setDouble(key, (Float) obj);
            } else if (obj instanceof Date) {
                statement.setDate(key, (Date) obj);
            }
        }
    }
}
