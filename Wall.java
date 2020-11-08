import greenfoot.*;

/**
 *  Klasse für den Wand collider, der dafür verantwortlich ist den Spieler innerhalb des
 *  Raums zu halten.
 *  @author Alex
 *  @version 12.5.2020
 */
public class Wall extends Collider {
    
    /**
     * Erstellt eine neue Wand
     * @param width Länge der Wand
     * @param height Breite der Wand
     */
    public Wall(int width, int height) {
        // Für fliegende Gegner ist die Wand massiv
        super(true);
        
        // Zeichne ein transparentes Bild mit den gewünschten Maßen
        GreenfootImage img = new GreenfootImage(width, height);
        
        /* Nur zum Testen
        TODO Code entfernen */
        //img.setColor(Color.RED);
        //img.fill();
        
        setImage(img);
    }
}
