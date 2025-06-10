package net.Gmaj7.funny_world.daiInit.daiUniqueData;

public class HumanitySet {
    private int humanity;

    public HumanitySet(int i){
        this.humanity = i;
    }

    public void setHumanity(int humanity) {
        this.humanity = humanity;
    }

    public void decreaseHumanity(){
        this.changeHumanity(-1);
    }

    public void addHumanity(){
        this.changeHumanity(1);
    }

    public void changeHumanity(int i){
        humanity += i;
    }

    public int getHumanity() {
        return humanity;
    }
}
