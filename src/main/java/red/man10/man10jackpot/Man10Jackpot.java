package red.man10.man10jackpot;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import red.man10.MySQLManager;
import red.man10.VaultManager;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

public final class Man10Jackpot extends JavaPlugin {

    class BetInfo{
        Player player;
        String name;
        UUID uuid;
        double amount;
    }

    public Inventory gameMenu = Bukkit.createInventory(null,54,"§5§kA§c§l優勝賞金:");

    public int totalBetInt = 0;
    public int time = 0;


    public Man10JackpotRunnable runnable = new Man10JackpotRunnable(this);
    public Man10JackpotCommand mjp = new Man10JackpotCommand(this);
    public Man10JackpotGame game = new Man10JackpotGame(this);
    public Man10JackpotListener listener = new Man10JackpotListener(this);
    public Man10JackpotMenu menu = new Man10JackpotMenu(this);
    public Man10JackpotIcons icon = new Man10JackpotIcons(this);


    public List<Player> playersInMenu = new ArrayList<>();
    public List<UUID> playersInGame = new ArrayList<>();
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
    public String starttime = null;


    public MySQLManager mysql = null;
    public VaultManager vault = null;

    public int ticket_price = 0;
  //  public MoneyPoolObject totalBet = null;


    public double totalBetPrice;

    public int gameID = 0;

    public String prefix = "§e§l[§c§lMJackpot§e§l]§f§l";

    public boolean timerStarted = false;
    public boolean inGame = false;
    public boolean someOneInMenu = false;
    public double tax = 0;
    public boolean lockdown = false;

    public int timer_time = 0;
    public String winner_broadcast = getConfig().getString("winner_broadcast");
    public String loser_broadcast = getConfig().getString("loser_broadcast");


    @Override
    public void onEnable() {
        // Plugin startup logic
        menu.setUpGameMenu();
        getCommand("mjackpot").setExecutor(mjp);
        getCommand("mj").setExecutor(mjp);
        Bukkit.getPluginManager().registerEvents(listener,this);
        saveDefaultConfig();
        timer_time = getConfig().getInt("timer");
        icon.setUpIcon();
        ticket_price = getConfig().getInt("ticket_price");
        vault = new VaultManager(this);
        tax = getConfig().getInt("tax_percentage");
        mysql = new MySQLManager(this,"man10juckpot");
        mysql.execute(gameTable);
        mysql.execute(betTable);
        refreshGame(true);

    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
        for(int i = 0; i < playersInGame.size(); i++){
            Player p = Bukkit.getPlayer(playersInGame.get(i));
            p.closeInventory();
        }
        for(int i = 0; i < playersInMenu.size(); i++){
            Player p = playersInMenu.get(i);
            p.closeInventory();
        }
        cancelGame(false);
        //savePlayerDataToFile();
        mysql.execute("DELETE FROM jackpot_game WHERE game_id = '" + gameID + "';");
        mysql.execute("insert into jackpot_game values ('0','" + gameID + "','" + totalBetInt + "','" + ticket_price + "','none','none'," + starttime + "," + currentTime() + ",'canceled');");
    }

    public String gameTable = "CREATE TABLE `jackpot_game` (\n" +
            "\t`id` INT NOT NULL AUTO_INCREMENT,\n" +
            "\t`game_id` INT NOT NULL DEFAULT '0',\n" +
            "\t`total_ticket` INT NOT NULL DEFAULT '0',\n" +
            "\t`ticket_price` INT NOT NULL DEFAULT '0',\n" +
            "\t`winner_name` VARCHAR(64) NOT NULL DEFAULT '0',\n" +
            "\t`winner_uuid` VARCHAR(64) NOT NULL DEFAULT '0',\n" +
            "\t`time_start` VARCHAR(50) NOT NULL DEFAULT '0',\n" +
            "\t`time_end` DATETIME NULL DEFAULT NULL,\n" +
            "\t`status` VARCHAR(50) NULL DEFAULT '0',\n" +
            "\tPRIMARY KEY (`id`)\n" +
            ")\n" +
            "COLLATE='utf8_general_ci'\n" +
            "ENGINE=InnoDB\n" +
            ";";


