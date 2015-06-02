package net.dgg.papayoo_scores.core;

import java.util.*;

/**
 * Created by DGG on 02-06-2015.
 */
public class Game {
    public static final int MAX_PLAYERS = 8;
    private final ArrayList<String> _players = new ArrayList<String>(MAX_PLAYERS);

    public Game addPlayer(String player) {
        if (!canAddPlayer()) {
            throw TooManyPlayersException.forSize(_players.size());
        }
        if (!canAddPlayer(player)){
            throw DuplicatedPlayerException.forPlayer(player);
        }
        _players.add(player);
        return this;
    }

    public boolean canAddPlayer(String player){
        return !_players.contains(player);
    }

    public boolean canAddPlayer(){
        return _players.size() < MAX_PLAYERS;
    }
}

