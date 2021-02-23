package sample.shapes;

import javafx.scene.canvas.GraphicsContext;

public class Line implements Shape {

    private Point first, second;

    public Line(Point inFirst, Point inSecond) {
        if(inFirst == null) inFirst = new Point(0, 0);
        if(inSecond == null) inSecond = new Point(0, 0);

        first = inFirst;
        second = inSecond;
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
        gc.strokeLine(first.getX(), first.getY(), second.getX(), second.getY());
    }

    @Override
    public String toString() {
        return "[" + first.toString() + ", " + second.toString() + "]";
    }
}
