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
        chooseunit.setValue(chooseunit.getItems().toArray()[0]);
        root.getChildren().add(chooseunit);
        
        Button units = new Button();
        units.setText("units");
        units.setLayoutY(250);
        units.setOnAction((ActionEvent event) -> {
            upgradeUnits(primaryStage, lvl, redekk, Integer.parseInt(chooseunit.getValue().toString().split("-")[0]));
        });
        root.getChildren().add(units);
        
        Button newunit = new Button();
        newunit.setText("add new unit: cost: 50");
        newunit.setLayoutY(300);
        newunit.setOnAction((ActionEvent event) -> {
            if(redekk.mass>50){
                redekk.mass-=50;
                Unit[] u=new Unit[redekk.units.length+1];
                for(int j=0;j<redekk.units.length;j++){
                    u[j]=redekk.units[j];
                }
                u[redekk.units.length]=redekk.newdrone();
                redekk.units=u;
                startUpgrading(primaryStage,lvl, redekk);
            }
        });
        root.getChildren().add(newunit);
        
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
        Unit res=redekk.units[i];
        
        Text mass=new Text();
        mass.setText("mass= "+Integer.toString(redekk.mass));
        mass.setLayoutY(10);
        root.getChildren().add(mass);
        
        Button material=new Button();
        material.setText("material density: health, armor : cost: "+Integer.toString(
                res.armor*3+res.hp)
                );
        material.setLayoutY(120);
        material.setOnAction((ActionEvent event) -> {
            if(redekk.mass>res.armor*3+res.hp){
                redekk.mass-=res.armor*3+res.hp;
                res.armor++;
                res.hp+=5;
                upgradeUnits(primaryStage,lvl,redekk,i);
            }
        });
        root.getChildren().add(material);
        
        Button powercore=new Button();
        powercore.setText("power core: stamina, resistance ,range : cost: "+Integer.toString(
                res.resistance*3+res.maxStamina*3+res.range*3)
                );
        powercore.setLayoutY(160);
        powercore.setOnAction((ActionEvent event) -> {
            if(redekk.mass>res.resistance*3+res.maxStamina*3+res.range*3){
                redekk.mass-=res.resistance*3+res.maxStamina*3+res.range*3;
                res.resistance++;
                res.maxStamina++;
                res.range++;
                upgradeUnits(primaryStage,lvl,redekk,i);
            }
        });
        root.getChildren().add(powercore);
        
        Button weaponry=new Button();
        weaponry.setText("weaponry: meleedmg, rangeddmg : cost: "+Integer.toString(
                res.meleedmg*3+res.rangeddmg*3)
                );
        weaponry.setLayoutY(200);
        weaponry.setOnAction((ActionEvent event) -> {
            if(redekk.mass>res.meleedmg*3+res.rangeddmg*3){
                redekk.mass-=res.meleedmg*3+res.rangeddmg*3;
                res.meleedmg++;
                res.rangeddmg++;
                upgradeUnits(primaryStage,lvl,redekk,i);
            }
        });
        root.getChildren().add(weaponry);
        
        Button grade=new Button();
        grade.setText("grade: all : cost: "+Integer.toString(
                res.size*res.size*100)
                );
        grade.setLayoutY(240);
        grade.setOnAction((ActionEvent event) -> {
            if(redekk.mass>res.size*res.size*100){
                redekk.mass-=res.size*res.size*100;
                res.armor+=5;
                res.hp+=25;
                res.meleedmg+=5;
                res.rangeddmg+=5;
                res.resistance+=5;
                res.maxStamina+=5;
                res.range+=5;
                res.size+=5;
                upgradeUnits(primaryStage,lvl,redekk,i);
            }
        });
        root.getChildren().add(grade);
        
        Button back = new Button();
        back.setText("back");
        back.setLayoutY(450);
        back.setOnAction((ActionEvent event) -> {
            redekk.units[i]=res;
            startUpgrading(primaryStage,lvl, redekk);
        });
        root.getChildren().add(back);
        
        Scene scene = new Scene(root, 500, 500);
        
        
        primaryStage.setTitle("Re'dekk");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
}
