import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Klasse f�r die Storyanzeige zu Beginn, Ist das erste, was man beim ersten �ffnen des Spiels
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
        //legt fest, dass die Story zuk�nftig nicht mehr abgespielt werden soll
        StartMenu.setStory(false);
    }
}

