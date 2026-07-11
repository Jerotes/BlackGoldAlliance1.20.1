package com.jerotes.blackgoldalliance.spell;

import com.jerotes.blackgoldalliance.entity.Boss.PiglinRaidNetherPortalEntity;
import com.jerotes.blackgoldalliance.entity.MagicSummoned.ZombiePigman.ZombiePigmanEntity;
import com.jerotes.blackgoldalliance.entity.Other.WarpedBombEntity;
import com.jerotes.blackgoldalliance.entity.Piglin.BlackGoldPiglin.BlackGoldPiglinEntity;
import com.jerotes.blackgoldalliance.entity.Shoot.Magic.Breath.WarpedBreathEntity;
import com.jerotes.blackgoldalliance.entity.Shoot.Magic.Ray.RayOfWarpedEntity;
import com.jerotes.blackgoldalliance.init.BGAEntityType;
import com.jerotes.blackgoldalliance.init.BGAParticleTypes;
import com.jerotes.jerotes.entity.Interface.BossEntity;
import com.jerotes.jerotes.entity.Other.FallingBlock.JerotesEarthrendBlock;
import com.jerotes.jerotes.init.JerotesParticleTypes;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.Main;
import com.jerotes.jerotes.util.ParticlesUse;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.scores.PlayerTeam;

import java.util.List;

