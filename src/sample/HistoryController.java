package sample;

import animatefx.animation.LightSpeedOut;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class HistoryController {
    private VBox parentLayout;
    private HBox layout;
    private Label heading, date;
    private Button removeBtn;
    private Node root;

    public HistoryController(Node root, VBox parent){
        this.root = root;
        layout = (HBox)this.root;
        this.parentLayout = parent;
        this.heading = (Label)layout.lookup("#noteLbl");
        this.date = (Label)layout.lookup("#dateLbl");

        removeBtn = (Button) layout.lookup("#removeBtn");

        parent.getChildren().add(root);

        removeBtn.setOnMouseClicked((MouseEvent event) -> {
            LightSpeedOut anim = new LightSpeedOut(this.layout);
            anim.setSpeed(2).setOnFinished(this::discard);
            anim.play();


            //Controller.removeController(tempIndex);
        });

    }
    private <T extends Event> void discard(T t){
        ObservableList<Node> workingCollection = FXCollections.observableArrayList(parentLayout.getChildren());
        //int tempIndex = parentLayout.getChildren().indexOf(this.parentLayout);
        workingCollection.remove(parentLayout.getChildren().indexOf(this.root));
        parentLayout.getChildren().setAll(workingCollection);


    }

    public void setHeading(String heading) {
        this.heading.setText(heading);
    }

    public void setDate(String date) {
        this.date.setText(date);
    }

    public String getDate(){
        return this.date.getText();
    }

    public String getHeading(){
        return  this.heading.getText();
    }
    public int getIndex(){
        return parentLayout.getChildren().indexOf(this.root);
    }
}
