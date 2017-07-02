package com.yolp900.charming.util;

import com.yolp900.charming.reference.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class SoundHandler {

    private static int index = 0;

    public static void registerSounds() {
        index = SoundEvent.REGISTRY.getKeys().size();

        for (ModSounds sound : ModSounds.values()) {
            sound.setSoundEvent(registerSound(sound));
        }
    }

    private static SoundEvent registerSound(ModSounds sound) {
        ResourceLocation loc = new ResourceLocation(Reference.MOD_ID, sound.name().toLowerCase());
        SoundEvent e = new SoundEvent(loc);

        SoundEvent.REGISTRY.register(index, loc, e);
        index++;

        return e;
    }

    public enum ModSounds {
        Ding(SoundCategory.PLAYERS), Construction(SoundCategory.BLOCKS), MagicDing(SoundCategory.PLAYERS), Elevator(SoundCategory.PLAYERS);

        private SoundCategory category;
        private SoundEvent soundEvent;

        ModSounds(SoundCategory category) {
            this.category = category;
        }

        public SoundEvent getSoundEvent() {
            return soundEvent;
        }

        public void setSoundEvent(SoundEvent soundEvent) {
            this.soundEvent = soundEvent;
        }

        public SoundCategory getCategory() {
            return category;
        }
    }

}
