/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package re.dekk;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author rasamog
 */
public class StartMenu extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Button start = new Button();
        start.setText("start game");
        start.setLayoutX(20);
        start.setLayoutY(20);
        start.setOnAction((ActionEvent event) -> {
            try {
                ChooseLevel.startChooseLevel(primaryStage);
            } catch (IOException ex) {
                Logger.getLogger(StartMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        Button exit = new Button();
        exit.setText("exit game");
        exit.setLayoutX(20);
        exit.setLayoutY(80);
        exit.setOnAction((ActionEvent event) -> {
            primaryStage.close();
        });
        
        Pane root = new Pane();
        root.getChildren().add(start);
        root.getChildren().add(exit);
        
        Scene scene = new Scene(root, 500, 500);
        
        primaryStage.setTitle("Re'dekk");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
