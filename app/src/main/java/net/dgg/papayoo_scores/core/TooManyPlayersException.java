package net.dgg.papayoo_scores.core;

/**
 * Created by dgon on 16/06/15.
 */
public class TooManyPlayersException extends IllegalArgumentException {
    public TooManyPlayersException(String detailMessage) {
        super(detailMessage);
    }

    public static TooManyPlayersException forSize(int size) {
        String detailedMessage = String.format(
                "The Game already contains {0} players.",
                size);
        return new TooManyPlayersException(detailedMessage);
    }
}