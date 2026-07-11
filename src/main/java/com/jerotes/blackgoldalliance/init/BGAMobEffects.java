package com.jerotes.blackgoldalliance.init;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.effect.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BGAMobEffects {
	public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, BGA.MODID);
	public static final RegistryObject<MobEffect> PIGLIN_OMEN = REGISTRY.register("piglin_omen", () -> new PiglinOmenMobEffect());
	public static final RegistryObject<MobEffect> BLACK_GOLD_OMEN = REGISTRY.register("black_gold_omen", () -> new BlackGoldOmenMobEffect());
	public static final RegistryObject<MobEffect> PIGLIN_DETERRENT = REGISTRY.register("piglin_deterrent", () -> new PiglinDeterrentMobEffect()
			.addAttributeModifier(Attributes.MOVEMENT_SPEED, "bf211a33-5d21-497e-9410-dddeeec8eeb1", -0.03, AttributeModifier.Operation.MULTIPLY_TOTAL));
	public static final RegistryObject<MobEffect> PIGLIN_BLESSING = REGISTRY.register("piglin_blessing", () -> new PiglinBlessingMobEffect());
	public static final RegistryObject<MobEffect> STEPPER_TOUCHED = REGISTRY.register("stepper_touched", () -> new StepperTouchedMobEffect()
			.addAttributeModifier(ForgeMod.ENTITY_REACH.get(), "552518a3-8bb0-42a4-8904-506e00f3106a", 6.0, AttributeModifier.Operation.ADDITION)
			.addAttributeModifier(ForgeMod.BLOCK_REACH.get(), "a5093b10-47ae-4a1d-9293-1809da43911a", 1.0, AttributeModifier.Operation.ADDITION));
}