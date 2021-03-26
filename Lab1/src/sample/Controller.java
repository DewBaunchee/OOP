package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sample.shapes.*;

public class Controller {

    @FXML
    private Button lineBtn;

    @FXML
    private Button circleBtn;

    @FXML
    private Button rhombusBtn;

    @FXML
    private Button rectangleBtn;

    @FXML
    private Button squareBtn;

    @FXML
    private Button ellipseBtn;

    @FXML
    private TextField firstCoordField;

    @FXML
    private TextField secondCoordField;

    @FXML
    private Canvas canvas;

    private static GraphicsContext gc;
    private static final Point first = new Point(0, 0);
    private static final Point second = new Point(0, 0);
    private static boolean isFirstNext = true;
    private static Shape drawableShape;

    @FXML
    void initialize() {
        gc = canvas.getGraphicsContext2D();
        firstCoordField.setText(first.toString());
        secondCoordField.setText(second.toString());

        canvas.setOnMouseClicked(mouseEvent -> {
            if(isFirstNext) {
                first.setX(mouseEvent.getX());
                first.setY(mouseEvent.getY());
                firstCoordField.setText(first.toString());
                isFirstNext = false;
            } else {
                second.setX(mouseEvent.getX());
                second.setY(mouseEvent.getY());
                secondCoordField.setText(second.toString());
                isFirstNext = true;
            }
        });

        lineBtn.setOnAction(actionEvent -> {
            drawableShape = new Line(first, second);
            drawableShape.draw(gc);
        });

        circleBtn.setOnAction(actionEvent -> {
            drawableShape = new Circle(first, second);
            drawableShape.draw(gc);
        });

        squareBtn.setOnAction(actionEvent -> {
            drawableShape = new Square(first, second);
            drawableShape.draw(gc);
        });

        rectangleBtn.setOnAction(actionEvent -> {
            drawableShape = new Rectangle(first, second);
            drawableShape.draw(gc);
        });

        rhombusBtn.setOnAction(actionEvent -> {
            drawableShape = new Rhombus(first, second);
            drawableShape.draw(gc);
        });

        ellipseBtn.setOnAction(actionEvent -> {
            drawableShape = new Ellipse(first, second);
            drawableShape.draw(gc);
        });
    }
}
