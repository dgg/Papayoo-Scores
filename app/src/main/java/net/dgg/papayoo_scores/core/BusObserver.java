package net.dgg.papayoo_scores.core;

import com.squareup.otto.Bus;

public class BusObserver implements IObserver{

    private final Bus _bus;

    public BusObserver(Bus bus){

        _bus = bus;
    }

    @Override
    public void notify(Object message) {
        _bus.post(message);
    }
}
