package sample.shapes;

import javafx.scene.canvas.GraphicsContext;

public abstract class Shape {
    private Point first, second;

    public abstract void draw(GraphicsContext gc);

    public abstract void setFirst(Point inFirst);

    public abstract void setSecond(Point inSecond);

    public abstract Point getFirst();

    public abstract Point getSecond();

    @Override
    public String toString() {
        return "[" + first.toString() + ", " + second.toString() + "]";
    }
}
