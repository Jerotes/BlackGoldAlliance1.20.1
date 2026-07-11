package com.jerotes.blackgoldalliance.init;

import com.jerotes.blackgoldalliance.BGA;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BGAParticleTypes {
	public static final DeferredRegister<ParticleType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, BGA.MODID);
	public static final RegistryObject<SimpleParticleType> PORTAL_POINT = REGISTRY.register("portal_point", () -> new SimpleParticleType(true));
	public static final RegistryObject<SimpleParticleType> WARPED_BREATH = REGISTRY.register("warped_breath", () -> new SimpleParticleType(true));
	public static final RegistryObject<SimpleParticleType> WARPED_BREATH_FOG = REGISTRY.register("warped_breath_fog", () -> new SimpleParticleType(true));
	public static final RegistryObject<SimpleParticleType> RAY_OF_WARPED = REGISTRY.register("ray_of_warped", () -> new SimpleParticleType(true));
	public static final RegistryObject<SimpleParticleType> NETHER_SIPHON_LINK_STATION = REGISTRY.register("nether_siphon_link_station", () -> new SimpleParticleType(true));
	public static final RegistryObject<SimpleParticleType> MARSHAL_SHOCK = REGISTRY.register("marshal_shock", () -> new SimpleParticleType(true));

	public static final RegistryObject<SimpleParticleType> CONJURE_SHAMANIC_ZOMBIE_PIGMAN_DISPLAY = REGISTRY.register("conjure_shamanic_zombie_pigman_display", () -> new SimpleParticleType(true));
	public static final RegistryObject<SimpleParticleType> EXORCISM_STOMP_DISPLAY = REGISTRY.register("exorcism_stomp_display", () -> new SimpleParticleType(true));
	public static final RegistryObject<SimpleParticleType> RAPID_IMPACT_DISPLAY = REGISTRY.register("rapid_impact_display", () -> new SimpleParticleType(true));
	public static final RegistryObject<SimpleParticleType> WARPED_BREATH_DISPLAY = REGISTRY.register("warped_breath_display", () -> new SimpleParticleType(true));
	public static final RegistryObject<SimpleParticleType> RAY_OF_WARPED_DISPLAY = REGISTRY.register("ray_of_warped_display", () -> new SimpleParticleType(true));
	public static final RegistryObject<SimpleParticleType> WARPED_BOMB_DISPLAY = REGISTRY.register("warped_bomb_display", () -> new SimpleParticleType(true));
	public static final RegistryObject<SimpleParticleType> BLACK_GOLD_GROUND_CRACK_DISPLAY = REGISTRY.register("black_gold_ground_crack_display", () -> new SimpleParticleType(true));
	public static final RegistryObject<SimpleParticleType> BLACK_GOLD_ORDER_DISPLAY = REGISTRY.register("black_gold_order_display", () -> new SimpleParticleType(true));

}
