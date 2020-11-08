import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * Zeigt das Bild an auf dem die Story geschrieben steht und zeigt es an indem die Transparenz
 * langsam erhöht wird
 * 
 * @author Anja 
 * @version 08.06.2020
 */
public class Story extends Actor
{
    
     public Story() {
         GreenfootImage img=new GreenfootImage("menu/story.png");
         img.scale(1000,640);
         img.setTransparency(0); //Bild ist am Anfang transparent
        setImage(img);
     }

    public void act() 
    {
        //erhöhe zuerst Transparenz-Variable um 2
        int t = getImage().getTransparency() + 2;

        //Öffnet das eigentliche Spiel wenn Enter gedrückt wird
        if(Greenfoot.isKeyDown("enter")) {
            
            openGame();
        }
        //bricht Methode ab, wenn Tranzparenz fast 255 ist
        if(t >= 250) { //Transparency darf nicht höher als 255 sein
            return;
        }
        

        GreenfootImage img = getImage();
        img.setTransparency(t); //erhöht Transparenz des Bilds nur wenn es noch möglich ist
        setImage(img);
    }

    public void openGame() {                
        Greenfoot.setWorld(new StartMenu());
    }

    
}
