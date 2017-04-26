package red.man10.man10jackpot;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Array;
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
                if(plugin.someOneInMenu == true) {
                    for (int i = 0; i < plugin.playersInMenu.size(); i++) {
                        if (plugin.playerMenuState.get(plugin.playersInMenu.get(i)).equalsIgnoreCase("main")) {
                            Player p = plugin.playersInMenu.get(i);
                            ItemStack item = p.getOpenInventory().getItem(47);
                            if(item != null) {
                                ItemMeta itemMeta = item.getItemMeta();
                                String[] lore = {"§a§l====================", "§d残り" + plugin.time + "秒", "§a§l===================="};
                                itemMeta.setLore(Arrays.asList(lore));
                                item.setItemMeta(itemMeta);
                                p.getOpenInventory().setItem(47, item);
                            }
                        }
                    }
                    plugin.time--;
                }else{
                    plugin.time--;
                }
            }
        }.runTaskTimer(plugin,0,20);
    }

    public void onSpin(){
        new BukkitRunnable(){
            Inventory inv = plugin.gameMenu;
            //int[] slot = {52,43,34,25,16,15,14,13,12,11,10,19,28,37,46};
            int[] slot = {46,37,28,19,10,11,12,13,14,15,16,25,34,43,52};
            //ItemStack[] slotsWithHead = new ItemStack[15];
            @Override
            public void run() {
                Random r = new Random();
                int result = r.nextInt(plugin.chanceInGame.size());
                Random rand = new Random();
                inv.setItem(52, plugin.idToItem.get(plugin.chanceInGame.get(result)));
                for(int i = 0; i < slot.length - 1; i++){
                  //  slotsWithHead[i] = inv.getItem(slot[i]);
                    inv.setItem(slot[i], inv.getItem(slot[i + 1]));
                }
            }
        }.runTaskTimer(plugin,0,1);
    }
}
