package com.jerotes.blackgoldalliance.init;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.block.NetherSiphonCoreEntity;
import com.jerotes.blackgoldalliance.block.NetherSiphonLinkStationEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class BGABlockEntityType {
	public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, BGA.MODID);

	public static final RegistryObject<BlockEntityType<NetherSiphonCoreEntity>> NETHER_SIPHON_CORE = REGISTRY.register("nether_siphon_core",
			() -> BlockEntityType.Builder.of(NetherSiphonCoreEntity::new, BGABlocks.NETHER_SIPHON_CORE.get()).build(null));
	public static final RegistryObject<BlockEntityType<NetherSiphonLinkStationEntity>> NETHER_SIPHON_LINK_STATION = REGISTRY.register("nether_siphon_link_station",
			() -> BlockEntityType.Builder.of(NetherSiphonLinkStationEntity::new, BGABlocks.NETHER_SIPHON_LINK_STATION.get()).build(null));

}
