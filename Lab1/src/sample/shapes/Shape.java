package sample.shapes;

import javafx.scene.canvas.GraphicsContext;

public abstract class Shape {
    public abstract void draw(GraphicsContext gc);

    public abstract void setFirst(Point inFirst);

    public abstract void setSecond(Point inSecond);

    public abstract Point getFirst();

    public abstract Point getSecond();
}
