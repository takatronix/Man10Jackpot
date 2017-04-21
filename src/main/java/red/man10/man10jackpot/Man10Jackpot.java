package red.man10.man10jackpot;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class Man10Jackpot extends JavaPlugin {

    public Man10JackpotCommand mjp = new Man10JackpotCommand(this);
    public Man10JackpotGame game = new Man10JackpotGame(this);
    public Man10JackpotListener listener = new Man10JackpotListener(this);
    public Man10JackpotMenu menu = new Man10JackpotMenu(this);

    public List<Player> playersInMenu = new ArrayList<>();

    public List<ItemStack> dummy = new ArrayList<>();


    public HashMap<Player,String> playerMenuState = new HashMap<>();
    public HashMap<Player,Integer> playerMenuPage = new HashMap<>();
    public HashMap<Player,String> playerCalcValue = new HashMap<>();



    public String prefix = "§e§l[§c§lMJackpot§e§l]§f§l";

    public boolean someOneInMenu = false;
    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("mjackpot").setExecutor(mjp);
        createDummy();
        Bukkit.getPluginManager().registerEvents(listener,this);

    }

    public void createDummy(){
        for(int i = 0; i < 300; i++){
            ItemStack item = new ItemStack(Material.SKULL_ITEM,1);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(String.valueOf(i));
            item.setItemMeta(itemMeta);
            dummy.add(item);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


}
