package net.coxev.raccoon.entity.custom;

import com.google.common.annotations.VisibleForTesting;
import net.coxev.raccoon.Raccoon;
import net.coxev.raccoon.entity.ModEntities;
import net.coxev.raccoon.entity.ai.RaccoonBegGoal;
import net.coxev.raccoon.entity.ai.RaccoonStealGoal;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class RaccoonEntity extends TameableEntity {

    private static final TrackedData<Boolean> SITTING = DataTracker.registerData(RaccoonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> BEGGING = DataTracker.registerData(RaccoonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> STEALING = DataTracker.registerData(RaccoonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public int eatingTime;

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public final AnimationState beggingAnimationState = new AnimationState();
    private int beggingAnimationTimeout = 0;

    public final AnimationState sittingAnimationState = new AnimationState();
    private int sittingAnimationTimeout = 0;

    public final AnimationState toBegAnimationState = new AnimationState();

    public final AnimationState toSitAnimationState = new AnimationState();

    public RaccoonEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
        this.setTamed(false);
    }

    private void setupAnimationStates() {
        if(this.idleAnimationTimeout <= 0){
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.age);
        } else {
            --this.idleAnimationTimeout;
        }

        if (this.isBegging()) {
            if(this.beggingAnimationTimeout <= 0){
                this.beggingAnimationTimeout = 11;
                this.toBegAnimationState.start(this.age);
            } else if(this.beggingAnimationTimeout == 1){
                this.toBegAnimationState.stop();
                this.beggingAnimationState.startIfNotRunning(this.age);
            } else {
                --beggingAnimationTimeout;
            }
        } else {
            this.toBegAnimationState.stop();
            this.beggingAnimationState.stop();
            this.beggingAnimationTimeout = 0;
        }

        if (this.isSitting()) {
            if(this.sittingAnimationTimeout <= 0){
                this.sittingAnimationTimeout = 11;
                this.toSitAnimationState.start(this.age);
            } else if(this.sittingAnimationTimeout == 1){
                this.toSitAnimationState.stop();
                this.sittingAnimationState.startIfNotRunning(this.age);
            } else {
                --sittingAnimationTimeout;
            }
        } else {
            this.toSitAnimationState.stop();
            this.sittingAnimationState.stop();
            this.sittingAnimationTimeout = 0;
        }
    }

    @Override
    protected void updateLimbs(float posDelta) {
        float f = this.getPose() == EntityPose.STANDING ? Math.min(posDelta * 6.0f, 1.0f) : 0.0f;
        this.limbAnimator.updateLimbs(f, 0.2f);
    }

    public boolean isSitting() {
        return this.dataTracker.get(SITTING);
    }

    public void setSitting(boolean sitting){
        this.dataTracker.set(SITTING, sitting);
        super.setSitting(sitting);
    }

    public boolean isBegging() {
        return this.dataTracker.get(BEGGING);
    }

    public void setBegging(boolean begging) {
        this.dataTracker.set(BEGGING, begging);
    }

    public boolean isStealing() {
        return this.dataTracker.get(STEALING);
    }

    public void setStealing(boolean stealing) {
        this.dataTracker.set(STEALING, stealing);
    }

    @Override
    public void tick() {
        super.tick();
        if(this.getWorld().isClient()) {
            setupAnimationStates();
        }
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));

        this.goalSelector.add(1, new SitGoal(this));
        this.goalSelector.add(2, new FollowOwnerGoal(this, 1.5, 10.0f, 2.0f, false));

        this.goalSelector.add(3, new RaccoonStealGoal(this, 3));
        this.goalSelector.add(4, new RaccoonBegGoal(this, 1.2D));

        this.goalSelector.add(5, new AnimalMateGoal(this, 1.150));

        this.goalSelector.add(6, new WanderAroundFarGoal(this, 1));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 8f, 1));
        this.goalSelector.add(8, new LookAroundGoal(this));


    }

    public static DefaultAttributeContainer.Builder createRaccoonAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 15)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SITTING, false);
        this.dataTracker.startTracking(BEGGING, false);
        this.dataTracker.startTracking(STEALING, false);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.SUSPICIOUS_STEW);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.RACCOON.create(world);
    }

    @Override
    public void setTamed(boolean tamed) {
        super.setTamed(tamed);
        if (tamed) {
            this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(20.0);
            this.setHealth(20.0f);
        } else {
            this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(8.0);
        }
    }

    public boolean isMexican(){
        final String name = this.getName().getString();
        if (name == null) return false;

        final String lowercaseName = name.toLowerCase(Locale.ROOT);
        return lowercaseName.contains("mexican");
    }

    @Override
    public void tickMovement() {
        if (!this.getWorld().isClient && this.isAlive()) {
            ItemStack itemStack = this.getEquippedStack(EquipmentSlot.MAINHAND);
            if (!this.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty()) {
                ++this.eatingTime;
                if (this.eatingTime > 600) {
                    ItemStack itemStack2 = itemStack.finishUsing(this.getWorld(), this);
                    if (!itemStack2.isEmpty()) {
                        this.equipStack(EquipmentSlot.MAINHAND, itemStack2);
                    }
                    this.eatingTime = 0;
                } else if (this.eatingTime > 560 && this.random.nextFloat() < 0.1f) {
                    this.playSound(this.getEatSound(itemStack), 1.0f, 1.0f);
                    this.getWorld().sendEntityStatus(this, EntityStatuses.CREATE_EATING_PARTICLES);
                }
            }
        }
        super.tickMovement();
    }

    @Override
    public void handleStatus(byte status) {
        if (status == EntityStatuses.CREATE_EATING_PARTICLES) {
            ItemStack itemStack = this.getEquippedStack(EquipmentSlot.MAINHAND);
            if (!itemStack.isEmpty()) {
                for (int i = 0; i < 8; ++i) {
                    Vec3d vec3d = new Vec3d(((double)this.random.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0).rotateX(-MathHelper.clamp(this.getPitch(), -20.0f, 20.0f)).rotateY(-MathHelper.clamp(headYaw, -20.0f, 20.0f));
                    this.getWorld().addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack), this.getX() + this.getRotationVector().x / 2.0, this.getY(), this.getZ() + this.getRotationVector().z / 2.0 - 1, vec3d.x, vec3d.y + 0.05, vec3d.z);
                }
            }
        } else {
            super.handleStatus(status);
        }
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();

        if(item == Items.GOLDEN_CARROT && !this.isTamed()){
            if(this.getWorld().isClient()){
                return ActionResult.CONSUME;
            } else {
                this.eat(player, hand, itemStack);

                if(!this.getWorld().isClient()){
                    if (this.random.nextInt(3) == 0) {
                        this.setOwner(player);
                        this.navigation.stop();
                        this.setTarget(null);
                        this.setSitting(true);
                        this.setTamed(true);
                        this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_POSITIVE_PLAYER_REACTION_PARTICLES);
                    } else {
                        this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_NEGATIVE_PLAYER_REACTION_PARTICLES);
                    }
                    return ActionResult.SUCCESS;
                }
            }
        }

        if (getEquippedStack(EquipmentSlot.MAINHAND).isEmpty() && isRaccoonFood(itemStack)) {
            if(this.getWorld().isClient()){
                return ActionResult.CONSUME;
            } else {
                this.eat(player, hand, itemStack);
                itemStack.setCount(1);
                this.equipStack(EquipmentSlot.MAINHAND, itemStack);
            }
        }

        if (isTamed() && !this.getWorld().isClient() && hand == Hand.MAIN_HAND){
            this.setSitting(!isSitting());
            return ActionResult.SUCCESS;
        }

        return super.interactMob(player, hand);
    }

    public void onEatItem() {
        this.heal(10);
        this.getWorld().sendEntityStatus(this, (byte) 92);
        this.emitGameEvent(GameEvent.EAT);
        this.playSound(SoundEvents.ENTITY_GENERIC_EAT, this.getSoundVolume(), this.getPitch());
    }

    public static boolean isRaccoonFood(ItemStack stack) {
        return stack.isFood();
    }

    @Override
    public EntityView method_48926() {
        return super.getWorld();
    }
}
