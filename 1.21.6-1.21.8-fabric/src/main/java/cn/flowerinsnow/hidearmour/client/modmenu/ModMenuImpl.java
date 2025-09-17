package cn.flowerinsnow.hidearmour.client.modmenu;

import cn.flowerinsnow.hidearmour.client.HideArmourClient;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import cn.flowerinsnow.hidearmour.client.screen.ConfigScreen;

@Environment(EnvType.CLIENT)
public class ModMenuImpl implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return screen -> ConfigScreen.create(screen, HideArmourClient.instance().config());
    }
}
