package com.jerotes.blackgoldalliance.block;

import com.jerotes.blackgoldalliance.init.BGABlockEntityType;
import com.jerotes.blackgoldalliance.init.BGAItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;

public class NetherSiphonCore extends BaseEntityBlock {
	public static final IntegerProperty TYPE = BlockStateProperties.AGE_3;
	public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
	public NetherSiphonCore() {
		super(BlockBehaviour.Properties.of().lightLevel(blockState -> blockState.getValue(BlockStateProperties.AGE_3) == 1 ? 5 : blockState.getValue(BlockStateProperties.AGE_3) == 2 ? 15 : 0).mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(50.0F, 1200.0F).sound(SoundType.NETHERITE_BLOCK));
		this.registerDefaultState(((this.stateDefinition.any()).setValue(TYPE, 0).setValue(OPEN, false)));
	}

	@Override
	public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
		if (player.getItemInHand(InteractionHand.MAIN_HAND).is(BGAItems.GOLDEN_VEINED_BLACKSTONE_TABLET.get())) {
			return InteractionResult.PASS;
		}
		if (player.getItemInHand(InteractionHand.MAIN_HAND).is(BGAItems.DETERRENT_EXPANSION_TABLET.get())) {
			return InteractionResult.PASS;
		}
		super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
		if (level.isClientSide) {
			return InteractionResult.SUCCESS;
		}
		else {
			BlockEntity blockEntity = level.getBlockEntity(blockPos);
			if (blockEntity instanceof NetherSiphonCoreEntity netherSiphonCoreEntity) {
				player.openMenu(netherSiphonCoreEntity);
			}
			return InteractionResult.CONSUME;
		}
	}
	@Override
	public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
		BlockEntity blockentity = serverLevel.getBlockEntity(blockPos);
		if (blockentity instanceof NetherSiphonCoreEntity netherSiphonCoreEntity) {
			netherSiphonCoreEntity.recheckOpen();
		}
	}

	@Override
	public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, LivingEntity livingEntity, ItemStack itemStack) {
		if (itemStack.hasCustomHoverName()) {
			BlockEntity blockentity = level.getBlockEntity(blockPos);
			if (blockentity instanceof NetherSiphonCoreEntity netherSiphonCoreEntity) {
				netherSiphonCoreEntity.setCustomName(itemStack.getHoverName());
			}
		}
	}
	@Override
	public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
		if (!blockState.is(blockState2.getBlock())) {
			BlockEntity blockentity = level.getBlockEntity(blockPos);
			if (blockentity instanceof Container) {
				Containers.dropContents(level, blockPos, (Container)blockentity);
				level.updateNeighbourForOutputSignal(blockPos, this);
			}
			super.onRemove(blockState, level, blockPos, blockState2, bl);
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(TYPE).add(OPEN);
	}

	public int getCoreType(BlockState blockState) {
		return blockState.getValue(TYPE);
	}
	public boolean isCoreOpen(BlockState blockState) {
		return blockState.getValue(OPEN);
	}

	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new NetherSiphonCoreEntity(blockPos, blockState);
	}

	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
		return createTickerHelper(blockEntityType, BGABlockEntityType.NETHER_SIPHON_CORE.get(),
				level.isClientSide() ? NetherSiphonCoreEntity::clientTick : NetherSiphonCoreEntity::serverTick);
	}

	@Override
	public RenderShape getRenderShape(BlockState blockState) {
		return RenderShape.MODEL;
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState blockState) {
		return true;
	}
	@Override
	public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos blockPos) {
		return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(blockPos));
	}

	public VoxelShape getVisualShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
		if (state.getValue(OPEN))
			return Shapes.empty();
		return super.getVisualShape(state, blockGetter, pos, context);
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter level, BlockPos pos) {
		return state.getValue(OPEN) ? 0 : super.getLightBlock(state, level, pos);
	}

	@Override
	public float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
		return state.getValue(OPEN) ? 1.0F : super.getShadeBrightness(state, level, pos);
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
		return state.getValue(OPEN);
	}

	@Override
	public void appendHoverText(ItemStack itemStack, @Nullable BlockGetter blockGetter, List<Component> list, TooltipFlag tooltipFlag) {
		list.add(this.getDisplayName().withStyle(ChatFormatting.GRAY));
		super.appendHoverText(itemStack, blockGetter, list, tooltipFlag);
	}

	public MutableComponent getDisplayName() {
		return Component.translatable(this.getDescriptionId() + ".desc");
	}
}