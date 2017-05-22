package sql;

import helpers.ResourceHelper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.ParseException;

/**
 * Created by korovin on 1/13/2017.
 * SQL helper that encapsulates connection object
 */
public class SQLManager {
    private Connection connection;
    private DbSettings settings;
    private static final Integer BATCH_LIMIT = 800;

    public SQLManager(String settingsPathCfg) throws SQLException, ClassNotFoundException, IOException, org.json.simple.parser.ParseException {
        settings = new DbSettings(settingsPathCfg);
        this.connection = this.createConnection();
    }

    private Connection createConnection() throws ClassNotFoundException, SQLException {
        Connection connection = null;
        Class.forName(this.settings.getJdbcDriver());
        connection = DriverManager.getConnection(
                this.settings.getConnectionString(),
                this.settings.getUserName(),
                this.settings.getPassword());
        return connection;
    }

    public void dropTable(String tableName) throws SQLException {
        String sql = String.format("DROP TABLE IF EXISTS %1$s", tableName);

        this.executeUpdateSQL(sql);
    }

    public boolean isTableExist(String tableName) throws SQLException {
        DatabaseMetaData meta = this.connection.getMetaData();
        ResultSet res = meta.getTables(null, null, tableName, null);
        return res.next();
    }

    /**
     * Insert entries from GES file
     * @throws SQLException
     * @throws ParseException
     */
    public void insertData(String insertSQL, JSONArray codeArr) throws SQLException, ParseException {
        PreparedStatement statement = connection.prepareStatement(insertSQL);
        JSONParser parser = new JSONParser();

        System.out.println(insertSQL);

        for (int i = 0; i < codeArr.size(); i++) {
            if (i % BATCH_LIMIT == 0) {
                statement.executeBatch();
            }
            JSONObject elem = (JSONObject)codeArr.get(i);
            String code = (String)elem.get("code");
            statement.setString(1, code);

            String isPartOf = (String)elem.get("isPartOf");
            if (isPartOf == null || isPartOf.length() == 0) {
                statement.setNull(3, Types.VARCHAR);
            }
            else {
                statement.setString(3, isPartOf);
            }

            String name = (String)elem.get("name");
            System.out.println(name);
            statement.setString(2, name);

            statement.addBatch();
        }

        statement.executeBatch();
        statement.close();
    }

    public void executeUpdateSQL(String sql) throws SQLException {
        Statement statement = this.connection.createStatement();
        statement.executeUpdate(sql);
    }

    public ResultSet executeQuerySQL(String sql) throws SQLException {
        Statement statement = this.connection.createStatement();
        return statement.executeQuery(sql);
    }

    public void closeConn() throws SQLException {
        if (this.connection != null) {
            this.connection.close();
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException, org.json.simple.parser.ParseException, IOException, ParseException {
        SQLManager manager = new SQLManager("db.settings.json");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new InputStreamReader(ResourceHelper.getResource("final.json", manager.getClass().getClassLoader())));

        JSONObject jsonObject = (JSONObject) obj;
        JSONArray arr = (JSONArray)jsonObject.get("diseases");
        manager.insertData("INSERT INTO icd VALUES (?, ?, ?)", arr);
    }
}
