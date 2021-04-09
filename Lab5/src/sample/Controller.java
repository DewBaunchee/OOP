package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Hierarchy.*;
import sample.commands.AddingCommand;
import sample.commands.AddingRankCommand;
import sample.commands.CommandExecutor;
import sample.commands.RemovingCommand;
import sample.deserializing.DeserializingController;
import sample.serializing.SerializingController;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Optional;

public class Controller {

    @FXML
    private Button createLegionBtn;

    @FXML
    private Button triariiBtn;

    @FXML
    private Button princepsBtn;

    @FXML
    private Button hastatiBtn;

    @FXML
    private Button velitesBtn;

    @FXML
    private Button evocatiBtn;

    @FXML
    private GridPane armyComposition;

    @FXML
    private Label generalLabel;

    @FXML
    private Label legionNameLabel;

    @FXML
    private Button serializeBtn;

    @FXML
    private Button deserializeBtn;

    public CommandExecutor executor;

    @FXML
    void initialize() {
        executor = new CommandExecutor();

        Main.stage.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.isControlDown() && keyEvent.isShiftDown() && keyEvent.getCode() == KeyCode.Z) {
                executor.redo();
                reload();
                return;
            }
            if (keyEvent.isControlDown() && keyEvent.getCode() == KeyCode.Z) {
                executor.undo();
                reload();
            }
        });

        Legion.getLegion().setName("Leg.I");
        Legion.getLegion().setGeneral(new Warrior("Sulla"));

        executor.execute(new AddingCommand(Legion.getLegion(), new Evocati("Evocati I")));
        executor.execute(new AddingCommand(Legion.getLegion(), new Hastati("Hastati I")));
        executor.execute(new AddingCommand(Legion.getLegion(), new Evocati("Evocati II")));
        executor.execute(new AddingCommand(Legion.getLegion(), new Princeps("Princeps I")));
        executor.execute(new AddingCommand(Legion.getLegion(), new Triarii("Triarii I")));
        executor.execute(new AddingCommand(Legion.getLegion(), new Velites("Velites I")));
        executor.execute(new AddingCommand(Legion.getLegion(), new Velites("Velites II")));
        reload();

        createLegionBtn.setOnAction(actionEvent -> {
            String answerName = alertInputText("Enter legion name: ", "Creating legion...");
            String answerGeneral = alertInputText("Enter general name: ", "Creating legion...");
            if (answerName == null || answerGeneral == null || answerName.length() == 0 || answerGeneral.length() == 0)
                return;

            Legion.getLegion().clear();
            Legion.getLegion().setName(answerName);
            Legion.getLegion().setGeneral(new Warrior(answerGeneral));
            reload();
        });

        triariiBtn.setOnAction(actionEvent -> {
            String answer = alertInputText("Enter unit name: ", "Creating unit...");
            if (answer == null || answer.length() == 0) return;

            executor.execute(new AddingCommand(Legion.getLegion(), new Triarii(answer)));
            reload();
        });

        hastatiBtn.setOnAction(actionEvent -> {
            String answer = alertInputText("Enter unit name: ", "Creating unit...");
            if (answer == null || answer.length() == 0) return;

            executor.execute(new AddingCommand(Legion.getLegion(), new Hastati(answer)));
            reload();
        });

        princepsBtn.setOnAction(actionEvent -> {
            String answer = alertInputText("Enter unit name: ", "Creating unit...");
            if (answer == null || answer.length() == 0) return;

            executor.execute(new AddingCommand(Legion.getLegion(), new Princeps(answer)));
            reload();
        });

        evocatiBtn.setOnAction(actionEvent -> {
            String answer = alertInputText("Enter unit name: ", "Creating unit...");
            if (answer == null || answer.length() == 0) return;

            executor.execute(new AddingCommand(Legion.getLegion(), new Evocati(answer)));
            reload();
        });

        velitesBtn.setOnAction(actionEvent -> {
            String answer = alertInputText("Enter unit name: ", "Creating unit...");
            if (answer == null || answer.length() == 0) return;

            executor.execute(new AddingCommand(Legion.getLegion(), new Velites(answer)));
            reload();
        });

        serializeBtn.setOnAction(actionEvent -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("serializing/serializing.fxml"));
                Stage primaryStage = new Stage();
                primaryStage.setTitle("Serialize dialog");
                primaryStage.setResizable(false);
                primaryStage.setMaximized(false);
                primaryStage.initModality(Modality.WINDOW_MODAL);
                primaryStage.initOwner(serializeBtn.getScene().getWindow());
                primaryStage.setScene(new Scene(root, 600, 170));
                primaryStage.setOnCloseRequest(SerializingController.onCloseRequest);
                primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        deserializeBtn.setOnAction(actionEvent -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("deserializing/deserializing.fxml"));
                Stage primaryStage = new Stage();
                primaryStage.setTitle("Deserialize dialog");
                primaryStage.setResizable(false);
                primaryStage.setMaximized(false);
                primaryStage.initModality(Modality.WINDOW_MODAL);
                primaryStage.initOwner(deserializeBtn.getScene().getWindow());
                primaryStage.setScene(new Scene(root, 600, 170));
                primaryStage.setOnCloseRequest(DeserializingController.onCloseRequest);
                primaryStage.showAndWait();
                reload();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        armyComposition.setOnMouseClicked(mouseEvent -> {
            Node node = mouseEvent.getPickResult().getIntersectedNode();
            if (node != armyComposition) {
                Node parent = node.getParent();
                while (parent != armyComposition) {
                    node = parent;
                    parent = node.getParent();
                }
            }

            Integer col = GridPane.getColumnIndex(node);
            Integer row = GridPane.getRowIndex(node);
            if (col == null || row == null) return;
            int index = row * 10 + col;

            if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                executor.execute(new RemovingCommand(Legion.getLegion(), index));
                reload();
            }

            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                executor.execute(new AddingRankCommand(Legion.getLegion(), index));
                reload();
            }
        });
    }

    private void reload() {
        legionNameLabel.setText(Legion.getLegion().getName());
        generalLabel.setText(Legion.getLegion().getGeneral().getName());

        ObservableList<Node> children = armyComposition.getChildren();
        for (int i = 0; i < children.size(); i++) {
            if (children.get(i) instanceof Label) {
                armyComposition.getChildren().remove(i--);
            }
        }

        ArrayList<Unit> army = Legion.getLegion().getUnitList();
        int i;
        for (i = 0; i < army.size(); i++) {
            addToGrid(army.get(i).toString(), i % 10, i / 10);
        }
    }

    private void addToGrid(String toLabel, int col, int row) {
        Label label = new Label(toLabel);
        label.setPrefWidth(98);
        label.setPrefHeight(150);
        label.setAlignment(Pos.TOP_LEFT);
        label.setTextFill(Paint.valueOf("#FFDB00"));
        label.setWrapText(true);
        armyComposition.add(label, col, row);
    }

    private String alertInputText(String content, String title) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setContentText(content);

        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }

    public static void alertWith(String content, String title, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
