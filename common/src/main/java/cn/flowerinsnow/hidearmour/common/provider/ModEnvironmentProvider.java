package cn.flowerinsnow.hidearmour.common.provider;

import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.nio.file.Path;

public interface ModEnvironmentProvider {
    @NotNull InputStream getDefaultConfigAsStream();

    @NotNull Path getConfigFile();

    void crash(@NotNull Throwable throwable, @NotNull String msg);
}
