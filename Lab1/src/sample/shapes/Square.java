package sample.shapes;

import javafx.scene.canvas.GraphicsContext;

public class Square implements Shape {

    private Point first, second;

    public Square(Point inFirst, Point inSecond) {
        if(inFirst == null) inFirst = new Point(0, 0);
        if(inSecond == null) inSecond = new Point(0, 0);

        double x1 = Math.min(inFirst.getX(), inSecond.getX());
        double y1 = Math.min(inFirst.getY(), inSecond.getY());
        double x2 = Math.max(inFirst.getX(), inSecond.getX());
        double y2 = Math.max(inFirst.getY(), inSecond.getY());

        first = new Point(x1, y1);
        second = new Point(x2, y2);
    }

    @Override
    public void setFirst(Point inFirst) {
        if(inFirst != null) {
            first = inFirst;
        }
    }

    @Override
    public void setSecond(Point inSecond) {
        if(inSecond != null) {
            second = inSecond;
        }
    }

    @Override
    public Point getFirst() {
        return first;
    }

    @Override
    public Point getSecond() {
        return second;
    }

    @Override
    public void draw(GraphicsContext gc) {
        double length = Math.max(Math.abs(second.getX() - first.getX()), Math.abs(second.getY() - first.getY()));
        gc.strokeRect(first.getX(), first.getY(), length, length);
    }

    @Override
    public String toString() {
        return "[" + first.toString() + ", " + second.toString() + "]";
    }
}