package sample.shapes;

import javafx.scene.canvas.GraphicsContext;

public interface Shape {
    void draw(GraphicsContext gc);

    void setFirst(Point inFirst);

    void setSecond(Point inSecond);

    Point getFirst();

    Point getSecond();
}
