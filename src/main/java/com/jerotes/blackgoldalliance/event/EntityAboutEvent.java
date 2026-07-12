package com.jerotes.blackgoldalliance.event;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.blackgoldalliance.block.NetherSiphonCoreEntity;
import com.jerotes.blackgoldalliance.entity.Other.NetherSiphonCoreForceEntity;
import com.jerotes.blackgoldalliance.entity.Piglin.BlackGoldPiglin.BlackGoldPiglinEntity;
import com.jerotes.blackgoldalliance.entity.Piglin.PiglinRaiderEntity;
import com.jerotes.blackgoldalliance.init.BGAMobEffects;
import com.jerotes.jerotes.util.Main;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDestroyBlockEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Mod.EventBusSubscriber(modid = BGA.MODID)
public class EntityAboutEvent {

	@SubscribeEvent
	public static void AddGoal(EntityJoinLevelEvent event) {
		Entity entity = event.getEntity();
	}

	//发现
	@SubscribeEvent
	public static void HeadVisibility(LivingEvent.LivingVisibilityEvent event) {
		LivingEntity self = event.getEntity();
		Entity lookingEntity = event.getLookingEntity();
		if (lookingEntity == null || self == null)
			return;
		{
			ItemStack itemstack = self.getItemBySlot(EquipmentSlot.HEAD);
			EntityType<?> entitytype = lookingEntity.getType();

			if ((lookingEntity instanceof BlackGoldPiglinEntity || lookingEntity instanceof PiglinRaiderEntity) && itemstack.is(Items.PIGLIN_HEAD)
			) {
				event.modifyVisibility(0.5);
			}
		}
	}

	@SubscribeEvent
	public static void DetroyBlock(LivingDestroyBlockEvent event) {
		LivingEntity self = event.getEntity();
		if (self.hasEffect(BGAMobEffects.PIGLIN_DETERRENT.get())) {
			AABB area = new AABB(event.getPos()).inflate(10);
			List<NetherSiphonCoreForceEntity> list = self.level().getEntitiesOfClass(NetherSiphonCoreForceEntity.class, area);
			if (!list.isEmpty()) {
				event.setCanceled(true);
			}
		}
	}


	//计时
	@SubscribeEvent
	public static void Tick(LivingEvent.LivingTickEvent event) {
		LivingEntity entity = event.getEntity();
		if (entity == null || !entity.isAlive())
			return;
		if (Main.getJerotesPersistentData(entity).getDouble("blackgoldalliance_piglin_omen") > 0) {
			Main.getJerotesPersistentData(entity).putDouble("blackgoldalliance_piglin_omen", Main.getJerotesPersistentData(entity).getDouble("blackgoldalliance_piglin_omen") - 1);
			if (Main.getJerotesPersistentData(entity).getDouble("blackgoldalliance_piglin_omen") == 5) {
				if (entity instanceof Player player) {
					findNearestNetherSiphonCore(player, 64).ifPresent(core -> {
						core.startChallenge((int) (Main.getJerotesPersistentData(entity).getDouble("blackgoldalliance_piglin_omen_level") + 1), player);
					});
					Main.getJerotesPersistentData(entity).putDouble("blackgoldalliance_piglin_omen", 0);
					Main.getJerotesPersistentData(entity).putDouble("blackgoldalliance_piglin_omen_level", -1);
				}
			}
		}
		else if (Main.getJerotesPersistentData(entity).getDouble("blackgoldalliance_piglin_omen_level") > -1) {
			Main.getJerotesPersistentData(entity).putDouble("blackgoldalliance_piglin_omen_level", -1);
		}
	}
	@SubscribeEvent
	public static void TickAboutDeath(LivingDeathEvent event) {
		LivingEntity entity = event.getEntity();
		if (entity == null)
			return;
		if (Main.getJerotesPersistentData(entity).getDouble("blackgoldalliance_piglin_omen") > 0) {
			Main.getJerotesPersistentData(entity).putDouble("blackgoldalliance_piglin_omen", 0);
			Main.getJerotesPersistentData(entity).putDouble("blackgoldalliance_piglin_omen_level", -1);
		}
	}
	public static Optional<NetherSiphonCoreEntity> findNearestNetherSiphonCore(Player player, int radius) {
		Level level = player.level();
		BlockPos pos = player.blockPosition();
		int r = radius;
		return BlockPos.betweenClosedStream(pos.offset(-r, -r, -r), pos.offset(r, r, r))
				.map(level::getBlockEntity)
				.filter(NetherSiphonCoreEntity.class::isInstance)
				.map(be -> (NetherSiphonCoreEntity) be)
				.filter(core -> !core.isRemoved() && !core.alreadyRaid && core.isCooldown)
				.min(Comparator.comparingDouble(core -> core.getBlockPos().distSqr(pos)));
	}
}