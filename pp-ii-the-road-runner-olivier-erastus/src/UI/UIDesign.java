package UI;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

// This class contains style for some UI elements to reduce codes in uiMap class
public class UIDesign {
//    General styling for buttons
    static void style(Button btn){
        btn.setStyle("-fx-font-size: 1.5em;" +
                "-fx-background-color: #00ff00;" +
                "-fx-text-fill: #ffffff;" +
                "-fx-font-family: SansSerif; " +
                "-fx-font-weight: bold; " +
                "-fx-border-radius: 0; " +
                "-fx-padding: 10px; " +
                "");

    }

    static void style(HBox hbox){
        hbox.setAlignment(Pos.CENTER);

    }
//    Styles for vbox
    static void style(VBox vbox){
//        vbox.setMinHeight(700);
        vbox.setStyle("-fx-border-color: black; -fx-border-width: 2");
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(25);
    }
    static void style(Text txt){
        txt.setStyle("-fx-font-weight: bold; -fx-text-alignment: center");

    }

//    Some buttons need different styling according to their statuses
    static void style(Button btn, boolean status){
        if(status){
            btn.setStyle("-fx-font-size: 10; " +
                    "-fx-font-family: SansSerif; " +
                    "-fx-font-weight: bold; " +
                    "-fx-border-radius: 0; " +
                    "-fx-padding: 10px; " +
                    "-fx-background-color: #2E8B57; ");
        }
        else{
            btn.setStyle("-fx-font-size: 10; " +
                    "-fx-font-family: SansSerif; " +
                    "-fx-font-weight: bold; " +
                    "-fx-border-radius: 0; " +
                    "-fx-padding: 10px; " +
                    "");
        }
    }
}
