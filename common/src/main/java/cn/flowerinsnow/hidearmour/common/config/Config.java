package cn.flowerinsnow.hidearmour.common.config;

import cn.flowerinsnow.hidearmour.common.provider.ModEnvironmentProvider;
import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.io.WritingException;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.electronwill.nightconfig.toml.TomlFormat;
import com.electronwill.nightconfig.toml.TomlParser;
import com.electronwill.nightconfig.toml.TomlWriter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public class Config {
    @NotNull private final ModEnvironmentProvider environmentProvider;

    public boolean enable;
    public boolean helmet;
    public boolean chestplate;
    public boolean leggings;
    public boolean boots;

    public Config(@NotNull ModEnvironmentProvider environmentProvider) {
        this.environmentProvider = Objects.requireNonNull(environmentProvider);
    }

    public void saveDefault(boolean replace) {
        if (Files.exists(this.environmentProvider.getConfigFile()) && !replace) {
            return;
        }
        try (InputStream in = this.environmentProvider.getDefaultConfigAsStream()) {
            Files.copy(
                    in,
                    this.environmentProvider.getConfigFile(),
                    StandardCopyOption.REPLACE_EXISTING
            );
        } catch (IOException e) {
            this.environmentProvider.crash(e, "unable to save default config");
        }
    }

    public void load() {
        TomlParser parser = new TomlParser();
        Path configFile = this.environmentProvider.getConfigFile();
        if (!Files.exists(configFile)) {
            this.saveDefault(false);
        }
        CommentedConfig config = parser.parse(configFile, (path, format) -> {
            Config.this.environmentProvider.crash(new AssertionError(), "config file not found, it should never happen");
            return false;
        }, StandardCharsets.UTF_8);

        this.enable = config.get("enable");
        this.helmet = config.get("helmet");
        this.chestplate = config.get("chestplate");
        this.leggings = config.get("leggings");
        this.boots = config.get("boots");
    }

    public void save() {
        TomlWriter writer = new TomlWriter();
        CommentedConfig config = TomlFormat.newConfig();
        config.set("enable", this.enable);
        config.set("helmet", this.helmet);
        config.set("chestplate", this.chestplate);
        config.set("leggings", this.leggings);
        config.set("boots", this.boots);
        try {
            writer.write(config, this.environmentProvider.getConfigFile(), WritingMode.REPLACE, StandardCharsets.UTF_8);
        } catch (WritingException e) {
            this.environmentProvider.crash(e, "unable to save default config");
        }
    }
}
