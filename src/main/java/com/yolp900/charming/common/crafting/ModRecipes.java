package com.yolp900.charming.common.crafting;

import com.google.common.collect.Lists;
import com.yolp900.charming.api.CharmingAPI;
import com.yolp900.charming.api.crafting.constructiontable.RecipeConstructionTable;
import com.yolp900.charming.api.crafting.wandinteraction.RecipeWandInteraction;
import com.yolp900.charming.api.crafting.wandinteraction.RecipeWandInteractionBlock;
import com.yolp900.charming.api.crafting.wandinteraction.RecipeWandInteractionItem;
import com.yolp900.charming.api.crafting.wandinteraction.TransmutationStructure;
import com.yolp900.charming.api.tiles.IConvertible;
import com.yolp900.charming.api.tiles.IInvertible;
import com.yolp900.charming.common.blocks.BlockFlower;
import com.yolp900.charming.common.blocks.ModBlocks;
import com.yolp900.charming.common.items.ItemCore;
import com.yolp900.charming.common.items.ItemEffectStone;
import com.yolp900.charming.common.items.ItemTransmutationWand;
import com.yolp900.charming.common.items.ModItems;
import com.yolp900.charming.config.ModConfig;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.yolp900.charming.reference.LibOreDict.*;

public class ModRecipes {

    public static void registerRecipes() {
        Crafting.registerCraftingRecipes();
        ConstructionTable.registerConstructionTableRecipes();
        WandInteraction.registerWandInteractions();
    }

    public static class Crafting {

