package com.jerotes.blackgoldalliance.init;

import com.jerotes.blackgoldalliance.BGA;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.NewRegistryEvent;

import java.lang.reflect.Field;

@SuppressWarnings("WeakerAccess")
@Mod.EventBusSubscriber(modid = BGA.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BGASoundEvents {

    public static final SoundEvent NETHER_SIPHON_CORE_START = createSoundEvent("nether_siphon_core_start");
    public static final SoundEvent NETHER_SIPHON_CORE_STOP = createSoundEvent("nether_siphon_core_stop");

    public static final SoundEvent BLACK_GOLD_BRUISER_ATTACK_1 = createSoundEvent("black_gold_bruiser_attack_1");
    public static final SoundEvent BLACK_GOLD_BRUISER_ATTACK_2 = createSoundEvent("black_gold_bruiser_attack_2");
    public static final SoundEvent BLACK_GOLD_BRUISER_WALK = createSoundEvent("black_gold_bruiser_walk");
    public static final SoundEvent BLACK_GOLD_BRUISER_STOMP = createSoundEvent("black_gold_bruiser_stomp");
    public static final SoundEvent BLACK_GOLD_BRUISER_RUSH = createSoundEvent("black_gold_bruiser_rush");
    public static final SoundEvent THE_BLACK_GOLD_MARSHAL_VOICE = createSoundEvent("the_black_gold_marshal_voice");
    public static final SoundEvent THE_BLACK_GOLD_MARSHAL_WALK = createSoundEvent("the_black_gold_marshal_walk");
    public static final SoundEvent THE_BLACK_GOLD_MARSHAL_HURT = createSoundEvent("the_black_gold_marshal_hurt");
    public static final SoundEvent THE_BLACK_GOLD_MARSHAL_DEATH = createSoundEvent("the_black_gold_marshal_death");
    public static final SoundEvent THE_BLACK_GOLD_MARSHAL_SWING = createSoundEvent("the_black_gold_marshal_swing");
    public static final SoundEvent THE_BLACK_GOLD_MARSHAL_HIT = createSoundEvent("the_black_gold_marshal_hit");
    public static final SoundEvent THE_BLACK_GOLD_MARSHAL_BLOCK = createSoundEvent("the_black_gold_marshal_block");
    public static final SoundEvent THE_BLACK_GOLD_MARSHAL_SHOOT = createSoundEvent("the_black_gold_marshal_shoot");
    public static final SoundEvent THE_BLACK_GOLD_MARSHAL_SUMMON = createSoundEvent("the_black_gold_marshal_summon");
    public static final SoundEvent THE_BLACK_GOLD_MARSHAL_BLOCKING = createSoundEvent("the_black_gold_marshal_blocking");

    public static final SoundEvent MAGIC_WARPED_BREATH = createSoundEvent("magic_warped_breath");

    public static final SoundEvent MAGIC_CONJURE_SHAMANIC_ZOMBIE_PIGMAN = createSoundEvent("magic_conjure_shamanic_zombie_pigman");
    public static final SoundEvent MAGIC_RAY_OF_WARPED = createSoundEvent("magic_ray_of_warped");
    public static final SoundEvent MAGIC_WARPED_BOMB = createSoundEvent("magic_warped_bomb");

    private static SoundEvent createSoundEvent(final String soundName) {
        final ResourceLocation soundID = new ResourceLocation(BGA.MODID, soundName);
        return SoundEvent.createVariableRangeEvent(soundID);
    }

    @SubscribeEvent
    public static void registerSoundEvents(final NewRegistryEvent event) {
        try {
            for (Field f : BGASoundEvents.class.getFields()) {
                Object obj = f.get(null);
                if (obj instanceof SoundEvent) {
                    ForgeRegistries.SOUND_EVENTS.register(((SoundEvent) obj).getLocation(), (SoundEvent) obj);
                } else if (obj instanceof SoundEvent[]) {
                    for (SoundEvent soundEvent : (SoundEvent[]) obj) {
                        ForgeRegistries.SOUND_EVENTS.register(soundEvent.getLocation(), soundEvent);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}

