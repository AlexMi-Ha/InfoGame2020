import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Dieser Gegner kann sich nicht bewegen und greift auch nicht an. Er ist nur für die Boss Fähigkeit
 * "Altar" hier, welche vom Alhoon Boss benutzt wird.
 * 
 * @author Alex 
 * @version 22.6.2020
 */
public class Altar extends Enemy {

    /**
     * Erzeugt einen neuen Altar
     */
    public Altar() {
        // Erzeugt Altar mit 0 damage, 5 Leben, nicht flugfähig, 0 speed und kein Fortbewegungstyp
        super(0,5,false,0, null,"altar",3,0,0, 10);

        //vorläufiges Bild erzeugen
        GreenfootImage img=new GreenfootImage(60,60);
        /*img.setColor(Color.RED);
        img.fill();
        img.setColor(Color.BLACK);
        img.drawString("A", 10, 20);*/
        setImage(img);
    }

    /**
     * Ãœberschreibt die act Methode, weil der Altar sich nicht bewegen muss.
     * Gegner muss trotzdem die animation abspielen und sterben können
     */ 
    public void act() {
        idleAnimation();
        deathCheck(); // Sterben bei 0 leben
    }
        
}
