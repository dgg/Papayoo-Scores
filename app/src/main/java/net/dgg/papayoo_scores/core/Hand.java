package net.dgg.papayoo_scores.core;

public class Hand{
    private final Players _players;
    public Hand(Players players){
        _players = players;
    }
    private int _aggregate = 0;
    public Hand score(String player, Point... points){


        return this;
    }

    public Hand end(){
        return this;
    }
}
