package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import sample.shapes.*;

public class Controller {

    private static GraphicsContext gc;

    @FXML
    private Canvas canvas;

    @FXML
    private Button drawBtn;

    @FXML
    void initialize() {
        Rectangle rect = new Rectangle(new Point(10, 10), new Point(30, 40));
        System.out.println(rect.getFirst());
        System.out.println(rect.getSecond());
        rect.setFirst(new Point(15, 15));
        rect.setSecond(new Point(40, 40));

        List<Shape> list = inputListOfShapes();
        gc = canvas.getGraphicsContext2D();

        drawBtn.setOnAction(actionEvent -> {
            System.out.println(list);
            List<Shape>.Iterator shape = list.getIterator();

            while(shape.hasNext()) {
                shape.next().draw(gc);
            }
        });
    }

    private List<Shape> inputListOfShapes() {
        List<Shape> list = new List<>();

        list.add(new Line(new Point(10, 10), new Point(20, 20)));
        list.add(new Circle(new Point(10, 10), new Point(20, 20)));
        list.add(new Rhombus(new Point(100, 100), new Point(200, 400)));
        list.add(new Ellipse(new Point(100, 100), new Point(200, 400)));
        list.add(new Square(new Point(100, 100), new Point(200, 400)));
        list.add(new Rectangle(new Point(100, 100), new Point(200, 400)));

        return list;
    }
}
