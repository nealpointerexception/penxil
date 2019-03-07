package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class Main extends Application {

    public static String savePath = "";
    public static Yaml yaml;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));

        primaryStage.setTitle("Penxil");
        primaryStage.setScene(new Scene(root, 733, 476));
        primaryStage.show();
    }


    public static void main(String[] args) {

        savePath = System.getProperty("user.home")+"/.config/penxil/save.yaml";
        File pathToFile = new File(savePath);
        pathToFile.getParentFile().mkdirs();
        try {
            new FileOutputStream(savePath, true).close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        DumperOptions ops = new DumperOptions();
        ops.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

            //yamlReader = new YamlReader(new FileReader("/home/nealc/Documents/gitprojs/penxil/src/sample/save.yaml"));
        yaml = new Yaml(ops);

        launch(args);
    }


    @Override
    public void stop() {

    }
}
