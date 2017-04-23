package red.man10.man10jackpot;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

/**
 * Created by sho-pc on 2017/04/22.
 */
public class Man10JackpotRunnable {
    private final Man10Jackpot plugin;

    public Man10JackpotRunnable(Man10Jackpot plugin) {
        this.plugin = plugin;
    }


    public void onSpin(){
        new BukkitRunnable(){
            Inventory inv = plugin.gameMenu;
            int[] slot = {52,43,34,25,16,15,14,13,12,11,10,19,28,37,46};
            int count = 0;
            //int[] slots = {46,37,28,19,10,11,12,13,14,15,16,25,34,43,52};
            ItemStack[] slotsWithHead = new ItemStack[15];
            @Override
            public void run() {
                Random r = new Random();
                int result = r.nextInt(plugin.chanceInGame.size());
                inv.setItem(52, plugin.idToHeadItem.get(plugin.chanceInGame.get(result)));
                for(int i = 0; i < slotsWithHead.length; i++){
                    slotsWithHead[i] = inv.getItem(slot[i]);
                    //inv.setItem(slot[i], inv.getItem(slot[i + 1]));
                }
                for(int i = 0; i < slotsWithHead.length - 1; i++){
                    inv.setItem(slot[i + 1],slotsWithHead[i]);
                }
                for(int i = 0; i < slotsWithHead.length - 1; i ++){
                    Bukkit.getServer().broadcastMessage(slot[i] + slotsWithHead[i].getItemMeta().getDisplayName());
                }

                String s = "";
                Bukkit.getServer().broadcastMessage(s);

            }
        }.runTaskTimer(plugin,0,3);
    }
}