public class OtherSpellFind {
	//召唤萨满制僵尸猪人$法术
	public static boolean ConjureShamanicZombiePigman(LivingEntity caster, int countMin, int countMax, int summonDistance) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			PlayerTeam teams = (PlayerTeam) caster.getTeam();
			int count = countMin;
			if (countMin < countMax) {
				count = Main.randomReach(caster.getRandom(), countMin, countMax);
			}
			for (int i = 0; i < count; ++i) {
				BlockPos summonPos = Main.findSpawnPositionNearFillOnBlock(caster, summonDistance);
				ZombiePigmanEntity zombiePigmanEntity = BGAEntityType.SHAMANIC_ZOMBIE_PIGMAN.get().spawn(serverLevel, BlockPos.containing(summonPos.getX(), summonPos.getY(), summonPos.getZ()), MobSpawnType.MOB_SUMMONED);
				if (zombiePigmanEntity != null) {
					zombiePigmanEntity.setTame(true);
					zombiePigmanEntity.setOwnerUUID(caster.getUUID());
					zombiePigmanEntity.setOrderedToSit(false);
					if (teams != null) {
						serverLevel.getScoreboard().addPlayerToTeam(zombiePigmanEntity.getStringUUID(), teams);
					}
					if (caster instanceof Mob mob && mob.getTarget() != null) {
						zombiePigmanEntity.setTarget(mob.getTarget());
					}
					for (int n = 0; n < 20; ++n) {
						serverLevel.sendParticles(BGAParticleTypes.PORTAL_POINT.get(), zombiePigmanEntity.getRandomX(0.5), zombiePigmanEntity.getRandomY(), zombiePigmanEntity.getRandomZ(0.5), 0, 0, 0, 0, 0.125f);
					}
					ParticlesUse.summonParticle(serverLevel, zombiePigmanEntity, zombiePigmanEntity.getX(), zombiePigmanEntity.getY(), zombiePigmanEntity.getZ(),
							0x40621e, 0xf0b1ac);
				}
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
		}
		return true;
	}

	//诡异吐息$法术
	public static boolean WarpedBreath(LivingEntity caster, Entity target, int spellLevelDamage, int spellLevelMainEffectTime, int spellLevelMainEffectLevel, float spellLevelAccuracy, int count, float distance, boolean isPlayer) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			WarpedBreathEntity breath;
			for (int i = 0; i < count; ++i) {
				if (!isPlayer && target != caster && caster instanceof Mob mob && target != null) {
					mob.getLookControl().setLookAt(target, 360.0f, 360.0f);
					mob.lookAt(target, 360.0f, 360.0f);
				}
				breath = new WarpedBreathEntity(spellLevelDamage, spellLevelMainEffectTime, spellLevelMainEffectLevel, serverLevel, caster, caster.getLookAngle().x, caster.getLookAngle().y, caster.getLookAngle().z);
				breath.setPos(caster.getX(), caster.getY(0.7), caster.getZ());
				breath.shootFromRotation(caster, caster.getXRot(), (caster.getYRot() - ((count - 1) * distance) / 2 + i * distance), 0f, 1f, spellLevelAccuracy);
				breath.setOwner(caster);
				serverLevel.addFreshEntity(breath);
			}
		}
		return true;
	}
	//诡异射线$法术
	public static boolean RayofWarped(LivingEntity caster, Entity target, int spellLevelDamage, int spellLevelMainEffectTime, int spellLevelMainEffectLevel, float spellLevelAccuracy, int count, float distance, boolean isPlayer) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			RayOfWarpedEntity spell;
			for (int i = 0; i < count; ++i) {
				if (!isPlayer && caster instanceof Mob mob && target != null) {
					mob.lookAt(target, 360.0f, 360.0f);
				}
				spell = new RayOfWarpedEntity(spellLevelDamage, spellLevelMainEffectTime, spellLevelMainEffectLevel, serverLevel, caster, caster.getLookAngle().x, caster.getLookAngle().y, caster.getLookAngle().z);
				spell.setPos(caster.getX(), caster.getY(0.7) - spell.getBbHeight()/2, caster.getZ());
				spell.shootFromRotation(caster, caster.getXRot(), (caster.getYRot() - ((count - 1) * distance) / 2 + i * distance), 0f, 1f, spellLevelAccuracy);
				spell.setOwner(caster);
				serverLevel.addFreshEntity(spell);
			}
		}
		return true;
	}
	//诡异炸弹$法术
	public static boolean WarpedBomb(LivingEntity caster, LivingEntity target, int countMin, int countMax, int summonDistance, int summonDistanceTarget) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			int count = countMin;
			if (countMin < countMax) {
				count = Main.randomReach(caster.getRandom(), countMin, countMax);
			}
			if (target == null) {
				target = caster;
			}
			for (int i = 0; i < count/2; ++i) {
				//自身位置
				BlockPos summonPos = Main.findSpawnPositionNearFillOnBlock(caster, summonDistance);
				WarpedBombEntity trap = BGAEntityType.WARPED_BOMB.get().spawn(serverLevel, BlockPos.containing(summonPos.getX(), summonPos.getY(), summonPos.getZ()), MobSpawnType.MOB_SUMMONED);
				//目标位置
				BlockPos summonPosTarget = Main.findSpawnPositionNearFillOnBlock(target, summonDistanceTarget);
				WarpedBombEntity trapTarget = BGAEntityType.WARPED_BOMB.get().spawn(serverLevel, BlockPos.containing(summonPosTarget.getX(), summonPosTarget.getY(), summonPosTarget.getZ()), MobSpawnType.MOB_SUMMONED);
				if (trap != null) {
					trap.setOwner(caster);
				}
				if (trapTarget != null) {
					trapTarget.setOwner(caster);
				}
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
		}
		return true;
	}
	//黑金地裂
	public static boolean BlackGoldGroundCrack(LivingEntity caster, LivingEntity target, int spellLevelDamage, int count, float summonDistance) {
		return BlackGoldGroundCrack(caster, target, spellLevelDamage, count, summonDistance, false, 0);
	}
	public static boolean BlackGoldGroundCrack(LivingEntity caster, LivingEntity target, int spellLevelDamage, int count, float summonDistance, boolean isDivide, int times) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			boolean sameEntity = caster == target;
			double baseY = sameEntity ? caster.getY() : Math.min(target.getY(), caster.getY());
			double maxY = sameEntity ? caster.getY() + 1.0 : Math.max(target.getY(), caster.getY()) + 1.0;
			float yRotRad = sameEntity ?
					(caster.getYRot() + 90) * ((float)Math.PI / 180F) :
					(float) Mth.atan2(target.getZ() - caster.getZ(), target.getX() - caster.getX());
			for (int i = 0; i < count; ++i) {
				if (!isDivide || i == times) {
					double offset = summonDistance * (i + 1);
					double x = caster.getX() + (double) Mth.cos(yRotRad) * offset;
					double z = caster.getZ() + (double) Mth.sin(yRotRad) * offset;
					BlackGoldGroundCrack(caster, target, spellLevelDamage, x, z, baseY, maxY, yRotRad, summonDistance);
				}
			}
		}
		return true;
	}
	static void BlackGoldGroundCrack(LivingEntity caster, LivingEntity target, int spellLevelDamage, double d, double d2, double d3, double d4, float f, float summonDistance) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			PlayerTeam teams = (PlayerTeam) caster.getTeam();

			BlockPos blockPos = BlockPos.containing(d, d4, d2);
			boolean bl = false;
			double d5 = 0.0;
			do {
				BlockState blockState;
				VoxelShape voxelShape;
				BlockPos blockPos2 = blockPos.below();
				BlockState blockState2 = caster.level().getBlockState(blockPos2);
				if (!blockState2.isFaceSturdy(caster.level(), blockPos2, Direction.UP)) continue;
				if (!caster.level().isEmptyBlock(blockPos) && !(voxelShape = (blockState = caster.level().getBlockState(blockPos)).getCollisionShape(caster.level(), blockPos)).isEmpty()) {
					d5 = voxelShape.max(Direction.Axis.Y);
				}
				bl = true;
				break;
			} while ((blockPos = blockPos.below()).getY() >= Mth.floor(d3) - 1);
			if (bl) {
				Main.spawnUnevenBlockByPos(serverLevel, BlockPos.containing(new Vec3(d, blockPos.getY() + d5 - 1, d2)), 2);
				float maxDistance = summonDistance / 2f;
                AABB aabb = AABB.ofSize(blockPos.getCenter(), maxDistance * 2 + 1, maxDistance, maxDistance * 2 + 1).move(0, Math.min(0, -(summonDistance - 2)),0);
				for (BlockPos pos : BlockPos.betweenClosed(Mth.floor(aabb.minX), Mth.floor(aabb.minY), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ))) {
					double distance = blockPos.getCenter().distanceTo(pos.getCenter());
					if (distance <= maxDistance) {;
						Vec3 vec3 = new Vec3(pos.getX() + 0.5f, pos.getY(), pos.getZ() + 0.5f);
						Vec3 vec32 = new Vec3(vec3.x, vec3.y + d5 - 1, vec3.z);
						BlockState blockBelow = serverLevel.getBlockState(BlockPos.containing(vec32));
						JerotesEarthrendBlock fallingBlock =
								new JerotesEarthrendBlock(serverLevel, vec3.x, vec3.y + d5 + 1 * distance/maxDistance + 0.2f, vec3.z, blockBelow, 10);
						fallingBlock.setOwner(caster);
						fallingBlock.setAttackDamage(spellLevelDamage * 2f);
						fallingBlock.push(0, 0.25, 0);
						fallingBlock.setPushSpeed(0.3f);
						fallingBlock.setCanBreakBlock(true);
						fallingBlock.setPushTick(1);
						serverLevel.addFreshEntity(fallingBlock);
					}
				}
			}
		}
	}
	//黑金号令
	public static boolean BlackGoldOrder(LivingEntity caster, LivingEntity target, int countMin, int countMax, int summonDistance, int spellLevelDistance) {
		if (caster.level() instanceof ServerLevel serverLevel) {
			if (target != null) {
				Vec3 vec3 = caster.getEyePosition();
				Vec3 vec32 = target.getEyePosition().subtract(vec3);
				Vec3 vec33 = vec32.normalize();
				for (int i = 1; i < Mth.floor(vec32.length()) * 6 + 1; ++i) {
					Vec3 vec34 = vec3.add(vec33.scale(i / 6f));
					serverLevel.sendParticles(ParticleTypes.PORTAL, vec34.x, vec34.y, vec34.z, 1, 0.0, 0.0, 0.0, 0.0);
				}
			}
			PlayerTeam teams = (PlayerTeam) caster.getTeam();
			int count = countMin;
			if (countMin < countMax) {
				count = Main.randomReach(caster.getRandom(), countMin, countMax);
			}
			for (int i = 0; i < count; ++i) {
				BlockPos summonPos = Main.findSpawnPositionNearFillOnBlock(caster, summonDistance);
				EntityType<?> randomEntity = PiglinRaidNetherPortalEntity.entityTypeFind(serverLevel, "level_vi", "ii");
				Entity blackGoldPiglin = randomEntity.spawn(serverLevel, BlockPos.containing(summonPos.getX(), summonPos.getY(), summonPos.getZ()), MobSpawnType.MOB_SUMMONED);
				if (blackGoldPiglin != null) {
					if (teams != null) {
						serverLevel.getScoreboard().addPlayerToTeam(blackGoldPiglin.getStringUUID(), teams);
					}
					if (target != null && target != caster && blackGoldPiglin instanceof Mob mob) {
						mob.setTarget(target);
					}
					ParticlesUse.summonParticle(serverLevel, blackGoldPiglin, blackGoldPiglin.getX(), blackGoldPiglin.getY(), blackGoldPiglin.getZ(),
							0x171313, 0xe9af15);
					serverLevel.sendParticles(ParticleTypes.PORTAL, blackGoldPiglin.getRandomX(0.5), blackGoldPiglin.getRandomY(), blackGoldPiglin.getRandomZ(0.5), 20, 0, 0, 0, 0);
				}
			}
			List<BlackGoldPiglinEntity> list = serverLevel.getEntitiesOfClass(BlackGoldPiglinEntity.class, caster.getBoundingBox().inflate(spellLevelDistance, spellLevelDistance, spellLevelDistance));
			for (BlackGoldPiglinEntity blackGoldPiglin : list) {
				if (blackGoldPiglin == null) continue;
				if (blackGoldPiglin == caster) continue;
				if (blackGoldPiglin == target) continue;
				if (blackGoldPiglin instanceof BossEntity) continue;
				if (!AttackFind.SameFactionAvoidDamage(caster, blackGoldPiglin, false)) continue;
				if ((caster.distanceTo(blackGoldPiglin)) > spellLevelDistance * 2) continue;
				if ((blackGoldPiglin.getTeam() != null || caster.getTeam() != null) && !caster.isAlliedTo(blackGoldPiglin)) continue;
				if (target != null && target != caster) {
					blackGoldPiglin.setTarget(target);
				}
			}
			if (target != null && target != caster) {
				serverLevel.sendParticles(JerotesParticleTypes.TARGET.get(), target.getX(), target.getY() + 0.1, target.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
			}
			serverLevel.gameEvent(GameEvent.ENTITY_PLACE, new BlockPos((int) caster.getX(), (int) caster.getY(), (int) caster.getZ()), GameEvent.Context.of(caster));
			if (!caster.isInvisible()) {
				serverLevel.sendParticles(BGAParticleTypes.BLACK_GOLD_ORDER_DISPLAY.get(), caster.getX(), caster.getBoundingBox().maxY + 0.5, caster.getZ(), 0, 0.0, 0.0, 0.0, 0.0);
			}
		}
		return true;
	}
}