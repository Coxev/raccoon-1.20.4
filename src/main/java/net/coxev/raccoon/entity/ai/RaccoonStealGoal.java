package net.coxev.raccoon.entity.ai;

import net.coxev.raccoon.block.ModBlocks;
import net.coxev.raccoon.entity.custom.RaccoonEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;

public class RaccoonStealGoal extends Goal {
    private final RaccoonEntity raccoon;
    private final double escapeSpeed;
    private Path path;
    private int cooldown;
    private double initialX;
    private double initialY;
    private double initialZ;

    public BlockPos getClosestTrashBin(int distance){
        Block targetBlock = ModBlocks.TRASH_BIN;
        BlockPos closestPos = null;
        BlockPos checkPos = raccoon.getBlockPos();
        int raccoonX = MathHelper.floor(this.raccoon.getX());
        int raccoonY = MathHelper.floor(this.raccoon.getY());
        int raccoonZ = MathHelper.floor(this.raccoon.getZ());

        for (int x = raccoonX - distance; x < raccoonX + distance; x++) {
            for (int y = raccoonY - distance; y < raccoonY + distance; y++) {
                for (int z = raccoonZ - distance; z < raccoonZ + distance; z++) {
                    checkPos = new BlockPos(x, y, z);
                    if (this.raccoon.getWorld().getBlockState(checkPos).getBlock() == targetBlock) {
                        // check if it is closer than any previously found position
                        if (closestPos == null ||
                                this.raccoon.squaredDistanceTo(raccoonX - checkPos.getX(),
                                        raccoonY - checkPos.getY(),
                                        raccoonZ - checkPos.getZ())
                                        < this.raccoon.squaredDistanceTo(raccoonX - closestPos.getX(),
                                        raccoonY - closestPos.getY(),
                                        raccoonZ - closestPos.getZ())) {
                            closestPos = checkPos;
                        }
                    }
                }
            }
        }
        return closestPos;
    }

    public ItemStack randomItem(){
        List<ItemStack> items = new ArrayList<>();
        items.add(Items.APPLE.getDefaultStack());
        items.add(Items.BREAD.getDefaultStack());
        items.add(Items.COOKIE.getDefaultStack());
        Random randomizer = new Random();
        return items.get(randomizer.nextInt(items.size()));
    }

    public RaccoonStealGoal(RaccoonEntity raccoon, double escapeSpeed) {
        this.raccoon = raccoon;
        this.escapeSpeed = escapeSpeed;
        this.setControls(EnumSet.of(Goal.Control.MOVE));
    }

    @Override
    public boolean canStart() {
        return getClosestTrashBin(10) != null && this.raccoon.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty();
    }

    @Override
    public boolean shouldContinue() {
        return canStart();
    }

    @Override
    public void start() {
        this.cooldown = this.getTickCount(10);
        this.initialX = this.raccoon.getX();
        this.initialY = this.raccoon.getY();
        this.initialZ = this.raccoon.getZ();
    }

    @Override
    public void tick() {
        --this.cooldown;
        if(this.cooldown <= 0){
            BlockPos blockPos = getClosestTrashBin(10);
            if (blockPos != null){
                if(this.raccoon.squaredDistanceTo(blockPos.getX(), blockPos.getY(), blockPos.getZ()) > 5) {
                    this.raccoon.getNavigation().startMovingTo(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 1);
                } else {
                    this.raccoon.setStealing(true);
                    this.raccoon.equipStack(EquipmentSlot.MAINHAND, randomItem());
                    this.raccoon.getNavigation().startMovingTo(initialX, initialY, initialZ, escapeSpeed);
                    this.cooldown = this.getTickCount((8 + this.raccoon.getRandom().nextInt(4)) * 120);
                }
            }
        }
    }
}
