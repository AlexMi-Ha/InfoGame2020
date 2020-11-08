import greenfoot.*;

/**
 * Klasse für den Schlüssel der gebraucht wird um in den Bossraum zu kommen
 * @author Alex, Daniel
 * @version 24.5.2020
 */
public class Key extends Item {
    private int animIndex= 0;
    private int timer= 0;
    
    /**
     *  Gibt die gewollte Textur zurück.
     *  @return GreenfootImage mit der Textur
     *  Autor: Daniel
     */
    public GreenfootImage loadTexture() {
        GreenfootImage img = new GreenfootImage("items/key/key" + animIndex + ".png");
        img.scale(img.getWidth() * 4, img.getHeight() * 4);
        return img;
    }
    
    /**
     *  Wird vom Spieler aufgerufen sobald er das Item berührt.
     */
    public void onPickUp() {
        // raum event abgeschlossen -> Kein 2. Key soll gespawnt werden
        WorldManager.getCurrentRoom().setRoomEventTriggerd(true);
        WorldManager.getPlayer().setKey(true); // Gib dem Spieler den Key
        
        // Key sound abspielen
        SoundManager.playSound("sounds/pickup_key.wav", SoundManager.getSFXVol());
    }
    
    // Autor: Daniel
    public void act(){
        idleAnimation(); //spielt dauerhaft die Animation ab
    }
    
    /**
     * Spielt die dauerhaft das Auf- und abschweben des Schlüssels ab
     * Autor: Daniel
     */
    private void idleAnimation(){
        timer++;
        if( timer > 20 ){
            setImage(loadTexture());
            animIndex++;
            timer= 0;
            if(animIndex > 3 ){
                animIndex = 0;
            }
        }
    }
}
