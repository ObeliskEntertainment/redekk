/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package re.dekk;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author rasamog
 */
public class Battle {

    Unit[][] map;
    static Unit selected=new Unit();
    static int selectedx,selectedy,sizex,sizey;
    static Unit clear=new Unit();
    
    private static void select(int x,int y,Battle batt,Stage primaryStage) {
        if(selected.owner.equals("none")){
            if(batt.map[x][y].owner.equals("Re'dekk")){
                selected=batt.map[x][y];
                selectedx=x;
                selectedy=y;
            }
        }else{
            if(batt.map[x][y].owner.equals("none")){
                if((Math.abs(selectedx-x)+Math.abs(selectedy-y))<=selected.stamina){
                    batt.map[x][y]=selected;
                    batt.map[selectedx][selectedy]=clear;
                    selected=clear;
                    reload(primaryStage,batt);
                }else{
                    selected=clear;
                }
            }else if(batt.map[x][y].owner.equals("Re'dekk")){
                selected=clear;
            }else if(batt.map[x][y].owner.equals("AI")){
                if((Math.abs(selectedx-x)+Math.abs(selectedy-y))==1){
                    batt.map[x][y]=selected.hit(batt.map[x][y], false);
                    if(batt.map[x][y].hp<=0){
                        batt.map[x][y]=clear;
                        reload(primaryStage,batt);
                    }
                }
                else if((Math.abs(selectedx-x)+Math.abs(selectedy-y))<=selected.range){
                    batt.map[x][y]=selected.hit(batt.map[x][y], true);
                    if(batt.map[x][y].hp<=0){
                        batt.map[x][y]=clear;
                        reload(primaryStage,batt);
                    }
                }
            }
        }
    }
    
    static void reload(Stage primaryStage,Battle batt){
        Pane root = new Pane();
        
        batt.map = AI.turn(batt);
        
        for(int i=0;i<sizex;i++){
            for(int j=0;j<sizey;j++){
                
                    Button b=new Button();
                    b.setLayoutX(40*i);
                    b.setLayoutY(40*j);
                    b.setPrefSize(40, 40);
                    if(!(batt.map[i][j].sign.equals("N"))){
                        b.setText(batt.map[i][j].sign);
                    }
                    int x=i,y=j;
                    b.setOnAction((ActionEvent event) -> {
                        Battle.select(x,y,batt,primaryStage);
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
        
        Scene scene = new Scene(root, 800, 500);
        
        
        primaryStage.setTitle("Re'dekk");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    Battle(int sizex,int sizey){
        map=new Unit[sizex][sizey];
        for(int i=0;i<sizex;i++){
            for(int j=0;j<sizey;j++){
                map[i][j]=new Unit();
            }
        }
    }
    
    static public void startBattle(Stage primaryStage,ShipPart part,Unit[] chosenteam) {
        
        sizex=part.xsize*5;
        sizey=part.ysize*5;
        Pane root = new Pane();
        Boolean done;
        Battle batt=new Battle(sizex,sizey);
        
        for(Unit u:chosenteam){
            done=false;
            while(!done){
                int x=(int) Math.round(Math.random()*(sizex-1));
                int y=(int) Math.round(Math.random()*(sizey-1));
                if(batt.map[x][y].sign.equals("N")){
                    batt.map[x][y]=u;
                    done=true;
                }
            }
        }
        for(Unit u:part.crew){
            done=false;
            while(!done){
                int x=(int) Math.round(Math.random()*(sizex-1));
                int y=(int) Math.round(Math.random()*(sizey-1));
                if(batt.map[x][y].sign.equals("N")){
                    batt.map[x][y]=u;
                    done=true;
                }
            }
        }
        
        for(int i=0;i<sizex;i++){
            for(int j=0;j<sizey;j++){
                
                    Button b=new Button();
                    b.setLayoutX(40*i);
                    b.setLayoutY(40*j);
                    b.setPrefSize(40, 40);
                    if(!(batt.map[i][j].sign.equals("N"))){
                        b.setText(batt.map[i][j].sign);
                    }
                    int x=i,y=j;
                    b.setOnAction((ActionEvent event) -> {
                        Battle.select(x,y,batt,primaryStage);
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
        
        Scene scene = new Scene(root, 800, 500);
        
        
        primaryStage.setTitle("Re'dekk");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
}
