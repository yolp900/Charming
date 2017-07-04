package com.yolp900.charming.common.crafting;

import com.yolp900.charming.api.CharmingAPI;
import com.yolp900.charming.api.crafting.wandinteraction.RecipeWandInteraction;
import com.yolp900.charming.client.particle.ModParticles;
import com.yolp900.charming.common.network.MessageParticle;
import com.yolp900.charming.common.network.MessageSound;
import com.yolp900.charming.common.network.NetworkHandler;
import com.yolp900.charming.util.SoundHandler;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;
import java.util.Random;

public class InteractionHandler {

    @SubscribeEvent
    public void onPlayerInteraction(PlayerInteractEvent.RightClickBlock event) {
        if (event.getHand() == EnumHand.MAIN_HAND) {
            World world = event.getWorld();
            EntityPlayer player = event.getEntityPlayer();
            ItemStack stack = event.getItemStack();
            BlockPos pos = event.getPos();
            List<EntityItem> itemEntities = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos, pos.add(1, 2, 1)));
            for (RecipeWandInteraction recipe : CharmingAPI.WandInteractions.interactionRecipeList) {
                if (!world.isRemote && recipe.matches(world, player, stack, pos, itemEntities)) {
                    while (recipe.matches(world, player, stack, pos, itemEntities)) {
                        itemEntities = recipe.handleInteractionReturnWorldItems(world, player, stack, pos, itemEntities);
                    }
                    handleAdditionalEffects(recipe, world, player, pos);
                }
            }
        }
    }

    private void handleAdditionalEffects(RecipeWandInteraction recipe, World world, EntityPlayer player, BlockPos pos) {
        if (!world.isRemote) {
            if (ModRecipes.WandInteraction.constructionParticleList.contains(recipe)) {
                NetworkHandler.sendToAllAround(new MessageSound(SoundHandler.ModSounds.Construction, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 1.5, 1), player.dimension, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 16);
                Random rand = new Random();
                int num = rand.nextInt(32) + 64;
                for (int i = 0; i < num; i++) {
                    NetworkHandler.sendToAllAround(new MessageParticle(ModParticles.Particles.ConstructionTableConstruction, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, -1 + rand.nextDouble() * 2, -1 + rand.nextDouble() * 2, -1 + rand.nextDouble() * 2), player.dimension, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 16);
                }
            }
            if (ModRecipes.WandInteraction.infusionParticleList.contains(recipe)) {
                NetworkHandler.sendToAllAround(new MessageSound(SoundHandler.ModSounds.MagicDing, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 1.5, 1), player.dimension, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 16);
                double y = pos.getY() + 1.5;
                if (recipe.getStructure().getBlockClicked() != null && !recipe.getStructure().getBlockClicked().isFullBlock()) {
                    y = pos.getY() + 0.5;
                }
                NetworkHandler.sendToAllAround(new MessageParticle(ModParticles.Particles.Transmutation, pos.getX() + 0.5, y, pos.getZ() + 0.5), player.dimension, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, 16);
            }
        }
    }

}
