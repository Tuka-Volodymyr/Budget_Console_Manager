package budget;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;
public class Database {
    public static Database data;
    static final String JDBC_DRIVER = "org.h2.Driver";
    public final Connection connection;
    public String url="jdbc:h2:.\\src\\budget\\db\\budget";

    public void createDatabase(){
        if (data == null) {
            data= new Database();
        }
    }
    public Database(){
        connection=getConnection();
    }

    public Connection getConnection() {
        Connection connection = null;
        try{
            Class.forName(JDBC_DRIVER);
            connection=DriverManager.getConnection(url);
            connection.setAutoCommit(true);
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Failed to get connection with db, check the url " + e.getMessage());
        }
        return connection;
    }
    public void closeDB() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
    public void createTable(){
        String sql= """
                CREATE TABLE IF NOT EXISTS budget(
                type VARCHAR(100) NOT NULL,
                purchase VARCHAR(100) NOT NULL,
                price FLOAT NOT NULL
                );""";
        String sql2= """
                CREATE TABLE IF NOT EXISTS balance(
                money FLOAT
                );""";
        try (Statement statement=connection.createStatement()){
            statement.executeUpdate(sql);
            statement.executeUpdate(sql2);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void save(Map<String,Float> purchaseMap,String name,float balance){
        if(!purchaseMap.isEmpty()) {
            String insertSQL2 = "INSERT INTO balance(money) VALUES(?)";
            String insertSQL = "INSERT INTO budget(type,purchase,price) VALUES(?,?,?)";
            for (String key : purchaseMap.keySet()) {
                try (PreparedStatement statement = connection.prepareStatement(insertSQL)) {
                    statement.setString(1, name);
                    statement.setString(2, key);
                    statement.setFloat(3, purchaseMap.get(key));
                    statement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try (PreparedStatement statement = connection.prepareStatement(insertSQL2)) {
                    statement.setFloat(1,balance);
                    statement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void deleteDataInTables(){
        String deleteBudget = "TRUNCATE TABLE budget";
        String deleteBalance = "TRUNCATE TABLE balance";
        try (Statement statement=connection.createStatement()){
            statement.executeUpdate(deleteBudget);
            statement.executeUpdate(deleteBalance);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public Map<String,Float> loadMap(String type) {
        Map<String,Float> purchaseMap=new LinkedHashMap<>();
        String SQL=String.format("SELECT purchase, price FROM budget WHERE type='%s'",type);
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet=statement.executeQuery(SQL);
            while (resultSet.next()){
                purchaseMap.put(resultSet.getString("purchase"), resultSet.getFloat("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return purchaseMap;
    }
    public float balance(){
        String SQL="SELECT money FROM balance";
        float balance=0;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet=statement.executeQuery(SQL);
            while (resultSet.next()){
                balance=resultSet.getFloat("money");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balance;
    }

}
