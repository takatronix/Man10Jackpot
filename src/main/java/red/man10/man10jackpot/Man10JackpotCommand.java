package red.man10.man10jackpot;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import red.man10.man10jackpot.Man10Jackpot;

/**
 * Created by sho-pc on 2017/04/19.
 */
public class Man10JackpotCommand implements CommandExecutor {

    private final Man10Jackpot plugin;

    public Man10JackpotCommand(Man10Jackpot plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player)sender;
        if(args.length == 1){
            if(args[0].equalsIgnoreCase("list")){
                p.sendMessage(String.valueOf(plugin.totalBetInt));
                plugin.list(p);

                return true;
            }
            if(args[0].equalsIgnoreCase("menu")){
                plugin.menu.setUpGameMenu();
                p.openInventory(plugin.gameMenu);
                return true;
            }
            if(args[0].equalsIgnoreCase("runnable")){
                p.openInventory(plugin.gameMenu);
                plugin.runnable.onSpin();
                return true;
            }
            if(args[0].equalsIgnoreCase("bet")){
                plugin.placeBet(p,100);
                Player player = Bukkit.getPlayer("hashing_bot");
                plugin.placeBet(player,100);
                return true;
            }
            if(args[0].equalsIgnoreCase("listt")){
                plugin.playersInMenu.add(p);
                plugin.someOneInMenu = true;
                plugin.playerMenuPage.put(p,1);
                plugin.playerMenuState.put(p,"dev");
                Inventory inv = Bukkit.createInventory(null,54,"put items");
                p.openInventory(inv);
                return true;
            }
        }
        plugin.playersInMenu.add(p);
        plugin.someOneInMenu = true;
        plugin.playerMenuPage.put(p,1);
        plugin.playerMenuState.put(p,"main");
        plugin.game.openInventory(p,plugin.game.setUpMainInv(p));
        return false;
    }
}
