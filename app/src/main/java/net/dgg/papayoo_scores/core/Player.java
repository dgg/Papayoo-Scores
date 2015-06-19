package net.dgg.papayoo_scores.core;

public class Player{
    private final String _name;

    private int _score;

    public Player(String name){
        _name = name;
        _score = 0;
    }

    public Player increment(Point points){
        _score += points.get_value();
        return this;
    }

    public boolean isNamed(String name) {
        return _name.equalsIgnoreCase(name);
    }

    public int get_score(){
        return _score;
    }
}
