package attackPhrase;

import javax.swing.*;
import java.awt.*;
import java.util.Random;


public class Pokemon extends JPanel {
    private double spD, speed, spatk, atk, hp, def;
    private int lvl, spdIV, speedIV, spatkIV, atkIV, hpIV, defIV;
    private String type,name;
    private MoveSet moveSet;
    private static final int IV_MAX = 31;
    private String imagePath;


    public Pokemon(String Name,int spd, int Speed, int specialAtk, int attack, int health, int defense, int level, String Type, String imagePath) {
        Random rand = new Random();
        this.name = Name;
        this.spdIV = rand.nextInt(IV_MAX + 1);
        this.speedIV = rand.nextInt(IV_MAX + 1);
        this.spatkIV = rand.nextInt(IV_MAX + 1);
        this.atkIV = rand.nextInt(IV_MAX + 1);
        this.hpIV = rand.nextInt(IV_MAX + 1);
        this.defIV = rand.nextInt(IV_MAX + 1);


        this.spD = Math.floor(Math.floor((2 * spd + spdIV/*+ EV */) * level / 100 + 5) /* x Nature */);
        this.speed = Math.floor(Math.floor((2 * Speed + speedIV/*+ EV */) * level / 100 + 5) /* x Nature */);
        this.spatk = Math.floor(Math.floor((2 * specialAtk + spatkIV/*+ EV */) * level / 100 + 5) /* x Nature */);
        this.atk = Math.floor(Math.floor((2 * attack + atkIV/*+ EV */) * level / 100 + 5) /* x Nature */);
        this.hp = Math.floor((2 * health + hpIV /* + EV */) * level / 100 + level + 10);
        this.def = Math.floor(Math.floor((2 * defense + defIV/*+ EV */) * level / 100 + 5) /* x Nature */);
        this.lvl = level;
        this.type = Type;
        this.moveSet = new MoveSet();  // Initialize the MoveSet
        this.imagePath = imagePath;

        setPreferredSize(new Dimension(550, 550));
    }

    public String getImagePath(){
        return imagePath;
    }

    public void addMove(Move move) {
        moveSet.addMove(move);
    }


    public Move getMove(int index) {
        return moveSet.getMove(index);
    }


    public MoveSet getMoveSet() {
        return moveSet;
    }

    public String getName(){
        return name;
    }


    public double getSpeed() {
        return speed;
    }


    public double getAttack() {
        return atk;
    }


    public double getSpatk() {
        return spatk;
    }


    public double getDefense() {
        return def;
    }


    public double getSpD() {
        return spD;
    }


    public double getHealth() {
        return hp;
    }


    public int getlvl() {
        return lvl;
    }


    public String getType() {
        return type;
    }


    public void takeDamage(int damage) {
        hp -= damage;
        if (hp < 0) hp = 0;
    }

}






