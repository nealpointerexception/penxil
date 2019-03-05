package sample;


import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;
import org.yaml.snakeyaml.DumperOptions;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private VBox list_panel = null;

    @FXML
    private Pane all_items_pane, create_todo_pane;

    @FXML
    private Button notesTabBtn, newTabBtn, createNoteBtn;

    @FXML
    private TextField newNoteBody;

    @FXML
    private DatePicker datePicker;

    private ArrayList<ItemController> controllers = new ArrayList<>();

    private  boolean serialized = false;
    // add in each item in list view.
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        all_items_pane.toFront();
        InputStream input = null;
        try {
             input = new FileInputStream(new File(
                    "/home/nealc/Documents/gitprojs/penxil/src/sample/save.yaml"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Object data = Main.yaml.loadAll(input);
        HashMap<String, String> map = (HashMap<String, String>)data;

        System.out.println(map);

//        while (true) {
//            try {
//                Object object = Main.yamlReader.read();
//                System.out.println(object);
//                Map data = (Map)object;
//                if (data == null) break;
//                Node node;
//
//                node = FXMLLoader.load(getClass().getResource("item.fxml"));
//                controllers.add(0, new ItemController(node, list_panel));
//                controllers.get(0).setText((String)data.get("text"));
//                //controllers.get(0).setDate();
//
//
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//
//        try {
//            Main.yamlReader.close();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }


    }

    private <T extends Event> void closeWindowEvent(T t)  {
        FileWriter writer = null;
        if(!serialized) {
            try {
                writer = new FileWriter("/home/nealc/Documents/gitprojs/penxil/src/sample/save.yaml");
            } catch (IOException e) {
                e.printStackTrace();
            }

            ItemController[] ordered = new ItemController[controllers.size()];

            for (ItemController controller : controllers) {
                 ordered[controller.getIndex()] = controller;
            }
            for(ItemController controller : ordered){
                Map<String, String> data = new HashMap<>();
                data.put("text", controller.getText());
                data.put("date", controller.getDate());


                Main.yaml.dump(data, writer);


            }
            serialized = true;
        }

    }

    public void handleClicks(ActionEvent actionEvent) {
        all_items_pane.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
        if (actionEvent.getSource() == notesTabBtn) {
            //notesTabBtn.setStyle("-fx-background-color : #E4563A;");

            all_items_pane.toFront();
        }
        if (actionEvent.getSource() == newTabBtn) {
            //newTabBtn.setStyle("-fx-background-color : #E4563A;");
            notesTabBtn.applyCss();
            create_todo_pane.toFront();
        }
        if(actionEvent.getSource() == createNoteBtn){
            Node node;
            try {
                node = FXMLLoader.load(getClass().getResource("item.fxml"));
                controllers.add(0, new ItemController(node, list_panel));
                controllers.get(0).setText(newNoteBody.getText());
                controllers.get(0).setDate(datePicker.getValue());


                newNoteBody.clear();
                datePicker.setValue(null);

                //yamlWriter.write("---");


            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }


}
