package online.flowerinsnow.hidearmour.client.config;

import cc.carm.lib.configuration.core.ConfigurationRoot;
import cc.carm.lib.configuration.core.value.type.ConfiguredValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class Config extends ConfigurationRoot {
    public static final ConfiguredValue<Boolean> ENABLE = ConfiguredValue.of(Boolean.class, false);
    public static final ConfiguredValue<Boolean> HELMET = ConfiguredValue.of(Boolean.class, true);
    public static final ConfiguredValue<Boolean> CHESTPLATE = ConfiguredValue.of(Boolean.class, true);
    public static final ConfiguredValue<Boolean> LEGGINGS = ConfiguredValue.of(Boolean.class, true);
    public static final ConfiguredValue<Boolean> BOOTS = ConfiguredValue.of(Boolean.class, true);
}