    public String betTable = "CREATE TABLE `jackpot_bet` (\n" +
            "\t`id` INT NOT NULL AUTO_INCREMENT,\n" +
            "\t`game_id` INT NULL DEFAULT '0',\n" +
            "\t`uuid` VARCHAR(64) NULL DEFAULT '0',\n" +
            "\t`name` VARCHAR(64) NULL DEFAULT '0',\n" +
            "\t`amount` INT NULL DEFAULT '0',\n" +
            "\t`ticket_price` INT NULL DEFAULT '0',\n" +
            "\t`time` DATETIME NULL DEFAULT NULL,\n" +
            "\tPRIMARY KEY (`id`)\n" +
            ")\n" +
            "COLLATE='utf8_general_ci'\n" +
            "ENGINE=InnoDB\n" +
            ";";

    public void placeBet(Player p,double amount){

        if(playerUUIDInGame.contains(p.getUniqueId())){
            BetInfo getBet = UUIDToBetInfo.get(p.getUniqueId());
            ItemStack itemt = idToItem.get(UUIDToId.get(p.getUniqueId()));
            ItemMeta itemMetat = itemt.getItemMeta();
            itemMetat.setDisplayName("§e§l" + p.getName());
            int intValueOfAmount = ((int)amount  + (int) getBet.amount);
            String[] lore = {"§c§l====================","§d§l掛け金" + (amount + getBet.amount) * ticket_price,"§d§l" + intValueOfAmount + "口購入","§c§l====================","§b§l勝率"};
            itemMetat.setLore(Arrays.asList(lore));
            itemt.setItemMeta(itemMetat);
            idToItem.put(UUIDToId.get(p.getUniqueId()),itemt);
            for(int i = 0; i < amount; i++){
                chanceInGame.add(UUIDToId.get(p.getUniqueId()));
                totalBetInt++;
            }
            getBet.amount = getBet.amount + amount;
            getBet.player = p;
            getBet.uuid = p.getUniqueId();
            getBet.name = p.getName();
            UUIDToBetInfo.put(p.getUniqueId(),getBet);
//            vault.transferMoneyPlayerToPool(p.getUniqueId(), totalBet.getId(), ticket_price * amount, TransactionCategory.GAMBLE, TransactionType.BET, "Man10Jackpot User:" + p.getName()+ " bet:" + String.valueOf(ticket_price * amount));
            p.sendMessage(prefix + "ベットしました");

            refreshPercentage();
            refreshMenu();
            openMainMenuForPlayer(p);
            return;
        }
        int amountOfPlayer = playersInGame.size();
        idToUUID.put(amountOfPlayer + 1,p.getUniqueId());
        UUIDToId.put(p.getUniqueId(),amountOfPlayer + 1);
        ItemStack item = icons.get(amountOfPlayer + 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("§e§l" + p.getName());
        int intValueOfamount = (int)amount;
        String[] lore = {"§c§l====================","§d§l掛け金" + amount * ticket_price,"§d§l" + intValueOfamount + "口購入","§c§l====================","§b§l勝率"};
        itemMeta.setLore(Arrays.asList(lore));
        item.setItemMeta(itemMeta);
        idToItem.put(amountOfPlayer + 1,item);
        BetInfo betInfo = new BetInfo();
        betInfo.amount = amount;
        betInfo.name = p.getName();
        betInfo.player = p;
        betInfo.uuid = p.getUniqueId();
        UUIDToBetInfo.put(p.getUniqueId(), betInfo);
        playersInGame.add(p.getUniqueId());
        playerUUIDInGame.add(p.getUniqueId());
        itemList.add(item);
        for(int i = 0; i < amount; i++){
            chanceInGame.add(amountOfPlayer + 1);
            totalBetInt++;
        }
      //  vault.transferMoneyPlayerToPool(p.getUniqueId(),totalBet.getId(), ticket_price * amount, TransactionCategory.GAMBLE, TransactionType.BET, "Man10Jackpot User:" + p.getName()+ " bet:" + String.valueOf(ticket_price * amount));
        startTimer(playersInGame.size());
        refreshPercentage();
        refreshMenu();
        openMainMenuForPlayer(p);
        p.sendMessage(prefix + "ベットしました");
        return;
    }
    public void startTimer(int amount){
        if(amount < 2 || timerStarted == true) {
           return;
        }
        timerStarted = true;
        runnable.onStartTimer();
    }

    public void openMainMenuForPlayer(Player p){
        p.closeInventory();
        p.closeInventory();
        playersInMenu.add(p);
        someOneInMenu = true;
        playerMenuPage.put(p, 1);
        playerMenuState.put(p, "main");
        game.openInventory(p, game.setUpMainInv(p));
    }

    public void refreshMenu(){
        if(someOneInMenu == false){
            return;
        }
        for(int i = 0; i < playersInMenu.size(); i++){
            Player p = playersInMenu.get(i);
            if(p.getOpenInventory().getTitle().contains("§c§l現在ベット")) {
                p.closeInventory();
                playersInMenu.add(p);
                someOneInMenu = true;
                playerMenuPage.put(p, 1);
                playerMenuState.put(p, "main");
                game.openInventory(p, game.setUpMainInv(p));
            }
        }
    }


    public void refreshPercentage(){
        for(int i = 0; i < playersInGame.size(); i ++){
            OfflinePlayer p = Bukkit.getOfflinePlayer(playersInGame.get(i));
            BetInfo info = UUIDToBetInfo.get(p.getUniqueId());
            ItemStack item = idToItem.get(UUIDToId.get(p.getUniqueId()));
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName("§e§l" + p.getName());
            double winRate = (info.amount/totalBetInt)*100;
            String[] lore = {"§c§l====================","§d§l掛け金" + info.amount * ticket_price,"§d§l" + ((int) info.amount) + "口購入","§c§l====================","§b§l勝率" + round(winRate,2) + "%"};
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
            Player p = Bukkit.getPlayer(playersInGame.get(i));
            if(p != null) {
                p.closeInventory();
                p.openInventory(gameMenu);
                playersInMenu.add(p);
            }
        }
        someOneInMenu = true;
        runnable.onSpin();
    }

    public void refreshGame(boolean createNew){
        totalBetInt = 0;

        playersInGame.clear();
        playersInMenu.clear();
        chanceInGame.clear();
        playerUUIDInGame.clear();
        idToItem.clear();
        idToUUID.clear();
        UUIDToId.clear();
        inGame = false;
        timerStarted = false;
        itemList.clear();
        time = timer_time;
        icon.setUpIcon();

        UUIDToBetInfo.clear();
        playerMenuPage.clear();
        playerMenuPage.clear();
        playerCalcValue.clear();
        starttime = currentTime();
        if(createNew) {
            updateGameID();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mysql.execute("insert into jackpot_game values ('0','" + gameID + "','0','0','none','none'," + starttime + ",'2017-00-00 00:00:00','open');");
                }
            }).start();
        }

    }

    public void cancelGame (boolean createNew){
        for(int i = 0; i < playersInGame.size(); i++){
            OfflinePlayer p = Bukkit.getOfflinePlayer(playersInGame.get(i));
        //    totalBet.transferBalanceToPlayer(p.getUniqueId(),UUIDToBetInfo.get(p.getUniqueId()).amount * ticket_price, TransactionCategory.GAMBLE, TransactionType.CANCEL, "Man10Jackpot Game Cancel Payout user:" + Bukkit.getOfflinePlayer(playersInGame.get(i)).getName()  + " value :" + String.valueOf( UUIDToBetInfo.get(p.getUniqueId()).amount * ticket_price));
        }
        for(int i = 0; i < playersInGame.size(); i++){
            Player p = Bukkit.getPlayer(playersInGame.get(i));
            if(p != null){
                p.closeInventory();
                p.sendMessage(prefix + "ゲームがキャンセルされました");
            }
        }
        for(int i = 0; i < playersInMenu.size(); i++){
            Player p = playersInMenu.get(i);
            p.closeInventory();
        }
        refreshGame(createNew);
    }

    public void updateGameID(){
        Random rand = new Random();
        int result = rand.nextInt(1000000000);
        gameID = result;
    }

    public String currentTime(){

        //long timestamp = 1371271256;
        //Date date = new Date(timestamp * 1000);

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy'-'MM'-'dd' 'HH':'mm':'ss");
        String currentTime = "'"+sdf.format(date)+"'";
        Bukkit.getLogger().info(currentTime);
        return currentTime;
    }

}
