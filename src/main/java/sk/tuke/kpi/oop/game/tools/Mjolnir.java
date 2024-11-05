package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.graphics.Animation;

public class Mjolnir extends Hammer {
    public Mjolnir() {
        super(4); // Call Hammer's constructor with 4 initial uses
        setAnimation(new Animation("sprites/hammer.png")); // Set Mjolnir animation
    }
}
