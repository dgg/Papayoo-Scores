package net.dgg.papayoo_scores.core;

public class DuplicatedPlayerException extends IllegalArgumentException {
    public DuplicatedPlayerException(String detailedMessage) {
        super(detailedMessage);
    }

    public static final DuplicatedPlayerException forPlayer(String player){
        String detailedMessage = String.format(
                "Player {0} was already playing, use another name to avoid confusion",
                player);
        return new DuplicatedPlayerException(detailedMessage);
    }
}
