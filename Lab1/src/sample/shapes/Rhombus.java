package sample.shapes;

import javafx.scene.canvas.GraphicsContext;

public class Rhombus implements Shape {

    private Point first, second;

    public Rhombus(Point inFirst, Point inSecond) {
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
        double xLength = Math.abs(second.getX() - first.getX());
        double yLength = Math.abs(second.getY() - first.getY());

        gc.strokeLine(first.getX() + xLength / 2,
                first.getY(),
                first.getX(),
                first.getY() + yLength / 2);
        gc.strokeLine(first.getX(),
                first.getY() + yLength / 2,
                second.getX() - xLength / 2,
                second.getY());
        gc.strokeLine(second.getX() - xLength / 2,
                second.getY(),
                second.getX(),
                second.getY() - yLength / 2);
        gc.strokeLine(second.getX(),
                second.getY() - yLength / 2,
                first.getX() + xLength / 2,
                first.getY());
    }

    @Override
    public String toString() {
        return "[" + first.toString() + ", " + second.toString() + "]";
    }
}