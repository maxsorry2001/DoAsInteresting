package net.Gmaj7.funny_world.daiInit;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;
import net.neoforged.neoforge.client.IArmPoseTransformer;

public class daiArmPose {
    public static final EnumProxy<HumanoidModel.ArmPose> TT_POSE = new EnumProxy<>(
            HumanoidModel.ArmPose.class,
            false,
            (IArmPoseTransformer) (humanoidModel, livingEntity, humanoidArm) -> {
                if(humanoidArm == HumanoidArm.RIGHT)
                    humanoidModel.rightArm.xRot = -90;
                else
                    humanoidModel.leftArm.xRot = -Mth.PI / 4;
            }
    );
}
