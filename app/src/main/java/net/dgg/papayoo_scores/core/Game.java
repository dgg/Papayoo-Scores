package net.dgg.papayoo_scores.core;

import java.util.*;

/**
 * Created by DGG on 02-06-2015.
 */
public class Game{

    public static final int MAX_PLAYERS = 8, MIN_PLAYERS = 3;
    private final ArrayList<String> _players = new ArrayList<String>(MAX_PLAYERS);
    private final IObserver _observer;

    public Game(IObserver observer) {
        _observer = observer;
    }

    public Game addPlayer(String player) {
        if (maxPlayers()) throw TooManyPlayersException.forSize(MAX_PLAYERS);
        if (!canAddPlayer(player)) throw DuplicatedPlayerException.forPlayer(player);

        _players.add(player);
        if (maxPlayers()) _observer.notify(new MaxPlayersReached(MAX_PLAYERS));
        if (minPlayers()) _observer.notify(new MinPlayersReached(MIN_PLAYERS));

        return this;
    }

    private boolean maxPlayers(){
        return _players.size() == MAX_PLAYERS;
    }

    private boolean minPlayers(){
        return _players.size() == MIN_PLAYERS;
    }

    public boolean canAddPlayer(String player){
        return !_players.contains(player);
    }
}

