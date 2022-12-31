package online.flowerinsnow.hidearmour.client.config;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import online.flowerinsnow.saussureautils.io.CopyOption;
import online.flowerinsnow.saussureautils.io.IOUtils;
import online.flowerinsnow.xml.XMLFactory;
import online.flowerinsnow.xml.node.XMLNodeDocument;
import online.flowerinsnow.xml.node.XMLNodeElement;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Environment(EnvType.CLIENT)
public final class Config {
    private Config() {
    }

    private static XMLNodeDocument document;
    private static XMLNodeElement config;

    public static void loadConfig() throws IOException {
        Path configFile = getConfigFile();
        if (!configFile.toFile().isFile()) {
            IOUtils.copy(Config.class.getResourceAsStream("/config.xml"), configFile, CopyOption.CLOSE_INPUT);
        }
        try (InputStream in = Files.newInputStream(configFile)) {
            document = XMLFactory.parse(in);
            config = document.getElement("hidearmour");
        }
    }

    public static XMLNodeElement getConfig() {
        return config;
    }

    public static void saveConfig() throws IOException {
        try (OutputStream out = Files.newOutputStream(getConfigFile())) {
            try {
                XMLFactory.save(document, out);
            } catch (TransformerException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static Path getConfigFile() {
        return FabricLoader.getInstance().getConfigDir().resolve("hidearmour.xml");
    }
}
