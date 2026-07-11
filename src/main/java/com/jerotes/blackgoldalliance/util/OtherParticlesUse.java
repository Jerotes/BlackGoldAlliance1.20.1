package com.jerotes.blackgoldalliance.util;

import com.jerotes.blackgoldalliance.entity.Boss.PiglinRaidNetherPortalEntity;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class OtherParticlesUse {

    //建造粒子
    public static void PiglinRaidNetherPortalBuild(PiglinRaidNetherPortalEntity portal) {
        Level level = portal.level();
        if (!(level instanceof ServerLevel serverLevel)) return;

        Vec3 position = portal.position();
        float facing = portal.getFacing();

        boolean bl = false;
        int xAdd = 0;
        int yAdd = 0;
        int type = 0;
        if (portal.getStartTick() == (int) (143 - 0.5f * 20)) {
            bl = true;
            xAdd = -2;
            type = 2;
        } else if (portal.getStartTick() == (int) (143 - 1f * 20)) {
            bl = true;
            xAdd = -1;
        } else if (portal.getStartTick() == (int) (143 - 1.42f * 20)) {
            bl = true;
            type = 1;
        } else if (portal.getStartTick() == (int) (143 - 1.83f * 20)) {
            bl = true;
            xAdd = 1;
        } else if (portal.getStartTick() == (int) (143 - 2.21f * 20)) {
            bl = true;
            xAdd = 2;
            type = 2;
        } else if (portal.getStartTick() == (int) (143 - 2.54f * 20)) {
            bl = true;
            xAdd = 2;
            yAdd = 1;
        } else if (portal.getStartTick() == (int) (143 - 2.88f * 20)) {
            bl = true;
            xAdd = 2;
            yAdd = 2;
            type = 2;
        } else if (portal.getStartTick() == (int) (143 - 3.21f * 20)) {
            bl = true;
            xAdd = 2;
            yAdd = 3;
            type = 1;
        } else if (portal.getStartTick() == (int) (143 - 3.54f * 20)) {
            bl = true;
            xAdd = 2;
            yAdd = 4;
        } else if (portal.getStartTick() == (int) (143 - 3.83f * 20)) {
            bl = true;
            xAdd = 2;
            yAdd = 5;
            type = 2;
        } else if (portal.getStartTick() == (int) (143 - 4.13f * 20)) {
            bl = true;
            xAdd = 1;
            yAdd = 5;
        } else if (portal.getStartTick() == (int) (143 - 4.38f * 20)) {
            bl = true;
            yAdd = 5;
            type = 2;
        } else if (portal.getStartTick() == (int) (143 - 4.63f * 20)) {
            bl = true;
            xAdd = -1;
            yAdd = 5;
        } else if (portal.getStartTick() == (int) (143 - 4.88f * 20)) {
            bl = true;
            xAdd = -2;
            yAdd = 5;
            type = 2;
        } else if (portal.getStartTick() == (int) (143 - 5.13f * 20)) {
            bl = true;
            xAdd = -2;
            yAdd = 4;
        } else if (portal.getStartTick() == (int) (143 - 5.33f * 20)) {
            bl = true;
            xAdd = -2;
            yAdd = 3;
            type = 1;
        } else if (portal.getStartTick() == (int) (143 - 5.58f * 20)) {
            bl = true;
            xAdd = -2;
            yAdd = 2;
            type = 2;
        } else if (portal.getStartTick() == (int) (143 - 5.83f * 20)) {
            bl = true;
            xAdd = -2;
            yAdd = 1;
        }

        if (bl) {
            PiglinRaidNetherPortalParticle(serverLevel, facing, position.x, position.y, position.z, xAdd, yAdd, type);
        }
    }
    //拆除粒子
    public static void PiglinRaidNetherPortalDestroy(PiglinRaidNetherPortalEntity portal) {
        Level level = portal.level();
        if (!(level instanceof ServerLevel serverLevel)) return;

        Vec3 position = portal.position();
        float facing = portal.getFacing();

        int xAdd = 0;
        int yAdd = 0;
        int type = 0;
        {
            xAdd = -2;
            yAdd = 0;
            type = 2;
            PiglinRaidNetherPortalParticle(serverLevel, facing, position.x, position.y, position.z, xAdd, yAdd, type);
        }
        {
            xAdd = -1;
            yAdd = 0;
            type = 0;
            PiglinRaidNetherPortalParticle(serverLevel, facing, position.x, position.y, position.z, xAdd, yAdd, type);
        }
        {
            xAdd = 0;
            yAdd = 0;
            type = 1;
            PiglinRaidNetherPortalParticle(serverLevel, facing, position.x, position.y, position.z, xAdd, yAdd, type);
        }
        {
            yAdd = 0;
            type = 0;
            xAdd = 1;
            PiglinRaidNetherPortalParticle(serverLevel, facing, position.x, position.y, position.z, xAdd, yAdd, type);
        }
        {
            yAdd = 0;
            xAdd = 2;
            type = 2;
            PiglinRaidNetherPortalParticle(serverLevel, facing, position.x, position.y, position.z, xAdd, yAdd, type);
        }
        {
            xAdd = 2;
            yAdd = 1;
            type = 0;
            PiglinRaidNetherPortalParticle(serverLevel, facing, position.x, position.y, position.z, xAdd, yAdd, type);
        }
        {
            xAdd = 2;
            yAdd = 2;
            type = 0;
            PiglinRaidNetherPortalParticle(serverLevel, facing, position.x, position.y, position.z, xAdd, yAdd, type);
        }
        {
            xAdd = 2;
            yAdd = 3;
            type = 1;
            PiglinRaidNetherPortalParticle(serverLevel, facing, position.x, position.y, position.z, xAdd, yAdd, type);
        }
        {
            xAdd = 2;
            yAdd = 4;
            type = 0;
            PiglinRaidNetherPortalParticle(serverLevel, facing, position.x, position.y, position.z, xAdd, yAdd, type);
        }
        {
            xAdd = 2;
            yAdd = 5;
            type = 2;
            PiglinRaidNetherPortalParticle(serverLevel, facing, position.x, position.y, position.z, xAdd, yAdd, type);
        }
        {
            xAdd = 1;
            yAdd = 5;
            type = 0;
            PiglinRaidNetherPortalParticle(serverLevel, facing, position.x, position.y, position.z, xAdd, yAdd, type);
        }
        {
            xAdd = 0;
            yAdd = 5;
            type = 2;
            PiglinRaidNetherPortalParticle(serverLevel, facing, position.x, position.y, position.z, xAdd, yAdd, type);
        }
        {
            xAdd = -1;
            yAdd = 5;
            type = 0;
            PiglinRaidNetherPortalParticle(serverLevel, facing, position.x, position.y, position.z, xAdd, yAdd, type);
        }
        {
            xAdd = -2;
            yAdd = 5;
            type = 2;
            PiglinRaidNetherPortalParticle(serverLevel, facing, position.x, position.y, position.z, xAdd, yAdd, type);
        }
        {
            xAdd = -2;
            yAdd = 4;
            type = 0;
            PiglinRaidNetherPortalParticle(serverLevel, facing, position.x, position.y, position.z, xAdd, yAdd, type);
        }
        {
            xAdd = -2;
            yAdd = 3;
            type = 1;
            PiglinRaidNetherPortalParticle(serverLevel, facing, position.x, position.y, position.z, xAdd, yAdd, type);
        }
        {
            xAdd = -2;
            yAdd = 2;
            type = 2;
            PiglinRaidNetherPortalParticle(serverLevel, facing, position.x, position.y, position.z, xAdd, yAdd, type);
        }
        {
            xAdd = -2;
            yAdd = 2;
            type = 0;
            PiglinRaidNetherPortalParticle(serverLevel, facing, position.x, position.y, position.z, xAdd, yAdd, type);
        }
    }

    public static void PiglinRaidNetherPortalParticle(ServerLevel serverLevel, float facing, double x, double y, double z, int xAdd, int yAdd, int type) {
        double xs = x;
        double ys = y + yAdd + 0.2f;
        double zs = z;
        if (facing == 0) {
            xs -= xAdd;
        }
        else if (facing == 90) {
            zs -= xAdd;
        }
        else if (facing == 180) {
            xs += xAdd;
        }
        else if (facing == 270) {
            zs += xAdd;
        }

        BlockState blockState = Blocks.OBSIDIAN.defaultBlockState();
        if (type == 1) {
            blockState = Blocks.CRYING_OBSIDIAN.defaultBlockState();
        }
        if (type == 2) {
            blockState = Blocks.GILDED_BLACKSTONE.defaultBlockState();
        }
        for (int i = 0; i < 16; ++i) {
            serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, blockState),
                    xs, ys, zs, 0, (serverLevel.getRandom().nextFloat() - 0.5f) * 0.5, -0.02, (serverLevel.getRandom().nextFloat() - 0.5f) * 0.5, 1.0);
        }
        for (int i = 0; i < 20; i++) {
            double angle = (Math.PI * 2 * i) / 20;
            double radius = 0.5;
            double offsetX = Math.cos(angle) * radius;
            double offsetZ = Math.sin(angle) * radius;
            serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, blockState), xs + offsetX, ys, zs + offsetZ, 0, offsetX * 0.05, -0.02, offsetZ * 0.05, 1.0);
        }
    }
}

