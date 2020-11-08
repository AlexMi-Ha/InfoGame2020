import greenfoot.*;

/**
 * Collider Superklasse. Diese wird benutzt um alle Collider wie Steine oder Wände
 * gebündelt als Collider ansprechen zu können.
 * Außerdem bestimmt sie, ob ein Gegner über einen Collider drüberfliegen kann.
 * 
 * Ein Actor ist im Collider sobald getOneIntersectingObject(Collider.class) nicht null ist.
 * @author Alex
 * @version 12.5.2020
 */
public abstract class Collider extends Actor {
    protected boolean solidToFlying;
    
    /**
     * Konstruktor der abstrakten Klasse Collider
     * @param solidToFlying Ist der Collider fest für fliegende Gegner?
     */
    public Collider(boolean solidToFlying) {
        this.solidToFlying = solidToFlying;
    }
    
    /**
     * Gibt zurück ob der Collider 
     * @return Boolean ob ein fliegender Gegner Ã¼ber diesen Collider drÃ¼ber fliegen kann
     */
    public boolean isSolidToFlying() {
        return solidToFlying;
    }
}
