package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Reactor;

public class PerpetualReactorHeating extends AbstractAction<Reactor> {
    private final int temperatureIncrement;

    public PerpetualReactorHeating(int temperatureIncrement) {
        this.temperatureIncrement = temperatureIncrement;
    }

    @Override
    public void execute (float deltaTime) {
        Reactor reactor = getActor();
        if (reactor!=null) {
            reactor.increaseTemperature(temperatureIncrement);
        }

    }

}


