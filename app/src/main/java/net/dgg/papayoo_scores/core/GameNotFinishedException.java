package net.dgg.papayoo_scores.core;

public class GameNotFinishedException extends IllegalStateException {
    public GameNotFinishedException() {
        super("The Game has not ended yet, there are hands to be played");
    }
}
