package net.dgg.papayoo_scores.core;

public class GameNotStartedException extends IllegalStateException {
    public GameNotStartedException() {
        super("The Game was not started");
    }
}

