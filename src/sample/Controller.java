package sample;


import animatefx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;
import org.controlsfx.control.Notifications;
import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Controller implements Initializable {
    @FXML
    private VBox list_panel = null;

    @FXML
    public VBox hist_list_panel = null;

    @FXML
    private Pane all_items_pane, create_todo_pane, hist_items_pane;

    @FXML
    private Button notesTabBtn, newTabBtn, createNoteBtn, taskHistTabBtn;

    @FXML
    private TextField newNoteBody;

    @FXML
    private DatePicker datePicker;

    @FXML
    AnchorPane parent_pane;

    private static ArrayList<ItemController> controllers = new ArrayList<>();
    public static ArrayList<HistoryController> historyControllers = new ArrayList<>();

    private String currentView = "list";
    private  boolean serialized = false, attached = false;
    // add in each item in list view.
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        all_items_pane.toFront();
        serialized = false;
        InputStream input = null, histInput = null;
        try {
             input = new FileInputStream(new File(
                    Main.savePath));
             histInput = new FileInputStream(new File(Main.historyPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Object data = Main.yaml.load(input);
        Object histData = Main.yaml.load(histInput);
        HashMap<String, String> map = (HashMap<String, String>)data;
        HashMap<String, String> histMap = (HashMap<String, String>)histData;

        if(map != null)
        for(int i  = 0; i < map.size()/2; i++){
            Node node;

            try {
                node = FXMLLoader.load(getClass().getResource("currItem.fxml"));
                controllers.add(0, new ItemController(node, list_panel));
                controllers.get(0).setText(map.get("text"+i));
                controllers.get(0).setDate(LocalDate.parse(map.get("date"+i),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

//
        if(histMap != null)
            for(int i  = 0; i < histMap.size()/2; i++){
                Node node = null;

                try {
                    node = FXMLLoader.load(getClass().getResource("histItem.fxml"));
                    historyControllers.add(0, new HistoryController(node, hist_list_panel));
                    historyControllers.get(0).setHeading( histMap.get("text"+i));
                    historyControllers.get(0).setDate(histMap.get("date"+i));

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

        notesTabBtn.setOnMouseEntered((MouseEvent event) -> new Pulse(notesTabBtn).setSpeed(1).play());
        newTabBtn.setOnMouseEntered((MouseEvent event) -> new Pulse(newTabBtn).setSpeed(1).play());
        createNoteBtn.setOnMouseEntered((MouseEvent event) -> new Pulse(createNoteBtn).play());
        taskHistTabBtn.setOnMouseEntered((MouseEvent event) -> new Pulse(taskHistTabBtn).play());
        //System.out.println(map);

    }

    private <T extends Event> void closeWindowEvent(T t)  {

        if(!serialized) {
            FileWriter writer = null, histWriter = null;

            try {
                writer = new FileWriter(Main.savePath);
                histWriter = new FileWriter(Main.historyPath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ItemController[] ordered = new ItemController[controllers.size()];

            for (ItemController controller : controllers) {
                //System.out.println(controller.getIndex());
                if(controller.getIndex() != -1)
                 ordered[controller.getIndex()] = controller;
            }
            //System.out.println(Arrays.toString(ordered));
            for(int i = 0; i < ordered.length; i++){
                if(ordered[i] != null) {
                    Map<String, String> data = new HashMap<>();
                    data.put("text" + i, ordered[i].getText());
                    data.put("date" + i, ordered[i].getDate());


                    Main.yaml.dump(data, writer);

                }
            }

            for(int i = 0; i < historyControllers.size(); i++){
                if(historyControllers.get(i).getIndex() != -1) {
                    Map<String, String> data = new HashMap<>();
                    data.put("text" + i, historyControllers.get(i).getHeading());
                    data.put("date" + i, historyControllers.get(i).getDate());


                    Main.yaml.dump(data, histWriter);
                }
            }

            serialized = true;
        }


    }

    public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == notesTabBtn) {
            //notesTabBtn.setStyle("-fx-background-color : #E4563A;");
            if(!currentView.equals("list")) {
                new FadeIn(all_items_pane).setSpeed(2).play();
                currentView = "list";
            }

            all_items_pane.toFront();


        }
        if (actionEvent.getSource() == newTabBtn) {
            //newTabBtn.setStyle("-fx-background-color : #E4563A;");
            if(!currentView.equals("new")) {
                new FadeIn(create_todo_pane).setSpeed(2).play();
                currentView = "new";
            }
            notesTabBtn.applyCss();
            create_todo_pane.toFront();
        }
        if(actionEvent.getSource() == taskHistTabBtn){
            if(!currentView.equals("hist")) {
                new FadeIn(hist_items_pane).setSpeed(2).play();
                currentView = "hist";
            }
            taskHistTabBtn.applyCss();
            hist_items_pane.toFront();
        }

        if(actionEvent.getSource() == createNoteBtn){
            new Tada(createNoteBtn).setSpeed(1).play();
            Node node;
            try {
                node = FXMLLoader.load(getClass().getResource("currItem.fxml"));
                controllers.add(0, new ItemController(node, list_panel));
                controllers.get(0).setText(newNoteBody.getText());
                controllers.get(0).setDate(datePicker.getValue());


                //Notifications.create().title("New Note:").text(newNoteBody.getText()).show();

                newNoteBody.clear();
                datePicker.setValue(null);

            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }
    public void bindExit(){
        if(!attached) {
            all_items_pane.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
            attached = true;
        }
    }
    public static void removeController(int index){
        controllers.remove(index);
    }


}
