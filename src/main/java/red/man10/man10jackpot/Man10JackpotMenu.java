package red.man10.man10jackpot;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

/**
 * Created by sho-pc on 2017/04/19.
 */
public class Man10JackpotMenu {


    private final Man10Jackpot plugin;

    public Man10JackpotMenu(Man10Jackpot plugin) {
        this.plugin = plugin;
    }


    public void betMenuInv(Player p){
        Inventory betMenu = Bukkit.createInventory(null,54,"§5§lベットメニュー 1口￥" + plugin.ticket_price);
        ItemStack i0 = new SkullMaker().withSkinUrl("http://textures.minecraft.net/texture/3f09018f46f349e553446946a38649fcfcf9fdfd62916aec33ebca96bb21b5").build();
        ItemStack i1 = new SkullMaker().withSkinUrl("http://textures.minecraft.net/texture/ca516fbae16058f251aef9a68d3078549f48f6d5b683f19cf5a1745217d72cc").build();
        ItemStack i2 = new SkullMaker().withSkinUrl("http://textures.minecraft.net/texture/4698add39cf9e4ea92d42fadefdec3be8a7dafa11fb359de752e9f54aecedc9a").build();
        ItemStack i3 = new SkullMaker().withSkinUrl("http://textures.minecraft.net/texture/fd9e4cd5e1b9f3c8d6ca5a1bf45d86edd1d51e535dbf855fe9d2f5d4cffcd2").build();
        ItemStack i4 = new SkullMaker().withSkinUrl("http://textures.minecraft.net/texture/f2a3d53898141c58d5acbcfc87469a87d48c5c1fc82fb4e72f7015a3648058").build();
        ItemStack i5 = new SkullMaker().withSkinUrl("http://textures.minecraft.net/texture/d1fe36c4104247c87ebfd358ae6ca7809b61affd6245fa984069275d1cba763").build();
        ItemStack i6 = new SkullMaker().withSkinUrl("http://textures.minecraft.net/texture/3ab4da2358b7b0e8980d03bdb64399efb4418763aaf89afb0434535637f0a1").build();
        ItemStack i7 = new SkullMaker().withSkinUrl("http://textures.minecraft.net/texture/297712ba32496c9e82b20cc7d16e168b035b6f89f3df014324e4d7c365db3fb").build();
        ItemStack i8 = new SkullMaker().withSkinUrl("http://textures.minecraft.net/texture/abc0fda9fa1d9847a3b146454ad6737ad1be48bdaa94324426eca0918512d").build();
        ItemStack i9 = new SkullMaker().withSkinUrl("http://textures.minecraft.net/texture/d6abc61dcaefbd52d9689c0697c24c7ec4bc1afb56b8b3755e6154b24a5d8ba").build();
        ItemStack b = new SkullMaker().withSkinUrl("http://textures.minecraft.net/texture/9eca98befd0d7efca9b11ebf4b2da459cc19a378114b3cdde67d4067afb896").withName("§7§lBET").build();
        ItemStack e = new SkullMaker().withSkinUrl("http://textures.minecraft.net/texture/dbb2737ecbf910efe3b267db7d4b327f360abc732c77bd0e4eff1d510cdef").withName("§7§lBET").build();
        ItemStack t = new SkullMaker().withSkinUrl("http://textures.minecraft.net/texture/1562e8c1d66b21e459be9a24e5c027a34d269bdce4fbee2f7678d2d3ee4718").withName("§7§lBET").build();
        ItemStack cancel = new ItemStack(Material.REDSTONE_BLOCK, 1);
        ItemStack Accept = new ItemStack(Material.EMERALD_BLOCK, 1);
        ItemStack clear = new ItemStack(Material.TNT, 1);
        ItemMeta clearm = clear.getItemMeta();
        clearm.setDisplayName("§c§lクリア");
        clear.setItemMeta(clearm);
        ArrayList<String> a = new ArrayList<String>();
        ItemMeta am = Accept.getItemMeta();
        ItemMeta cm = cancel.getItemMeta();
        a.add("§d§l掛け金");
        am.setDisplayName("§a§l確認");
        cm.setDisplayName("§c§lキャンセル");
        Accept.setItemMeta(am);
        cancel.setItemMeta(cm);
        ItemMeta i0m = i0.getItemMeta();
        ItemMeta i1m = i1.getItemMeta();
        ItemMeta i2m = i2.getItemMeta();
        ItemMeta i3m = i3.getItemMeta();
        ItemMeta i4m = i4.getItemMeta();
        ItemMeta i5m = i5.getItemMeta();
        ItemMeta i6m = i6.getItemMeta();
        ItemMeta i7m = i7.getItemMeta();
        ItemMeta i8m = i8.getItemMeta();
        ItemMeta i9m = i9.getItemMeta();
        i0m.setDisplayName("§7§l0");
        i1m.setDisplayName("§7§l1");
        i2m.setDisplayName("§7§l2");
        i3m.setDisplayName("§7§l3");
        i4m.setDisplayName("§7§l4");
        i5m.setDisplayName("§7§l5");
        i6m.setDisplayName("§7§l6");
        i7m.setDisplayName("§7§l7");
        i8m.setDisplayName("§7§l8");
        i9m.setDisplayName("§7§l9");
        i0.setItemMeta(i0m);
        i1.setItemMeta(i1m);
        i2.setItemMeta(i2m);
        i3.setItemMeta(i3m);
        i4.setItemMeta(i4m);
        i5.setItemMeta(i5m);
        i6.setItemMeta(i6m);
        i7.setItemMeta(i7m);
        i8.setItemMeta(i8m);
        i9.setItemMeta(i9m);
        betMenu.setItem(19, i7);
        betMenu.setItem(20, i8);
        betMenu.setItem(21, i9);
        betMenu.setItem(28, i4);
        betMenu.setItem(29, i5);
        betMenu.setItem(30, i6);
        betMenu.setItem(37, i1);
        betMenu.setItem(38, i2);
        betMenu.setItem(39, i3);
        betMenu.setItem(46, i0);
        betMenu.setItem(50, Accept);
        betMenu.setItem(52, cancel);
        betMenu.setItem(32, b);
        betMenu.setItem(33, e);
        betMenu.setItem(34, t);
        betMenu.setItem(48, clear);

        p.openInventory(betMenu);
    }

