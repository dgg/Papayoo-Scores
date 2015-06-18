package net.dgg.papayoo_scores.core;

/**
 * Created by DGG on 18-06-2015.
 */
public final class Point{
    public static final int MIN = 1, MAX = 20, PAPAYOO = 40;

    private final int _value;

    Point(int value){
        if (value < MIN || (value > MAX && value != PAPAYOO)) {
            String msg = String.format(
                    "A point must be a value between 1 and 20, or 40. Acual value was {0}.",
                    value);
            throw new  IllegalArgumentException(msg);
        }
        _value = value;
    }

    public int get_value(){
        return _value;
    }

    @Override
    public String toString() {
        return Integer.toString(_value);
    }
}
