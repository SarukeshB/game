package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.AbstractActor;

public abstract class BreakableTool<A extends Actor> extends AbstractActor implements Usable<A> {
    private int remainingUses;

    public BreakableTool(int uses) {
        this.remainingUses = uses;
    }

    public int getRemainingUses() {
        return remainingUses;
    }

    public void use() {
        if (remainingUses > 0) {
            remainingUses--;
            if (remainingUses == 0) {
                getScene().removeActor(this);
            }
        }
    }

    @Override
    public void useWith(A actor) {
        use();
    }
}

