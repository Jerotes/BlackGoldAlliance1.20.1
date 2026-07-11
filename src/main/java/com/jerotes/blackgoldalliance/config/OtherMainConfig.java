package com.jerotes.blackgoldalliance.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.google.common.collect.Lists;
import com.jerotes.blackgoldalliance.BGA;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.util.List;

@Mod.EventBusSubscriber(modid = BGA.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class OtherMainConfig {
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final CommonConfig COMMON;
    public static final ForgeConfigSpec CLIENT_SPEC;
    public static final ClientConfig CLIENT;

    public static double NetherSiphonUsefulToPiglinMaxReach;
    public static double NetherSiphonLinkStationLinkMaxReach;
    public static double NetherSiphonLinkStationMutuallyInfluenceReach;
    //Boss
    public static boolean BossBaseAttributeCanUseConfig;
    public static boolean RaidBossCanIncreaseSummonCountBasedOnPlayerCount;
    public static double RaidBossCanIncreaseSummonCountBasedOnPlayerCountFindReach;
    public static int RaidBossCanIncreaseSummonCountBasedOnPlayerCountMaxCount;
    public static boolean RaidBossCanIncreaseSummonCountBasedOnPlayerCountContainAllBoss;
    public static List<String> RaidBossCanIncreaseSummonCountBasedOnPlayerCountContainBoss;
    public static List<String> BossHasPercentageDamage;
    public static List<String> BossHasDamageCap;
    public static List<String> BossHasDamageCooldownTick;
    //猪灵袭击下界传送门
    public static int PiglinRaidNetherPortalBossBarColor;
    public static int PiglinRaidNetherPortalBossBarNameColor;
    public static double PiglinRaidNetherPortalMaxHealth;
    public static double PiglinRaidNetherPortalMeleeDamage;
    public static double PiglinRaidNetherPortalArmor;
    public static double PiglinRaidNetherPortalMovementSpeed;
    public static double PiglinRaidNetherPortalAttackKnockback;
    public static double PiglinRaidNetherPortalKnockbackResistance;
    public static double PiglinRaidNetherPortalRoundTime;
    public static double PiglinRaidNetherPortalLevelAddRoundTime;
    public static double PiglinRaidNetherPortalAttackPercentage;
    public static double PiglinRaidNetherPortalMagicAttackPercentage;
    public static double PiglinRaidNetherPortalDamageCap;
    public static double PiglinRaidNetherPortalDamageCooldownTick;
    //黑金军主
    public static int TheBlackGoldMarshalBossBarColor;
    public static int TheBlackGoldMarshalBossBarNameColor;
    public static double TheBlackGoldMarshalMaxHealth;
    public static double TheBlackGoldMarshalMeleeDamage;
    public static double TheBlackGoldMarshalArmor;
    public static double TheBlackGoldMarshalMovementSpeed;
    public static double TheBlackGoldMarshalAttackKnockback;
    public static double TheBlackGoldMarshalKnockbackResistance;
    public static double TheBlackGoldMarshalCrackCooldown;
    public static double TheBlackGoldMarshalShootCooldown;
    public static double TheBlackGoldMarshalLeadCooldown;
    public static double TheBlackGoldMarshalDropEquipmentChance;
    public static int TheBlackGoldMarshalSpellNormalLevel;
    public static double TheBlackGoldMarshalBreakBlockCooldown;
    public static double TheBlackGoldMarshalAttackPercentage;
    public static double TheBlackGoldMarshalMagicAttackPercentage;
    public static double TheBlackGoldMarshalDamageCap;
    public static double TheBlackGoldMarshalDamageCooldownTick;

    static {
        final Pair<CommonConfig, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        COMMON = pair.getLeft();
        COMMON_SPEC = pair.getRight();
        final Pair<ClientConfig, ForgeConfigSpec> pair1 = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
        CLIENT = pair1.getLeft();
        CLIENT_SPEC = pair1.getRight();
    }

    public static void loadConfig(ForgeConfigSpec config, String path) {
        final CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave()
                .writingMode(WritingMode.REPLACE).build();
        file.load();
        config.setConfig(file);
    }

    public static void bakeCommonConfig() {
        NetherSiphonUsefulToPiglinMaxReach = COMMON.NetherSiphonUsefulToPiglinMaxReach.get();
        NetherSiphonLinkStationLinkMaxReach = COMMON.NetherSiphonLinkStationLinkMaxReach.get();
        NetherSiphonLinkStationMutuallyInfluenceReach = COMMON.NetherSiphonLinkStationMutuallyInfluenceReach.get();

        BossBaseAttributeCanUseConfig = COMMON.BossBaseAttributeCanUseConfig.get();
        RaidBossCanIncreaseSummonCountBasedOnPlayerCount = COMMON.RaidBossCanIncreaseSummonCountBasedOnPlayerCount.get();
        RaidBossCanIncreaseSummonCountBasedOnPlayerCountFindReach = COMMON.RaidBossCanIncreaseSummonCountBasedOnPlayerCountFindReach.get();
        RaidBossCanIncreaseSummonCountBasedOnPlayerCountMaxCount = COMMON.RaidBossCanIncreaseSummonCountBasedOnPlayerCountMaxCount.get();
        RaidBossCanIncreaseSummonCountBasedOnPlayerCountContainAllBoss = COMMON.RaidBossCanIncreaseSummonCountBasedOnPlayerCountContainAllBoss.get();
        RaidBossCanIncreaseSummonCountBasedOnPlayerCountContainBoss = COMMON.RaidBossCanIncreaseSummonCountBasedOnPlayerCountContainBoss.get();
        BossHasPercentageDamage = COMMON.BossHasPercentageDamage.get();
        BossHasDamageCap = COMMON.BossHasDamageCap.get();
        BossHasDamageCooldownTick = COMMON.BossHasDamageCooldownTick.get();
        //猪灵袭击下界传送门
        PiglinRaidNetherPortalBossBarColor = COMMON.PiglinRaidNetherPortalBossBarColor.get();
        PiglinRaidNetherPortalBossBarNameColor = COMMON.PiglinRaidNetherPortalBossBarNameColor.get();
        PiglinRaidNetherPortalMaxHealth = COMMON.PiglinRaidNetherPortalMaxHealth.get();
        PiglinRaidNetherPortalMeleeDamage = COMMON.PiglinRaidNetherPortalMeleeDamage.get();
        PiglinRaidNetherPortalArmor = COMMON.PiglinRaidNetherPortalArmor.get();
        PiglinRaidNetherPortalMovementSpeed = COMMON.PiglinRaidNetherPortalMovementSpeed.get();
        PiglinRaidNetherPortalAttackKnockback = COMMON.PiglinRaidNetherPortalAttackKnockback.get();
        PiglinRaidNetherPortalKnockbackResistance = COMMON.PiglinRaidNetherPortalKnockbackResistance.get();
        PiglinRaidNetherPortalRoundTime = COMMON.PiglinRaidNetherPortalRoundTime.get();
        PiglinRaidNetherPortalLevelAddRoundTime = COMMON.PiglinRaidNetherPortalLevelAddRoundTime.get();
        PiglinRaidNetherPortalAttackPercentage = COMMON.PiglinRaidNetherPortalAttackPercentage.get();
        PiglinRaidNetherPortalMagicAttackPercentage = COMMON.PiglinRaidNetherPortalMagicAttackPercentage.get();
        PiglinRaidNetherPortalDamageCap = COMMON.PiglinRaidNetherPortalDamageCap.get();
        PiglinRaidNetherPortalDamageCooldownTick = COMMON.PiglinRaidNetherPortalDamageCooldownTick.get();
        //黑金军主
        TheBlackGoldMarshalBossBarColor = COMMON.TheBlackGoldMarshalBossBarColor.get();
        TheBlackGoldMarshalBossBarNameColor = COMMON.TheBlackGoldMarshalBossBarNameColor.get();
        TheBlackGoldMarshalMaxHealth = COMMON.TheBlackGoldMarshalMaxHealth.get();
        TheBlackGoldMarshalMeleeDamage = COMMON.TheBlackGoldMarshalMeleeDamage.get();
        TheBlackGoldMarshalArmor = COMMON.TheBlackGoldMarshalArmor.get();
        TheBlackGoldMarshalMovementSpeed = COMMON.TheBlackGoldMarshalMovementSpeed.get();
        TheBlackGoldMarshalAttackKnockback = COMMON.TheBlackGoldMarshalAttackKnockback.get();
        TheBlackGoldMarshalKnockbackResistance = COMMON.TheBlackGoldMarshalKnockbackResistance.get();
        TheBlackGoldMarshalCrackCooldown = COMMON.TheBlackGoldMarshalCrackCooldown.get();
        TheBlackGoldMarshalShootCooldown = COMMON.TheBlackGoldMarshalShootCooldown.get();
        TheBlackGoldMarshalLeadCooldown = COMMON.TheBlackGoldMarshalLeadCooldown.get();
        TheBlackGoldMarshalDropEquipmentChance = COMMON.TheBlackGoldMarshalDropEquipmentChance.get();
        TheBlackGoldMarshalSpellNormalLevel = COMMON.TheBlackGoldMarshalSpellNormalLevel.get();
        TheBlackGoldMarshalBreakBlockCooldown = COMMON.TheBlackGoldMarshalBreakBlockCooldown.get();
        TheBlackGoldMarshalAttackPercentage = COMMON.TheBlackGoldMarshalAttackPercentage.get();
        TheBlackGoldMarshalMagicAttackPercentage = COMMON.TheBlackGoldMarshalMagicAttackPercentage.get();
        TheBlackGoldMarshalDamageCap = COMMON.TheBlackGoldMarshalDamageCap.get();
        TheBlackGoldMarshalDamageCooldownTick = COMMON.TheBlackGoldMarshalDamageCooldownTick.get();
    }

    public static void bakeClientConfig() {
    }

    @SubscribeEvent
    public static void onModConfigEvent(final ModConfigEvent.Loading configEvent) {
        if (configEvent.getConfig().getSpec() == OtherMainConfig.COMMON_SPEC) {
            bakeCommonConfig();
        } else if (configEvent.getConfig().getSpec() == OtherMainConfig.CLIENT_SPEC) {
            bakeClientConfig();
        }
    }

    public static class CommonConfig {
        public final ForgeConfigSpec.DoubleValue NetherSiphonUsefulToPiglinMaxReach;
        public final ForgeConfigSpec.DoubleValue NetherSiphonLinkStationLinkMaxReach;
        public final ForgeConfigSpec.DoubleValue NetherSiphonLinkStationMutuallyInfluenceReach;

        public final ForgeConfigSpec.BooleanValue BossBaseAttributeCanUseConfig;
        public final ForgeConfigSpec.BooleanValue RaidBossCanIncreaseSummonCountBasedOnPlayerCount;
        public final ForgeConfigSpec.DoubleValue RaidBossCanIncreaseSummonCountBasedOnPlayerCountFindReach;
        public final ForgeConfigSpec.IntValue RaidBossCanIncreaseSummonCountBasedOnPlayerCountMaxCount;
        public final ForgeConfigSpec.BooleanValue RaidBossCanIncreaseSummonCountBasedOnPlayerCountContainAllBoss;
        public final ForgeConfigSpec.ConfigValue<List<String>> RaidBossCanIncreaseSummonCountBasedOnPlayerCountContainBoss;
        public final ForgeConfigSpec.ConfigValue<List<String>> BossHasPercentageDamage;
        public final ForgeConfigSpec.ConfigValue<List<String>> BossHasDamageCap;
        public final ForgeConfigSpec.ConfigValue<List<String>> BossHasDamageCooldownTick;
        //猪灵袭击下界传送门
        public final ForgeConfigSpec.IntValue PiglinRaidNetherPortalBossBarColor;
        public final ForgeConfigSpec.IntValue PiglinRaidNetherPortalBossBarNameColor;
        public final ForgeConfigSpec.DoubleValue PiglinRaidNetherPortalMaxHealth;
        public final ForgeConfigSpec.DoubleValue PiglinRaidNetherPortalMeleeDamage;
        public final ForgeConfigSpec.DoubleValue PiglinRaidNetherPortalArmor;
        public final ForgeConfigSpec.DoubleValue PiglinRaidNetherPortalMovementSpeed;
        public final ForgeConfigSpec.DoubleValue PiglinRaidNetherPortalAttackKnockback;
        public final ForgeConfigSpec.DoubleValue PiglinRaidNetherPortalKnockbackResistance;
        public final ForgeConfigSpec.DoubleValue PiglinRaidNetherPortalRoundTime;
        public final ForgeConfigSpec.DoubleValue PiglinRaidNetherPortalLevelAddRoundTime;
        public final ForgeConfigSpec.DoubleValue PiglinRaidNetherPortalAttackPercentage;
        public final ForgeConfigSpec.DoubleValue PiglinRaidNetherPortalMagicAttackPercentage;
        public final ForgeConfigSpec.DoubleValue PiglinRaidNetherPortalDamageCap;
        public final ForgeConfigSpec.DoubleValue PiglinRaidNetherPortalDamageCooldownTick;
        //黑金军主
        public final ForgeConfigSpec.IntValue TheBlackGoldMarshalBossBarColor;
        public final ForgeConfigSpec.IntValue TheBlackGoldMarshalBossBarNameColor;
        public final ForgeConfigSpec.DoubleValue TheBlackGoldMarshalMaxHealth;
        public final ForgeConfigSpec.DoubleValue TheBlackGoldMarshalMeleeDamage;
        public final ForgeConfigSpec.DoubleValue TheBlackGoldMarshalArmor;
        public final ForgeConfigSpec.DoubleValue TheBlackGoldMarshalMovementSpeed;
        public final ForgeConfigSpec.DoubleValue TheBlackGoldMarshalAttackKnockback;
        public final ForgeConfigSpec.DoubleValue TheBlackGoldMarshalKnockbackResistance;
        public final ForgeConfigSpec.DoubleValue TheBlackGoldMarshalCrackCooldown;
        public final ForgeConfigSpec.DoubleValue TheBlackGoldMarshalShootCooldown;
        public final ForgeConfigSpec.DoubleValue TheBlackGoldMarshalLeadCooldown;
        public final ForgeConfigSpec.DoubleValue TheBlackGoldMarshalDropEquipmentChance;
        public final ForgeConfigSpec.IntValue TheBlackGoldMarshalSpellNormalLevel;
        public final ForgeConfigSpec.DoubleValue TheBlackGoldMarshalBreakBlockCooldown;
        public final ForgeConfigSpec.DoubleValue TheBlackGoldMarshalAttackPercentage;
        public final ForgeConfigSpec.DoubleValue TheBlackGoldMarshalMagicAttackPercentage;
        public final ForgeConfigSpec.DoubleValue TheBlackGoldMarshalDamageCap;
        public final ForgeConfigSpec.DoubleValue TheBlackGoldMarshalDamageCooldownTick;

        public CommonConfig(ForgeConfigSpec.Builder builder) {
            builder.push(" ");
            builder.pop();

            builder.push("Main");
            NetherSiphonUsefulToPiglinMaxReach = builder.comment("Nether Siphon Core Useful To Piglin Max Reach")
                    .defineInRange("下界汲取器对猪灵生效最大距离", 64, 0, Double.MAX_VALUE);
            NetherSiphonLinkStationLinkMaxReach = builder.comment("Nether Siphon Link Station Link Max Reach")
                    .defineInRange("下界汲取连接站连接最大距离", 12, 0, Double.MAX_VALUE);
            NetherSiphonLinkStationMutuallyInfluenceReach = builder.comment("Nether Siphon Link Station Mutually Influence Reach")
                    .defineInRange("下界汲取连接站互相影响距离", 12, 0, Double.MAX_VALUE);
            builder.pop();

            builder.push("Attributes");
            builder.push("Boss");

            BossBaseAttributeCanUseConfig = builder.comment("Boss Base Attribute Can Use Config")
                    .define("Boss基础属性可以被配置影响", true);
            RaidBossCanIncreaseSummonCountBasedOnPlayerCount = builder.comment("Raid Boss Can Increase Summon Count Based On Player Count")
                    .define("袭击类boss可以依靠玩家数量提升生成数量", false);
            RaidBossCanIncreaseSummonCountBasedOnPlayerCountFindReach = builder.comment("Raid Boss Can Increase Summon Count Based On Player Count Find Reach")
                    .defineInRange("袭击类boss可以依靠玩家数量提升生成数量依照范围", 128.0, 0.0, Double.MAX_VALUE);
            RaidBossCanIncreaseSummonCountBasedOnPlayerCountMaxCount = builder.comment("Raid Boss Can Increase Summon Count Based On Player Count Max Count")
                    .defineInRange("袭击类boss可以依靠玩家数量提升生成数量最大数量", 6, 0, Integer.MAX_VALUE);
            RaidBossCanIncreaseSummonCountBasedOnPlayerCountContainAllBoss = builder.comment("Raid Boss Can Increase Summon Count Based On Player Count Contain All Boss")
                    .define("袭击类boss可以依靠玩家数量提升生成数量包含所有boss", true);
            RaidBossCanIncreaseSummonCountBasedOnPlayerCountContainBoss = builder.comment("Raid Boss Can Increase Summon Count Based On Player Count Contain Boss")
                    .define("袭击类boss可以依靠玩家数量提升生成数量包含boss", Lists.newArrayList(
                            "meowhem:mira_bell"));
            BossHasPercentageDamage = builder.comment("Boss Has Percentage Damage")
                    .define("Boss拥有百分比伤害", Lists.newArrayList());
            BossHasDamageCap = builder.comment("Boss Has Damage Cap")
                    .define("Boss拥有限伤", Lists.newArrayList());
            BossHasDamageCooldownTick = builder.comment("Boss Has Damage Cooldown Tick")
                    .define("Boss拥有无敌帧", Lists.newArrayList());
            //猪灵袭击下界传送门
            builder.push("Piglin Raid Nether Portal");
            PiglinRaidNetherPortalBossBarColor = builder.comment("Piglin Raid Nether Portal Boss Bar Color")
                    .defineInRange("猪灵袭击下界传送门Boss血条颜色", 0, 0, 6);
            PiglinRaidNetherPortalBossBarNameColor = builder.comment("Piglin Raid Nether Portal Boss Bar Name Color")
                    .defineInRange("猪灵袭击下界传送门Boss血条名字颜色", 0x2a004b, 0, Integer.MAX_VALUE);
            PiglinRaidNetherPortalMaxHealth = builder.comment("[Boss] Max Health : 300.0")
                    .defineInRange("Piglin Raid Nether Portal Max Health", 300, 0.0, Double.MAX_VALUE);
            PiglinRaidNetherPortalMeleeDamage = builder.comment("[Boss] Melee Damage : 6.0")
                    .defineInRange("Piglin Raid Nether Portal Melee Damage", 6.0, 0.0, Double.MAX_VALUE);
            PiglinRaidNetherPortalArmor = builder.comment("[Boss] Armor : 5.0")
                    .defineInRange("Piglin Raid Nether Portal Armor", 5.0, 0.0, Double.MAX_VALUE);
            PiglinRaidNetherPortalMovementSpeed = builder.comment("[Boss] Movement Speed : 0.0")
                    .defineInRange("Piglin Raid Nether Portal Movement Speed", 0.0, 0.0, Double.MAX_VALUE);
            PiglinRaidNetherPortalAttackKnockback = builder.comment("[Boss] Attack Knockback : 0.0")
                    .defineInRange("Piglin Raid Nether Portal Attack Knockback", 0.0, 0.0, Double.MAX_VALUE);
            PiglinRaidNetherPortalKnockbackResistance = builder.comment("[Boss] Knockback Resistance : 1.0")
                    .defineInRange("Piglin Raid Nether Portal Knockback Resistance", 1.0, 0.0, Double.MAX_VALUE);
            PiglinRaidNetherPortalRoundTime = builder.comment("[Boss] Round Time : 300.0")
                    .defineInRange("Piglin Raid Nether Portal Round Time", 300.0, 0.0, Double.MAX_VALUE);
            PiglinRaidNetherPortalLevelAddRoundTime = builder.comment("[Boss] Level Add Round Time : 15.0")
                    .defineInRange("Piglin Raid Nether Portal Level Add Round Time", 15.0, 0.0, Double.MAX_VALUE);
            PiglinRaidNetherPortalAttackPercentage = builder.comment("[Boss] Attack Percentage : 5.0")
                    .defineInRange("Piglin Raid Nether Portal Attack Percentage", 5.0, 0.0, Double.MAX_VALUE);
            PiglinRaidNetherPortalMagicAttackPercentage = builder.comment("[Boss] Magic Attack Percentage : 5.0")
                    .defineInRange("Piglin Raid Nether Portal Magic Attack Percentage", 5.0, 0.0, Double.MAX_VALUE);
            PiglinRaidNetherPortalDamageCap = builder.comment("[Boss] Damage Cap : 20")
                    .defineInRange("Piglin Raid Nether Portal Damage Cap", 20, 0.0, Double.MAX_VALUE);
            PiglinRaidNetherPortalDamageCooldownTick = builder.comment("[Boss] Damage Cooldown Tick : 20")
                    .defineInRange("Piglin Raid Nether Portal Damage Cooldown Tick", 20, 0.0, Double.MAX_VALUE);
            builder.pop();
            //黑金军主
            builder.push("The Black Gold Marshal");
            TheBlackGoldMarshalBossBarColor = builder.comment("The Black Gold Marshal Boss Bar Color")
                    .defineInRange("黑金军主Boss血条颜色", 4, 0, 6);
            TheBlackGoldMarshalBossBarNameColor = builder.comment("The Black Gold Marshal Boss Bar Name Color")
                    .defineInRange("黑金军主Boss血条名字颜色", 0xd38332, 0, Integer.MAX_VALUE);
            TheBlackGoldMarshalMaxHealth = builder.comment("[Boss] Max Health : 320.0")
                    .defineInRange("The Black Gold Marshal Max Health", 320.0, 0.0, Double.MAX_VALUE);
            TheBlackGoldMarshalMeleeDamage = builder.comment("[Boss] Melee Damage : 12.0")
                    .defineInRange("The Black Gold Marshal Melee Damage", 12.0, 0.0, Double.MAX_VALUE);
            TheBlackGoldMarshalArmor = builder.comment("[Boss] Armor : 5.0")
                    .defineInRange("The Black Gold Marshal Armor", 5.0, 0.0, Double.MAX_VALUE);
            TheBlackGoldMarshalMovementSpeed = builder.comment("[Boss] Movement Speed : 0.40")
                    .defineInRange("The Black Gold Marshal Movement Speed", 0.40, 0.0, Double.MAX_VALUE);
            TheBlackGoldMarshalAttackKnockback = builder.comment("[Boss] Attack Knockback : 0.5")
                    .defineInRange("The Black Gold Marshal Attack Knockback", 0.5, 0.0, Double.MAX_VALUE);
            TheBlackGoldMarshalKnockbackResistance = builder.comment("[Boss] Knockback Resistance : 1.0")
                    .defineInRange("The Black Gold Marshal Knockback Resistance", 1.0, 0.0, Double.MAX_VALUE);
            TheBlackGoldMarshalCrackCooldown = builder.comment("[Boss] Block Gold Ground Crack Cooldown : 18.0")
                    .defineInRange("The Black Gold Marshal Block Gold Ground Crack Cooldown", 18.0, 0.0, Double.MAX_VALUE);
            TheBlackGoldMarshalShootCooldown = builder.comment("[Boss] Crossbow Shoot Cooldown : 18.0")
                    .defineInRange("The Black Gold Marshal Crossbow Shoot Cooldown", 18.0, 0.0, Double.MAX_VALUE);
            TheBlackGoldMarshalLeadCooldown = builder.comment("[Boss] Block Gold Order Cooldown : 60.0")
                    .defineInRange("The Black Gold Marshal Block Gold  Order Cooldown", 60.0, 0.0, Double.MAX_VALUE);
            TheBlackGoldMarshalDropEquipmentChance = builder.comment("[Boss] Drop Equipment Chance : 0.0")
                    .defineInRange("The Black Gold Marshal Drop Equipment Chance", 0.0, 0.0, Double.MAX_VALUE);
            TheBlackGoldMarshalSpellNormalLevel = builder.comment("[Boss] Spell Normal Level : 5")
                    .defineInRange("The Black Gold Marshal Spell Normal Level", 5, 0, Integer.MAX_VALUE);
            TheBlackGoldMarshalBreakBlockCooldown = builder.comment("[Boss] Break Block Cooldown : 1.5")
                    .defineInRange("The Black Gold Marshal Break Block Cooldown", 1.5, 0.0, Double.MAX_VALUE);
            TheBlackGoldMarshalAttackPercentage = builder.comment("[Boss] Attack Percentage : 5.0")
                    .defineInRange("The Black Gold Marshal Attack Percentage", 5.0, 0.0, Double.MAX_VALUE);
            TheBlackGoldMarshalMagicAttackPercentage = builder.comment("[Boss] Magic Attack Percentage : 5.0")
                    .defineInRange("The Black Gold Marshal Magic Attack Percentage", 5.0, 0.0, Double.MAX_VALUE);
            TheBlackGoldMarshalDamageCap = builder.comment("[Boss] Damage Cap : 22")
                    .defineInRange("The Black Gold Marshal Damage Cap", 22, 0.0, Double.MAX_VALUE);
            TheBlackGoldMarshalDamageCooldownTick = builder.comment("[Boss] Damage Cooldown Tick : 20")
                    .defineInRange("The Black Gold Marshal Damage Cooldown Tick", 20, 0.0, Double.MAX_VALUE);
            builder.pop();
            builder.pop();
            builder.pop();
        }
    }

    public static class ClientConfig {
        public ClientConfig(ForgeConfigSpec.Builder builder) {
        }
    }
}