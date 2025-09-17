package cn.flowerinsnow.hidearmour.client.config;

import cn.flowerinsnow.hidearmour.client.HideArmourClient;
import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.electronwill.nightconfig.toml.TomlFormat;
import com.electronwill.nightconfig.toml.TomlParser;
import com.electronwill.nightconfig.toml.TomlWriter;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.crash.CrashReport;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

@Environment(EnvType.CLIENT)
public class Config {
    public boolean enable;
    public boolean helmet;
    public boolean chestplate;
    public boolean leggings;
    public boolean boots;

    public final @NotNull Path path;

    private Config(@NotNull Path path) {
        this.path = path;
    }

    @Contract("_ -> new")
    public static @NotNull Config create(@NotNull Path path) {
        Objects.requireNonNull(path, "path");
        return new Config(path);
    }

    @Contract(mutates = "io")
    private void init() {
        if (Files.notExists(this.path)) {
            try {
                //noinspection DataFlowIssue
                Files.copy(HideArmourClient.class.getResourceAsStream("/config.toml"), this.path);
            } catch (IOException e) {
                MinecraftClient.getInstance().printCrashReport(CrashReport.create(e, "Unable to save default config file: " + this.path));
            }
        }
    }

    @Contract(mutates = "this")
    public void load() {
        if (Files.notExists(this.path)) {
            this.init();
        }
        CommentedConfig config = new TomlParser().parse(this.path, null, StandardCharsets.UTF_8);
        this.enable = config.get("enable");
        this.helmet = config.get("helmet");
        this.chestplate = config.get("chestplate");
        this.leggings = config.get("leggings");
        this.boots = config.get("boots");
    }

    @Contract(mutates = "io")
    public void save() {
        CommentedConfig config = TomlFormat.newConfig();
        config.set("enable", this.enable);
        config.set("helmet", this.helmet);
        config.set("chestplate", this.chestplate);
        config.set("leggings", this.leggings);
        config.set("boots", this.boots);
        new TomlWriter().write(config, this.path, WritingMode.REPLACE, StandardCharsets.UTF_8);
    }
}
