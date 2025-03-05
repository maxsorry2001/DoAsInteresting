package net.Gmaj7.funny_world.daiInit.daiUniqueData;

import net.Gmaj7.funny_world.daiInit.daiHoneyEffects;

import java.util.ArrayList;
import java.util.List;

public class HoneyAbsorbEffect {
    private List<daiHoneyEffects.Entry> list = new ArrayList<>();

    public void addEffect(daiHoneyEffects.Entry entry){
        list.add(entry);
    }

    public List<daiHoneyEffects.Entry> getEffect(){
        return list;
    }
}
