package com.jerotes.blackgoldalliance.entity.Piglin.BlackGoldPiglin;

import com.jerotes.blackgoldalliance.BGA;
import com.jerotes.jerotes.entity.Interface.*;
import com.jerotes.jerotes.init.JerotesGameRules;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class BlackGoldRuntEntity extends BlackGoldPiglinEntity {
	public BlackGoldRuntEntity(EntityType<? extends BlackGoldRuntEntity> type, Level world) {
		super(type, world);
		xpReward = 10;
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.28);
		builder = builder.add(Attributes.MAX_HEALTH, 20);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 3f);
		builder = builder.add(Attributes.FOLLOW_RANGE, 48);
		return builder;
	}

	public float getVoicePitch() {
		return (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.5F;
	}
	public boolean isBaby() {
		return true;
	}
	public float getScale() {
		return 1.0F;
	}
	public boolean shouldDropExperience() {
		return true;
	}
	protected boolean shouldDropLoot() {
		return true;
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		if (!this.isSilent()) {
			this.playSound(SoundEvents.PIGLIN_ANGRY, this instanceof EliteEntity ? 2.0f : 1.0f, 2f);
		}
		if (!this.getMainHandItem().is(ItemTags.create(new ResourceLocation(BGA.MODID, "piglin_weapon")))) {
			this.setAnimTick(8);
			this.setAnimationState("attack");
			boolean bl = super.doHurtTarget(entity);
			if (bl) {
				if (JerotesGameRules.JEROTES_MELEE_CAN_BREAK != null && this.level().getLevelData().getGameRules().getBoolean(JerotesGameRules.JEROTES_MELEE_CAN_BREAK)) {
					ItemStack hand = this.getMainHandItem();
					hand.hurtAndBreak(1, this, player -> player.broadcastBreakEvent(EquipmentSlot.MAINHAND));
				}
			}
			return bl;
		}
		else {
			if (this.getAttackTick() > 0 && this.getMainHandItem().getItem().canDisableShield(this.getMainHandItem(), new ItemStack(Items.SHIELD), this, this)) {
				return false;
			}
			if (this.getAttackTick() > 5) {
				return false;
			}
			if (!this.level().isClientSide()) {
				int attackRandom = this.getRandom().nextInt(100);
				if (this.getMainHandItem().getItem().canDisableShield(this.getMainHandItem(), new ItemStack(Items.SHIELD), this, this)) {
					if (attackRandom > 55) {
						this.setAnimTick(20);
						this.setAttackTick(25);
						this.setAnimationState("heavyAttack2");
					}
					else if (attackRandom > 20) {
						this.setAnimTick(30);
						this.setAttackTick(25);
						this.setAnimationState("heavyAttack1");
					}
					else {
						this.setAnimTick(20);
						this.setAttackTick(25);
						this.setAnimationState("heavyAttack3");
					}
				}
				else {
					if (attackRandom > 70) {
						this.setAnimTick(20);
						this.setAttackTick(25);
						this.setAnimationState("longAttack1");
					}
					else if (attackRandom > 35) {
						this.setAnimTick(20);
						this.setAttackTick(25);
						this.setAnimationState("longAttack2");
					}
					else {
						this.setAnimTick(20);
						this.setAttackTick(25);
						this.setAnimationState("longAttack3");
					}
				}
			}
			this.getNavigation().stop();
			this.setDeltaMovement(this.getDeltaMovement().multiply(0,1,0));
			return true;
		}
	}
}