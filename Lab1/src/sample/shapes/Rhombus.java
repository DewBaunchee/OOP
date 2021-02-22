package sample.shapes;

import javafx.scene.canvas.GraphicsContext;

public class Rhombus implements Shape {

    private Point first, second;

    public Rhombus(Point inFirst, Point inSecond) {
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
        double xLength = Math.abs(second.X - first.X);
        double yLength = Math.abs(second.Y - first.Y);

        gc.strokeLine(first.X + xLength / 2, first.Y,
                first.X, first.Y + yLength / 2);
        gc.strokeLine(first.X, first.Y + yLength / 2,
                second.X - xLength / 2, second.Y);
        gc.strokeLine(second.X - xLength / 2, second.Y,
                second.X, second.Y - yLength / 2);
        gc.strokeLine(second.X, second.Y - yLength / 2,
                first.X + xLength / 2, first.Y);
    }

    @Override
    public String toString() {
        return "[" + first.toString() + ", " + second.toString() + "]";
    }
}