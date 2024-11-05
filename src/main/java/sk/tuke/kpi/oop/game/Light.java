package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Light extends AbstractActor implements Switchable, EnergyConsumer {
    private boolean isOn;
    private boolean isPowered;
    private Animation lightOn;
    private Animation lightOff;

    public Light() {
        this.isOn = false;
        this.isPowered = false;

        this.lightOn = new Animation("sprites/light_on.png", 16, 16);
        this.lightOff = new Animation("sprites/light_off.png", 16, 16);

        setAnimation(lightOff);
    }

    public void toggle() {
        this.isOn = !this.isOn;
        updateLightState();
    }

    public void setPowered(boolean powered) {
        this.isPowered = powered;
        updateLightState();
    }

    @Override
    public void turnOn() {
        this.isOn = true;
        updateLightState();
    }

    @Override
    public void turnOff() {
        this.isOn = false;
        updateLightState();
    }

    @Override
    public boolean isOn() {
        return this.isOn;
    }

    protected void updateLightState() {
        if (this.isOn && this.isPowered) {
            setAnimation(lightOn);
        } else {
            setAnimation(lightOff);
        }
    }
}


