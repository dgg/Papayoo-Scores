package net.dgg.papayoo_scores.core;

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
}
