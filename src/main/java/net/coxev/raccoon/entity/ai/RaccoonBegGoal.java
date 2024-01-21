package net.coxev.raccoon.entity.ai;

import net.coxev.raccoon.entity.custom.RaccoonEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;

import java.util.EnumSet;

public class RaccoonBegGoal extends Goal {

    private static final TargetPredicate ENTITY_PREDICATE = TargetPredicate.createNonAttackable().setBaseMaxDistance(32D);
    protected final RaccoonEntity raccoon;
    private final double speed;
    private int delayTemptCounter;
    protected PlayerEntity closestPlayer;
    private boolean isRunning;

    public RaccoonBegGoal(RaccoonEntity raccoon, double speed) {
        this.raccoon = raccoon;
        this.speed = speed;
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
    }

    @Override
    public boolean canStart() {
        if (this.delayTemptCounter > 0) {
            --this.delayTemptCounter;
            return false;
        } else {
            if(!this.raccoon.getMainHandStack().isEmpty()){
                return false;
            }
            this.closestPlayer = this.raccoon.getEntityWorld().getClosestPlayer(ENTITY_PREDICATE, this.raccoon);
            if (this.closestPlayer == null) {
                return false;
            } else {
                if(this.closestPlayer.getMainHandStack().isEmpty()){
                    return false;
                }
                boolean food =  RaccoonEntity.isRaccoonFood(this.closestPlayer.getMainHandStack()) || RaccoonEntity.isRaccoonFood(this.closestPlayer.getOffHandStack());
                return food;
            }
        }
    }

    @Override
    public boolean shouldContinue() {
        return this.raccoon.getMainHandStack().isEmpty() && this.canStart();
    }

    @Override
    public void start() {
        this.isRunning = true;
    }

    @Override
    public void stop() {
        this.closestPlayer = null;
        this.raccoon.getNavigation().stop();
        this.delayTemptCounter = 100;
        this.raccoon.setBegging(false);
        this.isRunning = false;
    }

    @Override
    public void tick() {
        this.raccoon.getLookControl().lookAt(this.closestPlayer, (float)(this.raccoon.getMaxLookYawChange() / 2), 0);
        if (this.raccoon.distanceTo(this.closestPlayer) < 4D) {
            this.raccoon.getNavigation().stop();
            this.raccoon.setBegging(true);
        } else {
            this.raccoon.setBegging(false);
            this.raccoon.getNavigation().startMovingTo(this.closestPlayer, this.speed);
        }
    }

    public boolean isRunning() {
        return this.isRunning;
    }
}
