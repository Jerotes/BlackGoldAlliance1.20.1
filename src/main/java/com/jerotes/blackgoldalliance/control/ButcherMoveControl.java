package com.jerotes.blackgoldalliance.control;

import com.jerotes.blackgoldalliance.entity.Piglin.BlackGoldPiglin.BlackGoldButcherEntity;
import com.jerotes.blackgoldalliance.init.BGAItems;
import net.minecraft.world.entity.ai.control.MoveControl;

public class ButcherMoveControl extends MoveControl {
    private final BlackGoldButcherEntity mob;

    public ButcherMoveControl(BlackGoldButcherEntity mob) {
        super(mob);
        this.mob = mob;
    }

    @Override
    public void tick() {
        if (mob.getChainsawTick() > 520 && mob.getMainHandItem().is(BGAItems.BLACK_GOLD_CHAINSAW.get()))
            return;
        super.tick();
    }
}