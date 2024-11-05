package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Teleport extends AbstractActor {
    private Teleport destination;

    public Teleport(Teleport destination) {
        this.destination = destination;
        setAnimation(new Animation("sprites/lift.png"));
    }

    public Teleport getDestination() {
        return destination;
    }

    public void setDestination(Teleport destination) {
        if (destination != this) this.destination = destination;
    }

    public void teleportPlayer(Player player) {
        if (destination != null) {
            player.setPosition(
                destination.getPosX() + destination.getWidth() / 2 - player.getWidth() / 2,
                destination.getPosY() + destination.getHeight() / 2 - player.getHeight() / 2
            );
        }
    }
}
