import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Klasse für die Storyanzeige zu Beginn, Ist das erste, was man beim ersten Öffnen des Spiels
 * sieht
 * 
 * @author Anja 
 * @version 08.06.2020
 */
public class StoryScreen extends World
{

    public StoryScreen()
    {    
        super(1000, 640, 1); 
        getBackground().setColor(Color.BLACK);
        getBackground().fill();
        
        
        addObject(new Story(), getWidth() / 2, getHeight() / 2);
        //legt fest, dass die Story zukünftig nicht mehr abgespielt werden soll
        StartMenu.setStory(false);
    }
}

