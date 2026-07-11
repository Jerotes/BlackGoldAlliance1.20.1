package com.jerotes.blackgoldalliance.item;

import com.jerotes.blackgoldalliance.entity.Animal.BlackGoldStepperEntity;
import com.jerotes.blackgoldalliance.entity.Animal.BlackGoldWarHoglinEntity;
import com.jerotes.blackgoldalliance.entity.Animal.PiglinRaiderHoglinEntity;
import com.jerotes.blackgoldalliance.init.BGAEntityType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.gameevent.GameEvent;

public class BabyItem extends Item {
	public final int entityType;
	public BabyItem(int n) {
		super(new Properties().stacksTo(1).rarity(Rarity.COMMON).fireResistant());
		entityType = n;
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		super.useOn(context);
		if (context.getLevel() instanceof ServerLevel serverLevel && context.getPlayer() != null) {
			PathfinderMob pathfinderMob;
			if (entityType == 0) {
				pathfinderMob = new BlackGoldWarHoglinEntity(BGAEntityType.BLACK_GOLD_WAR_HOGLIN.get(), serverLevel);
				pathfinderMob.setBaby(true);
				((BlackGoldWarHoglinEntity)pathfinderMob).tame(context.getPlayer());
				pathfinderMob.getNavigation().stop();
				pathfinderMob.setTarget(null);
				((BlackGoldWarHoglinEntity)pathfinderMob).setChangeType(1, context.getPlayer());
				context.getLevel().broadcastEntityEvent(context.getPlayer(), (byte)7);
			}
			else if (entityType == 1) {
				pathfinderMob = new PiglinRaiderHoglinEntity(BGAEntityType.PIGLIN_RAIDER_HOGLIN.get(), serverLevel);
				pathfinderMob.setBaby(true);
				((PiglinRaiderHoglinEntity)pathfinderMob).tame(context.getPlayer());
				pathfinderMob.getNavigation().stop();
				pathfinderMob.setTarget(null);
				((PiglinRaiderHoglinEntity)pathfinderMob).setChangeType(1, context.getPlayer());
				context.getLevel().broadcastEntityEvent(context.getPlayer(), (byte)7);
			}
			else {
				pathfinderMob = new BlackGoldStepperEntity(BGAEntityType.BLACK_GOLD_STEPPER.get(), serverLevel);
				pathfinderMob.setBaby(true);
				((BlackGoldStepperEntity)pathfinderMob).tame(context.getPlayer());
				pathfinderMob.getNavigation().stop();
				pathfinderMob.setTarget(null);
				((BlackGoldStepperEntity)pathfinderMob).setChangeType(1, context.getPlayer());
				context.getLevel().broadcastEntityEvent(context.getPlayer(), (byte)7);
			}
			pathfinderMob.moveTo((context.getClickedPos().getX() + 0.5), (context.getClickedPos().getY() + 1), (context.getClickedPos().getZ() + 0.5), context.getLevel().getRandom().nextFloat() * 360F, 0);
			pathfinderMob.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(pathfinderMob.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
			serverLevel.addFreshEntity(pathfinderMob);
			if (context.getPlayer() != null) {
				serverLevel.gameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, context.getClickedPos());
				context.getPlayer().awardStat(Stats.ITEM_USED.get(this));
				if (!context.getPlayer().getAbilities().instabuild) {
					context.getItemInHand().shrink(1);
				}
			}
			else {
				serverLevel.gameEvent(null, GameEvent.ENTITY_PLACE, context.getClickedPos());
			}
		}
		return InteractionResult.SUCCESS;
	}
//
//	@Override
//	public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
//		super.appendHoverText(itemStack, level, list, tooltipFlag);
//		list.add(this.getDisplayName_0().withStyle(ChatFormatting.GRAY));
//		list.add(this.getDisplayName_1().withStyle(ChatFormatting.GRAY));
//		list.add(this.getDisplayName_2().withStyle(ChatFormatting.GRAY));
//	}
//	public MutableComponent getDisplayName_0() {
//		return Component.translatable(this.getDescriptionId() + ".desc_0");
//	}
//	public MutableComponent getDisplayName_1() {
//		return Component.translatable(this.getDescriptionId() + ".desc_1");
//	}
//	public MutableComponent getDisplayName_2() {
//		return Component.translatable(this.getDescriptionId() + ".desc_2");
//	}
}
