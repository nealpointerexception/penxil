package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.time.LocalDate;
import java.util.Collections;
import java.util.ResourceBundle;

public class ItemController{

    private Label noteLbl;
    private HBox layout;
    private VBox parentLayout;
    private DatePicker datePicker;
    private Button upBtn, downBtn;
    private String text, date;
    public Node root;


    public ItemController(Node root, VBox parent){

        this.root = root;
        parentLayout = parent;

        //System.out.println(this.index);
        this.root.setOnMouseEntered((MouseEvent event) -> {
            this.root.setStyle("-fx-border-color: #FDFAFA; -fx-border-width: 3px; -fx-background-color : #a5a7aa;");

        });

        this.root.setOnMouseExited(event -> {
            this.root.setStyle("-fx-border-color: #FDFAFA; -fx-border-width: 3px; -fx-background-color :  #cdcfd3;");

        });



        layout = (HBox)this.root;
        noteLbl = (Label)layout.lookup("#noteLbl");
        datePicker = (DatePicker)layout.lookup("#date");
        upBtn = (Button)layout.lookup("#upBtn");
        downBtn = (Button)layout.lookup("#downBtn");




        downBtn.setOnMouseClicked((MouseEvent event) -> {
            //downBtn.setStyle(" -fx-background-color : #56727a; -fx-background-radius: 25px;");
            ObservableList<Node> workingCollection = FXCollections.observableArrayList(parentLayout.getChildren());
            Collections.swap(workingCollection, parent.getChildren().indexOf(this.root), parent.getChildren().indexOf(this.root)+1);
            parentLayout.getChildren().setAll(workingCollection);

        });
        downBtn.setOnMouseEntered((MouseEvent event) -> {
            downBtn.setStyle(" -fx-background-color : #9fd2e0; -fx-background-radius: 25px;");

        });
        downBtn.setOnMouseExited((MouseEvent event) -> {
            downBtn.setStyle(" -fx-background-color : #cccccc; -fx-background-radius: 25px;");

        });

        upBtn.setOnMouseClicked((MouseEvent event) -> {
            //upBtn.setStyle(" -fx-background-color : #56727a; -fx-background-radius: 25px;");
            ObservableList<Node> workingCollection = FXCollections.observableArrayList(parentLayout.getChildren());
            //System.out.println(this.index);
            Collections.swap(workingCollection, parent.getChildren().indexOf(this.root), parent.getChildren().indexOf(this.root)-1);
            parentLayout.getChildren().setAll(workingCollection);


        });
        upBtn.setOnMouseEntered((MouseEvent event) -> {
            upBtn.setStyle(" -fx-background-color : #9fd2e0; -fx-background-radius: 25px;");

        });
        upBtn.setOnMouseExited((MouseEvent event) -> {
            upBtn.setStyle(" -fx-background-color : #cccccc; -fx-background-radius: 25px;");

        });

        parentLayout.getChildren().add(this.root);


    }

    public void setText(String text){
        this.text = text;
        noteLbl.setText(text);

    }

    public void setDate(LocalDate date){
        datePicker.setValue(date);
        this.date = date.toString();
        System.out.println(this.date);
    }

    public LocalDate getDateObj() {
        return datePicker.getValue();
    }

    public String getDate() {
        return date;
    }

    public String getText() {
        return text;
    }
    public int getIndex(){
        return parentLayout.getChildren().indexOf(this.root);
    }
}
