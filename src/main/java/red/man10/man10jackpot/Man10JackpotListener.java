package red.man10.man10jackpot;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

/**
 * Created by sho-pc on 2017/04/19.
 */
public class Man10JackpotListener implements Listener {

    private final Man10Jackpot plugin;

    public Man10JackpotListener(Man10Jackpot plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClickInventory(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        if(plugin.someOneInMenu == false){
            return;
        }
        if (!plugin.playersInMenu.contains(p)) {
            return;
        }
        if(plugin.inGame == true){
            e.setCancelled(true);
        }
        if(plugin.playerMenuState.get(p).equalsIgnoreCase("main")){
            e.setCancelled(true);
            if (e.getSlot() == 53) {
                p.closeInventory();
                plugin.playersInMenu.remove(p);
                if (plugin.playersInMenu.isEmpty()) {
                    plugin.someOneInMenu = false;
                }
                return;
            }
            if (e.getSlot() == 50) {
                if (plugin.itemList.size() < 36) {
                    return;
                }
                int r = plugin.playerMenuPage.get(p) + 1;
                plugin.playerMenuPage.put(p, r);
                for (int i = 0; i < 36; i++) {
                    e.getInventory().setItem(i, plugin.itemList.get((plugin.playerMenuPage.get(p) - 1) * 36 + i));
                }
                return;
            }
            if(e.getSlot() == 49){
                plugin.playerMenuState.remove(p);
                plugin.menu.betMenuInv(p);
                plugin.playerMenuState.put(p,"bet");
            }
            if (e.getSlot() == 48) {
                if (plugin.playerMenuPage.get(p) == 1) {
                    return;
                }
                if (plugin.itemList.size() < 36) {
                    return;
                }
                int r = plugin.playerMenuPage.get(p) - 1;
                plugin.playerMenuPage.put(p, r);
                for (int i = 0; i < 36; i++) {
                    e.getInventory().setItem(i, plugin.itemList.get((plugin.playerMenuPage.get(p) - 1) * 36 + i));
                }

                return;
            }
            return;
        }
        if(plugin.playerMenuState.get(p).equalsIgnoreCase("bet")){
            e.setCancelled(true);
            Inventory inv = e.getInventory();
            String val = "";
            if(e.getCurrentItem() == null){

                return;
            }
            if(e.getInventory() == null){

                return;
            }
            if(e.getSlot() == -999){
                return;
            }
            if(e.getCurrentItem().getType() == Material.AIR){

                return;
            }
            if(e.getCurrentItem().getItemMeta().getDisplayName().contains("BET")){

                return;
            }
            if(e.getSlot() == 52 ){
                e.getWhoClicked().closeInventory();

                return;
            }
            if(e.getSlot() == 48){
                for (int i = 0; i < 9; i++){
                    inv.setItem(i, new ItemStack(Material.AIR));
                }
                plugin.playerCalcValue.put(p,null);
                ItemMeta itemMeta = inv.getItem(50).getItemMeta();
                itemMeta.setLore(null);
                inv.getItem(50).setItemMeta(itemMeta);

                return;
            }
            if(plugin.playerCalcValue.get(p) == null){
                plugin.playerCalcValue.put(p,"");
            }
            if(e.getSlot() == 50){
                //bet button
                if(plugin.playerCalcValue.get(p) == null || plugin.playerCalcValue.get(p).equalsIgnoreCase("")){
                    p.sendMessage(plugin.prefix + "購入は1口以上からです");
                    e.setCancelled(true);
                    return;
                }
                if(plugin.vault.getBalance(p.getUniqueId()) < (Double.valueOf(plugin.playerCalcValue.get(p)) * Double.valueOf(plugin.ticket_price))){
                    p.sendMessage(plugin.prefix + "十分な所持金を持っていません");
                    e.setCancelled(true);
                    return;
                }
                if(plugin.playersInGame.size() > plugin.icons.size()){
                    p.sendMessage(plugin.prefix + "満員です");
                    return;
                }
                //bet
                //bet
                //bet
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        plugin.mysql.execute("insert into jackpot_bet values ('0','" + plugin.gameID + "','" + p.getUniqueId() + "','" + p.getName() + "','" + plugin.playerCalcValue.get(p) + "','" + plugin.ticket_price + "'," + plugin.currentTime() + ");");
                    }
                }).start();
                plugin.placeBet(p, Double.parseDouble(plugin.playerCalcValue.get(p)));
                e.setCancelled(true);
                return;
            }
            if(e.getInventory().getItem(0) != null){
                p.sendMessage(plugin.prefix + "掛け金の上限です");
                e.setCancelled(true);
                return;
            }
            if(e.getSlot() == 19){
                moveD(inv);
                inv.setItem(8,inv.getItem(19));

                val = "7";
            }
            if(e.getSlot() == 20){
                moveD(inv);
                inv.setItem(8,inv.getItem(20));

                val = "8";
            }
            if(e.getSlot() == 21){
                moveD(inv);
                inv.setItem(8,inv.getItem(21));

                val = "9";
            }
            if(e.getSlot() == 28){
                moveD(inv);
                inv.setItem(8,inv.getItem(28));

                val = "4";
            }
            if(e.getSlot() == 29){
                moveD(inv);
                inv.setItem(8,inv.getItem(29));

                val = "5";
            }
            if(e.getSlot() == 30){
                moveD(inv);
                inv.setItem(8,inv.getItem(30));

                val = "6";
            }
            if(e.getSlot() == 37){
                moveD(inv);
                inv.setItem(8,inv.getItem(37));

                val = "1";
            }
            if(e.getSlot() == 38){
                moveD(inv);
                inv.setItem(8,inv.getItem(38));

                val = "2";
            }
            if(e.getSlot() == 39){
                moveD(inv);
                inv.setItem(8,inv.getItem(39));

                val = "3";
            }
            if(e.getSlot() == 46){
                if(inv.getItem(8) == null){
                    e.setCancelled(true);
                    return;
                }
                moveD(inv);
                inv.setItem(8,inv.getItem(46));

                val = "0";
            }
            plugin.playerCalcValue.put(p,plugin.playerCalcValue.get(p) + val);
            changeConfirmPrice(e.getInventory(),p);
            e.setCancelled(true);
        }
    }



    public void changeConfirmPrice(Inventory inv,Player p){
        String[] lore = {"§e§l" + plugin.playerCalcValue.get(p) + "口","§e§l" + String.valueOf(Double.valueOf(plugin.playerCalcValue.get(p)) * Integer.valueOf(plugin.ticket_price)) + "円"};
        ItemMeta buttonMeta = inv.getItem(50).getItemMeta();
        buttonMeta.setLore(Arrays.asList(lore));
        inv.getItem(50).setItemMeta(buttonMeta);
    }

    public void moveD(Inventory inv){
        if(inv.getItem(8) != null){
            plugin.menu.moveDisplay(inv);
        }
    }

    @EventHandler
    public void onCloseInventory(InventoryCloseEvent e){
        if(plugin.someOneInMenu == false){
            return;
        }
        Player p = (Player) e.getPlayer();
        if(!plugin.playersInMenu.contains(p)){
            return;
        }
        if(!plugin.playerMenuState.containsKey(p)){
            return;
        }
        plugin.playerCalcValue.remove(p);
        plugin.playersInMenu.remove(p);
        plugin.playerMenuPage.remove(p);
        if(plugin.playersInMenu.isEmpty()){
            plugin.someOneInMenu = false;
        }
    }
}
