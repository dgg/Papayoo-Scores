package net.dgg.papayoo_scores.core;

import com.squareup.otto.Bus;

import java.util.*;

/**
 * Created by DGG on 02-06-2015.
 */
public class Game{

    public static final int MAX_PLAYERS = 8;
    private final ArrayList<String> _players = new ArrayList<String>(MAX_PLAYERS);
    private final Bus _bus;

    public Game(Bus bus) {
        _bus = bus;
    }

    public Game addPlayer(String player) {
        if (!canAddPlayer()) {
            _bus.post(new TooManyPlayers(_players.size()));
        }
        if (!canAddPlayer(player)){
            _bus.post(new DuplicatedPlayer(player));
            //_duplicated.notifyObservers(new DuplicatedPlayer(player));
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

