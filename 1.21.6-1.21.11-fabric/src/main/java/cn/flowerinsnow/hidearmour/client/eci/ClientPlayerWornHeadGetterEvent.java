package cn.flowerinsnow.hidearmour.client.eci;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.block.SkullBlock;
import org.jetbrains.annotations.Nullable;

public interface ClientPlayerWornHeadGetterEvent {
    Event<Type> TYPE = EventFactory.createArrayBacked(Type.class, listeners -> originalType -> {
        for (Type listener : listeners) {
            SkullBlock.Type type = listener.onClientPlayerWornHeadTypeGet(originalType);
            if (type != originalType) {
                return type;
            }
        }
        return originalType;
    });

    Event<Profile> PROFILE = EventFactory.createArrayBacked(Profile.class, listeners -> originalProfile -> {
        for (Profile listener : listeners) {
            ResolvableProfile profile = listener.onClientPlayerWornHeadProfileGet(originalProfile);
            if (profile != originalProfile) {
                return profile;
            }
        }
        return originalProfile;
    });

    interface Type {
        @Nullable SkullBlock.Type onClientPlayerWornHeadTypeGet(@Nullable SkullBlock.Type originalType);
    }

    interface Profile {
        @Nullable ResolvableProfile onClientPlayerWornHeadProfileGet(@Nullable ResolvableProfile originalProfile);
    }
}
