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
import javafx.stage.Stage;

/**
 *
 * @author rasamog
 */
public class Level {
    static Ship[][] map;
    static Re_dekk player;

    private static Re_dekk endturn(Re_dekk player) {
        for(Ship[] ss:map){
            for(Ship s:ss){
                if(s!=null){
                    player=s.shoot(player);
                }
            }
        }
        player.power+=player.powergeneration;
        return player;
    }
    
    Level(int x,int y){
        map=new Ship[x][y];
    }
    
    static public void startLevel(Stage primaryStage,int level,Re_dekk playe) throws IOException {
        player=playe;
        
        Pane root = new Pane();
        Level lvl=LevelSetter.setLevel(level);
        for(int i=0;i<LevelSetter.levelX (level);i++){
            for(int j=0;j<LevelSetter.levelY (level);j++){
                Button b=new Button();
                b.setText(lvl.map[i][j].type);
                b.setLayoutX(50*i);
                b.setLayoutY(50*j);
                final Ship ship=lvl.map[i][j];
                b.setOnAction((ActionEvent event) -> {
                    try {
                        Ship.chooseBattle(primaryStage, ship,player.units);
                    } catch (IOException ex) {
                        Logger.getLogger(Level.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    }
                });
                root.getChildren().add(b);
            }
        }
        
        Button exit = new Button();
        exit.setText("exit game");
        exit.setLayoutY(450);
        exit.setOnAction((ActionEvent event) -> {
            primaryStage.close();
        });
        
        root.getChildren().add(exit);
        
        Button nextturn = new Button();
        nextturn.setText("next turn");
        nextturn.setLayoutY(450);
        nextturn.setLayoutX(100);
        nextturn.setOnAction((ActionEvent event) -> {
            endturn(player);
            if(player.mass<=0){
                System.out.println("YOU HAVE BEEN DESTROYED");
            }
            primaryStage.close();
        });
        
        root.getChildren().add(nextturn);
        
        Scene scene = new Scene(root, 500, 500);
        
        
        primaryStage.setTitle("Re'dekk");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
