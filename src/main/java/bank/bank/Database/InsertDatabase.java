package bank.bank.Database;

import bank.bank.Bank;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class InsertDatabase {
    private Bank bankClass = Bank.getInstance();
    private Boolean exists;
    // Queries through then inserts data into the database.
    public void insertQuery(Player player, int value){
        new BukkitRunnable(){
            @Override
            public void run(){
                QueryDatabase queryDatabase = new QueryDatabase();
                EstablishConnection establishConnection = new EstablishConnection();
                //Checks if the player exists in the database, if they then update the database DON'T insert.
                queryDatabase.fetchPlayer(player).thenAccept(exists ->{
                   if(exists){
                       //Establishes Connection with Database
                       Connection con = establishConnection.establishConnection();
                       //Gets the players balance from the async method.
                       String sql = "UPDATE vault SET balance = ? WHERE uuid = ?";
                       try {
                           PreparedStatement stmt = con.prepareStatement(sql);
                           stmt.setInt(1, value);
                           stmt.setString(2, player.getUniqueId().toString());
                           stmt.executeUpdate();
                       } catch (SQLException e) {
                           e.printStackTrace();
                       }
                   }
                   //If the player does not exist in the database then insert DON'T update.
                   else {
                       try {
                           Connection con = establishConnection.establishConnection();
                           String sql = "INSERT INTO `vault` (uuid, balance) VALUE ('"+player.getUniqueId()+"', '"+value+"')";
                           PreparedStatement stmt = con.prepareStatement(sql);
                           stmt.executeUpdate();
                       } catch (SQLException e) {
                           e.printStackTrace();
                       }
                   }
                });
            }
        }.runTaskAsynchronously(bankClass);
    }

}
