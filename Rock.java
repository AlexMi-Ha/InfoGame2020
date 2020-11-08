import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Klasse für die Hindernisse in Räumen, Steinen.
 * @author Daniel
 * @version 6.6.2020
 */
public class Rock extends Collider
{
    public Rock(int texture) {
        // Für fliegende Gegner ist der Stein "überfliegbar"
        super(false);

        // Wählt aus den verfügbaren Variationen aus
        GreenfootImage img = new GreenfootImage("images/environment/rocks/rock" + texture + ".png");
        img.scale(getImage().getWidth()* 3, getImage().getHeight()* 3);
        setImage(img);
    }
}
