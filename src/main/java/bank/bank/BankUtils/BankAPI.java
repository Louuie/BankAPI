package bank.bank.BankUtils;

import bank.bank.Bank;
import bank.bank.Config.ConfigGetter;
import bank.bank.Config.DatabaseConfig;
import bank.bank.Database.EstablishConnection;
import bank.bank.Database.InsertDatabase;
import bank.bank.Database.QueryDatabase;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

/**
 * Main BankAPI Class that handles all the methods
 */
public class BankAPI {
    private int balance;
    private final Bank bankClass = Bank.getInstance();

    EstablishConnection establishConnection = new EstablishConnection();
    QueryDatabase queryDatabase = new QueryDatabase();
    InsertDatabase insertDatabase = new InsertDatabase();
    ConfigGetter cg = new ConfigGetter();


    /**
     * Adds passed value to the players balance
     * @param player
     * @param m
     */
    public void addMoney(Player player,  int m){
        queryDatabase.fetchPlayer(player).thenAccept(exists -> {
            if(exists){
                getBalance(player).thenAccept(balance -> {
                    insertDatabase.insertQuery(player, balance + m);
                });
            } else {insertDatabase.createPlayer(player, m);}
        });
    }

    /**
     * Removes passed value from the players balance
     * @param player
     * @param m
     */
    public void removeMoney(Player player, int m){
        getBalance(player).thenAccept(balance -> {
            insertDatabase.insertQuery(player, balance - m);
        });
    }

    /**
     * Sets up a money transaction between two players (ONLY USE WHEN PLAYERS ARE SENDING MONEY ONLY, NOT ITEMS!)
     * @param sender
     * @param receiver
     * @param value
     */
    public void setupMoneyTransaction(Player sender, Player receiver, int value) {
        getBalance(sender.getPlayer()).thenAccept(balance -> {
            if (balance < value) {
                sender.sendMessage(ChatColor.RED + "Insufficient Funds!");
            } else {
                addMoney(receiver, value);
                removeMoney(sender, value);
                sender.sendMessage("you sent $" + value + " to " + receiver.getName());
            }
        });
    }

    /**
     * Setups a Item transaction between two players (ONLY USE WHEN PLAYERS ARE SELLING ITEMS TO EACH OTHER ONLY, NOT JUST MONEY!)
     * @param sender
     * @param receiver
     * @param value
     * @param item
     */
    public void setupItemTransaction(Player sender, Player receiver, ItemStack item, int value){
        getBalance(sender.getPlayer()).thenAccept(balance -> {
            if (balance < value) {
                sender.sendMessage(ChatColor.RED + "Insufficient Funds!");
            } else {
                addMoney(receiver, value);
                removeMoney(sender, value);
                receiver.getInventory().addItem(item);
                sender.sendMessage("you sent $" + value + " to " + receiver.getName());
            }
        });
    }

    /**
     * Returns the balance of the passed players
     * @param player
     * @return
     */
    public CompletableFuture<Integer> getBalance(Player player){
        CompletableFuture<Integer> result = new CompletableFuture<>();
        new BukkitRunnable(){
            @Override
            public void run(){
                String sql = "SELECT balance FROM " + cg.getTable() + " WHERE uuid=('"+player.getUniqueId()+"')";
                try {
                    Connection con = establishConnection.establishConnection();
                    PreparedStatement stmt = con.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery();
                    if(rs.next()){
                        balance = rs.getInt("balance");
                        result.complete(balance);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(bankClass);
        return result;
    }
}