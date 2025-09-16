package cn.flowerinsnow.hidearmour.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Config {
    public static final Config CONFIG;
    public static final ModConfigSpec CONFIG_SPEC;

    static {
        Pair<Config, ModConfigSpec> pair = new ModConfigSpec.Builder().configure(Config::new);
        CONFIG = pair.getLeft();
        CONFIG_SPEC = pair.getRight();
    }

    public final ModConfigSpec.BooleanValue enable;
    public final ModConfigSpec.BooleanValue helmet;
    public final ModConfigSpec.BooleanValue chestplate;
    public final ModConfigSpec.BooleanValue leggings;
    public final ModConfigSpec.BooleanValue boots;

    public Config(@NotNull ModConfigSpec.Builder builder) {
        Objects.requireNonNull(builder, "builder");
        this.enable = builder.define("enable", true);
        this.helmet = builder.define("helmet", true);
        this.chestplate = builder.define("chestplate", true);
        this.leggings = builder.define("leggings", true);
        this.boots = builder.define("boots", true);
    }
}
