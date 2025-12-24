package cn.flowerinsnow.hidearmour.client.eci;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.SkullBlock;
import net.minecraft.component.type.ProfileComponent;
import org.jetbrains.annotations.Nullable;

public interface ClientPlayerSkullHeadGetterEvent {
    Event<Type> TYPE = EventFactory.createArrayBacked(Type.class, listeners -> originalType -> {
        for (Type listener : listeners) {
            SkullBlock.SkullType type = listener.onClientPlayerWornHeadTypeGet(originalType);
            if (type != originalType) {
                return type;
            }
        }
        return originalType;
    });

    Event<Profile> PROFILE = EventFactory.createArrayBacked(Profile.class, listeners -> originalProfile -> {
        for (Profile listener : listeners) {
            ProfileComponent profile = listener.onClientPlayerWornHeadProfileGet(originalProfile);
            if (profile != originalProfile) {
                return profile;
            }
        }
        return originalProfile;
    });

    interface Type {
        @Nullable SkullBlock.SkullType onClientPlayerWornHeadTypeGet(@Nullable SkullBlock.SkullType originalType);
    }

    interface Profile {
        @Nullable ProfileComponent onClientPlayerWornHeadProfileGet(@Nullable ProfileComponent originalProfile);
    }
}
