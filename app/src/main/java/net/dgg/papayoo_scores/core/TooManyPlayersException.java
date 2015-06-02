package net.dgg.papayoo_scores.core;

/**
 * Created by DGG on 02-06-2015.
 */
public class TooManyPlayersException extends IllegalArgumentException {
    public TooManyPlayersException(String detailMessage) {
        super(detailMessage);
    }

    public static TooManyPlayersException forSize(int size) {
        String detailedMessage = String.format(
                "The Game alreadt contains {0} players.",
                size);
        return new TooManyPlayersException(detailedMessage);
    }
}
