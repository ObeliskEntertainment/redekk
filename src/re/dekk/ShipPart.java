/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package re.dekk;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rasamog
 */
public class ShipPart {
    Unit[] crew;
    String type,name;
    int xsize,ysize,powertoenter;
    ShipPartSpecial special;
    
    ShipPart(int count){
        crew=new Unit[count];
    }
    
    ShipPart(String ptype){
        try {
            ShipPart set=ShipPartSetter.setPart(ptype);
            crew=set.crew;
            type=set.type;
            xsize=set.xsize;
            ysize=set.ysize;
        } catch (IOException ex) {
            Logger.getLogger(ShipPart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
