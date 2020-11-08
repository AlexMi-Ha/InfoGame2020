import greenfoot.*;

/**
 * Superklasse für alle Items, die in den Räumen auf dem Boden liegen können
 * @author Alex
 * @version 17.5.2020
 */
public abstract class Item extends Actor {
    
    /**
     * Konstruktor für die abstrakte Klasse Item.
     * Setzt ein gewolltes Bild.
     */
    public Item() {
        setImage(loadTexture());
    }
    
    /**
     *  Gibt die gewollte Textur zurÃ¼ck. Wird automatisch beim erstellen des Items aufgerufen
     *  @return GreenfootImage mit der Textur
     */
    public abstract GreenfootImage loadTexture();
    /**
     *  Wird vom Spieler aufgerufen sobald er das Item berührt.
     */
    public abstract void onPickUp();
}
