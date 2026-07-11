package com.jerotes.blackgoldalliance.network;

import com.jerotes.blackgoldalliance.BGA;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = BGA.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BGAPlayerData {
	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
	}
	@SubscribeEvent
	public static void init(RegisterCapabilitiesEvent event) {
		event.register(PlayerVariables.class);
	}

	@Mod.EventBusSubscriber
	public static class EventBusVariableHandlers {
		@SubscribeEvent
		public static void onPlayerLoggedInSyncPlayerVariables(PlayerEvent.PlayerLoggedInEvent event) {
			if (!event.getEntity().level().isClientSide())
				(event.getEntity().getCapability(CAPABILITY, null).orElse(new PlayerVariables())).syncPlayerVariables(event.getEntity());
		}

		@SubscribeEvent
		public static void onPlayerRespawnedSyncPlayerVariables(PlayerEvent.PlayerRespawnEvent event) {
			if (!event.getEntity().level().isClientSide())
				(event.getEntity().getCapability(CAPABILITY, null).orElse(new PlayerVariables())).syncPlayerVariables(event.getEntity());
		}

		@SubscribeEvent
		public static void onPlayerChangedDimensionSyncPlayerVariables(PlayerEvent.PlayerChangedDimensionEvent event) {
			if (!event.getEntity().level().isClientSide())
				(event.getEntity().getCapability(CAPABILITY, null).orElse(new PlayerVariables())).syncPlayerVariables(event.getEntity());
		}

		@SubscribeEvent
		public static void clonePlayer(PlayerEvent.Clone event) {
			event.getOriginal().revive();
			PlayerVariables original = (event.getOriginal().getCapability(CAPABILITY, null).orElse(new PlayerVariables()));
			PlayerVariables clone = (event.getEntity().getCapability(CAPABILITY, null).orElse(new PlayerVariables()));

			//击败猪灵袭击最大等级
			clone.DefeatTheHighestLevelPiglinRaid = original.DefeatTheHighestLevelPiglinRaid;
//			if (!event.isWasDeath()) {
//			}
		}
	}

	public static final Capability<PlayerVariables> CAPABILITY = CapabilityManager.get(new CapabilityToken<PlayerVariables>() {
	});

	@Mod.EventBusSubscriber
	private static class PlayerVariablesProvider implements ICapabilitySerializable<Tag> {
		@SubscribeEvent
		public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
			if (event.getObject() instanceof Player)
				event.addCapability(new ResourceLocation(BGA.MODID, "player_variables"), new PlayerVariablesProvider());
		}

		private final PlayerVariables playerVariables = new PlayerVariables();
		private final LazyOptional<PlayerVariables> instance = LazyOptional.of(() -> playerVariables);

		@Override
		public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
			return cap == CAPABILITY ? instance.cast() : LazyOptional.empty();
		}

		@Override
		public Tag serializeNBT() {
			return playerVariables.writeNBT();
		}

		@Override
		public void deserializeNBT(Tag nbt) {
			playerVariables.readNBT(nbt);
		}
	}

	public static class PlayerVariables {
		//击败猪灵袭击最大等级
		public int DefeatTheHighestLevelPiglinRaid = 0;
		public void setDefeatTheHighestLevelPiglinRaid(int n){
			this.DefeatTheHighestLevelPiglinRaid = n;
		}

		public void syncPlayerVariables(Entity entity) {
			if (!entity.level().isClientSide() && entity instanceof ServerPlayer serverPlayer) {
				if (this.writeNBT() != null) {
					OtherPacketHandler.NETWORK_WRAPPER.sendTo(
							new PlayerVariablesSyncMessage(this.writeNBT()), serverPlayer.connection.connection,
							NetworkDirection.PLAY_TO_CLIENT);
				}
			}
		}


		public CompoundTag writeNBT() {
			CompoundTag nbt = new CompoundTag();
			//击败猪灵袭击最大等级
			nbt.putInt("BlackGoldAllianceDefeatTheHighestLevelPiglinRaid", DefeatTheHighestLevelPiglinRaid);
			return nbt;
		}

		public void readNBT(Tag tag) {
			CompoundTag nbt = (CompoundTag) tag;
			//击败猪灵袭击最大等级
			DefeatTheHighestLevelPiglinRaid = nbt.getInt("BlackGoldAllianceDefeatTheHighestLevelPiglinRaid");
		}
	}

	public static class PlayerVariablesSyncMessage {
		private final CompoundTag data;
		public PlayerVariablesSyncMessage(CompoundTag nbt) {
			this.data = nbt;
		}
		public static void encode(PlayerVariablesSyncMessage message, FriendlyByteBuf buffer) {
			buffer.writeNbt(message.data);
		}
		public static PlayerVariablesSyncMessage decode(FriendlyByteBuf buffer) {
			return new PlayerVariablesSyncMessage(buffer.readNbt());
		}
		public static void consume(PlayerVariablesSyncMessage message, Supplier<NetworkEvent.Context> context) {
			context.get().enqueueWork(() -> {
				Minecraft minecraft = Minecraft.getInstance();
				if (minecraft.player != null) {
					minecraft.player.getCapability(CAPABILITY).ifPresent(cap -> {
						cap.readNBT(message.data);
					});
				}
			});
			context.get().setPacketHandled(true);
		}
	}
}
