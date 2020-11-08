import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Klasse fÃ¼r ein Herz-Item, welches die Lebenspunkte des Spielers erhöht.
 * @author Daniel, Alex
 * @version 6.6.2020
 */
public class Heart extends Item
{   //Attribute zu durchlaufen der Animation
    private int animIndex= 0;
    private int timer= 0;

    /**
     *  Gibt die gewollte Textur zurÃ¼ck.
     *  @return GreenfootImage mit der Textur
     */
    public GreenfootImage loadTexture() {
        GreenfootImage img = new GreenfootImage("items/heart/heart" + animIndex + ".png");
        img.scale((int)(img.getWidth() * 2.7),(int) (img.getHeight() * 2.7));
        return img;
    }

    /**
     *  Wird vom Spieler aufgerufen sobald er das Item berÃ¼hrt.
     *  Autor: Alex
     */
    public void onPickUp() {
        WorldManager.getPlayer().setHealth(WorldManager.getPlayer().getHealth() + 15); //Spieler Lebenspunkte werden erhöht
        // Raumevent abgeschlossen-> Kein zweites Herz muss gespawnt werden
        WorldManager.getCurrentRoom().setRoomEventTriggerd(true);

        // Abspielen des Herzen sounds
        SoundManager.playSound("sounds/pickup_heart.wav", SoundManager.getSFXVol());
    }

    public void act(){
        idleAnimation(); //spielt dauerhaft die Animation ab
    }

    /**
     * Spielt die dauerhaft das Auf- und abschweben des Herzens ab
     */
    private void idleAnimation(){
        timer++;
        if( timer > 12 ){
            setImage(loadTexture());
            animIndex++;
            timer= 0;
            if(animIndex > 3 ){
                animIndex = 0;
            }
        }
    }
}