        static void registerCraftingRecipes() {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.SlotUpgrade, 1), " c ", "cbc", " c ", 'c', COBBLESTONE, 'b', Blocks.STONE_BUTTON));
            if (ModConfig.CRAFTABLE_CONSTRUCTION_TABLE.getValue()) {
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.ConstructionTable, 1), " c ", "ctc", " c ", 'c', COBBLESTONE, 't', WORKBENCH));
            }
            GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModBlocks.TintedPlanks, 4), new ItemStack(ModBlocks.TintedLog, 1)));
        }

    }

    public static class ConstructionTable {

        public static RecipeConstructionTable StoneTransmutationWand;
        public static RecipeConstructionTable BlankCore;
        public static RecipeConstructionTable NoneEffectStone;
        public static RecipeConstructionTable InversionEffectStone;
        public static RecipeConstructionTable ConversionEffectStone;
        public static RecipeConstructionTable Elevator;
        public static RecipeConstructionTable Levitator;
        public static RecipeConstructionTable Attractor;
        public static RecipeConstructionTable Impeller;

        public static RecipeConstructionTable[] TreeTransmutation = new RecipeConstructionTable[BlockSapling.TYPE.getAllowedValues().size()];
        public static RecipeConstructionTable[] TintedTreeTransmutation = new RecipeConstructionTable[BlockSapling.TYPE.getAllowedValues().size()];

        static void registerConstructionTableRecipes() {
            StoneTransmutationWand = registerRecipe(new ItemStack(ModItems.TransmutationWand, 1, ItemTransmutationWand.EnumTypes.Stone.ordinal()), obj(new ItemStack(ModItems.TransmutationWand, 1, ItemTransmutationWand.EnumTypes.Wood.ordinal())), obj(COBBLESTONE, Items.FLINT, STONE, NUGGET_GOLD, NUGGET_GOLD), true, false);
            BlankCore = registerRecipe(new ItemStack(ModItems.Core, 1, ItemCore.EnumTypes.Blank.ordinal()), obj(null, STONE, null, STONE, STONE, STONE, null, STONE, null), obj(INGOT_IRON, DUST_REDSTONE, NUGGET_GOLD), false, false);
            NoneEffectStone = registerRecipe(new ItemStack(ModItems.EffectStone, 1, ItemEffectStone.EnumStoneEffects.None.ordinal()), obj(PLANK_WOOD, null, PLANK_WOOD, null, COBBLESTONE, null, PLANK_WOOD, null, PLANK_WOOD), obj(NUGGET_GOLD, INGOT_IRON, DUST_REDSTONE), false, false);
            InversionEffectStone = registerRecipe(new ItemStack(ModItems.EffectStone, 1, ItemEffectStone.EnumStoneEffects.Inversion.ordinal()), obj(new ItemStack(ModItems.EffectStone, 1, ItemEffectStone.EnumStoneEffects.None.ordinal()), new ItemStack(ModItems.Core, 1, ItemCore.EnumTypes.Redstone.ordinal())), obj(DUST_REDSTONE, DUST_REDSTONE), true, false);
            ConversionEffectStone = registerRecipe(new ItemStack(ModItems.EffectStone, 1, ItemEffectStone.EnumStoneEffects.Conversion.ordinal()), obj(new ItemStack(ModItems.EffectStone, 1, ItemEffectStone.EnumStoneEffects.None.ordinal()), new ItemStack(ModItems.Core, 1, ItemCore.EnumTypes.Motion.ordinal()), new ItemStack(ModItems.Core, 1, ItemCore.EnumTypes.Repulsion.ordinal())), obj(DUST_GLOWSTONE, DUST_GLOWSTONE), true, false);
            Elevator = registerRecipe(new ItemStack(ModBlocks.Elevator, 2), obj(null, ModBlocks.TintedLog, null, ModBlocks.TintedLog, ModItems.HalfPearl, ModBlocks.TintedLog, null, ModBlocks.TintedLog, null), obj(ModItems.HalfPearl), false, false);
            Levitator = registerRecipe(new ItemStack(ModBlocks.Levitator), obj(null, ModBlocks.TintedLog, null, ModBlocks.TintedLog, ModItems.HalfPearl, ModBlocks.TintedLog, DUST_REDSTONE, ModBlocks.TintedLog, DUST_REDSTONE), obj(new ItemStack(ModItems.Core, 1, ItemCore.EnumTypes.Motion.ordinal()), new ItemStack(ModItems.Core, 1, ItemCore.EnumTypes.Repulsion.ordinal())), false, true);
            Attractor = registerRecipe(new ItemStack(ModBlocks.Attractor), obj(null, Blocks.STONE_SLAB, null, Blocks.STONE_SLAB, new ItemStack(ModItems.Core, 1, ItemCore.EnumTypes.Motion.ordinal()), Blocks.STONE_SLAB, null, Blocks.STONE_SLAB, null), obj(DUST_REDSTONE, DUST_REDSTONE), false, false);
            Impeller = registerRecipe(new ItemStack(ModBlocks.Impeller), obj(null, Blocks.STONE_SLAB, null, Blocks.STONE_SLAB, new ItemStack(ModItems.Core, 1, ItemCore.EnumTypes.Repulsion.ordinal()), Blocks.STONE_SLAB, null, Blocks.STONE_SLAB, null), obj(DUST_REDSTONE, DUST_REDSTONE), false, false);

            for (int i = 0; i < BlockSapling.TYPE.getAllowedValues().size(); i++) {
                int j = i + 1;
                if (i == BlockSapling.TYPE.getAllowedValues().size() - 1) {
                    j = 0;
                }
                TreeTransmutation[i] = registerRecipe(new ItemStack(Blocks.SAPLING, 1, j), obj(new ItemStack(Blocks.SAPLING, 1, i)), obj(new ItemStack(ModItems.EffectStone, 1, ItemEffectStone.EnumStoneEffects.Conversion.ordinal())), true, true);
                TintedTreeTransmutation[i] = registerRecipe(new ItemStack(ModBlocks.TintedSapling), obj(new ItemStack(Blocks.SAPLING, 1, i), DYES(0), DYES(0)), obj(new ItemStack(ModItems.EffectStone, 1, ItemEffectStone.EnumStoneEffects.Conversion.ordinal())), true, true);
            }
        }

        private static RecipeConstructionTable registerRecipe(@Nonnull ItemStack output, List<Object> gridInputs, List<Object> secInputs, boolean shapeless, boolean infusion) {
            return CharmingAPI.ConstructionTable.registerConstructionTableRecipe(output, gridInputs, secInputs, shapeless, infusion);
        }

        private static void registerRecipe(@Nonnull RecipeConstructionTable recipe) {
            CharmingAPI.ConstructionTable.registerConstructionTableRecipe(recipe);
        }
    }

    public static class WandInteraction {

        public static RecipeWandInteractionItem WoodenTransmutationWand;
        public static RecipeWandInteractionBlock ConstructionTable;
        public static RecipeWandInteractionItem EnderPearlSplitting;
        public static RecipeWandInteractionItem RedstoneCore;
        public static RecipeWandInteractionItem MotionCore;
        public static RecipeWandInteractionItem RepulsionCore;
        public static RecipeWandInteractionItem DesertRose;
        public static RecipeWandInteractionItem RottenFleshToLeather;

        public static RecipeWandInteractionBlock TintedSapling[] = new RecipeWandInteractionBlock[BlockSapling.TYPE.getAllowedValues().size()];

        public static RecipeWandInteraction BlockInversion = new RecipeWandInteraction(TransmutationStructure.EMPTY, obj(new ItemStack(ModItems.EffectStone, 1, ItemEffectStone.EnumStoneEffects.Inversion.ordinal())), null, false, 1) {
            @Override
            public List<EntityItem> handleInteractionReturnWorldItems(World world, EntityPlayer player, ItemStack stack, BlockPos pos, List<EntityItem> entityItems) {
                TileEntity tile = world.getTileEntity(pos);
                if (tile instanceof IInvertible) {
                    IInvertible invertibleTile = (IInvertible) tile;
                    if (invertibleTile.isInverted()) {
                        invertibleTile.setInverted(false);
                    } else {
                        invertibleTile.setInverted(true);
                    }
                }
                return entityItems;
            }
        };

        public static RecipeWandInteraction BlockConversion = new RecipeWandInteraction(TransmutationStructure.EMPTY, obj(new ItemStack(ModItems.EffectStone, 1, ItemEffectStone.EnumStoneEffects.Conversion.ordinal())), null, false, 1) {
            @Override
            public List<EntityItem> handleInteractionReturnWorldItems(World world, EntityPlayer player, ItemStack stack, BlockPos pos, List<EntityItem> entityItems) {
                IBlockState state = world.getBlockState(pos);
                TileEntity tile = world.getTileEntity(pos);
                IConvertible convertible = null;
                if (state.getBlock() instanceof IConvertible) {
                    convertible = (IConvertible) state.getBlock();
                } else if (tile instanceof IConvertible) {
                    convertible = (IConvertible) tile;
                }
                if (convertible != null) {
                    convertible.convertState(world, pos, player);
                }
                return entityItems;
            }
        };

        public static List<RecipeWandInteraction> infusionParticleList = new ArrayList<>();
        public static List<RecipeWandInteraction> constructionParticleList = new ArrayList<>();

        static void registerWandInteractions() {
            WoodenTransmutationWand = registerItemRecipe(stack(new ItemStack(ModItems.TransmutationWand, 1, ItemTransmutationWand.EnumTypes.Wood.ordinal())), TransmutationStructure.EMPTY, obj(STICK_WOOD, NUGGET_GOLD, NUGGET_GOLD), obj(STICK_WOOD, NUGGET_GOLD, NUGGET_GOLD), false, -1);
            ConstructionTable = registerBlockRecipe(ModBlocks.ConstructionTable.getDefaultState(), new TransmutationStructure(Blocks.CRAFTING_TABLE.getDefaultState(), bs(groupBS(Blocks.COBBLESTONE.getDefaultState(), 4)), null, Blocks.COBBLESTONE.getDefaultState()), obj(COBBLESTONE), obj(COBBLESTONE), false, 0);
            EnderPearlSplitting = registerItemRecipe(stack(new ItemStack(ModItems.HalfPearl, 2)), TransmutationStructure.EMPTY, obj(ENDERPEARL), obj(ENDERPEARL), false, 0);
            RedstoneCore = registerItemRecipe(stack(new ItemStack(ModItems.Core, 1, ItemCore.EnumTypes.Redstone.ordinal())), TransmutationStructure.EMPTY, obj(new ItemStack(ModItems.Core, 1, ItemCore.EnumTypes.Blank.ordinal()), Blocks.LEVER, Blocks.REDSTONE_TORCH), obj(new ItemStack(ModItems.Core, 1, ItemCore.EnumTypes.Blank.ordinal()), Blocks.LEVER, Blocks.REDSTONE_TORCH), false, 1);
            MotionCore = registerItemRecipe(stack(new ItemStack(ModItems.Core, 1, ItemCore.EnumTypes.Motion.ordinal())), TransmutationStructure.EMPTY, obj(new ItemStack(ModItems.Core, 1, ItemCore.EnumTypes.Blank.ordinal()), new ItemStack(ModItems.HalfPearl, 1), FEATHER), obj(new ItemStack(ModItems.Core, 1, ItemCore.EnumTypes.Blank.ordinal()), new ItemStack(ModItems.HalfPearl, 1), FEATHER), false, 1);
            RepulsionCore = registerItemRecipe(stack(new ItemStack(ModItems.Core, 1, ItemCore.EnumTypes.Repulsion.ordinal())), TransmutationStructure.EMPTY, obj(new ItemStack(ModItems.Core, 1, ItemCore.EnumTypes.Blank.ordinal()), new ItemStack(ModItems.HalfPearl, 1), OBSIDIAN), obj(new ItemStack(ModItems.Core, 1, ItemCore.EnumTypes.Blank.ordinal()), new ItemStack(ModItems.HalfPearl, 1), OBSIDIAN), false, 1);
            DesertRose = registerItemRecipe(stack(new ItemStack(ModBlocks.Flower, 1, BlockFlower.EnumTypes.DesertRose.getMetadata())), new TransmutationStructure(Blocks.SAND.getDefaultState(), bs(groupBS(Blocks.SAND.getDefaultState(), 4)), bs(groupBS(Blocks.SAND.getDefaultState(), 4)), null), obj(Blocks.YELLOW_FLOWER, new ItemStack(ModItems.EffectStone, 1, ItemEffectStone.EnumStoneEffects.Conversion.ordinal())), obj(Blocks.YELLOW_FLOWER), true, 1);
            RottenFleshToLeather = registerItemRecipe(stack(new ItemStack(Items.LEATHER)), TransmutationStructure.EMPTY, obj(new ItemStack(ModItems.EffectStone, 1, ItemEffectStone.EnumStoneEffects.Conversion.ordinal()), new ItemStack(Items.ROTTEN_FLESH, 2)), obj(new ItemStack(Items.ROTTEN_FLESH, 2)), false, 1);

            for (int i = 0; i < BlockSapling.TYPE.getAllowedValues().size(); i++) {
                TintedSapling[i] = registerBlockRecipe(ModBlocks.TintedSapling.getDefaultState(), new TransmutationStructure(Blocks.SAPLING.getDefaultState().withProperty(BlockSapling.TYPE, BlockPlanks.EnumType.values()[i]), null, null, null), obj(new ItemStack(ModItems.EffectStone, 1, ItemEffectStone.EnumStoneEffects.Conversion.ordinal()), DYES(0), DYES(0)), obj(DYES(0), DYES(0)), false, 0);
            }

            registerRecipe(BlockInversion);
            registerRecipe(BlockConversion);

            addAllToList(infusionParticleList, WoodenTransmutationWand, EnderPearlSplitting, TintedSapling, DesertRose);
            addAllToList(constructionParticleList, ConstructionTable);
        }

        private static RecipeWandInteractionBlock registerBlockRecipe(@Nonnull IBlockState output, @Nonnull TransmutationStructure structure, @Nullable List<Object> ingredients, List<Object> ingredientsToRemove, boolean keepAroundBlocks, int minimalWandLevel) {
            return CharmingAPI.WandInteractions.registerBlockInteractionRecipe(output, structure, ingredients, ingredientsToRemove, keepAroundBlocks, minimalWandLevel);
        }

        private static RecipeWandInteractionItem registerItemRecipe(@Nonnull NonNullList<ItemStack> outputs, @Nonnull TransmutationStructure structure, @Nullable List<Object> ingredients, List<Object> ingredientsToRemove, boolean keepAroundBlocks, int minimalWandLevel) {
            return CharmingAPI.WandInteractions.registerItemInteractionRecipe(outputs, structure, ingredients, ingredientsToRemove, keepAroundBlocks, minimalWandLevel);
        }

        private static void registerRecipe(RecipeWandInteraction recipe) {
            CharmingAPI.WandInteractions.registerInteractionRecipe(recipe);
        }

        private static void addAllToList(List<RecipeWandInteraction> list, Object... recipes) {
            for (Object object : recipes) {
                if (object instanceof RecipeWandInteraction) {
                    list.add((RecipeWandInteraction) object);
                } else if (object instanceof RecipeWandInteraction[]) {
                    list.addAll(Arrays.asList(((RecipeWandInteraction[]) object)));
                }
            }
        }

    }

    private static List<Object> obj(Object... inputs) {
        return Lists.newArrayList(inputs);
    }

    private static List<IBlockState> bs(IBlockState... states) {
        return Lists.newArrayList(states);
    }

    private static NonNullList<ItemStack> stack(@Nonnull ItemStack... stacks) {
        NonNullList<ItemStack> ret = NonNullList.create();
        ret.addAll(Arrays.asList(stacks));
        return ret;
    }

    private static Object[] group(Object object, int num) {
        Object[] ret = new Object[num];
        Arrays.fill(ret, 0, num, object);
        return ret;
    }

    private static IBlockState[] groupBS(IBlockState state, int num) {
        IBlockState[] ret = new IBlockState[num];
        Arrays.fill(ret, 0, num, state);
        return ret;
    }

    private static ItemStack[] groupIS(ItemStack stack, int num) {
        ItemStack[] ret = new ItemStack[num];
        Arrays.fill(ret, 0, num, stack);
        return ret;
    }

}
