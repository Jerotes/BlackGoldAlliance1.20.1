package com.jerotes.blackgoldalliance.entity.Other;

import com.jerotes.blackgoldalliance.init.BGAEntityType;
import com.jerotes.blackgoldalliance.init.BGAParticleTypes;
import com.jerotes.jerotes.entity.Other.SpellCloud.SpellCloudEntity;
import com.jerotes.jerotes.entity.Shoot.Magic.MagicAbout;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.util.AttackFind;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.scores.Team;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class WarpedBombEntity extends Entity implements TraceableEntity, OwnableEntity, MagicAbout {
    private int warmupDelayTicks = 20;
    private int spellLevelDamage = 1;
    private boolean sentSpikeEvent;
    private int lifeTicks = 30;
    private boolean clientSideAttackStarted;
    @Nullable
    private LivingEntity owner;
    @Nullable
    private UUID ownerUUID;

    @Override
    public int getSpellLevel() {
        return this.spellLevelDamage;
    }

    @Nullable
    @Override
    public UUID getOwnerUUID() {
        return ownerUUID;
    }

    public WarpedBombEntity(EntityType<? extends WarpedBombEntity> entityType, Level level) {
        super(entityType, level);
    }

    public WarpedBombEntity(Level level, double d, double d2, double d3, float f, int n, LivingEntity livingEntity) {
        this(BGAEntityType.WARPED_BOMB.get(), level);
        this.warmupDelayTicks = n;
        this.setOwner(livingEntity);
        this.setYRot(f * 57.295776f);
        this.setPos(d, d2, d3);
    }

    @Override
    protected void defineSynchedData() {
    }

    public void setOwner(@Nullable LivingEntity livingEntity) {
        this.owner = livingEntity;
        this.ownerUUID = livingEntity == null ? null : livingEntity.getUUID();
    }

    @Nullable
    @Override
    public LivingEntity getOwner() {
        Entity entity;
        if (this.owner == null && this.ownerUUID != null && this.level() instanceof ServerLevel && (entity = ((ServerLevel)this.level()).getEntity(this.ownerUUID)) instanceof LivingEntity) {
            this.owner = (LivingEntity)entity;
        }
        return this.owner;
    }

    @Override
    public boolean isAlliedTo(Entity entity) {
        if (this.getOwner() != null) {
            LivingEntity livingEntity = this.getOwner();
            if (entity == livingEntity) {
                return true;
            }
            if (livingEntity != null) {
                return livingEntity.isAlliedTo(entity);
            }
        }
        return super.isAlliedTo(entity);
    }
    @Override
    //1.20.4↑//
    //public PlayerTeam getTeam() {
    //1.20.1//
    public Team getTeam() {
        LivingEntity livingEntity;
        if (this.getOwner() != null && (livingEntity = this.getOwner()) != null) {
            return livingEntity.getTeam();
        }
        return super.getTeam();
    }

    public void setSpellLevelDamage(int n) {
        this.spellLevelDamage = n;
    }
    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        this.warmupDelayTicks = compoundTag.getInt("Warmup");
        this.spellLevelDamage = compoundTag.getInt("SpellLevelDamage");
        if (compoundTag.hasUUID("Owner")) {
            this.ownerUUID = compoundTag.getUUID("Owner");
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        compoundTag.putInt("Warmup", this.warmupDelayTicks);
        compoundTag.putInt("SpellLevelDamage", this.spellLevelDamage);
        if (this.ownerUUID != null) {
            compoundTag.putUUID("Owner", this.ownerUUID);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            if (this.clientSideAttackStarted) {
                --this.lifeTicks;
                if (this.lifeTicks <= 20) {
                    for (int i = 0; i < 4; ++i) {
                        double d = this.getX() + (this.random.nextDouble() * 2.0 - 1.0) * (double)this.getBbWidth() * 0.5;
                        double d2 = this.getY() + 0.05 + this.random.nextDouble();
                        double d3 = this.getZ() + (this.random.nextDouble() * 2.0 - 1.0) * (double)this.getBbWidth() * 0.5;
                        double d4 = (this.random.nextDouble() * 2.0 - 1.0) * 0.0105;
                        double d5 = this.random.nextDouble() * 0.0015;
                        double d6 = (this.random.nextDouble() * 2.0 - 1.0) * 0.0015;
                        this.level().addParticle(ParticleTypes.WARPED_SPORE, d, d2 + 1.0, d3, d4, d5, d6);
                    }
                    double d = this.getX() + (this.random.nextDouble() * 2.0 - 1.0) * (double)this.getBbWidth() * 0.5;
                    double d2 = this.getY() + 0.05 + this.random.nextDouble();
                    double d3 = this.getZ() + (this.random.nextDouble() * 2.0 - 1.0) * (double)this.getBbWidth() * 0.5;
                    double d4 = (this.random.nextDouble() * 2.0 - 1.0) * 0.0105;
                    double d5 = this.random.nextDouble() * 0.0015;
                    double d6 = (this.random.nextDouble() * 2.0 - 1.0) * 0.0015;
                    this.level().addParticle(BGAParticleTypes.WARPED_BREATH.get(), d, d2 + 1.0, d3, d4, d5, d6);
                }
            }
        }
        else if (--this.warmupDelayTicks < 0) {
            if (this.warmupDelayTicks == -20) {
                List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(0.2, 0.0, 0.2));
                for (LivingEntity livingEntity : list) {
                    this.dealDamageTo(livingEntity);
                }
                this.level().explode(this.getOwner() != null ? this.getOwner() : this, this.getX(), this.getY(), this.getZ(), 0.8f, Level.ExplosionInteraction.NONE);
                this.Cloud(this.getX(), this.getY(), this.getZ());
                this.discard();
            }
            if (!this.sentSpikeEvent) {
                this.level().broadcastEntityEvent(this, (byte)4);
                this.sentSpikeEvent = true;
            }
            if (--this.lifeTicks < 0) {
                this.discard();
            }
        }
    }

    public boolean Cloud(double x, double y, double z) {
        List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(4.0, 2.0, 4.0));
        list.removeIf(livingEntity -> livingEntity == this.getOwner());
        SpellCloudEntity areaEffectCloud = new SpellCloudEntity(this.level(), x, y, z);
        Entity entity = this.getOwner();
        if (entity instanceof LivingEntity) {
            areaEffectCloud.setOwner((LivingEntity)entity);
        }
        areaEffectCloud.spellLevelDamage = spellLevelDamage;
        areaEffectCloud.setParticle(BGAParticleTypes.WARPED_BREATH_FOG.get());
        areaEffectCloud.setRadius(0.6f);
        areaEffectCloud.setDuration((int) (60));
        areaEffectCloud.setRadiusPerTick((2.0f - areaEffectCloud.getRadius()) / (float)areaEffectCloud.getDuration());
        areaEffectCloud.addEffect(new MobEffectInstance(JerotesMobEffects.DEADLY_POISON.get(), 60, 0));
        if (!list.isEmpty()) {
            for (LivingEntity livingEntity : list) {
                double d = this.distanceToSqr(livingEntity);
                if (!(d < 16.0)) continue;
                areaEffectCloud.setPos(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
                break;
            }
        }
        this.playSound(JerotesSoundEvents.BREATH, 1.0f, 1.0f);
        this.level().addFreshEntity(areaEffectCloud);
        return true;
    }

    private void dealDamageTo(LivingEntity livingEntity) {
        LivingEntity livingEntity2 = this.getOwner();
        if (!livingEntity.isAlive() || livingEntity.isInvulnerable() || livingEntity == livingEntity2) {
            return;
        }
        if (livingEntity2 == null) {
            DamageSource damageSources = AttackFind.findDamageType(livingEntity, JerotesDamageTypes.POISON, this);
            boolean bl = livingEntity.hurt(damageSources, 6.0f);
            if (bl) {
                livingEntity.addEffect(new MobEffectInstance(JerotesMobEffects.DEADLY_POISON.get(), 60, 0), livingEntity2);
            }
        }
        else {
            DamageSource damageSources = AttackFind.findDamageType(livingEntity, JerotesDamageTypes.POISON, this, livingEntity2);
            boolean bl = livingEntity.hurt(damageSources, 6.0f);
            if (bl) {
                livingEntity.addEffect(new MobEffectInstance(JerotesMobEffects.DEADLY_POISON.get(), 60, 0));
            }
        }
    }

    @Override
    public void handleEntityEvent(byte by) {
        super.handleEntityEvent(by);
        if (by == 4) {
            this.clientSideAttackStarted = true;
            if (!this.isSilent()) {
                this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.AMBIENT_WARPED_FOREST_ADDITIONS.get(), this.getSoundSource(), 1.0F, this.random.nextFloat() * 0.2F + 0.85F, false);
            }
        }
    }

    public float getAnimationProgress(float f) {
        if (!this.clientSideAttackStarted) {
            return 0.0f;
        }
        int n = this.lifeTicks;
        if (n <= 0) {
            return 1.0f;
        }
        return 1.0f - ((float)n - f) / 30.0f;
    }
}

