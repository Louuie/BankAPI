package bank.bank.Commands;

import bank.bank.BankUtils.BankAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Pay implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("pay")){
            if(sender instanceof Player){
                Player p = (Player) sender;
                if(sender.hasPermission("PiggyBank.pay")){
                    if(args.length == 2){
                        //gets the players from the 1st argument.
                        Player tUser = Bukkit.getPlayer(args[0]);
                        //gets the value they pass and stores it into an integer
                        int value = Integer.parseInt(args[1]);
                        if(tUser == null){
                            p.sendMessage(ChatColor.RED + args[0] + " does not exist");
                        } else {
                            BankAPI bank = new BankAPI();
                            bank.setupMoneyTransaction(p, tUser, value);
                            }
                        }
                        }
                    }
                }
        return false;
            }
    }