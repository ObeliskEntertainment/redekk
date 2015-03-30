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
public class ShipPartSetter {
    static ShipPart part;
    
    static ShipPart setPart(String type) throws IOException{
        Path path = FileSystems.getDefault().getPath("resource", "parts.redekk");
        BufferedReader reader= Files.newBufferedReader(path, StandardCharsets.UTF_8);
        String[] line=reader.readLine().split(":");
        String[] stats=line[0].split(" ");
        String[] units=line[1].split(" ");
        while(!stats[0].equals(type)){
            line=reader.readLine().split(":");
            stats=line[0].split(" ");
            units=line[1].split(" ");
        }
        reader.close();
        int crewcount=0;
        for(int i=0;i<units.length/2;i++){
            crewcount+=Integer.parseInt(units[2*i+1]);
        }
        part=new ShipPart(crewcount);
        part.type=stats[0];
        part.name=stats[1];
        part.powertoenter=Integer.parseInt(stats[2]);
        part.xsize=Integer.parseInt(stats[3]);
        part.ysize=Integer.parseInt(stats[4]);
        part.special=new ShipPartSpecial();
        String[] specialbase=stats[5].split("_");
        switch (specialbase[0]) {
            case "main":
                part.special.type="main";
                part.special.resource="";
                part.special.resourceamount=0;
                break;
            case "supply":
                part.special.type="supply";
                part.special.resource=specialbase[1];
                part.special.resourceamount=0;
                break;
            case "attack":
                part.special.type="attack";
                part.special.resource=specialbase[1];
                part.special.resourceamount=Integer.parseInt(specialbase[2]);
                break;
        }
        part.special.amount=Integer.parseInt(stats[6]);
        part.special.turn=stats[7];
        String currentunit;
        int currentcount,unitsentered=0;
        for(int i=0;i<units.length/2;i++){
            currentunit=units[i*2];
            currentcount=Integer.parseInt(units[i*2+1]);
            for(int j=0;j<currentcount;j++){
                part.crew[unitsentered+j]=UnitSetter.setUnit(currentunit);
            }
            unitsentered+=currentcount;
        }
        return part;
    }
}
