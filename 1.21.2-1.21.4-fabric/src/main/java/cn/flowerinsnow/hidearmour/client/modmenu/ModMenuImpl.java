package cn.flowerinsnow.hidearmour.client.modmenu;

import cn.flowerinsnow.hidearmour.client.HideArmour;
import cn.flowerinsnow.hidearmour.client.screen.ConfigScreen;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ModMenuImpl implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> ConfigScreen.create(parent, HideArmour.instance().config());
    }
}
