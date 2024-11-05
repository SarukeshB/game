package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.graphics.Color;

public class PowerSwitch extends AbstractActor {
    private Switchable device;


    public PowerSwitch(Switchable device) {
        this.device = device;
        setAnimation(new Animation("sprites/switch.png", 16, 16));
        // setTint(Color.GRAY);
    }

    //    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);
        initializeAnimation();
        setTint(Color.GRAY);
    }

    private void initializeAnimation() {
    }

    public Switchable getDevice() {
        return device;
    }

    public void switchOn() {
        if (device != null) {
            device.turnOn();
            setTint(Color.WHITE);
        }
    }

    public void switchOff() {
        if (device != null) {
            device.turnOff();
            setTint(Color.GRAY);
        }
    }

    private void setTint(Color color) {
        Animation animation = getAnimation();
        if (animation != null) {
            animation.setTint(color);
        }
    }
}
