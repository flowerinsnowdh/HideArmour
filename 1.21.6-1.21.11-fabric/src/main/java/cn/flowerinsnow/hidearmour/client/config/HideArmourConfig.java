package cn.flowerinsnow.hidearmour.client.config;

import cn.flowerinsnow.hidearmour.client.HideArmour;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.CrashReport;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.Contract;
import tools.jackson.core.json.JsonReadFeature;
import tools.jackson.core.json.JsonWriteFeature;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Environment(EnvType.CLIENT)
public class HideArmourConfig {
    private ObjectNode root;

    private static final JsonMapper JSON_MAPPER = JsonMapper.builder()
            .enable(JsonReadFeature.ALLOW_JAVA_COMMENTS, JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, JsonReadFeature.ALLOW_SINGLE_QUOTES,
                    JsonReadFeature.ALLOW_UNQUOTED_PROPERTY_NAMES
            ).enable(SerializationFeature.INDENT_OUTPUT)
            .disable(JsonWriteFeature.QUOTE_PROPERTY_NAMES)
            .build();

    private static final String FIELD_NAME_VERSION = "version";
    private static final int VERSION = 2;
    private static final String FIELD_NAME_ENABLE = "enable";
    private static final String FIELD_NAME_HEAD = "head";
    private static final String FIELD_NAME_HEAD_HELMET = "helmet";
    private static final String FIELD_NAME_HEAD_SKULL = "skull";
    private static final String FIELD_NAME_HEAD_BLOCK = "block";
    private static final String FIELD_NAME_CHEST = "chest";
    private static final String FIELD_NAME_CHEST_CHESTPLATE = "chestplate";
    private static final String FIELD_NAME_CHEST_ELYTRA = "elytra";
    private static final String FIELD_NAME_LEGGINGS = "leggings";
    private static final String FIELD_NAME_BOOTS = "boots";

    private HideArmourConfig() {
    }

    @Contract("-> new")
    public static HideArmourConfig create() {
        return new HideArmourConfig();
    }

    @Contract(value = "-> new", pure = true)
    private static Path getConfigPath() {
        return FabricLoader.getInstance().getConfigDir().resolve(HideArmour.MODID + ".json5");
    }

    @Contract(mutates = "io,this")
    public void init() {
        try {
            Files.deleteIfExists(FabricLoader.getInstance().getConfigDir().resolve(HideArmour.MODID + ".toml"));
        } catch (IOException ignored) {
        }

        final Path configPath = getConfigPath();
        if (!Files.exists(configPath)) {
            this.root = JSON_MAPPER.createObjectNode();
            this.root.put(FIELD_NAME_VERSION, VERSION);
            this.root.put(FIELD_NAME_ENABLE, true);
            ObjectNode headNode = JSON_MAPPER.createObjectNode();
            headNode.put(FIELD_NAME_HEAD_HELMET, true);
            headNode.put(FIELD_NAME_HEAD_SKULL, true);
            headNode.put(FIELD_NAME_HEAD_BLOCK, true);
            this.root.set(FIELD_NAME_HEAD, headNode);
            ObjectNode chestNode = JSON_MAPPER.createObjectNode();
            chestNode.put(FIELD_NAME_CHEST_CHESTPLATE, true);
            chestNode.put(FIELD_NAME_CHEST_ELYTRA, true);
            this.root.set(FIELD_NAME_CHEST, chestNode);
            this.root.put(FIELD_NAME_LEGGINGS, true);
            this.root.put(FIELD_NAME_BOOTS, true);

            this.save();
        } else if (!Files.isRegularFile(configPath)) {
            IOException ex = new IOException("config file " + getConfigPath() + " is not a regular file.");
            Minecraft.getInstance().emergencySaveAndCrash(CrashReport.forThrowable(ex, ex.getMessage()));
        } else {
            this.root = ((ObjectNode) JSON_MAPPER.readTree(configPath));
            if (!this.root.has(FIELD_NAME_VERSION) || this.root.get(FIELD_NAME_VERSION).asInt() != VERSION) {
                try {
                    Files.delete(configPath);
                } catch (IOException e) {
                    Minecraft.getInstance().emergencySaveAndCrash(CrashReport.forThrowable(e, "unable to delete old config file."));
                    return;
                }
                this.init();
            }
        }
    }

    @Contract(mutates = "io")
    public void save() {
        JSON_MAPPER.writeValue(getConfigPath(), this.root);
    }

    @Contract(pure = true)
    public boolean enable() {
        return this.root.get(FIELD_NAME_ENABLE).asBoolean();
    }

    @SuppressWarnings("UnusedReturnValue")
    @Contract(value = "_ -> this", mutates = "this")
    public HideArmourConfig enable(boolean enable) {
        this.root.put(FIELD_NAME_ENABLE, enable);
        return this;
    }

