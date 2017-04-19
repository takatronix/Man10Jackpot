package red.man10.man10jackpot;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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
        if(plugin.playerMenuState.get(p).equalsIgnoreCase("main")){
            if (e.getSlot() == 53) {
                p.closeInventory();
                plugin.playersInMenu.remove(p);
                if (plugin.playersInMenu.isEmpty()) {
                    plugin.someOneInMenu = false;
                }
                e.setCancelled(true);
                return;
            }
            if (e.getSlot() == 50) {
                if (plugin.dummy.size() < 36) {
                    e.setCancelled(true);
                    return;
                }
                int r = plugin.playerMenuPage.get(p) + 1;
                plugin.playerMenuPage.put(p, r);
                for (int i = 0; i < 36; i++) {
                    e.getInventory().setItem(i, plugin.dummy.get((plugin.playerMenuPage.get(p) - 1) * 36 + i));
                }
                e.setCancelled(true);
                return;
            }
            if(e.getSlot() == 49){
                plugin.playerMenuState.remove(p);
                plugin.playerMenuState.put(p,"bet");
                plugin.menu.betMenuInv(p);
                e.setCancelled(true);
            }
            if (e.getSlot() == 48) {
                if (plugin.playerMenuPage.get(p) == 1) {
                    e.setCancelled(true);
                    return;
                }
                if (plugin.dummy.size() < 36) {
                    e.setCancelled(true);
                    return;
                }
                int r = plugin.playerMenuPage.get(p) - 1;
                plugin.playerMenuPage.put(p, r);
                for (int i = 0; i < 36; i++) {
                    e.getInventory().setItem(i, plugin.dummy.get((plugin.playerMenuPage.get(p) - 1) * 36 + i));
                }

                e.setCancelled(true);
                return;
            }
            e.setCancelled(true);
            return;
        }
        if(plugin.playerMenuState.get(p).equalsIgnoreCase("bet")){
            Inventory inv = e.getInventory();
            if(inv.getItem(8) != null){
                plugin.menu.moveDisplay(inv);
            }
            String val = "";
            if(e.getSlot() == 19){
                inv.setItem(8,inv.getItem(19));
                
                val = "7";
            }
            if(e.getSlot() == 20){
                inv.setItem(8,inv.getItem(20));
                
                val = "8";
            }
            if(e.getSlot() == 21){
                inv.setItem(8,inv.getItem(21));
                
                val = "9";
            }
            if(e.getSlot() == 28){
                inv.setItem(8,inv.getItem(28));
                
                val = "4";
            }
            if(e.getSlot() == 29){
                inv.setItem(8,inv.getItem(29));
                
                val = "5";
            }
            if(e.getSlot() == 30){
                inv.setItem(8,inv.getItem(30));
                
                val = "6";
            }
            if(e.getSlot() == 37){
                inv.setItem(8,inv.getItem(37));
                
                val = "1";
            }
            if(e.getSlot() == 38){
                inv.setItem(8,inv.getItem(38));
                
                val = "2";
            }
            if(e.getSlot() == 39){
                inv.setItem(8,inv.getItem(39));
                
                val = "3";
            }
            if(e.getSlot() == 46){
                if(inv.getItem(8) == null){
                    e.setCancelled(true);
                    return;
                }
                inv.setItem(8,inv.getItem(46));
                
                val = "0";
            }
            if(e.getSlot() == 48){
                for (int i = 0; i < 9; i++){
                    inv.setItem(i, new ItemStack(Material.AIR));
                }
                plugin.playerCalcValue.put(p,"");
            }
            if(plugin.playerCalcValue.get(p) == null){
                plugin.playerCalcValue.put(p,"");
            }
            plugin.playerCalcValue.put(p,plugin.playerCalcValue.get(p) + val);
            p.sendMessage(plugin.playerCalcValue.get(p));
            e.setCancelled(true);
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
        if(plugin.playerMenuState.get(p).equalsIgnoreCase("bet")){
            return;
        }
        plugin.playersInMenu.remove(p);
        plugin.playerMenuPage.remove(p);
        if(plugin.playersInMenu.isEmpty()){
            plugin.someOneInMenu = false;
        }
    }
}
