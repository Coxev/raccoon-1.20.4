package net.coxev.raccoon.entity.client;

import net.coxev.raccoon.entity.animation.ModAnimations;
import net.coxev.raccoon.entity.custom.RaccoonEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class RaccoonModel<T extends RaccoonEntity> extends SinglePartEntityModel<T> {
	public final ModelPart raccoon;
	public final ModelPart head;
	public final ModelPart snoot;

	public RaccoonModel(ModelPart root) {
		this.raccoon = root.getChild("raccoon");
		this.head = raccoon.getChild("body").getChild("torso").getChild("head");
		this.snoot = raccoon.getChild("body").getChild("torso").getChild("head").getChild("snoot");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData raccoon = modelPartData.addChild("raccoon", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData body = raccoon.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData legs = body.addChild("legs", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -5.0F, 6.0F));

		ModelPartData leg_right = legs.addChild("leg_right", ModelPartBuilder.create().uv(9, 32).cuboid(-1.0F, -2.0F, -0.5F, 2.0F, 7.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, 0.0F, 0.0F));

		ModelPartData leg_left = legs.addChild("leg_left", ModelPartBuilder.create().uv(9, 32).cuboid(-1.0F, -2.0F, -0.5F, 2.0F, 7.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(3.0F, 0.0F, 0.0F));

		ModelPartData torso = body.addChild("torso", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -6.0F, 6.0F));

		ModelPartData head = torso.addChild("head", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -2.0F, -13.5F));

		ModelPartData face = head.addChild("face", ModelPartBuilder.create().uv(30, 30).cuboid(-4.5F, -12.0F, -12.5F, 9.0F, 7.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 8.0F, 7.5F));

		ModelPartData ear_right = head.addChild("ear_right", ModelPartBuilder.create().uv(9, 24).mirrored().cuboid(-4.5F, -11.0F, -10.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 5.0F, 7.5F));

		ModelPartData ear_left = head.addChild("ear_left", ModelPartBuilder.create().uv(9, 24).cuboid(2.5F, -11.0F, -10.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 5.0F, 7.5F));

		ModelPartData snoot = head.addChild("snoot", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -5.0F, -15.0F, 4.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 5.0F, 7.5F));

		ModelPartData abdomen = torso.addChild("abdomen", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData abdomen_r1 = abdomen.addChild("abdomen_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-5.5F, -9.0F, -7.0F, 11.0F, 8.0F, 15.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 3.0F, -6.0F, 0.0873F, 0.0F, 0.0F));

		ModelPartData arms = torso.addChild("arms", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 1.0F, -12.0F));

		ModelPartData arm_right = arms.addChild("arm_right", ModelPartBuilder.create().uv(0, 24).cuboid(-1.0F, -2.0F, -0.5F, 2.0F, 7.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, 0.0F, 0.0F));

		ModelPartData arm_left = arms.addChild("arm_left", ModelPartBuilder.create().uv(0, 24).cuboid(-1.0F, -2.0F, -1.5F, 2.0F, 7.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(3.0F, 0.0F, 1.0F));

		ModelPartData tail = body.addChild("tail", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -7.0F, 7.0F));

		ModelPartData tail_r1 = tail.addChild("tail_r1", ModelPartBuilder.create().uv(0, 24).cuboid(-2.5F, -10.0F, 5.5F, 5.0F, 5.0F, 19.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 4.0F, -7.0F, -0.1309F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(RaccoonEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.setHeadAngles(netHeadYaw, headPitch);

		this.animateMovement(ModAnimations.RACCOON_WALKING, limbSwing, limbSwingAmount, 2f, 2.5f);
		this.updateAnimation(entity.idleAnimationState, ModAnimations.RACCOON_IDLING, ageInTicks, 1f);
		this.updateAnimation(entity.beggingAnimationState, ModAnimations.RACCOON_BEGGING, ageInTicks, 1f);
		this.updateAnimation(entity.sittingAnimationState, ModAnimations.RACCOON_SITTING, ageInTicks, 1f);
		this.updateAnimation(entity.toBegAnimationState, ModAnimations.RACCOON_TO_BEG, ageInTicks, 1f);
		this.updateAnimation(entity.toSitAnimationState, ModAnimations.RACCOON_TO_SIT, ageInTicks, 1f);
	}

	private void setHeadAngles(float headYaw, float headPitch){
		headYaw = MathHelper.clamp(headYaw, -20.0f, 20.0f);
		headPitch = MathHelper.clamp(headPitch, -10.f, 10.0f);

		this.head.yaw = headYaw * ((float)Math.PI / 180);
		this.head.pitch = headPitch * ((float)Math.PI / 180);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		raccoon.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return raccoon;
	}
}