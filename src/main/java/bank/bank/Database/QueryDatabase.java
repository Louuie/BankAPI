package bank.bank.Database;

import bank.bank.Bank;
import bank.bank.Config.ConfigGetter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class QueryDatabase {
    private Bank bankClass = Bank.getInstance();
    Connection connection;
    private Player p;
    private String uuid;
    private int balance;
    ConfigGetter cs = new ConfigGetter();
    EstablishConnection establishConnection = new EstablishConnection();

    public CompletableFuture<Integer> getBalance(Player player){
        CompletableFuture<Integer> result = new CompletableFuture<>();
        new BukkitRunnable(){
            @Override
            public void run(){
                String sql = "SELECT uuid, balance " + "FROM " + cs.getTable();
                try {
                    Connection con = establishConnection.establishConnection();
                    PreparedStatement stmt = con.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery();
                    while(rs.next()) {
                        UUID configUUID = UUID.fromString(rs.getString(cs.getUUIDColumnName()));
                        if(player.getUniqueId().equals(configUUID)) {
                            balance = rs.getInt(cs.getBalanceColumnName());
                            result.complete(balance);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(bankClass);
        return result;
    }



    public CompletableFuture<Boolean> fetchPlayer(Player p){
        CompletableFuture<Boolean> result = new CompletableFuture<>();
        new BukkitRunnable(){
            @Override
            public void run(){
                String sql = "SELECT * FROM `vault` WHERE uuid=?";
                try {
                    Connection con = establishConnection.establishConnection();
                    PreparedStatement stmt = con.prepareStatement(sql);
                    stmt.setString(1, p.getUniqueId().toString());
                    ResultSet rs = stmt.executeQuery();
                    while(rs.next()){
                        UUID configUUID = UUID.fromString(rs.getString(cs.getUUIDColumnName()));
                        if(p.getUniqueId().equals(configUUID)){
                            result.complete(true);
                        } else{
                            result.complete(false);
                        }
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(bankClass);
        return result;
    }


}
