package sample.shapes;

import javafx.scene.canvas.GraphicsContext;

public class Square implements Shape {

    private Point first, second;

    public Square(Point inFirst, Point inSecond) {
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
        double length = Math.max(Math.abs(second.X - first.X), Math.abs(second.Y - second.X));
        gc.strokeRect(first.X, first.Y, length, length);
    }

    @Override
    public String toString() {
        return "[" + first.toString() + ", " + second.toString() + "]";
    }
}