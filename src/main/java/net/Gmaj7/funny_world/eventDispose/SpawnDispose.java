package net.Gmaj7.funny_world.eventDispose;

import net.Gmaj7.funny_world.FunnyWorld;
import net.Gmaj7.funny_world.daiEffects.daiMobEffects;
import net.Gmaj7.funny_world.daiInit.daiFunctions;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.BabyEntitySpawnEvent;

import java.util.Random;

@EventBusSubscriber(modid = FunnyWorld.MODID)
public class SpawnDispose {
    @SubscribeEvent
    public static void babySpawn(BabyEntitySpawnEvent event){
        AgeableMob child = event.getChild();
        Mob parentA = event.getParentA();
        Mob parentB = event.getParentB();
        Level level = child.level();
        if(parentA.hasEffect(daiMobEffects.GENE_MUTATION) || parentB.hasEffect(daiMobEffects.GENE_MUTATION)){
            if(child instanceof Axolotl axolotl)
                axolotl.setVariant(Axolotl.Variant.BLUE);
            if(child instanceof Panda panda) {
                panda.setMainGene(Panda.Gene.BROWN);
                panda.setHiddenGene(Panda.Gene.BROWN);
            }
            if(child instanceof Cow){
                MushroomCow mushroomCow = EntityType.MOOSHROOM.create(level);
                if(new Random().nextBoolean()) mushroomCow.setVariant(MushroomCow.MushroomType.BROWN);
                event.setChild(mushroomCow);
            }
            if(child instanceof MushroomCow){
                Cow cow = EntityType.COW.create(level);
                event.setChild(cow);
            }
            if(child instanceof Fox fox && ((Fox) parentA).getVariant() == ((Fox) parentB).getVariant()){
                switch (fox.getVariant()){
                    case RED -> fox.setVariant(Fox.Type.SNOW);
                    case SNOW -> fox.setVariant(Fox.Type.RED);
                }
            }
            if(child instanceof Cat cat){
                Holder<CatVariant> holder = daiFunctions.getHolder(level, Registries.CAT_VARIANT, CatVariant.ALL_BLACK);
                cat.setVariant(holder);
            }
        }
    }
}
