package net.Gmaj7.funny_world.daiItems.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class MathReinforcedPlate extends Item {
    private Class<?> targetClass;
    int reinforceType;
    public MathReinforcedPlate(Class<?> targetClass, int reinforceType, Properties properties) {
        super(properties);
        this.targetClass = targetClass;
        this.reinforceType = reinforceType;
    }

    public boolean isInstance(ItemStack itemStack){
        return targetClass.isInstance(itemStack.getItem());
    }

    public int getReinforceType() {
        return reinforceType;
    }
}
