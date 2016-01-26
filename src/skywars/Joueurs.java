/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skywars;

import org.bukkit.event.Listener;

/**
 *
 * @author Romuald
 */
public class Joueurs implements Listener {
    private String name;
    
    public Joueurs(String pname) {
        name = pname;
    }
    
    public String getName() {
        return name;
    }
    
}
