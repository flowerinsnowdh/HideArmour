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
        enable = config.get("enable", category, false, "总开关");
        helmet = config.get("helmet", category, true, "是否隐藏头盔");
        chestplate = config.get("chestplate", category, true, "是否隐藏胸甲");
        leggings = config.get("leggings", category, true, "是否隐藏护腿");
        boots = config.get("boots", category, true, "是否隐藏靴子");

        saveConfig();
    }

    public static void saveConfig() {
        config.save();
    }
}
