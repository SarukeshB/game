package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.Scenario;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.map.MapMarker;
import sk.tuke.kpi.oop.game.Cooler;
import sk.tuke.kpi.oop.game.DefectiveLight;
import sk.tuke.kpi.oop.game.Reactor;
import sk.tuke.kpi.oop.game.SmartCooler;
import sk.tuke.kpi.oop.game.tools.Hammer;

import java.util.Map;

public class Gameplay extends Scenario {
    @Override
    public void setupPlay(Scene scene) {
        Map<String, MapMarker> markers = scene.getMap().getMarkers();

        MapMarker reactorMarker = markers.get("reactor-area-2");
        Reactor reactor = new Reactor();
        if (reactorMarker != null) {
            scene.addActor(reactor, reactorMarker.getPosX(), reactorMarker.getPosY());
            reactor.turnOn();
        } else {
            scene.addActor(reactor, 64, 64);
            reactor.turnOn();
        }

        MapMarker coolerMarker = markers.get("cooler-area-1");
        Cooler cooler = new Cooler(reactor);
        if (coolerMarker != null) {
            scene.addActor(cooler, coolerMarker.getPosX(), coolerMarker.getPosY());
        } else {
            scene.addActor(cooler, 100, 64);
        }

        new ActionSequence<>(
            new Wait<>(5),
            new Invoke<>(cooler::turnOn)
        ).scheduleFor(cooler);

        MapMarker hammerMarker = markers.get("hammer-area");
        Hammer hammer = new Hammer();
        if (hammerMarker != null) {
            scene.addActor(hammer, hammerMarker.getPosX(), hammerMarker.getPosY());
        } else {
            scene.addActor(hammer, 120, 64);
        }

        new When<>(
            () -> reactor.getTemperature() >= 3000,
            new Invoke<>(() -> reactor.repair())
        ).scheduleFor(reactor);

        addCustomScenario(scene, reactor, markers);
    }

    private void addCustomScenario(Scene scene, Reactor reactor, Map<String, MapMarker> markers) {
        MapMarker smartCoolerMarker = markers.get("smart-cooler-area");
        SmartCooler smartCooler = new SmartCooler(reactor);
        if (smartCoolerMarker != null) {
            scene.addActor(smartCooler, smartCoolerMarker.getPosX(), smartCoolerMarker.getPosY());
        } else {
            scene.addActor(smartCooler, 80, 100);
        }

        MapMarker lightMarker = markers.get("light-area");
        DefectiveLight defectiveLight = new DefectiveLight();
        if (lightMarker != null) {
            scene.addActor(defectiveLight, lightMarker.getPosX(), lightMarker.getPosY());
        } else {
            scene.addActor(defectiveLight, 150, 150);
        }

        defectiveLight.toggle();
    }
}

