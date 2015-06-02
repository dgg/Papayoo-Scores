package net.dgg.papayoo_scores.core;

import java.util.*;

/**
 * Created by DGG on 02-06-2015.
 */
public class Game {
    private final ArrayList<String> _players = new ArrayList<String>(8);

    public Game addPlayer(String player) {
        if (!canAddPlayer(player)){
            throw DuplicatedPlayerException.forPlayer(player);
        }
        _players.add(player);
        return this;
    }

    public boolean canAddPlayer(String player){
        return !_players.contains(player);
    }
}

