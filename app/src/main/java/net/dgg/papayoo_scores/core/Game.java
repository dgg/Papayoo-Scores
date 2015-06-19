package net.dgg.papayoo_scores.core;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by DGG on 02-06-2015.
 */
public class Game{

    public static final int MAX_PLAYERS = 8, MIN_PLAYERS = 3;
    private final ArrayList<String> _participants = new ArrayList<String>(MAX_PLAYERS);
    private final IObserver _observer;

    public Game(IObserver observer) {
        _observer = observer;
    }

    public Game addPlayers(String... participants) {
        for (String participant : participants) {
            if (maxPlayers()) throw TooManyPlayersException.forSize(MAX_PLAYERS);
            if (!canAddPlayer(participant)) throw DuplicatedPlayerException.forPlayer(participant);

            _participants.add(participant);
            if (maxPlayers()) _observer.notify(new MaxPlayersReached(MAX_PLAYERS));
            if (minPlayers()) _observer.notify(new MinPlayersReached(MIN_PLAYERS));
        }
        return this;
    }

    private boolean maxPlayers(){
        return _participants.size() == MAX_PLAYERS;
    }

    private boolean minPlayers(){
        return _participants.size() == MIN_PLAYERS;
    }

    public boolean canAddPlayer(String player){
        return !_participants.contains(player);
    }

    private int _rounds;
    public int get_rounds() {
        return _rounds;
    }

    private int _hands;
    public int get_hands(){
        return _hands;
    }

    private Hand[] _playedHands;
    public Game start(int rounds){
        if (_participants.size() < MIN_PLAYERS) {
            throw TooFewPlayersException.forSize(_participants.size(), MIN_PLAYERS);
        }
        _rounds =  rounds;
        _hands = rounds * _participants.size();

        Player[] players = new Player[_participants.size()];
        for (int i = 0; i < _participants.size(); i++) {
            players[i] = new Player(_participants.get(i));
        }
        _players = new Players(players);

        _playedHands = new Hand[get_hands()];
        return this;
    }

    private int _currentHand = 0;
    public Hand nextHand(){
        if (_players == null) throw new GameNotStartedException();
        Hand current = null;
        if (_currentHand < _playedHands.length){
            current = new Hand(_players);
            _playedHands[_currentHand++] = current;
        }
        return current;
    }

    private Players _players;
    public Player score(String player, Point... scores){
        if (_players == null) throw new GameNotStartedException();
        return null;
    }

    public int get_score(String player){
        if (_players == null) throw new GameNotStartedException();

        return 0;
    }

    public Game endHand(){
        return this;
    }


}

