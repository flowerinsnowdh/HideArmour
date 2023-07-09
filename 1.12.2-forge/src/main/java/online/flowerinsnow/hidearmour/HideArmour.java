package online.flowerinsnow.hidearmour;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import online.flowerinsnow.hidearmour.command.CommandHideArmour;
import online.flowerinsnow.hidearmour.config.Config;
import online.flowerinsnow.hidearmour.listener.RenderListener;

@Mod(
        modid = HideArmour.MODID,
        version = HideArmour.VERSION,
        clientSideOnly = true
)
@SideOnly(Side.CLIENT)
public class HideArmour {
    public static final String MODID = "hidearmour";
    public static final String VERSION = "1.12.1";

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Config.init(event.getSuggestedConfigurationFile());
        Config.reloadConfig();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new RenderListener());

        ClientCommandHandler.instance.registerCommand(new CommandHideArmour());
    }
}
