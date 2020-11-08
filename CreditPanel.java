import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * In CreditPanel wird ein Bild angezeigt, auf dem die Credits angezeigt werden
 * 
 * @author Anja 
 * @version 08.06.2020
 */
public class CreditPanel extends Actor
{
    public CreditPanel() {
        GreenfootImage img = new GreenfootImage("menu/credits/background.png");
        img.scale(1000,640);
        setImage(img);
    }  
    
    /**
     * Entfernt den Actor wieder und deaktiviert Tastensperre
     *
     */
    public void act() {
        if(Greenfoot.isKeyDown("escape")) {
            StartMenu.getWorld().setKeyLock(false);
            StartMenu.getWorld().removeObject(this);
        }
    }

}
