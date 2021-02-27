package sample;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import sample.Hierarchy.*;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

public class Controller {

    public Legion legion;

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

    @FXML
    void initialize() {
        legion = new Legion("Leg.I", new Warrior("Sulla"));
        legion.addUnit(new Evocati("Evocati I"));
        legion.addUnit(new Hastati("Hastati I"));
        legion.addUnit(new Evocati("Evocati II"));
        legion.addUnit(new Princeps("Princeps I"));
        legion.addUnit(new Triarii("Triarii I"));
        legion.addUnit(new Velites("Velites I"));
        legion.addUnit(new Velites("Velites II"));
        reload();

        createLegionBtn.setOnAction(actionEvent -> {
            String answerName = alertInputText("Enter legion name: ", "Creating legion...");
            String answerGeneral = alertInputText("Enter general name: ", "Creating legion...");
            if(answerName == null || answerGeneral == null || answerName.length() == 0 || answerGeneral.length() == 0) return;

            legion = new Legion(answerName, new Warrior(answerGeneral));
            reload();
        });

        triariiBtn.setOnAction(actionEvent -> {
            String answer = alertInputText("Enter unit name: ", "Creating unit...");
            if(answer == null || answer.length() == 0) return;

            legion.addUnit(new Triarii(answer));
            reload();
        });

        hastatiBtn.setOnAction(actionEvent -> {
            String answer = alertInputText("Enter unit name: ", "Creating unit...");
            if(answer == null || answer.length() == 0) return;

            legion.addUnit(new Hastati(answer));
            reload();
        });

        princepsBtn.setOnAction(actionEvent -> {
            String answer = alertInputText("Enter unit name: ", "Creating unit...");
            if(answer == null || answer.length() == 0) return;

            legion.addUnit(new Princeps(answer));
            reload();
        });

        evocatiBtn.setOnAction(actionEvent -> {
            String answer = alertInputText("Enter unit name: ", "Creating unit...");
            if(answer == null || answer.length() == 0) return;

            legion.addUnit(new Evocati(answer));
            reload();
        });

        velitesBtn.setOnAction(actionEvent -> {
            String answer = alertInputText("Enter unit name: ", "Creating unit...");
            if(answer == null || answer.length() == 0) return;

            legion.addUnit(new Velites(answer));
            reload();
        });

        serializeBtn.setOnAction(actionEvent -> {
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("serialized.txt"));
                legion.writeExternal(objectOutputStream);
                objectOutputStream.flush();
                objectOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        deserializeBtn.setOnAction(actionEvent -> {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("serialized.txt"));
                legion.readExternal(objectInputStream);
                objectInputStream.close();
                reload();
            } catch (IOException | ClassNotFoundException e) {
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
            if(col == null || row == null) return;
            int index = row * 10 + col;

            if(mouseEvent.getButton() == MouseButton.SECONDARY) {
                if(legion.removeUnit(index) == null) return;
                reload();
            }

            if(mouseEvent.getButton() == MouseButton.PRIMARY) {
                if(legion.addRankTo(index)) {
                    reload();
                }
            }
        });
    }

    private void reload() {
        legionNameLabel.setText(legion.getName());
        generalLabel.setText(legion.getGeneral().getName());

        ObservableList<Node> children = armyComposition.getChildren();
        for(int  i = 0; i < children.size(); i++) {
            if(children.get(i) instanceof Label) {
                armyComposition.getChildren().remove(i--);
            }
        }

        ArrayList<Unit> army = legion.getUnitList();
        int i;
        for(i = 0; i < army.size(); i++) {
            addToGrid(army.get(i).toString(), i % 10, i / 10);
        }

        System.out.println(legion);
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
