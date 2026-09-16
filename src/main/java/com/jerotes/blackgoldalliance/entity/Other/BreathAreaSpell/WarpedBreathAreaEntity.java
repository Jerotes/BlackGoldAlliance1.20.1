package com.jerotes.blackgoldalliance.entity.Other.BreathAreaSpell;

import com.jerotes.blackgoldalliance.init.BGAEntityType;
import com.jerotes.blackgoldalliance.init.BGAParticleTypes;
import com.jerotes.jerotes.entity.Other.BreathAreaSpell.BaseBreathAreaEntity;
import com.jerotes.jerotes.entity.Shoot.Magic.MagicAbout;
import com.jerotes.jerotes.init.JerotesDamageTypes;
import com.jerotes.jerotes.init.JerotesMobEffects;
import com.jerotes.jerotes.init.JerotesSoundEvents;
import com.jerotes.jerotes.util.AttackFind;
import com.jerotes.jerotes.util.EntityAndItemFind;
import com.jerotes.jerotes.util.Main;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.level.Level;

public class WarpedBreathAreaEntity extends BaseBreathAreaEntity implements TraceableEntity, OwnableEntity, MagicAbout {

    public WarpedBreathAreaEntity(EntityType<? extends WarpedBreathAreaEntity> entityType, Level level) {
        super(entityType, level);
    }

    public WarpedBreathAreaEntity(Level p_36926_, LivingEntity p_36932_) {
        this(BGAEntityType.WARPED_BREATH_AREA.get(), p_36926_);
        this.setOwnerUUID(p_36932_.getUUID());
    }

    public DamageSource getDamageSource() {
        LivingEntity livingEntity2 = this.getOwner();
        DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.POISON, this);
        if (livingEntity2 != null) {
            damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.POISON, this, livingEntity2);
        }
        return damageSource;
    }
    public float getMaxReach() {
        return 18.0f;
    }
    public int getParticleCount() {
        return 160;
    }

    public boolean dealDamageTo(LivingEntity livingEntity) {
        LivingEntity livingEntity2 = this.getOwner();
        if (!livingEntity.isAlive() || livingEntity.isInvulnerable() || livingEntity == livingEntity2) {
            return false;
        }
        if (AttackFind.FindCanNotAttack(this.getOwner(), livingEntity))
            return false;
        DamageSource damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.POISON, this);
        DamageSource damageSource2 = AttackFind.findDamageType(this, DamageTypes.INDIRECT_MAGIC, this);
        if (livingEntity2 != null) {
            damageSource = AttackFind.findDamageType(this, JerotesDamageTypes.POISON, this, livingEntity2);
            damageSource2 = AttackFind.findDamageType(this, DamageTypes.INDIRECT_MAGIC, this, livingEntity2);
        }
        if (livingEntity2 != null && AttackFind.FindCanNotAttack(livingEntity2, livingEntity)) {
            return false;
        }
        if (!EntityAndItemFind.savingSpellThrow(livingEntity)) {
            boolean bl = livingEntity.hurt(damageSource, Main.rollDice(getSpellLevel() * 2, 6, this.getRandom()));
            if (bl) {
                livingEntity.addEffect(new MobEffectInstance(JerotesMobEffects.DEADLY_POISON.get(),
                        (6 + (getSpellLevel() - 1) * 3) * 20,
                        (int)Math.ceil((getSpellLevel()) / 2.0)), this.getOwner() != null ? this.getOwner() : this);
                this.playSound(JerotesSoundEvents.SPELL, 1.0f, 1.0f);
            }
            else {
                livingEntity.hurt(this.damageSources().indirectMagic(this, this.getOwner()),
                        Main.rollDice(getSpellLevel() * 2, 6, this.getRandom())
                );
            }
        } else {
            boolean bl = livingEntity.hurt(damageSource, Main.rollDice(getSpellLevel() * 2, 6, this.getRandom()) / 2);
            if (bl) {
                this.playSound(JerotesSoundEvents.SPELL, 1.0f, 1.0f);
            }
            else {
                livingEntity.hurt(damageSource2,
                        Main.rollDice(getSpellLevel() * 2, 6, this.getRandom()) / 2
                );
            }
        }
        return true;
    }

    public ParticleOptions getParticle() {
        return BGAParticleTypes.WARPED_BREATH.get();
    }
    public int beamLightI() {
        return 0x16615b;
    }
    public int beamLightII() {
        return 0x119b85;
    }
    public int beamLightIII() {
        return 0x0d4e50;
    }
}

