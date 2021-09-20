package bank.bank.Database;

import bank.bank.Config.ConfigGetter;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class EstablishConnection {
    Connection connection;

    public Connection establishConnection(){
        ConfigGetter cg = new ConfigGetter();
        try{
            java.security.Security.setProperty("jdk.tls.disabledAlgorithms", "");
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(cg.getUrl(), cg.getUsername(), cg.getPassword());
            Bukkit.getServer().getLogger().info("Connection Successful to database...");
        } catch (ClassNotFoundException | SQLException e) {
            Bukkit.getServer().getLogger().info("Connection Failed");
            e.printStackTrace();
        }
        return connection;
    }
}
