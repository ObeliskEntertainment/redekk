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

/**
 *
 * @author rasamog
 */
public class LevelSetter {
    static Level level;
    
    static Level setLevel(int l) throws IOException{
        Path path = FileSystems.getDefault().getPath("resource", "levels.redekk");
        BufferedReader reader= Files.newBufferedReader(path, StandardCharsets.UTF_8);
        String line=reader.readLine();
        String[] stats=line.split(":")[0].split(" ");
        String[] shipsstats=line.split(":")[1].split(" ");
        while(!stats[0].equals(Integer.toString(l))){
            line=reader.readLine();
            stats=line.split(":")[0].split(" ");
            shipsstats=line.split(":")[1].split(" ");
        }
        
        level=new Level(Integer.parseInt(stats[1]),Integer.parseInt(stats[2]));
        for(int i=0;i<shipsstats.length/3;i++){
            level.map[Integer.parseInt(shipsstats[i*3+1])][Integer.parseInt(shipsstats[i*3+2])]=new Ship(shipsstats[i*3]);
        }
        
        return level;
    }
    
    static int levelX(int l) throws IOException{
        Path path = FileSystems.getDefault().getPath("resource", "levels.redekk");
        BufferedReader reader= Files.newBufferedReader(path, StandardCharsets.UTF_8);
        String line=reader.readLine();
        String[] stats=line.split(":")[0].split(" ");
        while(!stats[0].equals(Integer.toString(l))){
            line=reader.readLine();
            stats=line.split(":")[0].split(" ");
        }
        return Integer.parseInt(stats[1]);
    }
    
    static int levelY(int l) throws IOException{
        Path path = FileSystems.getDefault().getPath("resource", "levels.redekk");
        BufferedReader reader= Files.newBufferedReader(path, StandardCharsets.UTF_8);
        String line=reader.readLine();
        String[] stats=line.split(":")[0].split(" ");
        while(!stats[0].equals(Integer.toString(l))){
            line=reader.readLine();
            stats=line.split(":")[0].split(" ");
        }
        return Integer.parseInt(stats[2]);
    }
}
