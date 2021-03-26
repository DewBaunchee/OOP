package sample.serializing;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.WindowEvent;
import sample.Hierarchy.Legion;
import sample.plugins.IPlugin;

import javax.xml.bind.JAXBException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class SerializingController {

    @FXML
    private ChoiceBox<String> pluginChooser;

    @FXML
    private ImageView saveFileBtn;

    @FXML
    private ImageView pluginsFolder;

    @FXML
    private TextField saveFileField;

    @FXML
    private Button chooseBtn;

    @FXML
    private TextField keyField;

    @FXML
    private Button serializeBtn;

    @FXML
    private ToggleGroup serializeMethod;

    @FXML
    private RadioButton textMethod;

    @FXML
    private RadioButton xmlMethod;

    @FXML
    private CheckBox useCryptCheck;

    private String keyRegex;
    private IPlugin loadedPlugin;
    private List<IPlugin> plugins;
    private Thread statusChecker;
    public static EventHandler<WindowEvent> onCloseRequest;
    public static Legion serializable;

    @FXML
    void initialize() {
        statusChecker = new Thread(() -> {
            boolean isDisabled = true;
            while (!statusChecker.isInterrupted()) {
                if (useCryptCheck.isSelected()) {
                    if (loadedPlugin != null && keyField.getText().length() > 3
                            && saveFileField.getText().length() > 0
                            && Files.exists(Paths.get(saveFileField.getText()))) {
                        if (isDisabled) {
                            serializeBtn.setDisable(false);
                            isDisabled = false;
                        }
                    } else {
                        if (!isDisabled) {
                            serializeBtn.setDisable(true);
                            isDisabled = true;
                        }
                    }
                } else {
                    if (saveFileField.getText().length() > 0
                            && Files.exists(Paths.get(saveFileField.getText()))) {
                        if (isDisabled) {
                            serializeBtn.setDisable(false);
                            isDisabled = false;
                        }
                    } else {
                        if (!isDisabled) {
                            serializeBtn.setDisable(true);
                            isDisabled = true;
                        }
                    }
                }
            }
        });
        statusChecker.start();
        onCloseRequest = event -> statusChecker.interrupt();
        keyRegex = "";

        serializeBtn.setOnAction(actionEvent -> {
            Path saveFile = Paths.get(saveFileField.getText());
            String loadedPluginClass = useCryptCheck.isSelected() ? loadedPlugin.getClass().toString() : "";
            if (xmlMethod.isSelected()) {
                try {
                    byte[] header = (loadedPluginClass + "|" + "xml" + "|").getBytes();
                    byte[] xml = serializable.xmlSerialize();

                    if(useCryptCheck.isSelected()) xml = loadedPlugin.encrypt(xml, keyField.getText().getBytes());
                    byte[] output = Arrays.copyOf(header, header.length + xml.length);
                    System.arraycopy(xml, 0, output, header.length, xml.length);

                    Files.write(saveFile, output);
                } catch (JAXBException | IOException e) {
                    e.printStackTrace();
                }
            } else if (textMethod.isSelected()) {
                try {
                    byte[] header = (loadedPluginClass + "|" + "text" + "|").getBytes();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    serializable.writeExternal(new ObjectOutputStream(byteArrayOutputStream));
                    byte[] serialized = byteArrayOutputStream.toByteArray();

                    if(useCryptCheck.isSelected()) serialized = loadedPlugin.encrypt(serialized, keyField.getText().getBytes());
                    byte[] output = Arrays.copyOf(header, header.length + serialized.length);
                    System.arraycopy(serialized, 0, output, header.length, serialized.length);

                    System.out.println(new String(output));
                    Files.write(saveFile, output);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        chooseBtn.setOnAction(actionEvent -> saveFileField.setText(askFile("Choose save file").getAbsolutePath()));

        pluginChooser.valueProperty().addListener(((observable, oldValue, newValue) -> {
            String choice = pluginChooser.getValue();
            if (choice.length() > 0) {
                loadedPlugin = plugins.get(Integer.parseInt(choice.substring(0, choice.indexOf("."))) - 1);
                keyRegex = loadedPlugin.keyRegex() + "*";
            }
        }));

        useCryptCheck.setOnAction(actionEvent -> {
            keyField.setDisable(!useCryptCheck.isSelected());
        });

        saveFileBtn.setOnMouseClicked(mouseEvent -> saveFileField.setText(askFile("Choose save file").getAbsolutePath()));

        pluginsFolder.setOnMouseClicked(mouseEvent -> {
            plugins = findPlugins(askFolder("Choose plugins folder"));
            if (plugins == null) return;

            pluginChooser.getItems().clear();
            int number = 1;
            for (IPlugin plugin : plugins) {
                pluginChooser.getItems().add(number++ + ". " + plugin.name());
            }
        });

        keyField.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue.matches(keyRegex)) keyField.setText(oldValue);
        }));
    }

    private List<IPlugin> findPlugins(File folder) {
        if (folder == null) return null;

        File[] files = folder.listFiles();
        List<IPlugin> listOfPlugins = new ArrayList<>();
        if (files == null) {
            return listOfPlugins;
        }

        for (File current : files) {
            if (current.isDirectory()) {
                listOfPlugins.addAll(findPlugins(current));
            } else {
                List<IPlugin> plugin = getPluginClasses(current);
                if (plugin != null) listOfPlugins.addAll(plugin);
            }
        }

        return listOfPlugins;
    }

    private List<IPlugin> getPluginClasses(File current) {
        if (!current.getName().endsWith(".jar")) return null;

        try {
            URL jarPath = current.toURI().toURL();
            URLClassLoader classLoader = new URLClassLoader(new URL[]{jarPath});
            JarFile jar = new JarFile(current);
            Enumeration<JarEntry> entries = jar.entries();

            List<IPlugin> plugins = new ArrayList<>();
            while (entries.hasMoreElements()) {
                String entry = entries.nextElement().getName();

                if (!entry.endsWith(".class")) continue;

                entry = entry.replaceAll("/", ".");
                entry = entry.replaceAll(".class", "");

                Class<?> plugin = classLoader.loadClass(entry);
                Class<?>[] interfaces = plugin.getInterfaces();

                for (Class<?> _interface : interfaces) {
                    if (_interface.getName().endsWith(".IPlugin")) {
                        plugins.add((IPlugin) classLoader.loadClass(plugin.getName()).newInstance());
                    }
                }
            }

            return plugins;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private File askFile(String title) {
        FileChooser fc = new FileChooser();
        fc.setTitle(title);
        fc.setInitialDirectory(new File("").getAbsoluteFile());
        return fc.showSaveDialog(serializeBtn.getScene().getWindow());
    }

    private File askFolder(String title) {
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle(title);
        dc.setInitialDirectory(new File("").getAbsoluteFile());
        return dc.showDialog(serializeBtn.getScene().getWindow());
    }
}
