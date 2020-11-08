import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Platziert Minimap
 * 
 * @author Sebi
 * @version 01.06.2020
 */
public class Minimap extends Actor
{
    public void updateImage(GreenfootImage img){
        img.scale(110, 110);
        setImage(img);
    } 
}
