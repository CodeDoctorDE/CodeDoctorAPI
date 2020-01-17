package com.gitlab.codedoctorde.api.config.database;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.HashMap;

/**
 * @author CodeDoctorDE
 */
public class BlobConfig {
    private final Connection connection;
    private final String table;
    private final String keyColumn;

    public BlobConfig(String url, String username, String password, String table, String keyColumn) throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
        this.keyColumn = keyColumn;
        this.table = table;
        createTable();
    }

    public BlobConfig(Connection connection, String table, String keyColumn) throws SQLException {
        this.table = table;
        this.keyColumn = keyColumn;
        this.connection = connection;
        createTable();
    }

    public void createTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + table + "(" + keyColumn + " varchar(255)" + "value varchar)");
        statement.close();
    }

    public void set(String key, String value) throws SQLException {
        String pSql = "insert into " + table + " values(?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(pSql);
        preparedStatement.setString(0, key);
        InputStream valueStream = new ByteArrayInputStream(value.getBytes(StandardCharsets.UTF_8));
        preparedStatement.setBinaryStream(1, valueStream);
        preparedStatement.execute();
    }

    public String get(String key) throws SQLException, IOException {
        ResultSet rs = connection.createStatement().executeQuery("select * from " + table + " where " + keyColumn + "=" + key);
        rs.next();
        return CharStreams.toString(new InputStreamReader(
                rs.getBinaryStream(1), Charsets.UTF_8));
    }

    /**
     * Get all values of this config
     *
     * @return
     * @throws SQLException
     * @throws IOException
     */
    public HashMap<String, String> get() throws SQLException, IOException {
        ResultSet rs = connection.createStatement().executeQuery("select * from " + table);
        HashMap<String, String> result = new HashMap<>();
        while (rs.next())
            result.put(rs.getString(0), CharStreams.toString(new InputStreamReader(rs.getBinaryStream(1), Charsets.UTF_8)));
        return result;
    }
}