package bank.bank.Events;

import bank.bank.BankUtils.BankUtils;
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
        BankUtils bank = new BankUtils();
        Player p = e.getPlayer();
        queryDatabase.fetchPlayer(p).thenAccept(exists -> {
            if (exists) {
                bank.addMoney(p, 100);
            } else {
                insertDatabase.insertQuery(p, 100);
            }
        });
    }
}
