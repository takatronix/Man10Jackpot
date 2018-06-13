package red.man10.man10jackpot;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import red.man10.man10vaultapiplus.enums.TransactionCategory;
import red.man10.man10vaultapiplus.enums.TransactionType;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by sho-pc on 2017/04/22.
 */
public class Man10JackpotRunnable {
    private final Man10Jackpot plugin;

    public Man10JackpotRunnable(Man10Jackpot plugin) {
        this.plugin = plugin;
    }

    public void onStartTimer(){
        new BukkitRunnable(){
            @Override
            public void run() {
                if(plugin.time == 0){
                    cancel();
                    plugin.inGame = true;
                    plugin.gameMenu = Bukkit.createInventory(null,54,"§5§kA§c§l優勝賞金:$" + Double.valueOf(plugin.totalBet.getBalance()) + "§5§kA");
                    plugin.menu.setUpGameMenu();
                    plugin.openSpinMenuForPlayer();
                    return;
                }
                if(plugin.someOneInMenu == true) {
                    if(plugin.playersInMenu.size() == 0 || plugin.playersInMenu == null){
                        return;
                    }
                    for (int i = 0; i < plugin.playersInMenu.size(); i++) {
                        if (plugin.playerMenuState.get(plugin.playersInMenu.get(i)).equalsIgnoreCase("main")) {
                            try {
                                Player p = plugin.playersInMenu.get(i);
                                ItemStack item = new ItemStack(Material.WATCH);
                                ItemMeta itemMeta = item.getItemMeta();
                                itemMeta.setDisplayName("§d§l残り時間");
                                String[] lore = {"§a§l====================", "§d残り" + plugin.time + "秒", "§a§l===================="};
                                itemMeta.setLore(Arrays.asList(lore));
                                item.setItemMeta(itemMeta);
                                p.getOpenInventory().setItem(47, item);
                            }catch (Exception ee){
                            }
                        }
                    }
                    plugin.time--;
                    return;
                }
                plugin.time--;
            }
        }.runTaskTimer(plugin,0,20);
    }

    public void onSpin(){
        new BukkitRunnable(){
            Inventory inv = plugin.gameMenu;
            //int[] slot = {52,43,34,25,16,15,14,13,12,11,10,19,28,37,46};
            int[] slot = {46,37,28,19,10,11,12,13,14,15,16,25,34,43,52};
            int[] record = new int[8];
            //ItemStack[] slotsWithHead = new ItemStack[15];
            int count = 0;
            int require = 0;
            int mainCount = 0;
            @Override
            public void run() {
                if(require < count) {
                    require++;
                    for(int i = 0; i < plugin.playersInMenu.size(); i++){
                        Player p = plugin.playersInMenu.get(i);
                        p.playSound(p.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE,1,1);
                    }
                    count = 0;
                    Random r = new Random();
                    if(mainCount < 80){
                        require--;
                    }
                    if(mainCount > 80){
                        require++;
                    }
                    if(mainCount > 90){
                        require = require + 2;
                    }
                    if(mainCount > 200){
                        cancel();
                        plugin.inGame = false;
                        Man10Jackpot.BetInfo bet = plugin.UUIDToBetInfo.get(plugin.idToUUID.get(record[0]));
                        double winRate = (bet.amount/plugin.totalBetInt)*100;
                        double payout = plugin.totalBet.getBalance() * ((100 - plugin.tax)/100);
                        new Thread(() -> {
                            plugin.mysql.execute("DELETE FROM jackpot_game WHERE game_id = '" + plugin.gameID + "';");
                            plugin.mysql.execute("insert into jackpot_game values ('0','" + plugin.gameID + "','" + plugin.totalBetInt + "','" + plugin.ticket_price + "','" + bet.name + "','" + bet.uuid + "'," + plugin.starttime + "," + plugin.currentTime() + ",'end');");
                        }).start();
                        for(Player p : Bukkit.getOnlinePlayers()){
                            if(!p.getUniqueId().toString().equalsIgnoreCase(bet.uuid.toString())){
                                //p.sendMessage(plugin.prefix + bet.name + "さんが" + plugin.round(winRate,2) + "%の確立で" + Double.valueOf(plugin.totalBet) + "円を勝ち取りました");
                                p.sendMessage(plugin.loser_broadcast.replaceAll("&","§").replaceAll("%AMOUNT%", String.valueOf(Double.valueOf(plugin.totalBet.getBalance()))).replaceAll("%TAXED%", String.valueOf(payout)).replaceAll("%WINNER%", bet.name).replaceAll("%PERCENTAGE%", String.valueOf(plugin.round(winRate,2))));
                            }else{
                                p.sendMessage(plugin.winner_broadcast.replaceAll("&","§").replaceAll("%AMOUNT%", String.valueOf(Double.valueOf(plugin.totalBet.getBalance()))).replaceAll("%TAXED%", String.valueOf(payout)).replaceAll("%WINNER%", bet.name).replaceAll("%PERCENTAGE%", String.valueOf(plugin.round(winRate,2))));
                            }
                        }
                       plugin.vault.transferMoneyPoolToPlayer(plugin.totalBet.getId(), plugin.idToUUID.get(record[0]),payout, TransactionCategory.GAMBLE, TransactionType.WIN, "Man10Jackpot WInner Payout Price:" + String.valueOf(payout));
                        plugin.vault.transferMoneyPoolToCountry(plugin.totalBet.getId(), plugin.totalBet.getCurrentBalance() - payout, TransactionCategory.TAX, TransactionType.PAY, "Man10Jackpot Winner Payout Tax");
                        for(Player p : Bukkit.getOnlinePlayers()){
                            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,1,1);
                        }
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                            public void run() {
                                for(int i = 0; i < plugin.playersInGame.size(); i++){
                                    Player p = Bukkit.getPlayer(plugin.playersInGame.get(i));
                                    if(p != null){
                                        p.closeInventory();
                                    }
                                }
                                for(int i = 0; i < plugin.playersInGame.size(); i++){
                                    Player p = Bukkit.getPlayer(plugin.playersInGame.get(i));
                                    if(p != null){
                                        p.closeInventory();
                                    }
                                }
                                plugin.refreshGame(true);
                            }
                        },60);
                        return;
                    }
                    int result = r.nextInt(plugin.chanceInGame.size());
                    for (int i = 0; i < slot.length - 1; i++) {
                        //  slotsWithHead[i] = inv.getItem(slot[i]);
                        inv.setItem(slot[i], inv.getItem(slot[i + 1]));
                    }
                    for(int i = 0; i < record.length - 1;i++){
                        record[i] = record[i + 1];
                    }
                    String s = "";
                    for(int i = 0; i < record.length; i++){
                        s = s + record[i];
                    }
                    record[7] = plugin.chanceInGame.get(result);
                    inv.setItem(52, plugin.idToItem.get(plugin.chanceInGame.get(result)));
                    mainCount++;
                    return;
                }
                mainCount++;
                count++;
            }
        }.runTaskTimer(plugin,0,0);
    }
}
