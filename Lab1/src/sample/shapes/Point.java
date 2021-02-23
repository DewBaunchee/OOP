package sample.shapes;

public class Point {
    public double getX() {
        return X;
    }

    public void setX(double x) {
        X = x;
    }

    public double getY() {
        return Y;
    }

    public void setY(double y) {
        Y = y;
    }

    private double X, Y;

    public Point(double inX, double inY) {
        X = inX;
        Y = inY;
    }

    @Override
    public String toString() {
        return "{ X: " + (int) X + ", Y: " + (int) Y + " }";
    }
}
