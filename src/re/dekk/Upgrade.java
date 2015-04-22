/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package re.dekk;

import java.io.IOException;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author rasamog
 */
public class Upgrade {
    
    static public void startUpgrading(Stage primaryStage,Level lvl,Re_dekk redekk) {
        Pane root = new Pane();
        
        Text mass=new Text();
        mass.setText("mass= "+Integer.toString(redekk.mass));
        mass.setLayoutY(10);
        root.getChildren().add(mass);
        
        Button shield = new Button();
        shield.setText("shields:["+Integer.toString(redekk.shield) +"]cost: "+Integer.toString(redekk.shield*5+3));
        shield.setLayoutY(50);
        shield.setOnAction((ActionEvent event) -> {
            if(redekk.mass>redekk.shield*5+3){
                redekk.mass-=redekk.shield*5+3;
                redekk.shield++;
                startUpgrading(primaryStage,lvl, redekk);
            }
        });
        root.getChildren().add(shield);
        
        Button armor = new Button();
        armor.setText("armor:["+Integer.toString(redekk.armor) +"]cost: "+Integer.toString(redekk.armor*5+3));
        armor.setLayoutY(100);
        armor.setOnAction((ActionEvent event) -> {
            if(redekk.mass>redekk.armor*5+3){
                redekk.mass-=redekk.armor*5+3;
                redekk.armor++;
                startUpgrading(primaryStage,lvl, redekk);
            }
        });
        root.getChildren().add(armor);
        
        Button powergeneration = new Button();
        powergeneration.setText("powergeneration:["+Integer.toString(redekk.powergeneration) +"]cost: "+Integer.toString(redekk.powergeneration*8));
        powergeneration.setLayoutY(150);
        powergeneration.setOnAction((ActionEvent event) -> {
            if(redekk.mass>redekk.powergeneration*8){
                redekk.mass-=redekk.powergeneration*8;
                redekk.powergeneration++;
                startUpgrading(primaryStage,lvl, redekk);
            }
        });
        root.getChildren().add(powergeneration);
        
        Button units = new Button();
        units.setText("units");
        units.setLayoutY(200);
        units.setOnAction((ActionEvent event) -> {
            upgradeUnits(primaryStage, lvl, redekk);
        });
        root.getChildren().add(units);
        
        Button back = new Button();
        back.setText("back");
        back.setLayoutY(450);
        back.setOnAction((ActionEvent event) -> {
            try {
                Level.resumeLevel(primaryStage, lvl, redekk);
            } catch (IOException ex) {
                Logger.getLogger(Upgrade.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        });
        root.getChildren().add(back);
        
        Scene scene = new Scene(root, 500, 500);
        
        
        primaryStage.setTitle("Re'dekk");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    static public void upgradeUnits(Stage primaryStage,Level lvl,Re_dekk redekk) {
        Pane root = new Pane();
        
        Text mass=new Text();
        mass.setText("mass= "+Integer.toString(redekk.mass));
        mass.setLayoutY(10);
        root.getChildren().add(mass);
        
        
        Button back = new Button();
        back.setText("back");
        back.setLayoutY(450);
        back.setOnAction((ActionEvent event) -> {
            try {
                Level.resumeLevel(primaryStage, lvl, redekk);
            } catch (IOException ex) {
                Logger.getLogger(Upgrade.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        });
        root.getChildren().add(back);
        
        Scene scene = new Scene(root, 500, 500);
        
        
        primaryStage.setTitle("Re'dekk");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
}
