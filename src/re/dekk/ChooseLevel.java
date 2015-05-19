/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package re.dekk;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author rasamog
 */
public class ChooseLevel {
    static public void startChooseLevel(Stage primaryStage) throws IOException {
        Re_dekk player=new Re_dekk();
        
        //player=Re_dekk.load();
        
        Pane root = new Pane();
        ObservableList<String> levels=FXCollections.observableArrayList();
        Path path = FileSystems.getDefault().getPath("resource", "levels.redekk");
        BufferedReader reader= Files.newBufferedReader(path, StandardCharsets.UTF_8);
        String[] stats=reader.readLine().split(":")[0].split(" ");
        while(!stats[0].equals("|")){
            levels.add(stats[0]);
            stats=reader.readLine().split(":")[0].split(" ");
        }
        
        ComboBox chooselevel = new ComboBox(levels);
        chooselevel.setLayoutX(00);
        chooselevel.setLayoutY(00);
        chooselevel.setMaxWidth(100);
        chooselevel.setValue(chooselevel.getItems().toArray()[0]);
        root.getChildren().add(chooselevel);
        
        Button startShosenLevel=new Button();
        startShosenLevel.setText("start level");
        startShosenLevel.setLayoutX(0);
        startShosenLevel.setLayoutY(30);
        startShosenLevel.setOnAction((ActionEvent event) -> {
            try {
                Level.startLevel(primaryStage, Integer.parseInt(chooselevel.getValue().toString()),player);
            } catch (IOException ex) {
                Logger.getLogger(ChooseLevel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            
        });
        root.getChildren().add(startShosenLevel);
        
        Scene scene = new Scene(root, 500, 500);
        
        
        primaryStage.setTitle("Re'dekk");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
