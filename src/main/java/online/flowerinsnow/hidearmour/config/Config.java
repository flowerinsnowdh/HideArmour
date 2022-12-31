package online.flowerinsnow.hidearmour.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;

@SideOnly(Side.CLIENT)
public class Config {
    private static File configFile;
    private static Configuration config;

    public static Property enable;
    public static Property helmet;
    public static Property chestplate;
    public static Property leggings;
    public static Property boots;

    public static void init(File configFile) {
        Config.configFile = configFile;
    }

    public static void reloadConfig() {
        config = new Configuration(configFile);
        config.load();

        final String category = "configuration";
        enable = config.get(category, "enable", false, "总开关");
        helmet = config.get(category, "helmet", true, "是否隐藏头盔");
        chestplate = config.get(category, "chestplate", true, "是否隐藏胸甲");
        leggings = config.get(category, "leggings", true, "是否隐藏护腿");
        boots = config.get(category, "boots", true, "是否隐藏靴子");

        saveConfig();
    }

    public static void saveConfig() {
        config.save();
    }
}
