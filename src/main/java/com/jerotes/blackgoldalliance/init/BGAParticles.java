package com.jerotes.blackgoldalliance.init;

import com.jerotes.blackgoldalliance.client.particle.*;
import com.jerotes.jerotes.client.particle.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BGAParticles {
	@SubscribeEvent
	public static void registerParticles(RegisterParticleProvidersEvent event) {
		event.registerSpriteSet(BGAParticleTypes.PORTAL_POINT.get(), ShootParticle::provider);
		event.registerSpriteSet(BGAParticleTypes.WARPED_BREATH.get(), ShootParticle::provider);
		event.registerSpriteSet(BGAParticleTypes.WARPED_BREATH_FOG.get(), FogParticle::provider);
		event.registerSpriteSet(BGAParticleTypes.RAY_OF_WARPED.get(), RayParticle::provider);
		event.registerSpriteSet(BGAParticleTypes.NETHER_SIPHON_LINK_STATION.get(), ShockwaveParticle.Provider::new);
		event.registerSpriteSet(BGAParticleTypes.MARSHAL_SHOCK.get(), ShootParticle::provider);

		event.registerSpriteSet(BGAParticleTypes.CONJURE_SHAMANIC_ZOMBIE_PIGMAN_DISPLAY.get(), DisplayParticle::provider);
		event.registerSpriteSet(BGAParticleTypes.EXORCISM_STOMP_DISPLAY.get(), DisplayParticle::provider);
		event.registerSpriteSet(BGAParticleTypes.RAPID_IMPACT_DISPLAY.get(), DisplayParticle::provider);
		event.registerSpriteSet(BGAParticleTypes.WARPED_BREATH_DISPLAY.get(), DisplayParticle::provider);
		event.registerSpriteSet(BGAParticleTypes.RAY_OF_WARPED_DISPLAY.get(), DisplayParticle::provider);
		event.registerSpriteSet(BGAParticleTypes.WARPED_BOMB_DISPLAY.get(), DisplayParticle::provider);
		event.registerSpriteSet(BGAParticleTypes.BLACK_GOLD_GROUND_CRACK_DISPLAY.get(), DisplayParticle::provider);
		event.registerSpriteSet(BGAParticleTypes.BLACK_GOLD_ORDER_DISPLAY.get(), DisplayParticle::provider);
	}
}
