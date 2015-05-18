/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package re.dekk;

import java.io.IOException;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
        
        ObservableList<String> listofunits=FXCollections.observableArrayList();
        for(int i=0;i<redekk.units.length;i++){
            listofunits.add(Integer.toString(i)+"-"+redekk.units[i].name);
        }
        
        ComboBox chooseunit = new ComboBox(listofunits);
        chooseunit.setLayoutY(200);
        root.getChildren().add(chooseunit);
        
        Button units = new Button();
        units.setText("units");
        units.setLayoutY(250);
        units.setOnAction((ActionEvent event) -> {
            upgradeUnits(primaryStage, lvl, redekk, Integer.parseInt(chooseunit.getValue().toString().split("-")[0]));
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
    
    static public void upgradeUnits(Stage primaryStage,Level lvl,Re_dekk redekk,int i) {
        Pane root = new Pane();
        
        Text mass=new Text();
        mass.setText("mass= "+Integer.toString(redekk.mass));
        mass.setLayoutY(10);
        root.getChildren().add(mass);
        
        Button material=new Button();
        material.setText("material density: health, armor : cost: "+Integer.toString(
                redekk.units[i].armor*3+redekk.units[i].hp)
                );
        material.setLayoutY(120);
        material.setOnAction((ActionEvent event) -> {
            if(redekk.mass>redekk.units[i].armor*3+redekk.units[i].hp){
                redekk.mass-=redekk.units[i].armor*3+redekk.units[i].hp;
                redekk.units[i].armor++;
                redekk.units[i].hp+=5;
                upgradeUnits(primaryStage,lvl,redekk,i);
            }
        });
        root.getChildren().add(material);
        
        Button powercore=new Button();
        powercore.setText("power core: stamina, resistance ,range : cost: "+Integer.toString(
                redekk.units[i].resistance*3+redekk.units[i].maxStamina*3+redekk.units[i].range*3)
                );
        powercore.setLayoutY(160);
        powercore.setOnAction((ActionEvent event) -> {
            if(redekk.mass>redekk.units[i].resistance*3+redekk.units[i].maxStamina*3+redekk.units[i].range*3){
                redekk.mass-=redekk.units[i].resistance*3+redekk.units[i].maxStamina*3+redekk.units[i].range*3;
                redekk.units[i].resistance++;
                redekk.units[i].maxStamina++;
                redekk.units[i].range++;
                upgradeUnits(primaryStage,lvl,redekk,i);
            }
        });
        root.getChildren().add(powercore);
        
        Button weaponry=new Button();
        weaponry.setText("weaponry: meleedmg, rangeddmg : cost: "+Integer.toString(
                redekk.units[i].meleedmg*3+redekk.units[i].rangeddmg*3)
                );
        weaponry.setLayoutY(200);
        weaponry.setOnAction((ActionEvent event) -> {
            if(redekk.mass>redekk.units[i].meleedmg*3+redekk.units[i].rangeddmg*3){
                redekk.mass-=redekk.units[i].meleedmg*3+redekk.units[i].rangeddmg*3;
                redekk.units[i].meleedmg++;
                redekk.units[i].rangeddmg++;
                upgradeUnits(primaryStage,lvl,redekk,i);
            }
        });
        root.getChildren().add(weaponry);
        
        Button grade=new Button();
        grade.setText("grade: all : cost: "+Integer.toString(
                redekk.units[i].size*redekk.units[i].size*100)
                );
        grade.setLayoutY(240);
        grade.setOnAction((ActionEvent event) -> {
            if(redekk.mass>redekk.units[i].size*redekk.units[i].size*100){
                redekk.mass-=redekk.units[i].size*redekk.units[i].size*100;
                redekk.units[i].armor+=5;
                redekk.units[i].hp+=25;
                redekk.units[i].meleedmg+=5;
                redekk.units[i].rangeddmg+=5;
                redekk.units[i].resistance+=5;
                redekk.units[i].maxStamina+=5;
                redekk.units[i].range+=5;
                redekk.units[i].size+=5;
                upgradeUnits(primaryStage,lvl,redekk,i);
            }
        });
        root.getChildren().add(grade);
        
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
