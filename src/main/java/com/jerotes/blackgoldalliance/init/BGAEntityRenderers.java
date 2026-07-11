package com.jerotes.blackgoldalliance.init;

import com.jerotes.blackgoldalliance.client.renderer.*;
import com.jerotes.jerotes.client.renderer.RayRenderer;
import com.jerotes.jerotes.client.renderer.ShootRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BGAEntityRenderers {

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(BGABlockEntityType.NETHER_SIPHON_LINK_STATION.get(), NetherSiphonLinkStationRenderer::new);

        event.registerEntityRenderer(BGAEntityType.PIGLIN_RAID_NETHER_PORTAL.get(), PiglinRaidNetherPortalRenderer::new);
        event.registerEntityRenderer(BGAEntityType.NETHER_SIPHON_CORE_FORCE.get(), NetherSiphonCoreForceRenderer::new);
        event.registerEntityRenderer(BGAEntityType.PORTAL_POINT.get(), PortalPointRenderer::new);

        event.registerEntityRenderer(BGAEntityType.PIGLIN_RAIDER.get(), PiglinRaiderRenderer::new);
        event.registerEntityRenderer(BGAEntityType.PIGLIN_RAIDER_WARRIOR.get(), PiglinRaiderRenderer::new);
        event.registerEntityRenderer(BGAEntityType.PIGLIN_RAIDER_HUNTER.get(), PiglinRaiderRenderer::new);
        event.registerEntityRenderer(BGAEntityType.PIGLIN_RAIDER_BRUTE.get(), PiglinRaiderRenderer::new);
        event.registerEntityRenderer(BGAEntityType.PIGLIN_RAIDER_HOGLIN.get(), PiglinRaiderHoglinRenderer::new);
        event.registerEntityRenderer(BGAEntityType.SHAMANIC_ZOMBIE_PIGMAN.get(), ZombiePigmanRenderer::new);
        event.registerEntityRenderer(BGAEntityType.BLACK_GOLD_PIGLIN.get(), BlackGoldPiglinRenderer::new);
        event.registerEntityRenderer(BGAEntityType.BLACK_GOLD_RUNT.get(), BlackGoldRuntRenderer::new);
        event.registerEntityRenderer(BGAEntityType.BLACK_GOLD_RECRUIT.get(), BlackGoldRuntRenderer::new);
        event.registerEntityRenderer(BGAEntityType.BLACK_GOLD_THUMPER.get(), BlackGoldRuntRenderer::new);
        event.registerEntityRenderer(BGAEntityType.BLACK_GOLD_WARRIOR.get(), BlackGoldPiglinRenderer::new);;
        event.registerEntityRenderer(BGAEntityType.BLACK_GOLD_MAULER.get(), BlackGoldPiglinRenderer::new);
        event.registerEntityRenderer(BGAEntityType.BLACK_GOLD_HUNTER.get(), BlackGoldPiglinRenderer::new);
        event.registerEntityRenderer(BGAEntityType.BLACK_GOLD_CAVALRY.get(), BlackGoldPiglinRenderer::new);
        event.registerEntityRenderer(BGAEntityType.BLACK_GOLD_WAR_HOGLIN.get(), BlackGoldWarHoglinRenderer::new);
        event.registerEntityRenderer(BGAEntityType.BLACK_GOLD_BRUISER.get(), BlackGoldBruiserRenderer::new);
        event.registerEntityRenderer(BGAEntityType.BLACK_GOLD_BUTCHER.get(), BlackGoldButcherRenderer::new);
        event.registerEntityRenderer(BGAEntityType.BLACK_GOLD_SHAMAN.get(), BlackGoldShamanRenderer::new);
        event.registerEntityRenderer(BGAEntityType.BLACK_GOLD_STEPPER.get(), BlackGoldStepperRenderer::new);
        event.registerEntityRenderer(BGAEntityType.BLACK_GOLD_STEPPER_SQUAD.get(), BlackGoldStepperRenderer::new);
        event.registerEntityRenderer(BGAEntityType.THE_BLACK_GOLD_MARSHAL.get(), TheBlackGoldMarshalRenderer::new);

        event.registerEntityRenderer(BGAEntityType.OATH_WITHER_SKELETON.get(), OathWitherSkeletonRenderer::new);
        event.registerEntityRenderer(BGAEntityType.OATH_WITHER_SQUIRE.get(), OathWitherSkeletonRenderer::new);
        event.registerEntityRenderer(BGAEntityType.OATH_CONSTRUCT_SPIDER.get(), OathConstructSpiderRenderer::new);
        event.registerEntityRenderer(BGAEntityType.OATH_CONSTRUCT_SPIDER_JOCKEY.get(), OathWitherSkeletonRenderer::new);

        event.registerEntityRenderer(BGAEntityType.BLACK_GOLD_SPECTRAL_ARROW.get(), BlackGoldSpectralArrowRenderer::new);

        event.registerEntityRenderer(BGAEntityType.WARPED_BREATH.get(), ShootRenderer::new);
        event.registerEntityRenderer(BGAEntityType.RAY_OF_WARPED.get(), RayRenderer::new);
        event.registerEntityRenderer(BGAEntityType.WARPED_BOMB.get(), WarpedBombRenderer::new);
    }
}
