package online.flowerinsnow.hidearmour.client.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import online.flowerinsnow.hidearmour.client.gui.GuiConfiguration;

public class ModMenuImpl implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return GuiConfiguration::new;
    }
}
