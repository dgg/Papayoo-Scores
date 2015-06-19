package net.dgg.papayoo_scores.core;

/**
 * Created by DGG on 18-06-2015.
 */
public final class Point{
    public static final int MIN = 1, MAX = 20, PAPAYOO = 40;

    private final int _value;

    private Point(int value){
        _value = value;
    }

    public int get_value(){
        return _value;
    }

    @Override
    public String toString() {
        return Integer.toString(_value);
    }

    private static final Point[] _pool;
    static{
        _pool = new Point[21];
        for (int i = 1; i <= 20; i++) {
            _pool[i - 1] = new Point(i);
        }
        _pool[_pool.length - 1] = new Point(40);
    }

    public static Point of(int value){
        if (value < MIN || (value > MAX && value != PAPAYOO)) {
            String msg = String.format(
                    "A point must be a value between 1 and 20, or 40. Actual value was {0}.",
                    value);
            throw new  IllegalArgumentException(msg);
        }
        return value == PAPAYOO ? _pool[_pool.length - 1] : _pool[value - 1];
    }

    public static Point[] some(int... values){
        Point[] points = new Point[values.length];
        for (int i = 0; i < values.length; i++) {
            points[i] = of(values[i]);
        }
        return points;
    }
}
