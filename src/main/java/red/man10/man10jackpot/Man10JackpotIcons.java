package red.man10.man10jackpot;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.Collections;

/**
 * Created by sho-pc on 2017/04/25.
 */
public class Man10JackpotIcons {
    private final Man10Jackpot plugin;

    public Man10JackpotIcons(Man10Jackpot plugin) {
        this.plugin = plugin;
    }

    public void setUpIcon(){
        plugin.icons.clear();
        ItemStack[] ico = {new ItemStack(Material.DIAMOND_BLOCK,1,(short)0),
                new ItemStack(Material.GOLD_BLOCK,1,(short)0),
                new ItemStack(Material.IRON_BLOCK,1,(short)0),
                new ItemStack(Material.WOOL,1,(short)2),
                new ItemStack(Material.WOOL,1,(short)11),
                new ItemStack(Material.WOOL,1,(short)13),
                new ItemStack(Material.WOOL,1,(short)14),
                new ItemStack(Material.WOOL,1,(short)9),
                new ItemStack(Material.WOOL,1,(short)15),
                new ItemStack(Material.WOOL,1,(short)4),
                new ItemStack(Material.WOOL,1,(short)3),
                new ItemStack(Material.WOOL,1,(short)5),
                new ItemStack(Material.WOOL,1,(short)6),
                new ItemStack(Material.WOOL,1,(short)12),
                new ItemStack(Material.WOOL,1,(short)1),
                new ItemStack(Material.RED_ROSE,1,(short)0),
                new ItemStack(Material.YELLOW_FLOWER,1,(short)0),
                new ItemStack(Material.RED_ROSE,1,(short)1),
                new ItemStack(Material.RED_ROSE,1,(short)6),
                new ItemStack(Material.RED_ROSE,1,(short)5),
                new ItemStack(Material.RED_ROSE,1,(short)7),
                new ItemStack(Material.RED_ROSE,1,(short)8),
                new ItemStack(Material.RED_ROSE,1,(short)3),
                new ItemStack(Material.RED_ROSE,1,(short)4),
                new ItemStack(Material.RED_MUSHROOM,1,(short)0),
                new ItemStack(Material.BROWN_MUSHROOM,1,(short)0),
                new ItemStack(Material.RED_ROSE,1,(short)2),
                new ItemStack(Material.DIAMOND_ORE,1,(short)0),
                new ItemStack(Material.GOLD_ORE,1,(short)0),
                new ItemStack(Material.IRON_ORE,1,(short)0),
                new ItemStack(Material.COAL_ORE,1,(short)0),
                new ItemStack(Material.LAPIS_ORE,1,(short)0),
                new ItemStack(Material.REDSTONE_ORE,1,(short)0),
                new ItemStack(Material.STAINED_GLASS,1,(short) 4),
                new ItemStack(Material.STAINED_GLASS,1,(short) 14),
                new ItemStack(Material.STAINED_GLASS,1,(short) 6),
                new ItemStack(Material.STAINED_GLASS,1,(short) 12),
                new ItemStack(Material.STAINED_GLASS,1,(short) 11),
                new ItemStack(Material.STAINED_GLASS,1,(short) 1),
                new ItemStack(Material.STAINED_GLASS,1,(short) 2),
                new ItemStack(Material.STAINED_GLASS,1,(short) 3),
                new ItemStack(Material.STAINED_GLASS,1,(short) 5),
                new ItemStack(Material.STAINED_GLASS,1,(short) 8),
                new ItemStack(Material.STAINED_GLASS,1,(short) 9),
                new ItemStack(Material.STAINED_GLASS,1,(short) 13),
                new ItemStack(Material.STAINED_CLAY,1,(short) 2),
                new ItemStack(Material.STAINED_CLAY,1,(short) 7),
                new ItemStack(Material.STAINED_CLAY,1,(short) 3),
                new ItemStack(Material.STAINED_CLAY,1,(short) 1),
                new ItemStack(Material.STAINED_CLAY,1,(short) 6),
                new ItemStack(Material.STAINED_CLAY,1,(short) 5),
                new ItemStack(Material.RED_SHULKER_BOX,1,(short) 0),
                new ItemStack(Material.MAGENTA_SHULKER_BOX,1,(short) 0),
                new ItemStack(Material.LIGHT_BLUE_SHULKER_BOX,1,(short) 0),
                new ItemStack(Material.YELLOW_SHULKER_BOX,1,(short) 0),
                new ItemStack(Material.GRAY_SHULKER_BOX,1,(short) 0),
                new ItemStack(Material.BROWN_SHULKER_BOX,1,(short) 0),
                new ItemStack(Material.LIME_SHULKER_BOX,1,(short) 0),
                new ItemStack(Material.ORANGE_SHULKER_BOX,1,(short) 0),
                new ItemStack(Material.CLAY_BRICK,1,(short) 0),
                new ItemStack(Material.SUGAR_CANE,1,(short) 0),
                new ItemStack(Material.IRON_INGOT,1,(short) 0),
                new ItemStack(Material.STICK,1,(short) 0),
                new ItemStack(Material.INK_SACK,1,(short) 14),
                new ItemStack(Material.INK_SACK,1,(short) 11),
                new ItemStack(Material.INK_SACK,1,(short) 1),
                new ItemStack(Material.BOWL,1,(short) 0),
                new ItemStack(Material.FEATHER,1,(short) 0),
                new ItemStack(Material.CLAY_BALL,1,(short) 0),
                new ItemStack(Material.EGG,1,(short) 0),
                new ItemStack(Material.LEATHER,1,(short) 0),
                new ItemStack(Material.SEEDS,1,(short) 0),
                new ItemStack(Material.WHEAT,1,(short) 0),
                new ItemStack(Material.INK_SACK,1,(short) 3),
                new ItemStack(Material.SULPHUR,1,(short) 0),
                new ItemStack(Material.INK_SACK,1,(short) 0),
                new ItemStack(Material.FLINT,1,(short) 0),
                new ItemStack(Material.INK_SACK,1,(short) 9),
                new ItemStack(Material.GLOWSTONE_DUST,1,(short) 0),
                new ItemStack(Material.INK_SACK,1,(short) 4),
                new ItemStack(Material.INK_SACK,1,(short) 6),
                new ItemStack(Material.INK_SACK,1,(short) 2),
                new ItemStack(Material.INK_SACK,1,(short) 12),
                new ItemStack(Material.COAL,1,(short) 0),
                new ItemStack(Material.INK_SACK,1,(short) 7),
                new ItemStack(Material.INK_SACK,1,(short) 5),
                new ItemStack(Material.INK_SACK,1,(short) 15),
                new ItemStack(Material.INK_SACK,1,(short) 8),
                new ItemStack(Material.DIAMOND,1,(short) 0),
                new ItemStack(Material.GOLD_INGOT,1,(short) 0),
                new ItemStack(Material.INK_SACK,1,(short) 10),
                new ItemStack(Material.PUMPKIN_SEEDS,1,(short) 0),
                new ItemStack(Material.STRING,1,(short) 0),
                new ItemStack(Material.INK_SACK,1,(short) 13)};

        for(int i = 0; i < ico.length; i++){
            plugin.icons.add(ico[i]);
        }
        Collections.shuffle(plugin.icons);

    }
}
