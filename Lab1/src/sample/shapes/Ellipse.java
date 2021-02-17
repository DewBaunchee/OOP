package sample.shapes;

import javafx.scene.canvas.GraphicsContext;

public class Ellipse extends Shape {

    private Point first, second;

    public Ellipse(Point inFirst, Point inSecond) {
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
        double x1 = first.X;
        double y1 = first.Y;
        double x2 = second.X;
        double y2 = second.Y;

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

        gc.strokeOval(first.X, first.Y, second.X - first.X, second.Y - first.Y);
    }
}