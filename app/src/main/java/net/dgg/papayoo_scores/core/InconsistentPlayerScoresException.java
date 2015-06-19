package net.dgg.papayoo_scores.core;

public class InconsistentPlayerScoresException extends IllegalStateException{
    private InconsistentPlayerScoresException(String detailedMessage){
        super(detailedMessage);
    }

    public static InconsistentPlayerScoresException forScores(int handTotalScore){
        String detailedMessage = String.format(
                "Cannot end a hand with an aggregated score of {0}",
                handTotalScore);
        return new InconsistentPlayerScoresException(detailedMessage);
    }
}
