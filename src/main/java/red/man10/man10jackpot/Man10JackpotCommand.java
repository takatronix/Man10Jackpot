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
        if(plugin.inGame == true){
            p.openInventory(plugin.gameMenu);
            return true;
        }
        if(args.length == 1){
            if(args[0].equalsIgnoreCase("cancel")){
                plugin.cancelGame();
                return true;
            }
            if(args[0].equalsIgnoreCase("reload")){
                plugin.reloadConfig();
                plugin.tax = plugin.getConfig().getInt("tax_percentage");
                plugin.ticket_price = plugin.getConfig().getInt("ticket_price");
                plugin.timer_time = plugin.getConfig().getInt("timmer");
                plugin.winner_broadcast = plugin.getConfig().getString("winner_broadcast");
                plugin.loser_broadcast = plugin.getConfig().getString("loser_broadcast");
                p.sendMessage(plugin.prefix + "リロードが完了しました");
                return true;
            }
            if(args[0].equalsIgnoreCase("help")){
                p.sendMessage("§e§l=--------=§b[§c§lMan10Jackpot§b]§e§l=--------=");
                p.sendMessage("§d/mj help ヘルプコマンド表示");
                p.sendMessage("§d/mj cancel キャンセルコマンド");
                p.sendMessage("§d/mj reload リロードコマンド");
                p.sendMessage("§e§l=-------------------------------=");
                p.sendMessage("§d§lCreated By Sho0");
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
