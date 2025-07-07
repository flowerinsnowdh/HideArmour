package cn.flowerinsnow.hidearmour.client.config;

import cc.carm.lib.configuration.Configuration;
import cc.carm.lib.configuration.value.standard.ConfiguredValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class Config implements Configuration {
    public static final ConfiguredValue<Boolean> ENABLE = ConfiguredValue.of(true);
    public static final ConfiguredValue<Boolean> HELMET = ConfiguredValue.of(true);
    public static final ConfiguredValue<Boolean> CHESTPLATE = ConfiguredValue.of(true);
    public static final ConfiguredValue<Boolean> LEGGINGS = ConfiguredValue.of(true);
    public static final ConfiguredValue<Boolean> BOOTS = ConfiguredValue.of(true);
}
