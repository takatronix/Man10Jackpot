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
import java.util.UUID;

public final class Man10Jackpot extends JavaPlugin {

    class BetInfo{
        Player player;
        String name;
        UUID uuid;
        double ammount;
    }

    public Inventory gameMenu = Bukkit.createInventory(null,54,"ゲーム");


    public Man10JackpotRunnable runnable = new Man10JackpotRunnable(this);
    public Man10JackpotCommand mjp = new Man10JackpotCommand(this);
    public Man10JackpotGame game = new Man10JackpotGame(this);
    public Man10JackpotListener listener = new Man10JackpotListener(this);
    public Man10JackpotMenu menu = new Man10JackpotMenu(this);

    public List<Player> playersInMenu = new ArrayList<>();
    public List<Player> playersInGame = new ArrayList<>();
    public List<Integer> chanceInGame = new ArrayList<>();
    public List<ItemStack> dummy = new ArrayList<>();


    public HashMap<Integer,ItemStack> idToHeadItem = new HashMap<>();
    public HashMap<Integer,UUID> idToUUID = new HashMap<>();
    public HashMap<UUID,Integer> UUIDToId = new HashMap<>();

    public HashMap<UUID,BetInfo> uuidToBetInfo = new HashMap<>();
    public HashMap<Player,String> playerMenuState = new HashMap<>();
    public HashMap<Player,Integer> playerMenuPage = new HashMap<>();
    public HashMap<Player,String> playerCalcValue = new HashMap<>();

    public VaultManager vault = null;

    public int ticket_price = 0;

    public String prefix = "§e§l[§c§lMJackpot§e§l]§f§l";

    public boolean someOneInMenu = false;
    @Override
    public void onEnable() {
        // Plugin startup logic
        menu.setUpGameMenu();
        getCommand("mjackpot").setExecutor(mjp);
        createDummy();
        vault = new VaultManager(this);
        Bukkit.getPluginManager().registerEvents(listener,this);
        saveDefaultConfig();
        ticket_price = getConfig().getInt("ticket_price");

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

    public void placeBet(Player p,double ammount){
        if(playersInGame.contains(p)){
            BetInfo getBet = uuidToBetInfo.get(p.getUniqueId());
            getBet.ammount = getBet.ammount + ammount;
            uuidToBetInfo.put(p.getUniqueId(),getBet);
            for(int i = 0; i < ammount; i++){
                chanceInGame.add(UUIDToId.get(p.getUniqueId()));
            }
            return;
        }
        int ammountOfPlayer = playersInGame.size();
        idToUUID.put(ammountOfPlayer + 1,p.getUniqueId());
        UUIDToId.put(p.getUniqueId(),ammountOfPlayer + 1);
        idToHeadItem.put(ammountOfPlayer + 1,new SkullMaker().withOwner(p.getName()).withName(p.getName()).build());
        BetInfo betInfo = new BetInfo();
        betInfo.ammount = ammount;
        betInfo.name = p.getName();
        betInfo.player = p;
        betInfo.uuid = p.getUniqueId();
        uuidToBetInfo.put(p.getUniqueId(), betInfo);
        playersInGame.add(p);
        for(int i = 0; i < ammount; i++){
            chanceInGame.add(ammountOfPlayer + 1);
        }
        return;
    }

    public void list(Player p){
        for(Player player : playersInGame){
            BetInfo bet = uuidToBetInfo.get(player.getUniqueId());
        }
    }


}
