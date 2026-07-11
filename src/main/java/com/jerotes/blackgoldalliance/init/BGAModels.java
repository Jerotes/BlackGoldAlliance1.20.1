package com.jerotes.blackgoldalliance.init;

import com.jerotes.blackgoldalliance.client.model.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class BGAModels {
	@SubscribeEvent
	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(Modelpiglin_raid_nether_portal.LAYER_LOCATION, Modelpiglin_raid_nether_portal::createBodyLayer);
		event.registerLayerDefinition(Modelnether_siphon_core_force.LAYER_LOCATION, Modelnether_siphon_core_force::createBodyLayer);
		event.registerLayerDefinition(Modelpiglin_raider.LAYER_LOCATION, Modelpiglin_raider::createBodyLayer);
		event.registerLayerDefinition(Modelpiglin_raider_hoglin.LAYER_LOCATION, Modelpiglin_raider_hoglin::createBodyLayer);
		event.registerLayerDefinition(Modelpiglin_raider_hoglin_armor.LAYER_LOCATION, Modelpiglin_raider_hoglin_armor::createBodyLayer);
		event.registerLayerDefinition(Modelblack_gold_piglin.LAYER_LOCATION, Modelblack_gold_piglin::createBodyLayer);
		event.registerLayerDefinition(Modelblack_gold_runt.LAYER_LOCATION, Modelblack_gold_runt::createBodyLayer);
		event.registerLayerDefinition(Modelblack_gold_runt_armor.LAYER_LOCATION, Modelblack_gold_runt_armor::createBodyLayer);
		event.registerLayerDefinition(Modelblack_gold_warrior_armor.LAYER_LOCATION, Modelblack_gold_warrior_armor::createBodyLayer);
		event.registerLayerDefinition(Modelblack_gold_mauler_armor.LAYER_LOCATION, Modelblack_gold_mauler_armor::createBodyLayer);
		event.registerLayerDefinition(Modelblack_gold_hunter_armor.LAYER_LOCATION, Modelblack_gold_hunter_armor::createBodyLayer);
		event.registerLayerDefinition(Modelblack_gold_cavalry_armor.LAYER_LOCATION, Modelblack_gold_cavalry_armor::createBodyLayer);
		event.registerLayerDefinition(Modelblack_gold_war_hoglin.LAYER_LOCATION, Modelblack_gold_war_hoglin::createBodyLayer);
		event.registerLayerDefinition(Modelblack_gold_war_hoglin_armor.LAYER_LOCATION, Modelblack_gold_war_hoglin_armor::createBodyLayer);
		event.registerLayerDefinition(Modelblack_gold_bruiser.LAYER_LOCATION, Modelblack_gold_bruiser::createBodyLayer);
		event.registerLayerDefinition(Modelblack_gold_butcher.LAYER_LOCATION, Modelblack_gold_butcher::createBodyLayer);
		event.registerLayerDefinition(Modelblack_gold_shaman.LAYER_LOCATION, Modelblack_gold_shaman::createBodyLayer);
		event.registerLayerDefinition(Modelzombie_pigman.LAYER_LOCATION, Modelzombie_pigman::createBodyLayer);
		event.registerLayerDefinition(Modelwarped_bomb.LAYER_LOCATION, Modelwarped_bomb::createBodyLayer);
		event.registerLayerDefinition(Modelblack_gold_stepper.LAYER_LOCATION, Modelblack_gold_stepper::createBodyLayer);
		event.registerLayerDefinition(Modelthe_black_gold_marshal.LAYER_LOCATION, Modelthe_black_gold_marshal::createBodyLayer);
		event.registerLayerDefinition(Modelthe_black_gold_marshal_armor.LAYER_LOCATION, Modelthe_black_gold_marshal_armor::createBodyLayer);
		event.registerLayerDefinition(Modeloath_wither_skeleton.LAYER_LOCATION, Modeloath_wither_skeleton::createBodyLayer);
		event.registerLayerDefinition(Modeloath_wither_squire_armor.LAYER_LOCATION, Modeloath_wither_squire_armor::createBodyLayer);
		event.registerLayerDefinition(Modeloath_construct_spider.LAYER_LOCATION, Modeloath_construct_spider::createBodyLayer);
	}
}
