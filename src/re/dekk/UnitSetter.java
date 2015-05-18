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
public class UnitSetter {
    static Unit unit;
    
    static Unit setUnit(String type) throws IOException{
        Path path = FileSystems.getDefault().getPath("resource", "units.redekk");
        BufferedReader reader= Files.newBufferedReader(path, StandardCharsets.UTF_8);
        String[] stats=reader.readLine().split(" ");
        while(!stats[0].equals(type)){
            stats=reader.readLine().split(" ");
        }
        reader.close();
        unit=new Unit();
        unit.sign=stats[0];
        unit.name=stats[1];
        unit.hp=Integer.parseInt(stats[2]);
        unit.stamina=Integer.parseInt(stats[3]);
        unit.size=Integer.parseInt(stats[4]);
        unit.armortype=stats[5];
        unit.armor=Integer.parseInt(stats[6]);
        unit.resistance=Integer.parseInt(stats[7]);
        unit.rangeddmg=Integer.parseInt(stats[8]);
        unit.rangedtype=stats[9];
        unit.range=Integer.parseInt(stats[10]);
        unit.meleedmg=Integer.parseInt(stats[11]);
        unit.meleetype=stats[12];
        unit.maxStamina=unit.stamina;
        unit.owner="AI";
        return unit;
    }
    
}
