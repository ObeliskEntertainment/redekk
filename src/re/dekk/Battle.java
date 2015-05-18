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
import java.util.*;

/**
 *
 * @author rasamog
 */
public class Battle {

    static Unit[][] map;
    static Unit selected=new Unit();
    static int selectedx,selectedy,sizex,sizey;
    static Unit clear=new Unit();
    
    private static void setDistance (int y, int x, int crrDist, int[][] mapTemplate, LinkedList<Integer> queueY, LinkedList<Integer> queueX)
    {
        if (x >= 0 && y >= 0 &&
            x < sizex && y < sizey &&//is the cell inside the map
            mapTemplate[y][x] != -1 &&//is the cell an obstacle
            mapTemplate[y][x] == 0)//is the cell calculated
        {
            mapTemplate[y][x] = crrDist + 1;
            queueX.add(x);
            queueY.add(y);
        }
    }
    private static int calcDisdtance (int targetX, int targetY)//if the path is more complicated
    {
        //ARRAYS ARE ALLWAYS [ROWS(Y)][COLUMS(X)], not [X][Y]
        if (selectedx == targetX && selectedy - 1 == targetY) {return 1;}//if the target is a neighbour cell to the start
        if (selectedx + 1 == targetX && selectedy == targetY) {return 1;}//simply move one cell in the desired direction
        if (selectedx == targetX && selectedy + 1 == targetY) {return 1;}
        if (selectedx - 1 == targetX && selectedy == targetY) {return 1;}
        //===============================================================
        int[][] mapTemplate = new int[sizey][sizex];//mapTemplate - the value in each cell represents the distance from the start to the target - 0 (zero) is not yet found
        LinkedList<Integer> queueX = new LinkedList<Integer>();
        LinkedList<Integer> queueY = new LinkedList<Integer>();
        for (int y = 0; y < sizey; y++)
        {
            for (int x = 0; x < sizex; x++)
            {
                if (map[y][x].isEmpty())
                    mapTemplate[y][x] = 0;
                else
                    mapTemplate[y][x] = -1;//obstacle
            }
        }
        int distance = 0;
        boolean targetFound = false;
        mapTemplate[selectedy][selectedx] = -1;
        setDistance (selectedy - 1, selectedx, 0, mapTemplate, queueY, queueX);//up
        setDistance (selectedy, selectedx + 1, 0, mapTemplate, queueY, queueX);//right
        setDistance (selectedy + 1, selectedx, 0, mapTemplate, queueY, queueX);//down
        setDistance (selectedy, selectedx - 1, 0, mapTemplate, queueY, queueX);//left
        while (!targetFound)
        {
            if (queueX.peek() == targetX && queueY.peek() - 1 == targetY)
                return mapTemplate[queueY.peek()][queueX.peek()] + 1;//if the target is a neighbour cell to the start, simply move one cell in the desired direction
            if (queueX.peek() + 1 == targetX && queueY.peek() == targetY)
                return mapTemplate[queueY.peek()][queueX.peek()] + 1;
            if (queueX.peek() == targetX && queueY.peek() + 1 == targetY)
                return mapTemplate[queueY.peek()][queueX.peek()] + 1;
            if (queueX.peek() - 1 == targetX && queueY.peek() == targetY)
                return mapTemplate[queueY.peek()][queueX.peek()] + 1;
            setDistance (queueY.peek() - 1, queueX.peek(),//neighbour cell to check //up
                    mapTemplate[queueY.peek()][queueX.peek()],//how far is the current cell from the start
                    mapTemplate, queueY, queueX);//map template data
            setDistance (queueY.peek(), queueX.peek() + 1, mapTemplate[queueY.peek()][queueX.peek()], mapTemplate, queueY, queueX);//right
            setDistance (queueY.peek() + 1, queueX.peek(), mapTemplate[queueY.peek()][queueX.peek()], mapTemplate, queueY, queueX);//down
            setDistance (queueY.peek(), queueX.peek() - 1, mapTemplate[queueY.peek()][queueX.peek()], mapTemplate, queueY, queueX);//left
            queueX.remove();
            queueY.remove();
        }
        return distance;
    }
    
    private static void select(int x,int y,Battle batt,Stage primaryStage) {
        if(selected.owner.equals("none")){
            if(batt.map[x][y].owner.equals("Re'dekk")){
                selected=batt.map[x][y];
                selectedx=x;
                selectedy=y;
            }
        }else{
            if(batt.map[x][y].owner.equals("none"))
            {
                //player controlled unit move
                selected.resetStamina();
                int distance = calcDisdtance(x ,y);//if the path is more complicated
                if(distance <= selected.stamina){
                    batt.map[x][y] = selected;
                    batt.map[selectedx][selectedy] = clear;
                    selected.lowerStamina(distance);//if there are any reasons to lower stamina, use this method
                    selected = clear;
                    reload(primaryStage,batt);
                }
                else
                {
                    selected=clear;
                }
            }
            else if(batt.map[x][y].owner.equals("Re'dekk")){
                selected=clear;
            }
            else if(batt.map[x][y].owner.equals("AI"))
            {
                if((Math.abs(selectedx-x)+Math.abs(selectedy-y))==1)
                {
                    batt.map[x][y]=selected.hit(batt.map[x][y], false);
                    if(batt.map[x][y].hp<=0)
                    {
                        batt.map[x][y]=clear;
                        reload(primaryStage,batt);
                    }
                }
                else if((Math.abs(selectedx-x)+Math.abs(selectedy-y))<=selected.range)
                {
                    batt.map[x][y]=selected.hit(batt.map[x][y], true);
                    if(batt.map[x][y].hp<=0)
                    {
                        batt.map[x][y]=clear;
                        reload(primaryStage,batt);
                    }
                }
            }
        }
    }
    
    static void reload(Stage primaryStage,Battle batt)
    {
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
