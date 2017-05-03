package red.man10.man10jackpot;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by sho-pc on 2017/04/19.
 */
public class Man10JackpotGame {

    private final Man10Jackpot plugin;

    public Man10JackpotGame(Man10Jackpot plugin) {
        this.plugin = plugin;
    }

    public Inventory setUpMainInv(Player p){
        Inventory inv = Bukkit.createInventory(null,54,"§c§l現在ベット:$" + Double.valueOf(plugin.totalBet));
        ItemStack greyGlass = new ItemStack(Material.STAINED_GLASS_PANE,1,(short) 15);
        ItemMeta greyMeta = greyGlass.getItemMeta();
        greyMeta.setDisplayName(" ");
        greyGlass.setItemMeta(greyMeta);
        for(int i = 36; i < 45; i++){
            inv.setItem(i,greyGlass);
        }
        ItemStack buyTickets = new ItemStack(Material.EMERALD);
        ItemMeta buyTicketsMeta = buyTickets.getItemMeta();
        buyTicketsMeta.setDisplayName("§a§lベット");
        buyTicketsMeta.addEnchant(Enchantment.FIRE_ASPECT,1,true);
        buyTickets.setItemMeta(buyTicketsMeta);
        inv.setItem(49,buyTickets);

        ItemStack clock = new ItemStack(Material.WATCH);
        ItemMeta clockMeta = clock.getItemMeta();
        clockMeta.setDisplayName("§l残り時間");
        clock.setItemMeta(clockMeta);
        inv.setItem(47,clock);

        ItemStack info = new ItemStack(Material.PAPER);
        ItemMeta infoMeta = info.getItemMeta();
        infoMeta.setDisplayName("§d§lゲーム情報");
        info.setItemMeta(infoMeta);
        inv.setItem(51,info);

        ItemStack leave = new ItemStack(Material.STAINED_GLASS_PANE,1,(short) 14);
        ItemMeta leaveMeta = leave.getItemMeta();
        leaveMeta.setDisplayName("§c§lメニューを閉じる");
        leave.setItemMeta(leaveMeta);
        inv.setItem(53,leave);

        ItemStack leftArrow = new SkullMaker().withSkinUrl("http://textures.minecraft.net/texture/3ebf907494a935e955bfcadab81beafb90fb9be49c7026ba97d798d5f1a23").build();
        inv.setItem(48,leftArrow);

        ItemStack rightArrow = new SkullMaker().withSkinUrl("http://textures.minecraft.net/texture/1b6f1a25b6bc199946472aedb370522584ff6f4e83221e5946bd2e41b5ca13b").build();
        inv.setItem(50,rightArrow);

        int rep = 0;
        if(plugin.itemList.size() > 36){
            rep = 36;
        }
        if(plugin.itemList.size() < 36){
            rep = plugin.itemList.size();
        }
        for(int i = 0; i < rep; i++){
            inv.setItem(i,plugin.itemList.get((plugin.playerMenuPage.get(p) - 1) * 36 + i));
        }

        return inv;
    }
    public void openInventory(Player p,Inventory inv){
        p.openInventory(inv);
    }



}