    @Contract(mutates = "this")
    public boolean invertEnable() {
        boolean invert = !this.enable();
        this.enable(invert);
        return invert;
    }

    @Contract(pure = true)
    public boolean headHelmet() {
        return this.root.get(FIELD_NAME_HEAD).get(FIELD_NAME_HEAD_HELMET).asBoolean();
    }

    @SuppressWarnings("UnusedReturnValue")
    @Contract(value = "_ -> this", mutates = "this")
    public HideArmourConfig headHelmet(boolean helmet) {
        ((ObjectNode) this.root.get(FIELD_NAME_HEAD)).put(FIELD_NAME_HEAD_HELMET, helmet);
        return this;
    }

    @Contract(mutates = "this")
    public boolean invertHeadHelmet() {
        boolean invert = !this.headHelmet();
        this.headHelmet(invert);
        return invert;
    }

    @Contract(pure = true)
    public boolean headSkull() {
        return this.root.get(FIELD_NAME_HEAD).get(FIELD_NAME_HEAD_SKULL).asBoolean();
    }

    @SuppressWarnings("UnusedReturnValue")
    @Contract(value = "_ -> this", mutates = "this")
    public HideArmourConfig headSkull(boolean headSkull) {
        ((ObjectNode) this.root.get(FIELD_NAME_HEAD)).put(FIELD_NAME_HEAD_SKULL, headSkull);
        return this;
    }

    @Contract(mutates = "this")
    public boolean invertHeadSkull() {
        boolean invert = !this.headSkull();
        this.headSkull(invert);
        return invert;
    }

    @Contract(pure = true)
    public boolean headBlock() {
        return this.root.get(FIELD_NAME_HEAD).get(FIELD_NAME_HEAD_BLOCK).asBoolean();
    }

    @SuppressWarnings("UnusedReturnValue")
    @Contract(value = "_ -> this", mutates = "this")
    public HideArmourConfig headBlock(boolean headBlock) {
        ((ObjectNode) this.root.get(FIELD_NAME_HEAD)).put(FIELD_NAME_HEAD_BLOCK, headBlock);
        return this;
    }

    @Contract(mutates = "this")
    public boolean invertHeadBlock() {
        boolean invert = !this.headBlock();
        this.headBlock(invert);
        return invert;
    }

    @Contract(pure = true)
    public boolean chestChestplate() {
        return this.root.get(FIELD_NAME_CHEST).get(FIELD_NAME_CHEST_CHESTPLATE).asBoolean();
    }

    @SuppressWarnings("UnusedReturnValue")
    @Contract(value = "_ -> this", mutates = "this")
    public HideArmourConfig chestChestplate(boolean chestChestplate) {
        ((ObjectNode) this.root.get(FIELD_NAME_CHEST)).put(FIELD_NAME_CHEST_CHESTPLATE, chestChestplate);
        return this;
    }

    @Contract(mutates = "this")
    public boolean invertChestChestplate() {
        boolean invert = !this.chestChestplate();
        this.chestChestplate(invert);
        return invert;
    }

    @Contract(pure = true)
    public boolean chestElytra() {
        return this.root.get(FIELD_NAME_CHEST).get(FIELD_NAME_CHEST_ELYTRA).asBoolean();
    }

    @SuppressWarnings("UnusedReturnValue")
    @Contract(value = "_ -> this", mutates = "this")
    public HideArmourConfig chestElytra(boolean chestElytra) {
        ((ObjectNode) this.root.get(FIELD_NAME_CHEST)).put(FIELD_NAME_CHEST_ELYTRA, chestElytra);
        return this;
    }

    @Contract(mutates = "this")
    public boolean invertChestElytra() {
        boolean invert = !this.chestElytra();
        this.chestElytra(invert);
        return invert;
    }

    @Contract(pure = true)
    public boolean leggings() {
        return this.root.get(FIELD_NAME_LEGGINGS).asBoolean();
    }

    @SuppressWarnings("UnusedReturnValue")
    @Contract(value = "_ -> this", mutates = "this")
    public HideArmourConfig leggings(boolean leggings) {
        this.root.put(FIELD_NAME_LEGGINGS, leggings);
        return this;
    }

    @Contract(mutates = "this")
    public boolean invertLeggings() {
        boolean invert = !this.leggings();
        this.leggings(invert);
        return invert;
    }

    @Contract(pure = true)
    public boolean boots() {
        return this.root.get(FIELD_NAME_BOOTS).asBoolean();
    }

    @SuppressWarnings("UnusedReturnValue")
    @Contract(value = "_ -> this", mutates = "this")
    public HideArmourConfig boots(boolean boots) {
        this.root.put(FIELD_NAME_BOOTS, boots);
        return this;
    }

    @Contract(mutates = "this")
    public boolean invertBoots() {
        boolean invert = !this.boots();
        this.boots(invert);
        return invert;
    }
}
