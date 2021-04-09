package sample.deserializing;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.Controller;
import sample.Hierarchy.IJSONAdapter;
import sample.Hierarchy.JSONAdapter;
import sample.Hierarchy.Legion;
import sample.plugins.IPlugin;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class DeserializingController {

    @FXML
    private ImageView pluginsFolder;

    @FXML
    private TextField pluginsFolderField;

    @FXML
    private ImageView openFileImg;

    @FXML
    private TextField openFileField;

    @FXML
    private Button chooseBtn;

    @FXML
    private TextField keyField;

    @FXML
    private Button deserializeBtn;

    private IPlugin loadedPlugin;
    private List<IPlugin> plugins;
    private Thread statusChecker;
    public static EventHandler<WindowEvent> onCloseRequest;

    @FXML
    void initialize() {
        statusChecker = new Thread(() -> {
            boolean isDisabled = true;
            while (!statusChecker.isInterrupted()) {
                if (openFileField.getText().length() > 0
                        && Files.exists(Paths.get(openFileField.getText()))) {
                    if (isDisabled) {
                        deserializeBtn.setDisable(false);
                        isDisabled = false;
                    }
                } else {
                    if (!isDisabled) {
                        deserializeBtn.setDisable(true);
                        isDisabled = true;
                    }
                }
            }
        });

        statusChecker.start();
        onCloseRequest = event -> statusChecker.interrupt();
        loadedPlugin = null;

        deserializeBtn.setOnAction(actionEvent -> {
            try {
                byte[] allBytes = Files.readAllBytes(Paths.get(openFileField.getText()));
                try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(allBytes)) {
                    byte b;

                    StringBuilder pluginClassSB = new StringBuilder();
                    while ((b = (byte) byteArrayInputStream.read()) != '|') {
                        pluginClassSB.append((char) b);
                    }

                    if (pluginClassSB.length() > 0) {
                        for (IPlugin plugin : plugins) {
                            if (plugin.getClass().toString().equals(pluginClassSB.toString())) {
                                loadedPlugin = plugin;
                                break;
                            }
                        }

                        if (loadedPlugin == null) {
                            alert("Error", "Plugin " + pluginClassSB + " is not found", Alert.AlertType.ERROR);
                            return;
                        }
                    }

                    StringBuilder methodSB = new StringBuilder();
                    while ((b = (byte) byteArrayInputStream.read()) != '|') {
                        methodSB.append((char) b);
                    }

                    String method = methodSB.toString();
                    if (!method.equals("text") && !method.equals("xml") && !method.equals("json")) {
                        alert("Error", "Unsupported deserialize method", Alert.AlertType.ERROR);
                        return;
                    }

                    ArrayList<Byte> objectBytesAL = new ArrayList<>();
                    while (byteArrayInputStream.available() > 0) {
                        objectBytesAL.add((byte) byteArrayInputStream.read());
                    }

                    byte[] objectBytes = new byte[objectBytesAL.size()];
                    int i = 0;
                    for (Byte current : objectBytesAL) {
                        objectBytes[i++] = current;
                    }

                    if (loadedPlugin != null && keyField.getText().length() > 0) {
                        objectBytes = loadedPlugin.decrypt(objectBytes, keyField.getText().getBytes());
                    }

                    if (method.equals("text")) {
                        Legion.getLegion().readExternal(new ObjectInputStream(new ByteArrayInputStream(objectBytes)));
                    }

                    if(method.equals("xml")) {
                        Legion.getLegion().xmlDeserialize(new StringReader(new String(objectBytes)));
                    }

                    if(method.equals("json")) {
                        IJSONAdapter jsonAdapter = new JSONAdapter(Legion.getLegion());
                        jsonAdapter.fromJson(objectBytes);
                    }

                    alert("Deserializing", "Done!", Alert.AlertType.INFORMATION);
                    ((Stage) deserializeBtn.getScene().getWindow()).close();
                } catch (IOException | JAXBException | ClassNotFoundException e) {
                    e.printStackTrace();
                    alert("Error", "Error during deserializing", Alert.AlertType.ERROR);
                }
            } catch (IOException e) {
                e.printStackTrace();
                alert("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        });

        chooseBtn.setOnAction(actionEvent -> openFileField.setText(askFile("Choose file").getAbsolutePath()));

        openFileImg.setOnMouseClicked(mouseEvent -> openFileField.setText(askFile("Choose save file").getAbsolutePath()));

        pluginsFolder.setOnMouseClicked(mouseEvent -> {
            File folder = askFolder("Choose plugins folder");
            pluginsFolderField.setText(folder.getAbsolutePath());
            plugins = findPlugins(folder);
        });
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
        return fc.showOpenDialog(deserializeBtn.getScene().getWindow());
    }

    private File askFolder(String title) {
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle(title);
        dc.setInitialDirectory(new File("").getAbsoluteFile());
        return dc.showDialog(deserializeBtn.getScene().getWindow());
    }

    private void alert(String title, String text, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }
}
