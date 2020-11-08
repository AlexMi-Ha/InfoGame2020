import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Das UI-Element der Healthbar: Eine visuelle Darstellung der aktuellen Lebenspunkte des Spielers.
 * 
 * 
 * @author Daniel, Alex
 * @version 30.05.2020
 */
public class Healthbar extends Actor {   

    private Color color; // Farbe der Healthbar

    public Healthbar(int width, int height, Color color){
        this.color = color;

        GreenfootImage img=new GreenfootImage(width,height);

        img.setColor(new Color( 0, 0, 0, 190));
        img.fill();

        img.setColor(color);
        img.fillRect(0, 0, width, height);
        setImage(img);
    }

    /**
     * Aktualisieren der Healthbar, falls der Spieler Schaden nimmt oder Leben regeneriert
     */
    public void updateHealthBar(int health, int max_health){
        GreenfootImage img=getImage();
        img.clear();
        img.setColor(new Color( 0, 0, 0, 190));
        img.fill();

        img.setColor(color);
        // healthbar um 'health' / 'maxhealth' prozent füllen
        img.fillRect(0, 0, (int)(((double)health /(double)max_health)*img.getWidth()), img.getHeight());

        setImage(img);
    }
}
