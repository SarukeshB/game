package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.oop.game.actions.PerpetualReactorHeating;
import java.util.HashSet;
import java.util.Set;

public class Reactor extends AbstractActor implements Switchable, Repairable {
    private int temperature;
    private int damage;
    private boolean isOn;
    private final Animation normalAnimation;
    private final Animation hotAnimation;
    private final Animation brokenAnimation;
    private final Animation extinguishedAnimation;
    private final Animation offAnimation;

    private Set<EnergyConsumer> devices;

    public Reactor() {
        this.temperature = 0;
        this.damage = 0;
        this.isOn = false;

        this.normalAnimation = new Animation("sprites/reactor_on.png", 80, 80, 0.1f);
        this.hotAnimation = new Animation("sprites/reactor_hot.png", 80, 80, 0.05f);
        this.brokenAnimation = new Animation("sprites/reactor_broken.png", 80, 80, 0.1f);
        this.extinguishedAnimation = new Animation("sprites/reactor_extinguished.png", 80, 80, 0.1f);
        this.offAnimation = new Animation("sprites/reactor.png");

        setAnimation(offAnimation);
        devices = new HashSet<>();
    }

    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);
        new PerpetualReactorHeating(1).scheduleFor(this);

        new Loop<>(new Invoke<>(this::gradualTemperatureChange)).scheduleFor(this);
    }

    private void gradualTemperatureChange() {
        if (isOn && damage < 100) {
            temperature++;
            updateDamageOnIncrease();
            updateAnimation();
        }
    }

    public int getTemperature() {
        return temperature;
    }

    public int getDamage() {
        return damage;
    }

    public void increaseTemperature(int increment) {
        if (!isOn || increment < 0 || damage >= 100) return;

        int adjustedIncrement = increment;
        if (damage >= 66) adjustedIncrement *= 2;
        else if (damage >= 33) adjustedIncrement *= 1.5;

        temperature += adjustedIncrement;
        updateDamageOnIncrease();
        updateAnimation();
    }

    public void decreaseTemperature(int decrement) {
        if (decrement < 0 || damage >= 100 || !isOn) return;

        int adjustedDecrement = decrement;
        if (damage >= 50) adjustedDecrement /= 2;

        temperature = Math.max(0, temperature - adjustedDecrement);
        updateAnimation();
    }

    private void updateDamageOnIncrease() {
        if (temperature > 6000) {
            damage = 100;
            isOn = false;
            powerDevices(false);
        } else if (temperature > 2000) {
            damage = Math.min(100, (temperature - 2000) / 40);
        }
    }

    private void updateAnimation() {
        if (damage == 100) {
            setAnimation(brokenAnimation);
        } else if (temperature > 4000) {
            setAnimation(hotAnimation);
        } else if (isOn) {
            setAnimation(normalAnimation);
        } else {
            setAnimation(offAnimation);
        }
    }

    @Override
    public boolean repair() {
        if ((damage > 0) && (damage < 100)) {
            temperature = ((damage - 50) * 40) + 2000;
            if (damage > 50) {
                damage = damage - 50;
                updateAnimation();
            } else {
                damage = 0;
                updateAnimation();
            }
            return true;
        }
        return false;
    }

    public boolean extinguish() {
        if (damage == 100 && temperature > 4000) {
            temperature = 4000;
            setAnimation(extinguishedAnimation);
            return true;
        }
        return false;
    }

    public void addDevice(EnergyConsumer device) {
        if (device != null) {
            devices.add(device);
            device.setPowered(isOn);
        }
    }

    public void removeDevice(EnergyConsumer device) {
        if (device != null) {
            devices.remove(device);
            device.setPowered(false);
        }
    }

    private void powerDevices(boolean powered) {
        for (EnergyConsumer device : devices) {
            device.setPowered(powered);
        }
    }

    @Override
    public void turnOn() {
        if (damage < 100) {
            isOn = true;
            powerDevices(true);
            updateAnimation();
        }
    }

    @Override
    public void turnOff() {
        isOn = false;
        powerDevices(false);
        updateAnimation();
    }

    @Override
    public boolean isOn() {
        return isOn;
    }
}








