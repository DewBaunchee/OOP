package sample.shapes;

import javafx.scene.canvas.GraphicsContext;

public class Line extends Shape {

    private Point first, second;

    public Line(Point inFirst, Point inSecond) {
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
        gc.strokeLine(first.X, first.Y, second.X, second.Y);
    }
}
