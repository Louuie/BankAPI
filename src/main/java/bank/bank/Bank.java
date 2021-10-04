package bank.bank;

import bank.bank.Database.InsertDatabase;
import bank.bank.Events.JoinEvent;
import bank.bank.Commands.Balance;
import bank.bank.Commands.Pay;
import bank.bank.Config.DatabaseConfig;
import bank.bank.Database.QueryDatabase;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Bank extends JavaPlugin {

    private QueryDatabase qdb;
    public QueryDatabase getQueryDB(){
        return qdb;
    }
    private static Bank instance;

    public static Bank getInstance(){
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        DatabaseConfig.setup();
        DatabaseConfig.save();
        InsertDatabase insertDatabase = new InsertDatabase();
        insertDatabase.createTable();
        Bukkit.getLogger().info("Starting Bank");
        this.getCommand("pay").setExecutor(new Pay());
        this.getCommand("bal").setExecutor(new Balance());
        Bukkit.getPluginManager().registerEvents(new JoinEvent(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
