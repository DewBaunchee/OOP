package sample;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import sample.shapes.*;

import java.util.List;

public class Controller {

    private static GraphicsContext gc;

    @FXML
    private Canvas canvas;

    @FXML
    private Button drawBtn;

    @FXML
    void initialize() {
        gc = canvas.getGraphicsContext2D();

        drawBtn.setOnAction(actionEvent -> {

        });
    }
}
