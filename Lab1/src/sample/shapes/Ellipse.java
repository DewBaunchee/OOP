package sample.shapes;

import javafx.scene.canvas.GraphicsContext;

public class Ellipse implements Shape {

    private Point first, second;

    public Ellipse(Point inFirst, Point inSecond) {
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
        double x1 = first.getX();
        double y1 = first.getY();
        double x2 = second.getX();
        double y2 = second.getY();

        if(x1 > x2) {
            double temp = x1;
            x1 = x2;
            x2 = temp;
        }
        if(y1 > y2) {
            double temp = y1;
            y1 = y2;
            y2 = temp;
        }

        gc.strokeOval(x1, y1, x2 - x1, y2 - y1);
    }

    @Override
    public String toString() {
        return "[" + first.toString() + ", " + second.toString() + "]";
    }
}