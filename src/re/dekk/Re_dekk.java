/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package re.dekk;

/**
 *
 * @author rasamog
 */
public class Re_dekk {
    Unit[] units;
    int mass,shield,armor,power,powergeneration;
    
    Re_dekk(){
        Unit drone=new Unit();
        drone.armor=0;
        drone.armortype="solid";
        drone.hp=5;
        drone.meleedmg=1;
        drone.meleetype="kinetic";
        drone.name="drone";
        drone.owner="Re'dekk";
        drone.range=2;
        drone.rangeddmg=1;
        drone.rangedtype="kinetic";
        drone.resistance=0;
        drone.sign="D";
        drone.size=1;
        drone.stamina=1;
        units=new Unit[2];
        units[0]=drone;
        units[1]=drone;
        mass=10;
        shield=0;
        armor=0;
        power=1;
        powergeneration=1;
    }
}
