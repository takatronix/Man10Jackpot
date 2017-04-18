package red.man10.man10jackpot;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

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
        if(plugin.someOneInMenu == false){
            return;
        }
        Player p = (Player) e.getWhoClicked();
        if(!plugin.playersInMenu.contains(p)){
            return;
        }
        if(e.getSlot() == 53){
            p.closeInventory();
            plugin.playersInMenu.remove(p);
            if(plugin.playersInMenu.isEmpty()){
                plugin.someOneInMenu = false;
            }
            e.setCancelled(true);
            return;
        }
        if(e.getSlot() == 50){
            if(plugin.dummy.size() < 36){
                e.setCancelled(true);
                return;
            }
            int r = plugin.playerMenuPage.get(p) + 1;
            plugin.playerMenuPage.put(p,r);
            for(int i = 0; i < 36; i++){
                e.getInventory().setItem(i,plugin.dummy.get((plugin.playerMenuPage.get(p) - 1) * 36 + i));
            }
            e.setCancelled(true);
            return;
        }
        if(e.getSlot() == 48){
            if(plugin.playerMenuPage.get(e.getWhoClicked()) == 1){
                e.setCancelled(true);
                return;
            }
            if(plugin.dummy.size() < 36){
                e.setCancelled(true);
                return;
            }
            int r = plugin.playerMenuPage.get(p) - 1;
            plugin.playerMenuPage.put(p,r);
            for(int i = 0; i < 36; i++){
                e.getInventory().setItem(i,plugin.dummy.get((plugin.playerMenuPage.get(p) - 1) * 36 + i));
            }
            e.setCancelled(true);
            return;
        }
        e.setCancelled(true);

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
        plugin.playersInMenu.remove(p);
        plugin.playerMenuPage.remove(p);
        if(plugin.playersInMenu.isEmpty()){
            plugin.someOneInMenu = false;
        }
    }
}
