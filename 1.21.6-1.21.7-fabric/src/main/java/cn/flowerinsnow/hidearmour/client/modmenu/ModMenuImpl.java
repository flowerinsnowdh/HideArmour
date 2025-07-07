package cn.flowerinsnow.hidearmour.client.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import cn.flowerinsnow.hidearmour.client.gui.GuiConfiguration;

@Environment(EnvType.CLIENT)
public class ModMenuImpl implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return GuiConfiguration::new;
    }
}
