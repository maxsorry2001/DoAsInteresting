package net.Gmaj7.funny_world.daiInit;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.ArrayList;
import java.util.List;

public record daiHoneyEffects(List<Entry> effects) {
    public static final daiHoneyEffects EMPTY = new daiHoneyEffects(new ArrayList<Entry>());
    public static final Codec<daiHoneyEffects> CODEC;
    public static final StreamCodec<RegistryFriendlyByteBuf, daiHoneyEffects> STREAM_CODEC;

    public daiHoneyEffects(List<daiHoneyEffects.Entry> effects) {
        this.effects = effects;
    }

    public daiHoneyEffects withEffectAdded(daiHoneyEffects.Entry entry) {
        return new daiHoneyEffects(Util.copyAndAdd(this.effects, entry));
    }

    public List<daiHoneyEffects.Entry> effects() {
        return this.effects;
    }

    public void addEffect(Entry entry){
        if(this.effects().isEmpty()) {
            this.effects().add(entry);
        }
        else
            for (Entry target : this.effects()){
                if(entry.effect() == target.effect()){
                    if(entry.effectLevel() > target.effectLevel() || entry.duration() > target.duration())
                        this.effects().set(this.effects().indexOf(target), entry);
                    break;
            }
        }
    }

    static {
        CODEC = daiHoneyEffects.Entry.CODEC.listOf().xmap(daiHoneyEffects::new, daiHoneyEffects::effects);
        STREAM_CODEC = daiHoneyEffects.Entry.STREAM_CODEC.apply(ByteBufCodecs.list()).map(daiHoneyEffects::new, daiHoneyEffects::effects);
    }
    public record Entry(Holder<MobEffect> effect, int duration, int effectLevel){
        public static final Codec<daiHoneyEffects.Entry> CODEC = RecordCodecBuilder.create((p_348389_) -> {
            return p_348389_.group(MobEffect.CODEC.fieldOf("id").forGetter(daiHoneyEffects.Entry::effect), Codec.INT.lenientOptionalFieldOf("duration", 160).forGetter(daiHoneyEffects.Entry::duration), Codec.INT.lenientOptionalFieldOf("effect_level", 0).forGetter(daiHoneyEffects.Entry::effectLevel)).apply(p_348389_, daiHoneyEffects.Entry::new);
        });
        public static final StreamCodec<RegistryFriendlyByteBuf, daiHoneyEffects.Entry> STREAM_CODEC;

        public Entry(Holder<MobEffect> effect, int duration, int effectLevel) {
            this.effect = effect;
            this.duration = duration;
            this.effectLevel = effectLevel;
        }

        public MobEffectInstance createEffectInstance() {
            return new MobEffectInstance(this.effect, this.duration, this.effectLevel);
        }

        public Holder<MobEffect> effect() {
            return this.effect;
        }

        public int duration() {
            return this.duration;
        }

        public int effectLevel(){
            return this.effectLevel;
        }

        static {
            STREAM_CODEC = StreamCodec.composite(MobEffect.STREAM_CODEC, daiHoneyEffects.Entry::effect, ByteBufCodecs.VAR_INT, daiHoneyEffects.Entry::duration, ByteBufCodecs.VAR_INT, daiHoneyEffects.Entry::effectLevel, daiHoneyEffects.Entry::new);
        }
    }
}
