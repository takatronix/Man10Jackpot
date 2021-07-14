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
        ItemStack[] ico = {
                new ItemStack(Material.DIAMOND_BLOCK),
                new ItemStack(Material.GOLD_BLOCK),
                new ItemStack(Material.IRON_BLOCK),
                new ItemStack(Material.BONE_BLOCK),
                new ItemStack(Material.REDSTONE_BLOCK),
                new ItemStack(Material.MAGMA_BLOCK),
                new ItemStack(Material.WHITE_WOOL),
                new ItemStack(Material.CLAY_BALL),
                new ItemStack(Material.RED_SHULKER_BOX),
                new ItemStack(Material.MAGENTA_SHULKER_BOX),
                new ItemStack(Material.LIGHT_BLUE_SHULKER_BOX),
                new ItemStack(Material.YELLOW_SHULKER_BOX),
                new ItemStack(Material.GRAY_SHULKER_BOX),
                new ItemStack(Material.BROWN_SHULKER_BOX),
                new ItemStack(Material.LIME_SHULKER_BOX),
                new ItemStack(Material.ORANGE_SHULKER_BOX),
                new ItemStack(Material.BRICK),
                new ItemStack(Material.SUGAR_CANE),
                new ItemStack(Material.IRON_INGOT),
                new ItemStack(Material.STICK),
                new ItemStack(Material.BOWL),
                new ItemStack(Material.FEATHER),
                new ItemStack(Material.CLAY_BALL),
                new ItemStack(Material.EGG),
                new ItemStack(Material.LEATHER),
                new ItemStack(Material.PUMPKIN_SEEDS),
                new ItemStack(Material.WHEAT),
                new ItemStack(Material.SHULKER_BOX),
                new ItemStack(Material.APPLE),
                new ItemStack(Material.FLINT),
                new ItemStack(Material.GLOWSTONE_DUST),
                new ItemStack(Material.COAL),
                new ItemStack(Material.DIAMOND),
                new ItemStack(Material.GOLD_INGOT),
                new ItemStack(Material.PUMPKIN_SEEDS),
                new ItemStack(Material.STRING),
                new ItemStack(Material.INK_SAC)
        };

        for(int i = 0; i < ico.length; i++){
            plugin.icons.add(ico[i]);
        }
        Collections.shuffle(plugin.icons);

    }
}
