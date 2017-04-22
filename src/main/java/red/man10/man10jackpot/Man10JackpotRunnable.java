package red.man10.man10jackpot;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
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
            @Override
            public void run() {
                Random r = new Random();
                int result = r.nextInt(plugin.chanceInGame.size());
                Player p = Bukkit.getPlayer(plugin.chanceInGame.get(result));
                inv.setItem(46,inv.getItem(37));
                inv.setItem(37,inv.getItem(28));
                inv.setItem(28,inv.getItem(19));
                inv.setItem(19,inv.getItem(10));
                inv.setItem(10,inv.getItem(11));
                inv.setItem(11,inv.getItem(12));
                inv.setItem(12,inv.getItem(13));
                inv.setItem(13,inv.getItem(14));
                inv.setItem(14,inv.getItem(15));
                inv.setItem(15,inv.getItem(16));
                inv.setItem(16,inv.getItem(25));
                inv.setItem(25,inv.getItem(34));
                inv.setItem(34,inv.getItem(43));
                inv.setItem(43,inv.getItem(52));
                inv.setItem(52,new SkullMaker().withOwner(p.getName()).build());
            }
        }.runTaskTimer(plugin,0,2);
    }
}
