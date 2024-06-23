package graysblock.graysmod.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class CluckshroomEntityModel<T extends Entity> extends AnimalModel<T> {
	private final ModelPart head;
	private final ModelPart bill;
	private final ModelPart chin;
	private final ModelPart body;
	private final ModelPart left_wing;
	private final ModelPart right_wing;
	private final ModelPart left_leg;
	private final ModelPart right_leg;
	private final ModelPart head_mushrooms;
	private final ModelPart body_mushrooms;

	public CluckshroomEntityModel(ModelPart root) {
		this.head = root.getChild("head");
		this.bill = root.getChild("bill");
		this.chin = root.getChild("chin");
		this.body = root.getChild("body");
		this.left_wing = root.getChild("left_wing");
		this.right_wing = root.getChild("right_wing");
		this.left_leg = root.getChild("left_leg");
		this.right_leg = root.getChild("right_leg");
		this.head_mushrooms = root.getChild(EntityModelPartNames.HEAD).getChild("head_mushrooms");
		this.body_mushrooms = root.getChild(EntityModelPartNames.BODY).getChild("body_mushrooms");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 3.0F), ModelTransform.pivot(0.0F, 15.0F, -4.0F));
		modelPartData.addChild("bill", ModelPartBuilder.create().uv(14, 0).cuboid(-2.0F, -4.0F, -4.0F, 4.0F, 2.0F, 2.0F), ModelTransform.pivot(0.0F, 15.0F, -4.0F));
		modelPartData.addChild("chin", ModelPartBuilder.create().uv(14, 4).cuboid(-1.0F, -2.0F, -3.0F, 2.0F, 2.0F, 2.0F), ModelTransform.pivot(0.0F, 15.0F, -4.0F));
		modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 9).cuboid(-3.0F, -4.0F, -3.0F, 6.0F, 8.0F, 6.0F), ModelTransform.of(0.0F, 16.0F, 0.0F, 1.5708F, 0.0F, 0.0F));
		modelPartData.addChild("left_wing", ModelPartBuilder.create().uv(24, 13).cuboid(-1.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F), ModelTransform.pivot(4.0F, 13.0F, 0.0F));
		modelPartData.addChild("right_wing", ModelPartBuilder.create().uv(24, 13).cuboid(0.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F), ModelTransform.pivot(-4.0F, 13.0F, 0.0F));
		modelPartData.addChild("left_leg", ModelPartBuilder.create().uv(26, 0).cuboid(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F), ModelTransform.pivot(1.0F, 19.0F, 1.0F));
		modelPartData.addChild("right_leg", ModelPartBuilder.create().uv(26, 0).cuboid(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F), ModelTransform.pivot(-2.0F, 19.0F, 1.0F));

		ModelPartData modelPartData2 = modelPartData.getChild(EntityModelPartNames.HEAD).addChild("head_mushrooms", ModelPartBuilder.create(), ModelTransform.NONE);
		modelPartData2.addChild("head_mushroom_1", ModelPartBuilder.create().uv(38, 19).cuboid(-3.0F, -3.0F, 0.0F, 5.0F, 4.0F, 0.0F), ModelTransform.of(1.5F, -7.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		modelPartData2.addChild("head_mushroom_2", ModelPartBuilder.create().uv(38, 14).cuboid(-3.0F, -3.0F, 0.0F, 0.0F, 4.0F, 5.0F), ModelTransform.of(-2.0F, -7.0F, 2.5F, 0.0F, 3.1416F, 0.0F));

		ModelPartData modelPartData3 = modelPartData.getChild(EntityModelPartNames.BODY).addChild("body_mushrooms", ModelPartBuilder.create(), ModelTransform.NONE);
		modelPartData3.addChild("body_mushroom_1", ModelPartBuilder.create().uv(38, 23).cuboid(-1.0F, -3.0F, -1.0F, 5.0F, 5.0F, 0.0F), ModelTransform.of(-2.5F, 2.5F, 5.0F, 1.5707964F, 3.1416F, 3.1416F));
		modelPartData3.addChild("body_mushroom_2", ModelPartBuilder.create().uv(38, 23).cuboid(-1.0F, -3.0F, -1.0F, 5.0F, 5.0F, 0.0F), ModelTransform.of(-2.0F, 0.0F, 5.0F, 1.5707964F, 3.1416F, -1.5707964F));

		return TexturedModelData.of(modelData, 64, 32);
	}

	@Override
	protected Iterable<ModelPart> getHeadParts() {
		return ImmutableList.of(this.head, this.bill, this.chin);
	}

	@Override
	protected Iterable<ModelPart> getBodyParts() {
		return ImmutableList.of(this.body, this.right_leg, this.left_leg, this.right_wing, this.left_wing);
	}

	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.head.pitch = headPitch * 0.017453292F;
		this.head.yaw = headYaw * 0.017453292F;
		this.bill.pitch = this.head.pitch;
		this.bill.yaw = this.head.yaw;
		this.chin.pitch = this.head.pitch;
		this.chin.yaw = this.head.yaw;
		this.right_leg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
		this.left_leg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
		this.right_wing.roll = animationProgress;
		this.left_wing.roll = -animationProgress;
	}
}