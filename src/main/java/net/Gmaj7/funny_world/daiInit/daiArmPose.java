package net.Gmaj7.funny_world.daiInit;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;
import net.neoforged.neoforge.client.IArmPoseTransformer;

public class daiArmPose {
    public static final EnumProxy<HumanoidModel.ArmPose> SIDEWAYS_BOW = new EnumProxy<>(
            HumanoidModel.ArmPose.class,
            false,
            (IArmPoseTransformer) (humanoidModel, livingEntity, humanoidArm) -> {
                if(humanoidArm == HumanoidArm.RIGHT) {
                    if(livingEntity.isUsingItem()) {
                        humanoidModel.rightArm.yRot = -0.1F + humanoidModel.head.yRot;
                        humanoidModel.leftArm.yRot = 0.1F + humanoidModel.head.yRot + 0.4F;
                        humanoidModel.rightArm.xRot = (float) (-Math.PI / 2) + humanoidModel.head.xRot;
                        humanoidModel.leftArm.xRot = (float) (-Math.PI / 2) + humanoidModel.head.xRot;
                    }
                }
                else {
                    if(livingEntity.isUsingItem()) {
                        humanoidModel.rightArm.yRot = -0.1F + humanoidModel.head.yRot - 0.4F;
                        humanoidModel.leftArm.yRot = 0.1F + humanoidModel.head.yRot;
                        humanoidModel.rightArm.xRot = (float) (-Math.PI / 2) + humanoidModel.head.xRot;
                        humanoidModel.leftArm.xRot = (float) (-Math.PI / 2) + humanoidModel.head.xRot;
                    }
                }
            }
    );
}
