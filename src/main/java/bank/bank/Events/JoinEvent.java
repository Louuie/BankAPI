package bank.bank.Events;

import bank.bank.BankUtils.BankAPI;
import bank.bank.Database.InsertDatabase;
import bank.bank.Database.QueryDatabase;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {
    private int exists;
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        QueryDatabase queryDatabase = new QueryDatabase();
        InsertDatabase insertDatabase = new InsertDatabase();
        BankAPI bank = new BankAPI();
        Player p = e.getPlayer();
        bank.addMoney(p, 0);
    }
}
