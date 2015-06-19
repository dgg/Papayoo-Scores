package net.dgg.papayoo_scores.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Players{
    private final Player[] _players;
    public Players(Player... players){
        _players = players;
    }

    public Player find(String name){
        Player found = null;
        for (Player player : _players){
            if (player.isNamed(name)){
                found = player;
                break;
            }
        }
        if (found == null) throw PlayerNotFoundException.forName(name);
        return found;
    }

    public Player[] findWithLowestScore() {
        ArrayList<Player> withLowest = new ArrayList<Player>(_players.length);
        int lowestScore = _players[0].get_score();
        withLowest.add(_players[0]);
        for (int i = 1; i < _players.length; i++) {
            Player player = _players[i];
            int score = player.get_score();
            if (score < lowestScore){
                withLowest.clear();
                withLowest.add(player);
                lowestScore = score;
            }
            else if (score == lowestScore){
                withLowest.add(player);
            }
        }
        return withLowest.toArray(new Player[withLowest.size()]);
    }
}
