package sample.shapes;

import javafx.scene.canvas.GraphicsContext;

public class Circle extends Shape {

    private Point first;
    private Point second;

    public Circle(Point inFirst, Point inSecond) {
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
        double diameter = Math.max(Math.abs(second.X - first.X),
        Math.abs(second.Y - first.Y));

        gc.strokeOval(first.X, first.X, diameter, diameter);
    }
}