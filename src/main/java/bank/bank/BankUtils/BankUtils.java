package bank.bank.BankUtils;

import bank.bank.Bank;
import bank.bank.Config.DatabaseConfig;
import bank.bank.Database.InsertDatabase;
import bank.bank.Database.QueryDatabase;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class BankUtils {
    private int balance;
    QueryDatabase queryDatabase = new QueryDatabase();
    InsertDatabase insertDatabase = new InsertDatabase();
    public void addMoney(Player player,  int m){
        queryDatabase.getBalance(player).thenAccept(balance -> {
            insertDatabase.insertQuery(player, balance + m);
        });
    }

    public void removeMoney(Player player, int m){
        queryDatabase.getBalance(player).thenAccept(balance -> {
            insertDatabase.insertQuery(player, balance - m);
        });
    }

    public void setupTransaction(Player sender, Player receiver, int value) {
        queryDatabase.getBalance(sender.getPlayer()).thenAccept(balance -> {
            if (balance < value) {
                sender.sendMessage(ChatColor.RED + "Insufficient Funds!");
            } else {
                addMoney(receiver, value);
                removeMoney(sender, value);
                sender.sendMessage("you sent $" + value + " to " + receiver.getName());
            }
        });
    }
}