/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package re.dekk;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author rasamog
 */
public class Ship {
    ShipPart[][] parts;
    String type,name;
    int mass,kineticdmg,ammo,power,ammodrain,powerdrain,energydmg;
    
    Ship(String stype){
        try {
            Ship set = ShipSetter.setShip(stype);
            type=set.type;
            name=set.name;
            mass=set.mass;
            parts=set.parts;
            update();
        } catch (IOException ex) {
            Logger.getLogger(Ship.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void update(){
        kineticdmg=0;
        ammo=0;
        ammodrain=0;
        power=0;
        powerdrain=0;
        energydmg=0;
        for(ShipPart[] pp:parts){
            for(ShipPart p:pp){
                if(p!=null){
                    switch (p.special.type){
                        case "attack":
                            if(p.special.resource.equals("ammo")){
                                kineticdmg+=p.special.amount;
                                ammodrain+=p.special.resourceamount;
                            }else{
                                energydmg+=p.special.amount;
                                powerdrain+=p.special.resourceamount;
                            }
                        break;
                        case "supply":
                            if(p.special.resource.equals("ammo")){
                                ammo+=p.special.amount;
                            }else{
                                power+=p.special.amount;
                            }
                        break;
                        default:break;
                    }
                }
            }
        }
    }
    
    Ship(int x,int y){
        parts=new ShipPart[x][y];
        type="";
    }
    
    static public void chooseBattle(Stage primaryStage,Ship ship,Unit[] team) throws IOException {
        
        Pane root = new Pane();
        for(int i=0;i<ShipSetter.shipX(ship.type);i++){
            for(int j=0;j<ShipSetter.shipY(ship.type);j++){
                if(ship.parts[i][j]!=null){
                    Button b=new Button();
                    b.setText(ship.parts[i][j].type);
                    b.setLayoutX(40*i);
                    b.setLayoutY(40*j);
                    b.setPrefSize(40*ship.parts[i][j].xsize, 40*ship.parts[i][j].ysize);
                    final ShipPart part=ship.parts[i][j];
                    b.setOnAction((ActionEvent event) -> {
                        Battle.startBattle(primaryStage, part,team);
                    });
                    root.getChildren().add(b);
                }
            }
        }
        
        Button exit = new Button();
        exit.setText("exit game");
        exit.setLayoutY(450);
        exit.setOnAction((ActionEvent event) -> {
            primaryStage.close();
        });
        
        root.getChildren().add(exit);
        
        Scene scene = new Scene(root, 500, 500);
        
        
        primaryStage.setTitle("Re'dekk");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    Re_dekk shoot(Re_dekk player) {
        if(ammo>=ammodrain){
            player.mass-=kineticdmg-player.armor;
            ammo-=ammodrain;
        }
        if(power>=powerdrain){
            player.mass-=energydmg-player.shield;
        }
        return player;
    }
    
}
