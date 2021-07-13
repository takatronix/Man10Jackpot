package red.man10.man10jackpot;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import red.man10.MySQLManager;

import java.awt.font.NumericShaper;

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
        Player p = (Player) sender;
        if (plugin.inGame == true) {
            plugin.playersInMenu.add(p);
            plugin.someOneInMenu = true;
            p.openInventory(plugin.gameMenu);
            return true;
        }
        if (args.length == 1) {
            if(args[0].equalsIgnoreCase("forcestart")){
                if(!p.hasPermission("man10.jackpot.forcestart")){
                    p.sendMessage(plugin.prefix + "あなたには権限はありません");
                    return false;
                }
                plugin.time = 3;
                p.sendMessage(plugin.prefix + "開始３秒前にセットしました");
                return true;
            }
            if (args[0].equalsIgnoreCase("cancel")) {
                if(!p.hasPermission("man10.jackpot.cancel")){
                    p.sendMessage(plugin.prefix + "あなたには権限はありません");
                    return false;
                }
                for(int i = 0; i < plugin.playersInMenu.size(); i++){
                    Player pp = plugin.playersInMenu.get(i);
                    pp.closeInventory();
                }
                p.sendMessage(plugin.prefix + "ゲームがキャンセルされました");
                plugin.cancelGame(true);
                return true;
            }
            if(args[0].equalsIgnoreCase("lock")){
                if(!p.hasPermission("man10.jackpot.lock")){
                    p.sendMessage(plugin.prefix + "あなたには権限はありません");
                    return true;
                }
                if(plugin.lockdown){
                    plugin.lockdown = false;
                    p.sendMessage(plugin.prefix + "ロックを解除しました");
                    return true;
                }
                plugin.lockdown = true;
                p.sendMessage(plugin.prefix + "ロックしました");
                return true;
            }
            if (args[0].equalsIgnoreCase("reload")) {
                if(!p.hasPermission("man10.jackpot.reload")){
                    p.sendMessage(plugin.prefix + "あなたには権限はありません");
                    return false;
                }
                plugin.reloadConfig();
                plugin.tax = plugin.getConfig().getInt("tax_percentage");
                plugin.ticket_price = plugin.getConfig().getInt("ticket_price");
                plugin.timer_time = plugin.getConfig().getInt("timer");
                plugin.winner_broadcast = plugin.getConfig().getString("winner_broadcast");
                plugin.loser_broadcast = plugin.getConfig().getString("loser_broadcast");
                plugin.mysql = new MySQLManager(this.plugin,"man10juckpot");
                plugin.time = plugin.timer_time;
                p.sendMessage(plugin.prefix + "リロードが完了しました");
                return true;
            }
            if (args[0].equalsIgnoreCase("help")) {
                p.sendMessage("§e§l=--------=§b[§c§lMan10Jackpot§b]§e§l=--------=");
                p.sendMessage("§b/mj help ヘルプコマンド表示");
                p.sendMessage("§b/mj lock ゲームをロックダウン");
                p.sendMessage("§b/mj cancel キャンセルコマンド");
                p.sendMessage("§b/mj reload リロードコマンド");
                p.sendMessage("§b/mj forcestart ゲームを強制開始");
                p.sendMessage("§b/mj addtime <数字> 時間を追加する");
                p.sendMessage("§d Man10 Jackpot V1.5");
                p.sendMessage("§e§l=-------------------------------=");
                p.sendMessage("§d§lCreated By Sho0");
                return true;

            }
        }
        if(args.length == 2){
            if(args[0].equalsIgnoreCase("addtime")){
                if(!p.hasPermission("man10.jackpot.addtime")){
                    p.sendMessage(plugin.prefix + "あなたには権限はありません");
                    return false;
                }
                try{
                    int addTime = Integer.parseInt(args[1]);
                    plugin.time = plugin.time + addTime;
                    p.sendMessage(plugin.prefix + addTime + "秒を追加しました ゲーム開始まで" + plugin.time + "秒です");
                }catch (NumberFormatException e){
                    p.sendMessage(plugin.prefix + "§d/mj addtime <数字>");
                }
                return true;
            }
        }
        if(plugin.lockdown){
            p.sendMessage(plugin.prefix + "現在ゲームはロックダウンされてます");
            return true;
        }
        plugin.playersInMenu.add(p);
        plugin.someOneInMenu = true;
        plugin.playerMenuPage.put(p, 1);
        plugin.playerMenuState.put(p, "main");
        plugin.game.openInventory(p, plugin.game.setUpMainInv(p));
        return false;
    }
}
