package sample.shapes;

public class Point {
    double X, Y;

    public Point(double inX, double inY) {
        X = inX;
        Y = inY;
    }

    @Override
    public String toString() {
        return "{ X: " + X + ", Y: " + Y + " }";
    }
}
