package net.dgg.papayoo_scores.core;

public class Hand{
    public static int TOTAL_POINTS = 250;

    private final Players _players;
    public Hand(Players players){
        _players = players;
    }
    private int _aggregate = 0;

    public Hand score(String player, Point... points){

        Player found = _players.find(player);
        for (Point point: points) {
            found.increment(point);
            _aggregate += point.get_value();
        }
        return this;
    }

    public Hand end(){
        if (_aggregate != 250) {
            throw InconsistentPlayerScoresException.forScores(_aggregate);
        }
        return this;
    }
}

