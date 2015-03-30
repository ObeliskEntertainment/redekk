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
public class ShipSetter {
    static Ship ship;
    
    static Ship setShip(String type) throws IOException{
        Path path = FileSystems.getDefault().getPath("resource", "ships.redekk");
        BufferedReader reader= Files.newBufferedReader(path, StandardCharsets.UTF_8);
        String[] line=reader.readLine().split(":");
        String[] stats=line[0].split(" ");
        String[] parts=line[1].split(" ");
        while(!stats[0].equals(type)){
            line=reader.readLine().split(":");
            stats=line[0].split(" ");
            parts=line[1].split(" ");
        }
        reader.close();
        ship=new Ship(Integer.parseInt(stats[2]),Integer.parseInt(stats[3]));
        ship.type=stats[0];
        ship.name=stats[1];
        ship.mass=Integer.parseInt(stats[4]);
        for(int i=0;i<parts.length/3;i++){
            ship.parts[Integer.parseInt(parts[i*3+1])][Integer.parseInt(parts[i*3+2])]
                    =ShipPartSetter.setPart(parts[i*3]);
        }
        
        return ship;
    }
    
    static int shipX(String type) throws IOException{
        Path path = FileSystems.getDefault().getPath("resource", "ships.redekk");
        BufferedReader reader= Files.newBufferedReader(path, StandardCharsets.UTF_8);
        String[] stats=reader.readLine().split(":")[0].split(" ");
        while(!stats[0].equals(type)){
            stats=reader.readLine().split(" ");
        }
        reader.close();
        return Integer.parseInt(stats[2]);
    }
    
    static int shipY(String type) throws IOException{
        Path path = FileSystems.getDefault().getPath("resource", "ships.redekk");
        BufferedReader reader= Files.newBufferedReader(path, StandardCharsets.UTF_8);
        String[] stats=reader.readLine().split(":")[0].split(" ");
        while(!stats[0].equals(type)){
            stats=reader.readLine().split(" ");
        }
        reader.close();
        return Integer.parseInt(stats[3]);
    }
}
