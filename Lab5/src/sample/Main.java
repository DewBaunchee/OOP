package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Legion constructor");
        primaryStage.setMinWidth(1400);
        primaryStage.setMinHeight(600);
        primaryStage.setScene(new Scene(root, 1400, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