    public void moveDisplay(Inventory inv){
        inv.setItem(0,inv.getItem(1));
        inv.setItem(1,inv.getItem(2));
        inv.setItem(2,inv.getItem(3));
        inv.setItem(3,inv.getItem(4));
        inv.setItem(4,inv.getItem(5));
        inv.setItem(5,inv.getItem(6));
        inv.setItem(6,inv.getItem(7));
        inv.setItem(7,inv.getItem(8));
    }

    public ItemStack createDisplay(Inventory i,int num){
        ItemStack item = new ItemStack(Material.AIR);
        if(num==0){
            item = new SkullMaker().withSkinUrl("http://textures.minecraft.net/texture/0ebe7e5215169a699acc6cefa7b73fdb108db87bb6dae2849fbe24714b27").build();
        }else if(num==1){
            item = new SkullMaker().withSkinUrl("http://textures.minecraft.net/texture/71bc2bcfb2bd3759e6b1e86fc7a79585e1127dd357fc202893f9de241bc9e530").build();
        }else if(num==2){
            item = new SkullMaker().withSkinUrl("http://textures.minecraft.net/texture/4cd9eeee883468881d83848a46bf3012485c23f75753b8fbe8487341419847").build();
        }else if(num==3){
            item = new SkullMaker().withSkinUrl("http://textures.minecraft.net/texture/1d4eae13933860a6df5e8e955693b95a8c3b15c36b8b587532ac0996bc37e5").build();
        }else if(num==4){
            item = new SkullMaker().withSkinUrl("http://textures.minecraft.net/texture/d2e78fb22424232dc27b81fbcb47fd24c1acf76098753f2d9c28598287db5").build();
        }else if(num==5){
            item = new SkullMaker().withSkinUrl("http://textures.minecraft.net/texture/6d57e3bc88a65730e31a14e3f41e038a5ecf0891a6c243643b8e5476ae2").build();
        }else if(num==6){
            item = new SkullMaker().withSkinUrl("http://textures.minecraft.net/texture/334b36de7d679b8bbc725499adaef24dc518f5ae23e716981e1dcc6b2720ab").build();
        }else if(num==7){
            item = new SkullMaker().withSkinUrl("http://textures.minecraft.net/texture/6db6eb25d1faabe30cf444dc633b5832475e38096b7e2402a3ec476dd7b9").build();
        }else if(num==8){
            item = new SkullMaker().withSkinUrl("http://textures.minecraft.net/texture/59194973a3f17bda9978ed6273383997222774b454386c8319c04f1f4f74c2b5").build();
        }else if(num==9){
            item = new SkullMaker().withSkinUrl("http://textures.minecraft.net/texture/e67caf7591b38e125a8017d58cfc6433bfaf84cd499d794f41d10bff2e5b840").build();
        }
        return item;
    }

    public void setUpGameMenu(){
        Inventory inv = plugin.gameMenu;
        ItemStack redGlass = new ItemStack(Material.RED_STAINED_GLASS_PANE,1,(short) 14);
        ItemStack greyGlass = new ItemStack(Material.GRAY_STAINED_GLASS_PANE,1,(short) 15);
        ItemMeta greyMeta = greyGlass.getItemMeta();
        greyMeta.setDisplayName(" ");
        greyGlass.setItemMeta(greyMeta);
        redGlass.setItemMeta(greyMeta);
        int[] grey = {0,1,2,3,5,6,7,8,9,17,18,20,21,23,24,26,27,29,33,35,36,38,42,44,45,47,51,53};
        for(int i = 0;i < grey.length; i++){
            inv.setItem(grey[i], greyGlass);
        }
        inv.setItem(4,redGlass);
        inv.setItem(22, redGlass);
    }
}
