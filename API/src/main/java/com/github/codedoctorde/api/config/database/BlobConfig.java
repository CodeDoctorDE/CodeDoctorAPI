package com.github.codedoctorde.api.config.database;

import com.github.codedoctorde.api.config.JsonConfig;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.gson.JsonObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author CodeDoctorDE
 */
public class BlobConfig extends JsonConfig {
    private final Connection connection;
    private final String table;
    private JsonObject jsonObject;
    private List<String> changes = new ArrayList<>();

    public BlobConfig(Connection connection, String table) throws SQLException {
        this.table = table;
        this.connection = connection;
        createTable();
    }

    public BlobConfig(String url, String username, String password, String table) throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
        this.table = table;
        createTable();
    }

    public void createTable() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS ?(current_key varchar(255), current_value blob)");
        statement.setString(1, table);
        statement.executeUpdate();
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

    public String get(String key, boolean fromTable) throws SQLException, IOException {

        ResultSet rs = connection.createStatement().executeQuery("select * from " + table + " where current_key =" + key);
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

    @Override
    public void reload() {

    }

    @Override
    public void save() {

    }
}
