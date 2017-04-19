package red.man10.man10jackpot;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import red.man10.man10jackpot.Man10Jackpot;

/**
 * Created by sho-pc on 2017/04/19.
 */
public class Man10JackpotCommand implements CommandExecutor {

    private final Man10Jackpot plugin;

    public Man10JackpotCommand(Man10Jackpot plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player)sender;
        plugin.playersInMenu.add(p);
        plugin.someOneInMenu = true;
        plugin.playerMenuPage.put(p,1);
        plugin.playerMenuState.put(p,"main");
        plugin.game.openInventory(p,plugin.game.setUpMainInv(p));
        return false;
    }
}
