package net.dgg.papayoo_scores.core;

public class TooFewPlayersException extends IllegalArgumentException {
    public TooFewPlayersException(String detailMessage) {
        super(detailMessage);
    }

    public static TooFewPlayersException forSize(int size, int minSize) {
        String detailedMessage = String.format(
                "The Game contains only {0} players, it should contain at least {1}.",
                size, minSize);
        return new TooFewPlayersException(detailedMessage);
    }
}
