package bank.bank.Database;

import bank.bank.Bank;
import bank.bank.Config.ConfigGetter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * Class that inserts the data into the database.
 */
public class InsertDatabase {
    ConfigGetter cg = new ConfigGetter();
    private Bank bankClass = Bank.getInstance();
    private Boolean exists;

    /**
     * Inserts the player into the database
     * @param player
     * @param value
     */
    public void insertQuery(Player player, int value){
        new BukkitRunnable(){
            @Override
            public void run(){
                QueryDatabase queryDatabase = new QueryDatabase();
                EstablishConnection establishConnection = new EstablishConnection();
                //Checks if the player exists in the database, if they then update the database DON'T insert.
                //Establishes Connection with Database
                Connection con = establishConnection.establishConnection();
                //Gets the players balance from the async method.
                String sql = "UPDATE " + cg.getTable() + " SET balance = ? WHERE uuid = ?";
                try {
                    PreparedStatement stmt = con.prepareStatement(sql);
                    stmt.setInt(1, value);
                    stmt.setString(2, player.getUniqueId().toString());
                    stmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(bankClass);
    }

    /**
     * Creates the table if it doesn't already exist
     */
    public void createTable(){
        new BukkitRunnable(){
            @Override
            public void run() {
                try {
                    EstablishConnection establishConnection = new EstablishConnection();
                    Connection con = establishConnection.establishConnection();
                    String sql = "CREATE TABLE IF NOT EXISTS " + cg.getTable() + " (uuid VARCHAR(255), balance INT(255), PRIMARY KEY (uuid))";
                    PreparedStatement stmt = con.prepareStatement(sql);
                    stmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(bankClass);
    }

    /**
     * Creates a player if they don't already exist
     * @param player
     * @param value
     */
    public void createPlayer(Player player, int value){
        new BukkitRunnable(){
            @Override
            public void run() {
                try {
                    EstablishConnection establishConnection = new EstablishConnection();
                    Connection con = establishConnection.establishConnection();
                    String sql = "INSERT INTO " + cg.getTable() + " (uuid, balance) VALUE (?, ?)";
                    PreparedStatement stmt = con.prepareStatement(sql);
                    stmt.setString(1, player.getUniqueId().toString());
                    stmt.setInt(2, value);
                    stmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(bankClass);
    }

}
