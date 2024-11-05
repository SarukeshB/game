package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Reactor;

public class FireExtinguisher extends BreakableTool<Reactor> {

    public FireExtinguisher(int uses) {
        super(uses);
        setAnimation(new Animation("sprites/extinguisher.png"));
    }

    public FireExtinguisher() {
        this(1);
    }

    public void useWith(Reactor reactor) {
        if (reactor == null) {
            return;
        } else if (reactor.extinguish()) {
            super.useWith(reactor);
        }
    }


    public Class<Reactor> getUsingActorClass() {
        return Reactor.class;
    }
}



