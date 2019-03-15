package sample;

import animatefx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static sample.Controller.historyControllers;

class ItemController{

    private Label noteLbl;
    private HBox layout;
    private VBox parentLayout;
    private DatePicker datePicker;
    private Button upBtn, downBtn, removeBtn;
    private String text, date;
    private ImageView xIcon;
    private Node root;


    ItemController(Node root, VBox parent){

        this.root = root;
        parentLayout = parent;

        //System.out.println(this.index);
        this.root.setOnMouseEntered((MouseEvent event) -> {
            this.root.setStyle("-fx-border-color: #FDFAFA; -fx-border-width: 3px; -fx-background-color : #a5a7aa;");
            removeBtn.setStyle("-fx-background-color : #a5a7aa;");

        });
        this.root.setOnMouseExited(event -> {
            this.root.setStyle("-fx-border-color: #FDFAFA; -fx-border-width: 3px; -fx-background-color :  #cdcfd3;");
            removeBtn.setStyle("-fx-background-color : #cdcfd3;");
        });



        layout = (HBox)this.root;
        noteLbl = (Label)layout.lookup("#noteLbl");
        datePicker = (DatePicker)layout.lookup("#date");
        upBtn = (Button)layout.lookup("#upBtn");
        downBtn = (Button)layout.lookup("#downBtn");
        removeBtn = (Button)layout.lookup("#removeBtn");
        xIcon = (ImageView)layout.lookup("#xIcon");

        downBtn.setOnMouseEntered((MouseEvent event) -> downBtn.setStyle(" -fx-background-color : #9fd2e0; -fx-background-radius: 25px;"));
        downBtn.setOnMouseExited((MouseEvent event) ->  downBtn.setStyle(" -fx-background-color : #cccccc; -fx-background-radius: 25px;"));
        upBtn.setOnMouseEntered((MouseEvent event) -> upBtn.setStyle(" -fx-background-color : #9fd2e0; -fx-background-radius: 25px;"));
        upBtn.setOnMouseExited((MouseEvent event) -> upBtn.setStyle(" -fx-background-color : #cccccc; -fx-background-radius: 25px;"));


        handleClicks();

        parentLayout.getChildren().add(this.root);


    }


    private void handleClicks() {
        upBtn.setOnMouseClicked((MouseEvent event) -> {
            //upBtn.setStyle(" -fx-background-color : #56727a; -fx-background-radius: 25px;");
            ObservableList<Node> workingCollection = FXCollections.observableArrayList(parentLayout.getChildren());
            //System.out.println(this.index);
            Collections.swap(workingCollection, parentLayout.getChildren().indexOf(this.root)-1, parentLayout.getChildren().indexOf(this.root));
            new SlideInUp(this.root).setSpeed(2).play();
            parentLayout.getChildren().setAll(workingCollection);

            //System.out.println(parentLayout.getChildren().indexOf(this.root));


        });
        downBtn.setOnMouseClicked((MouseEvent event) -> {
            //downBtn.setStyle(" -fx-background-color : #56727a; -fx-background-radius: 25px;");
            ObservableList<Node> workingCollection = FXCollections.observableArrayList(parentLayout.getChildren());
            Collections.swap(workingCollection, parentLayout.getChildren().indexOf(this.root)+1, parentLayout.getChildren().indexOf(this.root));
            new SlideInDown(this.root).setSpeed(2).play();
            parentLayout.getChildren().setAll(workingCollection);

            //System.out.println(parentLayout.getChildren().indexOf(this.root));

        });
        removeBtn.setOnMouseClicked((MouseEvent event) -> {
            LightSpeedOut anim = new LightSpeedOut(this.root);
            anim.setSpeed(2).setOnFinished(this::discard);
            anim.play();


            //Controller.removeController(tempIndex);
        });

        removeBtn.setOnMouseEntered((MouseEvent event) -> new Flash(removeBtn).play());

    }
    private <T extends Event> void discard(T t){
        ObservableList<Node> workingCollection = FXCollections.observableArrayList(parentLayout.getChildren());
        //int tempIndex = parentLayout.getChildren().indexOf(this.root);
        workingCollection.remove(parentLayout.getChildren().indexOf(this.root));
        parentLayout.getChildren().setAll(workingCollection);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");


        Node node = null;

        try {
            node = FXMLLoader.load(getClass().getResource("histItem.fxml"));
            historyControllers.add(0, new HistoryController(node, Main.ctrl.hist_list_panel));
            historyControllers.get(0).setHeading(noteLbl.getText());
            historyControllers.get(0).setDate(format.format(new Date()));

        } catch (IOException e) {
            e.printStackTrace();
        }



    }
     void setText(String text){
        this.text = text;
        noteLbl.setText(text);

    }

     void setDate(LocalDate date){
        datePicker.setValue(date);
        this.date = date.toString();
        //System.out.println(this.date);
    }

     String getDate() {
        return date;
    }

     String getText() {
        return text;
    }
     int getIndex(){

        return parentLayout.getChildren().indexOf(root);
    }
}