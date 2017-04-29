package red.man10.man10jackpot;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public final class Man10Jackpot extends JavaPlugin {

    class BetInfo{
        Player player;
        String name;
        UUID uuid;
        double ammount;
    }

    public Inventory gameMenu = Bukkit.createInventory(null,54,"§5§kA§c§l優勝賞金:");

    public int totalBetInt = 0;
    public int time = 20;


    public Man10JackpotRunnable runnable = new Man10JackpotRunnable(this);
    public Man10JackpotCommand mjp = new Man10JackpotCommand(this);
    public Man10JackpotGame game = new Man10JackpotGame(this);
    public Man10JackpotListener listener = new Man10JackpotListener(this);
    public Man10JackpotMenu menu = new Man10JackpotMenu(this);
    public Man10JackpotIcons icon = new Man10JackpotIcons(this);

    public List<Player> playersInMenu = new ArrayList<>();
    public List<Player> playersInGame = new ArrayList<>();
    public List<Integer> chanceInGame = new ArrayList<>();
    public List<UUID> playerUUIDInGame = new ArrayList<>();

    public HashMap<Integer,ItemStack> idToItem = new HashMap<>();
    public HashMap<Integer,UUID> idToUUID = new HashMap<>();
    public HashMap<UUID,Integer> UUIDToId = new HashMap<>();
    public List<ItemStack> itemList = new ArrayList<>();

    public List<ItemStack> icons = new ArrayList<>();


    public HashMap<UUID,BetInfo> UUIDToBetInfo = new HashMap<>();
    public HashMap<Player,String> playerMenuState = new HashMap<>();
    public HashMap<Player,Integer> playerMenuPage = new HashMap<>();
    public HashMap<Player,String> playerCalcValue = new HashMap<>();

    public VaultManager vault = null;

    public int ticket_price = 0;

    public String prefix = "§e§l[§c§lMJackpot§e§l]§f§l";

    public boolean timerStarted = false;
    public boolean inGame = false;
    public boolean someOneInMenu = false;
    @Override
    public void onEnable() {
        // Plugin startup logic
        menu.setUpGameMenu();
        getCommand("mjackpot").setExecutor(mjp);
        getCommand("mj").setExecutor(mjp);
        Bukkit.getPluginManager().registerEvents(listener,this);
        saveDefaultConfig();
        icon.setUpIcon();
        ticket_price = getConfig().getInt("ticket_price");
        vault = new VaultManager(this);

    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void placeBet(Player p,double ammount){
        int ammountOfPlayer = playersInGame.size();
        if(playerUUIDInGame.contains(p.getUniqueId())){
            BetInfo getBet = UUIDToBetInfo.get(p.getUniqueId());
            ItemStack itemt = idToItem.get(UUIDToId.get(p.getUniqueId()));
            ItemMeta itemMetat = itemt.getItemMeta();
            itemMetat.setDisplayName("§e§l" + p.getName());
            int intValueOfAmmount = ((int)ammount  + (int) getBet.ammount);
            String[] lore = {"§c§l====================","§d§l掛け金" + (ammount + getBet.ammount) * ticket_price,"§d§l" + intValueOfAmmount + "口購入","§c§l====================","§b§l勝率"};
            itemMetat.setLore(Arrays.asList(lore));
            itemt.setItemMeta(itemMetat);
            idToItem.put(UUIDToId.get(p.getUniqueId()),itemt);
            for(int i = 0; i < ammount; i++){
                chanceInGame.add(UUIDToId.get(p.getUniqueId()));
                totalBetInt++;
            }
            getBet.ammount = getBet.ammount + ammount;
            getBet.player = p;
            getBet.uuid = p.getUniqueId();
            getBet.name = p.getName();
            UUIDToBetInfo.put(p.getUniqueId(),getBet);
            refreshPercentage();
            startTimer(ammountOfPlayer);
            refreshMenu();
            return;
        }
        idToUUID.put(ammountOfPlayer + 1,p.getUniqueId());
        UUIDToId.put(p.getUniqueId(),ammountOfPlayer + 1);
        ItemStack item = icons.get(ammountOfPlayer + 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("§e§l" + p.getName());
        int intValueOfAmmount = (int)ammount;
        String[] lore = {"§c§l====================","§d§l掛け金" + ammount * ticket_price,"§d§l" + intValueOfAmmount + "口購入","§c§l====================","§b§l勝率"};
        itemMeta.setLore(Arrays.asList(lore));
        item.setItemMeta(itemMeta);
        idToItem.put(ammountOfPlayer + 1,item);
        BetInfo betInfo = new BetInfo();
        betInfo.ammount = ammount;
        betInfo.name = p.getName();
        betInfo.player = p;
        betInfo.uuid = p.getUniqueId();
        UUIDToBetInfo.put(p.getUniqueId(), betInfo);
        playersInGame.add(p);
        playerUUIDInGame.add(p.getUniqueId());
        itemList.add(item);
        for(int i = 0; i < ammount; i++){
            chanceInGame.add(ammountOfPlayer + 1);
        }
        totalBetInt = ((int) (totalBetInt + ammount));
        refreshPercentage();
        startTimer(ammountOfPlayer);
        refreshMenu();
        return;
    }
    public void startTimer(int ammount){
        if(ammount < 1 || timerStarted == true) {
           return;
        }
        timerStarted = true;
        runnable.onStartTimer();
    }

    public void refreshMenu(){
        if(someOneInMenu == false){
            return;
        }
        for(int i = 0; i < playersInMenu.size(); i++){
            Player p = playersInMenu.get(i);
            if(playerMenuState.get(p).equalsIgnoreCase("main")){
                p.closeInventory();
                playersInMenu.add(p);
                someOneInMenu = true;
                playerMenuPage.put(p,1);
                playerMenuState.put(p,"main");
                game.openInventory(p,game.setUpMainInv(p));
                return;
            }
        }
    }

    public void list(Player p){
        for(Player player : playersInGame){
            BetInfo bet = UUIDToBetInfo.get(player.getUniqueId());
            p.sendMessage(bet.name + " id:" + UUIDToId.get(player.getUniqueId()) + " bet:" + bet.ammount);
        }
        for(int i = 0;i < playersInGame.size(); i ++){
            p.sendMessage(playersInGame.get(i).getName());
        }
    }

    public void refreshPercentage(){
        for(int i = 0; i < playersInGame.size(); i ++){
            Player p = playersInGame.get(i);
            BetInfo info = UUIDToBetInfo.get(p.getUniqueId());
            ItemStack item = idToItem.get(UUIDToId.get(p.getUniqueId()));
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName("§e§l" + p.getName());
            double winRate = (info.ammount/totalBetInt)*100;
            String[] lore = {"§c§l====================","§d§l掛け金" + info.ammount * ticket_price,"§d§l" + ((int) info.ammount) + "口購入","§c§l====================","§b§l勝率" + round(winRate,2) + "%"};
            itemMeta.setLore(Arrays.asList(lore));
            item.setItemMeta(itemMeta);
            idToItem.put(UUIDToId.get(p.getUniqueId()),item);
        }
    }
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public void openSpinMenuForPlayer(){
        if(playersInMenu.size() != 0) {
            for (int i = 0; i < playersInMenu.size(); i++) {
                Player p = playersInMenu.get(i);
                p.closeInventory();
                p.openInventory(gameMenu);
                playersInMenu.add(p);
            }
        }
        for(int i = 0; i < playersInGame.size(); i++){
            Player p = playersInGame.get(i);
            p.closeInventory();
            p.openInventory(gameMenu);
            playersInMenu.add(p);
        }
        someOneInMenu = true;
        runnable.onSpin();
    }


}
