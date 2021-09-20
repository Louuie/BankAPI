package bank.bank.Commands;

import bank.bank.Bank;
import bank.bank.BankUtils.BankUtils;
import bank.bank.Database.QueryDatabase;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Balance implements CommandExecutor {
    private int bal;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
       Bank bankClass = Bank.getInstance();
        if(label.equalsIgnoreCase("bal")){
            if(sender instanceof Player){
                Player p = (Player) sender;
                QueryDatabase bankDatabase = new QueryDatabase();
                if(sender.hasPermission("bank.checkbal")){
                    if(args.length == 0){
                        bankDatabase.getBalance(p).thenAccept(bal -> {
                            p.sendMessage("your bal $" + bal);
                        });
                    }
                }
            }
        }
        return false;
    }
}
