package com.yolp900.charming.reference;

public class LibOreDict {

    /**
     * Vanilla OreDicts
     */
    // Tree- and wood-related things
    public static final String LOG_WOOD = "logWood";
    public static final String PLANK_WOOD = "plankWood";
    public static final String SLAB_WOOD = "slabWood";
    public static final String STAIR_WOOD = "stairWood";
    public static final String STICK_WOOD = "stickWood";
    public static final String SAPLING = "treeSapling";
    public static final String LEAVES = "treeLeaves";
    public static final String VINES = "vine";
    // Ores
    public static final String ORE_GOLD = "oreGold";
    public static final String ORE_IRON = "oreIron";
    public static final String ORE_LAPIS = "oreLapis";
    public static final String ORE_DIAMOND = "oreDiamond";
    public static final String ORE_REDSTONE = "oreRedstone";
    public static final String ORE_EMERALD = "oreEmerald";
    public static final String ORE_QUARTZ = "oreQuartz";
    public static final String ORE_COAL = "oreCoal";
    // Ingots/nuggets
    public static final String INGOT_IRON = "ingotIron";
    public static final String INGOT_GOLD = "ingotGold";
    public static final String BRICK = "ingotBrick";
    public static final String BRICK_NETHER = "ingotBrickNether";
    public static final String NUGGET_GOLD = "nuggetGold";
    public static final String NUGGET_IRON = "nuggetIron";
    // Gems and dusts
    public static final String DIAMOND = "gemDiamond";
    public static final String EMERALD = "gemEmerald";
    public static final String NETHER_QUARTZ = "gemQuartz";
    public static final String PRISMARINE_SHARD = "gemPrismarine";
    public static final String PRISMARINE_CRYSTAL = "dustPrismarine";
    public static final String DUST_REDSTONE = "dustRedstone";
    public static final String DUST_GLOWSTONE = "dustGlowstone";
    public static final String GEM_LAPIS = "gemLapis";
    // Storage blocks
    public static final String BLOCK_GOLD = "blockGold";
    public static final String BLOCK_IRON = "blockIron";
    public static final String BLOCK_LAPIS = "blockLapis";
    public static final String BLOCK_DIAMOND = "blockDiamond";
    public static final String BLOCK_REDSTONE = "blockRedstone";
    public static final String BLOCK_EMERALD = "blockEmerald";
    public static final String BLOCK_QUARTZ = "blockQuartz";
    public static final String BLOCK_COAL = "blockCoal";
    // Crops
    public static final String CROP_WHEAT = "cropWheat";
    public static final String CROP_POTATO = "cropPotato";
    public static final String CROP_CARROT = "cropCarrot";
    public static final String NETHER_WART = "cropNetherWart";
    public static final String SUGARCANE = "sugarcane";
    public static final String CACTUS = "blockCactus";
    // Misc Materials
    public static final String DYE = "dye";
    public static final String PAPER = "paper";
    // Mob Drops
    public static final String SLIMEBALL = "slimeball";
    public static final String ENDERPEARL = "enderpearl";
    public static final String BONE = "bone";
    public static final String GUNPOWDER = "gunpowder";
    public static final String STRING = "string";
    public static final String NETHER_STAR = "netherStar";
    public static final String LEATHER = "leather";
    public static final String FEATHER = "feather";
    public static final String EGG = "egg";
    // Records
    public static final String RECORD = "record";
    // Blocks
    public static final String DIRT = "dirt";
    public static final String GRASS = "grass";
    public static final String STONE = "stone";
    public static final String COBBLESTONE = "cobblestone";
    public static final String GRAVEL = "gravel";
    public static final String SAND = "sand";
    public static final String SANDSTONE = "sandstone";
    public static final String NETHERRACK = "netherrack";
    public static final String OBSIDIAN = "obsidian";
    public static final String BLOCK_GLOWSTONE = "glowstone";
    public static final String ENDSTONE = "endstone";
    public static final String TORCH = "torch";
    public static final String WORKBENCH = "workbench";
    public static final String BLOCK_SLIME = "blockSlime";
    public static final String BLOCK_PRISMARINE = "blockPrismarine";
    public static final String BLOCK_PRISMARINE_BRICK = "blockPrismarineBrick";
    public static final String BLOCK_PRISMARINE_DARK = "blockPrismarineDark";
    public static final String STONE_GRANITE = "stoneGranite";
    public static final String STONE_GRANITE_POLISHED = "stoneGranitePolished";
    public static final String STONE_DIORITE = "stoneDiorite";
    public static final String STONE_DIORITE_POLISHED = "stoneDioritePolished";
    public static final String STONE_ANDESITE = "stoneAndesite";
    public static final String STONE_ANDESITE_POLISHED = "stoneAndesitePolished";
    public static final String COLORLESS_GLASS = "blockGlassColorless";
    public static final String BLOCK_GLASS = "blockGlass";
    public static final String PANE_GLASS_COLORLESS = "paneGlassColorless";
    public static final String PANE_GLASS = "paneGlass";
    // Chests
    public static final String ALL_CHESTS = "chest";
    public static final String WOOD_CHEST = "chestWood";
    public static final String ENDER_CHEST = "chestEnder";
    public static final String TRAPPED_CHEST = "chestTrapped";

    public static String DYES(int meta) {
        return "dye" + LibMisc.COLORS[meta];
    }

    /**
     * Charming OreDicts
     */
    // Vanilla entries that should really be in forge
    public static final String BRICK_STONE = "brickStone";

}
